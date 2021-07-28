package com.ntnghia.bookmanagement.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.Date;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "book")
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column
    @NotNull
    @NotEmpty
    private String title;

    @Column
    @NotNull
    @NotEmpty
    private String author;

    @Column(columnDefinition="TEXT")
    private String description;

    @Temporal(TemporalType.TIMESTAMP)
    @NotNull
    @NotEmpty
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date created_at;

    @Temporal(TemporalType.TIMESTAMP)
    @NotNull
    @NotEmpty
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updated_at;

    @Column
    private String image;

    @Column
    private Boolean enabled;

    @ManyToOne(targetEntity = User.class)
    private User user;
}
