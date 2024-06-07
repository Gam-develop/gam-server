package com.gam.api.domain.user.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.gam.api.domain.block.entity.Block;
import com.gam.api.domain.report.entity.Report;
import com.gam.api.domain.work.entity.Work;
import com.gam.api.common.entity.superclass.TimeStamped;
import io.hypersistence.utils.hibernate.type.array.IntArrayType;
import java.util.stream.Collectors;
import lombok.*;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.Where;
import org.hibernate.annotations.Type;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import javax.persistence.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


import static javax.persistence.GenerationType.IDENTITY;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "\"User\"")
@TypeDef(name = "int-array", typeClass = IntArrayType.class)
public class User extends TimeStamped {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "user_id")
    private Long id;

    @Column(name = "user_name")
    private String userName;

    @Column(name = "user_status")
    @Enumerated(EnumType.STRING)
    private UserStatus userStatus;

    @Column(name = "role")
    @Enumerated(EnumType.STRING)
    private Role role;

    @Column(name = "info")
    private String info;

    @Column(name = "detail")
    private String detail;

    @Column(name = "email")
    private String email;

    @Column(name = "behance")
    private String behanceLink;

    @Column(name = "instagram")
    private String instagramLink;

    @Column(name = "notion")
    private String notionLink;

    @Column(name = "scrap_count")
    private int scrapCount;

    @Column(name = "view_count")
    private int viewCount;

    @Column(name = "work_thumb_nail")
    private String workThumbNail;

    @Column(name = "first_work_id")
    private Long firstWorkId;

    @Column(name = "device_token")
    private String deviceToken;

    @Column(name = "magazine_view_count")
    private int magazineViewCount;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Block> blocks;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<UserTag> userTag;

    @Where(clause = "status = true")
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    List<UserScrap> userScraps = new ArrayList<>();

    @Where(clause = "status = true")
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    List<MagazineScrap> magazineScraps = new ArrayList<>();

    @Where(clause = "is_active = true")
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    List<Work> works = new ArrayList<>();

    @OneToMany(mappedBy = "targetUser", cascade = CascadeType.ALL) // 내가 신고 당한 'Report' 리스트
    private List<Report> reported;

    @OneToMany(mappedBy = "reportUser") // 내가 신고한 'Report' 리스트
    private List<Report> reports;

    @Type(type = "int-array")
    @Column(name = "tag",
            columnDefinition = "integer[]")
    private int[] tags;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:SS")
    @Column(name = "selected_first_at")
    private LocalDateTime selectedFirstAt;

    @OneToMany(mappedBy = "user")
    private List<UserDeleteAccountReason> userDeleteAccountReason;

    public void updateSelectedFirstAt() {
        this.selectedFirstAt = LocalDateTime.now();
    }

    @Builder
    public User(Role role, UserStatus userStatus) {
        this.role = role;
        this.userStatus = userStatus;
        this.scrapCount = 0;
        this.viewCount = 0;
        this.magazineViewCount = 0;
    }

    public List<Work> getActiveWorks() {
        return this.getWorks()
                .stream()
                .filter(Work::isActive)
                .collect(Collectors.toList());
    }

    public void updateUserStatus(UserStatus userStatus) {
        this.userStatus = userStatus;
    }

    public void updateDeviceToken(String deviceToken) {
        this.deviceToken = deviceToken;
    }

    public void scrapCountUp() {
        this.scrapCount += 1;
    }

    public void scrapCountDown() {
        this.scrapCount -= 1;
    }

    public void magazineViewCountUp() {
        this.magazineViewCount += 1;
    }

    public void onboardUser(String userName, String info, int[] tags) {
        this.userName = userName;
        this.info = info;
        this.tags = tags;
    }

    public void deleteLinks() {
        this.behanceLink = null;
        this.instagramLink = null;
        this.notionLink = null;
    }

}

