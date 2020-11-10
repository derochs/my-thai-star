package com.devonfw.mtsj.gateway.general.common.impl.security;

import com.devonfw.mtsj.gateway.general.common.api.security.UserData;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import javax.inject.Named;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

@Named
public class MockUserDetailsService implements UserDetailsService {

    //In memory database
    private final ArrayList<UserDetails> USERS = new ArrayList<>(Arrays.asList(
            new UserData("cook", "cook", Collections.
                    singleton(new SimpleGrantedAuthority("ROLE_Waiter"))),
            new UserData("user", "password", Collections.
                    singleton(new SimpleGrantedAuthority("ROLE_Customer"))),
            new UserData("waiter", "waiter", Collections.
                    singleton(new SimpleGrantedAuthority("ROLE_Waiter")))));

    public MockUserDetailsService() {}

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return USERS
                .stream()
                .filter(userDetails -> userDetails.getUsername().equals(username))
                .findFirst()
                .get();
    }
}
