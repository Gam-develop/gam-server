package com.gam.api.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "\"Block\"")
public class Block {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "block_id")
    private Long id;

    @Column(name = "status")
    private boolean status;

    @Column(name = "target_id")
    private Long targetId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Builder Block(User user, Long targetId) {
        setUser(user);
        this.status = true;
        this.targetId = targetId;
    }

    private void setUser(User user) {
        if (Objects.nonNull(this.user)) {
            this.user.getBlocks().remove(this);
        }
        this.user = user;
        user.getBlocks().add(this);
    }

}
