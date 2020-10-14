package com.devonfw.mtsj.gateway.general.common.api.security;

import com.devonfw.mtsj.gateway.general.common.api.UserProfile;
import com.devonfw.mtsj.gateway.general.common.api.to.UserDetailsClientTo;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;

import java.security.Principal;
import java.util.Collection;

public class UserData extends User implements Principal {

    private UserProfile userProfile;

    public UserData(String username, String password, boolean enabled, boolean accountNonExpired,
                    boolean credentialsNonExpired, boolean accountNonLocked, Collection<? extends GrantedAuthority> authorities) {
        super(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
    }

    public UserData(String username, String password, Collection<? extends GrantedAuthority> authorities) {
        super(username, password, authorities);
    }

    @Override
    public String getName() {
        return getUsername();
    }

    public UserDetailsClientTo toClientTo() {
        UserDetailsClientTo clientTo = new UserDetailsClientTo();
        clientTo.setId(this.userProfile.getId());
        clientTo.setName(this.userProfile.getName());
        clientTo.setFirstName(this.userProfile.getFirstName());
        clientTo.setLastName(this.userProfile.getLastName());
        clientTo.setRole(this.userProfile.getRole());
        return clientTo;
    }

    @Override
    public String toString() {
        return getName();
    }

    public UserProfile getUserProfile() {
        return this.userProfile;
    }

    public void setUserProfile(UserProfile userProfile) {
        this.userProfile = userProfile;
    }

    public static UserData get() {

        return get(SecurityContextHolder.getContext().getAuthentication());
    }

    /**
     * @param authentication is the {@link Authentication} where to retrieve the user from.
     * @return the {@link UserData} of the logged in user from the given {@link Authentication}.
     */
    public static UserData get(Authentication authentication) {

        if (authentication == null) {
            throw new IllegalStateException("Authentication not available!");
        }
        Object principal = authentication.getPrincipal();
        if (principal == null) {
            throw new IllegalStateException("Principal not available!");
        }
        try {
            return (UserData) principal;
        } catch (ClassCastException e) {
            throw new IllegalStateException("Principal (" + principal + ") is not an instance of UserData!", e);
        }
    }
}
