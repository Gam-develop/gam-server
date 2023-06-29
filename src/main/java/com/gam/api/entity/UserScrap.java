package com.gam.api.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

import static javax.persistence.GenerationType.IDENTITY;


@Getter
@Setter
@NoArgsConstructor
@Entity
public class UserScrap {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "user_scrap_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User userScrap;

    @Column(name = "status")
    private boolean status;

    @Column(name = "target_id")
    private Long targetId;

    @Builder
    public UserScrap(User userScrap, Long targetId) {
            this.userScrap = userScrap;
            this.status = true;
            this.targetId = targetId;
    }

    public UserScrap setScrapStatus(boolean status){
        this.status = status;
        return this;
    }

}
