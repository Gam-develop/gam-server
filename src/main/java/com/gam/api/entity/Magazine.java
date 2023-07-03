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
@Table(name = "\"Magazine\"")
public class Magazine {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "magazine_id")
    private Long id;

    @Column(name = "thumb_nail")
    private String thumbNail;

    @Column(name = "title")
    private String magazineTitle;

    @Column(name = "introduction")
    private String introduction;

    @Column(name = "interview_person")
    private String interviewPerson;

    @Column(name = "view_count")
    private Long viewCount;

    @Column(name = "scrap_count")
    private int scrapCount;

    @OneToMany(mappedBy = "magazine")
    private List<MagazinePhoto> magazinePhotos = new ArrayList<>();

    @OneToMany(mappedBy = "magazine")
    private List<Question> questions = new ArrayList<>();

    @OneToOne(mappedBy = "magazine")
    private MagazineScrap magazineScraps;

}
