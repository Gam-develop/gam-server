package com.gam.api.entity;

import com.gam.api.entity.superclass.TimeStamped;
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
@Table(name = "\"UserScrap\"")
public class UserScrap extends TimeStamped {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "user_scrap_id")
    private Long id;

    @Column(name = "status")
    private boolean status;

    @Column(name = "target_id")
    private Long targetId;
  
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Builder
    public UserScrap(User user, Long targetId) {
            this.user = user;
            this.status = true;
            this.targetId = targetId;
    }

    public UserScrap setScrapStatus(boolean status){
        this.status = status;
        return this;
    }
}
