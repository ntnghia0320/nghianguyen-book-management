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
@Table(name = "comment")
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column(columnDefinition="TEXT")
    @NotNull
    @NotEmpty
    private String message;

    @ManyToOne(targetEntity = User.class)
    private User user;

    @ManyToOne(targetEntity = Book.class)
    private Book book;

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
}
