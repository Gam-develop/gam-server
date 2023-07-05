package com.gam.api.entity;

import com.gam.api.entity.superclass.TimeStamped;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

import static javax.persistence.GenerationType.IDENTITY;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "\"Tag\"")
public class Tag extends TimeStamped {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "tag_id")
    private int id;

    @Column(name = "tag_name")
    private String tagName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_tag_id")
    private UserTag userTag;
}
