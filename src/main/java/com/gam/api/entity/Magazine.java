package com.gam.api.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

import static javax.persistence.GenerationType.IDENTITY;

@Getter
@Setter
@NoArgsConstructor
@Table(name = "\"Magazine\"")
@Entity
public class Magazine {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "magazine_id")
    private Long id;

    @Column(name = "contents")
    private String content;

}
