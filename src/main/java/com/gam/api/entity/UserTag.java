package com.gam.api.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

import java.util.List;
import java.util.Objects;

import static javax.persistence.GenerationType.IDENTITY;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "\"UserTag\"")
public class UserTag {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "user_tag_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tag_id")
    private Tag tag;

    @Builder
    public UserTag(User user, Tag tag){
        setUser(user);
        this.tag = tag;
    }

    private void setUser(User user) {
        if (Objects.nonNull(this.user)) {
            this.user.getUserTag().remove(this);
        }
        this.user = user;
        user.getUserTag().add(this);
    }
}
