package com.ntnghia.bookmanagement.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "\"user\"")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column(unique = true)
    @NotNull
    @NotEmpty
    @Email
    private String email;

    @Column
    @NotNull
    @NotEmpty
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    @Column
    private String firstName;

    @Column
    private String lastName;

    @Column(columnDefinition = "boolean default true")
    @Builder.Default
    private Boolean enabled = true;

    @Column
    private String avatar;

    @ManyToOne(targetEntity = Role.class)
    private Role role;
}
