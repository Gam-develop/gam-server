package com.gam.api.entity;

import com.gam.api.entity.superclass.TimeStamped;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

import static javax.persistence.GenerationType.IDENTITY;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "\"UserWithdrawalReason\"")
public class UserDeleteAccountReason extends TimeStamped {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "user_withdrawal_reason_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "withdrawal_reason_id")
    private DeleteAccountReason deleteAccountReason;

    @Column(name = "withdrawal_direct_input")
    private String directInput;

    @Builder
    public UserDeleteAccountReason(User user, DeleteAccountReason deleteAccountReason, String directInput){
        setUser(user);
        this.deleteAccountReason = deleteAccountReason;
        this.directInput = directInput;
    }
}
