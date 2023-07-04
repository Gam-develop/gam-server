package com.gam.api.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Setter;
import lombok.Builder;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
@Table(name = "\"AuthProvider\"")
public class AuthProvider {

    @Id
    @Column(name = "auth_provider_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name="provider_type")
    @Enumerated(EnumType.STRING)
    private ProviderType providerType;
}
