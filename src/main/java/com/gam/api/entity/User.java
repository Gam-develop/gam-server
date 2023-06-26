package com.gam.api.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import javax.persistence.*;
import static javax.persistence.GenerationType.IDENTITY;
@Entity
public class User {

    @GeneratedValue(strategy = IDENTITY)
    @Id
    private Long id;
}
