package com.ntnghia.bookmanagement.payload.request;

import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RoleDto {
    private int id;

    @NotNull
    @NotEmpty
    private String name;
}
