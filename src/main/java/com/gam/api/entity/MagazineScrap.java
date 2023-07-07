package com.gam.api.entity;

import com.gam.api.entity.superclass.TimeStamped;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

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

    public Long getMagazineId() {
        return this.magazine.getId();
    }
}
