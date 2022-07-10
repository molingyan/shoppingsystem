package com.shop.domain.dto;

import lombok.Data;

import java.util.List;

@Data
public class TypeDto {
    private String typeId;
    private String typeName;
    private String typeParent;
    private int typeState;
    private int typeLevel;
    private List<TypeDto> typeDtos;
}
