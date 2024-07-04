package org.mcs.productv1service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ProductResponseDto {

    private Long id;

    private String name;

    private String description;

    private String createdDt;

    private String updatedDt;

    private Long price;
}
