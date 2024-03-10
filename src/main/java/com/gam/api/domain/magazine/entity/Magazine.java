package com.gam.api.domain.magazine.entity;

import com.gam.api.domain.user.entity.MagazineScrap;
import com.gam.api.common.entity.superclass.TimeStamped;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Type;

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

    @Type(type = "string-array")
    @Column(name = "magazine_photos",
            columnDefinition = "varchar(800)[]")
    private String[] magazine_photos;

    @OneToMany(mappedBy = "magazine", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Question> questions = new ArrayList<>();

    @OneToMany(mappedBy = "magazine")
    private List<MagazineScrap> magazineScraps = new ArrayList<>();

    public void scrapCountUp(){ this.scrapCount += 1; }

    public void scrapCountDown(){ this.scrapCount -= 1; }

    @Builder
    public Magazine(String thumbNail, String magazineTitle, String introduction, String interviewPerson) {
        this.thumbNail = thumbNail;
        this.magazineTitle = magazineTitle;
        this.introduction = introduction;
        this.interviewPerson = interviewPerson;
        this.viewCount = 0L;
        this.scrapCount = 0;
    }
}
