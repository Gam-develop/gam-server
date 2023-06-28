package com.gam.api.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

import java.util.ArrayList;
import java.util.List;

import static javax.persistence.GenerationType.IDENTITY;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Magazine {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "magazine_id")
    private Long id;

    @Column(name = "contents")
    private String content;

    @Column(name = "view_count")
    private int viewCount;

    @OneToMany(mappedBy = "magazine")
    private List<MagazineScrap> magazineScraps = new ArrayList<>();
}
