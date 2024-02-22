package com.gam.api.domain.work.entity;

import com.gam.api.common.entity.superclass.TimeStamped;
import com.gam.api.domain.user.entity.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.*;
import javax.persistence.*;
import java.util.Objects;
import static javax.persistence.GenerationType.IDENTITY;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "\"Work\"")
public class Work extends TimeStamped {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "work_id")
    private Long id;

    @Column(name = "title")
    private String title;

    @Column(name = "detail")
    private String detail;

    @Column(name = "photo_url")
    private String photoUrl;

    @Column(name = "view_count")
    private int viewCount;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "is_first")
    private boolean isFirst;

    @Column(name = "is_active")
    private boolean isActive;


    @Builder
    public Work(User user, String title, String detail, String photoUrl) {
        setUser(user);
        this.title = title;
        this.detail = detail;
        this.photoUrl = photoUrl;
        this.viewCount = 0;
        this.isFirst = false;
        this.isActive = true;
    }

    public boolean isOwner(Long userId) {
        if(!user.getId().equals(userId)) {
            return false;
        }
        return true;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    private void setUser(User user) {
        if (Objects.nonNull(this.user)) {
            this.user.getWorks().remove(this);
        }
        this.user = user;
        user.getWorks().add(this);
    }

    public void setIsFirst(boolean status){
        this.isFirst = status;
    }
}