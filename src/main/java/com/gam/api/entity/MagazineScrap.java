package com.gam.api.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

import static javax.persistence.GenerationType.IDENTITY;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class MagazineScrap {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "magazine_scrap_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "magazine_id")
    private Magazine magazine;

    @Column(name = "thumb_nail")
    private String thumbNail;

    @Column(name = "author")
    private String author;

    @Column(name = "title")
    private String title;

    @Column(name = "status")
    private boolean status;

    @Column(name = "owner_id")
    private Long owner_id;
}
