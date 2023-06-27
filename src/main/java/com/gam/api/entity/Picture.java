package com.gam.api.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

import static javax.persistence.GenerationType.IDENTITY;

@Getter
@Setter
@NoArgsConstructor
@Table(name = "\"Picture\"")
@Entity
public class Picture {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "picture_id")
    private Long id;

    @Column(name = "url")
    private String url;

    @ManyToOne
    @JoinColumn(name = "work_id")
    private Work work;

}
