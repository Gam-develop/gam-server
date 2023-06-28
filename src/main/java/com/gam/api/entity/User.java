package com.gam.api.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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

    @Column(name = "user_tag")
    private String userTag;

    @Column(name = "filter_tag")
    private String filterTag;

    @Column(name = "additional_link")
    private String additionalLink;

    @Column(name = "scarp_count")
    private int scrapCount;

    @Column(name = "view_count")
    private int viewCount;

    @OneToMany(mappedBy = "userScrap")
    List<UserScrap> userScraps = new ArrayList<>();

    @OneToMany(mappedBy = "userWork")
    List<Work> works = new ArrayList<>();
}


