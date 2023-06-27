package com.gam.api.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

import static javax.persistence.GenerationType.IDENTITY;

@Getter
@Setter
@NoArgsConstructor
@Table(name = "\"AuthenticationProvider\"")
@Entity
public class AuthenticationProvider {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name="provider_type")
    @Enumerated(EnumType.STRING)
    private ProviderType providerType;

}
