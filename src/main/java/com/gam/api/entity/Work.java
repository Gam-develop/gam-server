package com.gam.api.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

import static javax.persistence.GenerationType.IDENTITY;

@Getter
@Setter
@NoArgsConstructor
@Table(name = "\"Work\"")
@Entity
public class Work {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "work_id")
    private Long id;

    @Column(name = "status")
    private String status;

    @Column(name = "owner_id")
    private Long ownerId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;
}
