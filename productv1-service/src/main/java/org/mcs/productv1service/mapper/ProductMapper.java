package org.mcs.productv1service.mapper;

import org.mapstruct.*;
import org.mcs.productv1service.dto.ProductRequestDto;
import org.mcs.productv1service.dto.ProductResponseDto;
import org.mcs.productv1service.model.Product;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.List;

@Mapper(componentModel = "spring", imports = {Instant.class, BigDecimal.class})
public interface ProductMapper {

    @Mapping(target = "createdDt", defaultExpression = "java(Instant.now().toString())", dateFormat = "dd/mm/yy/hh/mm/ss")
    @Mapping(target = "updatedDt", defaultExpression = "java(Instant.now().toString())", dateFormat = "dd/mm/yy/hh/mm/ss")
    ProductResponseDto mapProductToProductResponse(Product product);

    @InheritConfiguration
    List<ProductResponseDto> mapProductsToProductResponse(List<Product> products);

    @Mapping(target = "createdDt", expression = "java(Instant.now())", dateFormat = "dd/mm/yy/hh/mm/ss")
    @Mapping(target = "updatedDt", expression = "java(Instant.now())", dateFormat = "dd/mm/yy/hh/mm/ss")
    Product mapProductRequestToProduct(ProductRequestDto productRequestDto);


    @Mapping(target = "name", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.SET_TO_DEFAULT)
    @Mapping(target = "description", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.SET_TO_DEFAULT)
    @Mapping(target = "price", expression = "java(BigDecimal.valueOf(productRequestDto.getPrice()))")
    @Mapping(target = "updatedDt", expression = "java(Instant.now())", dateFormat = "dd/mm/yy/hh/mm/ss")
    Product updateProduct(@MappingTarget Product product, ProductRequestDto productRequestDto);
}
