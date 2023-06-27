package com.gam.api.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

import static javax.persistence.GenerationType.IDENTITY;


@Getter
@Setter
@NoArgsConstructor
@Table(name = "\"UserScrap\"")
@Entity
public class UserScrap {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "scrap_id")
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "status")
    private boolean status;

    @Column(name = "owner_id")
    private Long ownerId;
}
