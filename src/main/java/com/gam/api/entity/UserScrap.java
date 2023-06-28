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
}
