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
@Table(name = "\"Work\"")
public class Work {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "work_id")
    private Long id;

    @Column(name = "thumb_nail")
    private String thumbNail;

    @Column(name = "title")
    private String title;

    @Column(name = "detail")
    private String detail;

    @Column(name = "photo_url")
    private String photoUrl;

    @Column(name = "view_count")
    private int viewCount;

    @OneToOne(mappedBy = "work")
    private Report report;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;
}
