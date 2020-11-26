package com.devonfw.mtsj.gateway.general.common.impl.security;

import java.util.*;

import javax.inject.Inject;
import javax.inject.Named;

import com.devonfw.mtsj.gateway.general.common.api.security.UserData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.devonfw.module.security.common.api.accesscontrol.AccessControl;
import com.devonfw.module.security.common.api.accesscontrol.AccessControlProvider;
import com.devonfw.module.security.common.base.accesscontrol.AccessControlGrantedAuthority;

/**
 * Custom implementation of {@link UserDetailsService}.<br>
 *
 * @see com.devonfw.mtsj.gateway.general.service.impl.config.BaseWebSecurityConfig
 */
//@Named
public class BaseUserDetailsService implements UserDetailsService {

  /** Logger instance. */
  private static final Logger LOG = LoggerFactory.getLogger(BaseUserDetailsService.class);

  private AuthenticationManagerBuilder amBuilder;

  private AccessControlProvider accessControlProvider;

  /** Mock Userdata */

  public ArrayList<UserDetails> fillUserList() {
    ArrayList<UserDetails> users = new ArrayList<>();
    users.add(new UserData("cook", "cook", Collections.singleton(new SimpleGrantedAuthority("ADMIN"))));
    users.add(new UserData("user", "password", Collections.singleton(new SimpleGrantedAuthority("USER"))));
    return users;
  }

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

    ArrayList<UserDetails> userList = fillUserList();

    for(UserDetails user : userList) {
      if (user.getUsername().equals(username)){
        return user;
      }
    }

    UsernameNotFoundException exception = new UsernameNotFoundException("Authentication failed");
    LOG.warn("Failed to get user {}.", username);
    throw exception;

    /*
    Set<GrantedAuthority> authorities = getAuthorities(username);
    UserDetails user;
    try {
      user = getAmBuilder().getDefaultUserDetailsService().loadUserByUsername(username);
      User userData = new User(user.getUsername(), user.getPassword(), authorities);
      return userData;
    } catch (Exception e) {
      e.printStackTrace();
      UsernameNotFoundException exception = new UsernameNotFoundException("Authentication failed.", e);
      LOG.warn("Failed to get user {}.", username, exception);
      throw exception;
    }
    */
  }

  /**
   * @param username the login of the user
   * @return the associated {@link GrantedAuthority}s
   * @throws AuthenticationException if no principal is retrievable for the given {@code username}
   */
  protected Set<GrantedAuthority> getAuthorities(String username) throws AuthenticationException {

    Objects.requireNonNull(username, "username");
    // determine granted authorities for spring-security...
    Set<GrantedAuthority> authorities = new HashSet<>();
    Collection<String> accessControlIds = getRoles(username);
    Set<AccessControl> accessControlSet = new HashSet<>();
    for (String id : accessControlIds) {
      boolean success = this.accessControlProvider.collectAccessControls(id, accessControlSet);
      if (!success) {
        LOG.warn("Undefined access control {}.", id);
      }
    }
    for (AccessControl accessControl : accessControlSet) {
      authorities.add(new AccessControlGrantedAuthority(accessControl));
    }
    return authorities;
  }

  private Collection<String> getRoles(String username) {

    Collection<String> roles = new ArrayList<>();
    // TODO for a reasonable application you need to retrieve the roles of the user from a central IAM system
    roles.add(username);
    return roles;
  }

  /**
   * @return amBuilder
   */
  public AuthenticationManagerBuilder getAmBuilder() {

    return this.amBuilder;
  }

  /**
   * @param amBuilder new value of {@link #getAmBuilder}.
   */
  @Inject
  public void setAmBuilder(AuthenticationManagerBuilder amBuilder) {

    this.amBuilder = amBuilder;
  }

  /**
   * @return accessControlProvider
   */
  public AccessControlProvider getAccessControlProvider() {

    return this.accessControlProvider;
  }

  /**
   * @param accessControlProvider new value of {@link #getAccessControlProvider}.
   */
  @Inject
  public void setAccessControlProvider(AccessControlProvider accessControlProvider) {

    this.accessControlProvider = accessControlProvider;
  }
}
