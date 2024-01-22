package com.gam.api.entity;

import com.gam.api.entity.superclass.TimeStamped;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

import java.util.List;

import static javax.persistence.GenerationType.IDENTITY;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "\"WithdrawalReason\"")
public class DeleteAccountReason extends TimeStamped {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "withdrawal_reason_id")
    private int id;

    @Column(name = "withdrawal_reason_name")
    private String tagName;

    @OneToMany(mappedBy = "deleteAccountReason")
    private List<UserDeleteAccountReason> userDeleteAccountReasons;
}
