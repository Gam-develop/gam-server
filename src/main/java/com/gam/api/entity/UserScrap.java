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
@Table(name = "\"UserScrap\"")
public class UserScrap {

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

}
