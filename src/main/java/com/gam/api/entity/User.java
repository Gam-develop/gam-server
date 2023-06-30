package com.gam.api.entity;

import lombok.*;
import org.hibernate.annotations.Where;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.GenerationType.IDENTITY;


@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "\"User\"")
public class User {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "user_id")
    private Long id;

    @Column(name = "user_name")
    private String userName;

    @Column(name = "role")
    @Enumerated(EnumType.STRING)
    private Role role;

    @Column(name = "info")
    private String info;

    @Column(name = "detail")
    private String detail;

    @Column(name = "email")
    private String email;

    @Column(name= "behance")
    private String behance;

    @Column(name = "instagram")
    private String instagram;

    @OneToOne
    @JoinColumn(name = "user_tag_id")
    private UserTag userTag;

    @OneToOne
    @JoinColumn(name= "filter_tag_id")
    private FilterTag filterTag;

    @Column(name = "additional_link")
    private String additionalLink;

    @Column(name = "scarp_count")
    private int scrapCount;

    @Column(name = "view_count")
    private int viewCount;

    @Column(name = "refresh_token")
    private String refreshToken;

    @Where(clause = "status = true")
    @OneToMany(mappedBy = "userScrap")
    List<UserScrap> userScraps = new ArrayList<>();

    @OneToMany(mappedBy = "userWork")
    List<Work> works = new ArrayList<>();

    @Builder
    public User(Role role) {
        this.role = role;
        this.scrapCount = 0;
        this.viewCount = 0;
    }

    public void updateRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }
    public void scrapCountUp(){ this.scrapCount += 1; }
    public void scrapCountDown(){ this.scrapCount -= 1; }


}


