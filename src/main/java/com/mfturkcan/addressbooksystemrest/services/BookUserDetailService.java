package com.mfturkcan.addressbooksystemrest.services;

import com.mfturkcan.addressbooksystemrest.models.BookUser;
import com.mfturkcan.addressbooksystemrest.models.BookUserDetail;
import com.mfturkcan.addressbooksystemrest.repositories.BookUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class BookUserDetailService implements UserDetailsService {
    @Autowired
    private BookUserRepository bookUserRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        BookUser bookUser = bookUserRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("No user found with username: "+username));

        return new BookUserDetail(bookUser);
    }
}