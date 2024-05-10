package com.gam.api.domain.user.entity;

import com.gam.api.common.entity.superclass.TimeStamped;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import java.util.List;

import static javax.persistence.GenerationType.IDENTITY;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "\"DeleteAccountReason\"")
public class DeleteAccountReason extends TimeStamped {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "delete_reason_id")
    private int id;

    @Column(name = "delete_reason_name")
    private String tagName;

    @OneToMany(mappedBy = "deleteAccountReason")
    private List<UserDeleteAccountReason> userDeleteAccountReasons;
}
