package com.gam.api.service.user;

import com.gam.api.common.message.ExceptionMessage;
import com.gam.api.entity.GamUserDetails;
import com.gam.api.repository.AuthProviderRepository;
import com.gam.api.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    private final UserRepository userRepository;
    private final AuthProviderRepository authProviderRepository;

    @Override
    public UserDetails loadUserByUsername(String userId) {
        List<GrantedAuthority> authorities = new ArrayList<>();
        val user = userRepository.findById(Long.parseLong(userId))
                .orElseThrow(() -> new EntityNotFoundException(ExceptionMessage.NOT_FOUND_USER.getMessage()));

        val userRole = user.getRole();
        val userStatus = user.getUserStatus();

        val authUser = authProviderRepository.searchAuthProviderByUser_Id(user.getId())
                .orElseThrow(() -> new EntityNotFoundException(ExceptionMessage.NOT_FOUND_USER.getMessage()));

        val authUserId = String.valueOf(authUser.getId());

        authorities.add(new SimpleGrantedAuthority(userRole.toString()));
        authorities.add(new SimpleGrantedAuthority(userStatus.toString()));

        return GamUserDetails.builder()
                .id(user.getId())
                .authUserId(authUserId)
                .username(user.getUserName())
                .authorities(authorities)
                .build();
    }
}