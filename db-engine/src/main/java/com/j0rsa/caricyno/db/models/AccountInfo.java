package com.j0rsa.caricyno.db.models;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "account_info")
@Data
public class AccountInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
}
