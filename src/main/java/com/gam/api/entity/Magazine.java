package com.gam.api.entity;

import com.gam.api.entity.superclass.TimeStamped;
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
public class Magazine extends TimeStamped {

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

    @OneToMany(mappedBy = "magazine")
    private List<MagazineScrap> magazineScraps = new ArrayList<>();

    public void scrapCountUp(){ this.scrapCount += 1; }

    public void scrapCountDown(){ this.scrapCount -= 1; }
}
