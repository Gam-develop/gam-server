package com.gam.api.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

import static javax.persistence.GenerationType.IDENTITY;

@Getter
@Setter
@NoArgsConstructor
@Table(name = "\"WorkScrap\"")
@Entity
public class WorkScrap {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "scrap_id")
    private Long id;

    @Column(name = "status")
    private boolean status;

    @Column(name = "owner_id")
    private Long ownerId;

    @OneToOne
    @JoinColumn(name = "work_id")
    private Work work;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

}
