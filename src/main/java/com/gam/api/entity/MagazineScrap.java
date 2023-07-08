package com.gam.api.entity;

import com.gam.api.entity.superclass.TimeStamped;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

import java.util.Objects;

import static javax.persistence.GenerationType.IDENTITY;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "\"MagazineScrap\"")
public class MagazineScrap extends TimeStamped {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "magazine_scrap_id")
    private Long id;

    @Column(name = "status")
    private boolean status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "magazine_id")
    private Magazine magazine;

    @Builder
    public MagazineScrap(User user, Magazine magazine) {
        setUser(user);
        this.status = true;
        this.magazine = magazine;
    }

    private void setUser(User user) {
        if (Objects.nonNull(this.user)) {
            this.user.getMagazineScraps().remove(this);
        }
        this.user = user;
        user.getMagazineScraps().add(this);
    }

    public Long getMagazineId() {
        return this.magazine.getId();
    }

    public Magazine getMagazine() {
        return this.magazine;
    }

    public MagazineScrap setScrapStatus(boolean status){
        this.status = status;
        return this;
    }
}
