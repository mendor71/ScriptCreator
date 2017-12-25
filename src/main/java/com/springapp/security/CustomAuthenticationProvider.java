package com.springapp.security;

import com.springapp.appcfg.AppProperties;
import com.springapp.entity.Role;
import com.springapp.entity.User;
import com.springapp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class CustomAuthenticationProvider implements AuthenticationProvider {

    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private AppProperties appProperties;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String userName = authentication.getPrincipal().toString();
        String password = authentication.getCredentials().toString();

        return authByUserNameAndPassword(userName, password);
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return  aClass.equals(UsernamePasswordAuthenticationToken.class);
    }

    private UsernamePasswordAuthenticationToken authByUserNameAndPassword(String userName, String password) {
        List<User> userList = userRepository.findByUserLogin(userName);

        if (userList.size() == 0) {
            throw new BadCredentialsException("1000");
        }

        User user = userList.get(0);

        if (!passwordEncoder.matches(password, user.getUserPassword())) {
            throw new BadCredentialsException("1000");
        }

        if (user.getUserStateId().getStateName().equals(appProperties.getUserDisabledState())) {
            throw new DisabledException("1001");
        }

        Set<GrantedAuthority> roles = new HashSet<GrantedAuthority>();
        for (Role r: user.getUserRolesList()) {
            roles.add(new SimpleGrantedAuthority(r.getRoleName()));
        }
        return new UsernamePasswordAuthenticationToken(userName, password, roles);
    }


    public PasswordEncoder getPasswordEncoder() {
        return passwordEncoder;
    }

    public void setPasswordEncoder(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }
}
