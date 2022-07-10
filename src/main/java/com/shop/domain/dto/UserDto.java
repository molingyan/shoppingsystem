package com.shop.domain.dto;

import com.shop.domain.pojo.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Set;
@AllArgsConstructor
@Data
@NoArgsConstructor
public class UserDto {
    private String userId;
    private String userName;
    private String phone;
    private String email;
    private int state;
   private List<String> roles;
}
