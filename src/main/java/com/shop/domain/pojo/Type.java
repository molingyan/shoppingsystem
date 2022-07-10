package com.shop.domain.pojo;

import lombok.Data;

import java.security.Timestamp;
import java.util.Date;
import java.util.List;

@Data
public class Type {
    private String typeId;
    private String typeName;
    private String typeParent;
    private int typeState;
    private int typeLevel;
    private Date createdDate;
    private Date  updateDate;

}
