package com.mfturkcan.addressbooksystemrest.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Component
public class BookUserDetail implements UserDetails {

    private BookUser bookUser;
    private List<GrantedAuthority> authorityList = new ArrayList<>();

    public BookUserDetail(BookUser bookUser){
        this.bookUser = bookUser;
        this.authorityList.add(new SimpleGrantedAuthority("ROLE_" + bookUser.getDepartment().toUpperCase()));
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.authorityList;
    }

    @Override
    public String getPassword() {
        return this.bookUser.getPassword();
    }

    @Override
    public String getUsername() {
        return this.bookUser.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}