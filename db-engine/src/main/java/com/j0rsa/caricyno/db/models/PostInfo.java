package com.j0rsa.caricyno.db.models;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "post")
@Data
public class PostInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    private Integer integrationId;

    @NotNull
    private Boolean isPublished = false;

    @NotNull
    private Boolean isIgnored = false;

    public Boolean isPosted() {
        return isPublished;
    }

    public Boolean isIgnored() {
        return isIgnored;
    }
}
