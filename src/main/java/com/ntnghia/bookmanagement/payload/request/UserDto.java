package com.ntnghia.bookmanagement.payload.request;

import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {
    private int id;

    @NotNull
    @NotEmpty
    @Email
    private String email;

    private String password;

    private String firstName;

    private String lastName;

    @Builder.Default
    private Boolean enabled = true;

    private String avatar;

    private RoleDto role;
}
