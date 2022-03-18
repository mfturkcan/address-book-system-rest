package com.mfturkcan.addressbooksystemrest.services;

import com.mfturkcan.addressbooksystemrest.models.BookUser;
import com.mfturkcan.addressbooksystemrest.repositories.BookUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class BookUserService {
    private final BookUserRepository bookUserRepository;

    @Autowired
    public BookUserService(BookUserRepository bookUserRepository) {
        this.bookUserRepository = bookUserRepository;
    }


    public boolean isUserExists(String username) {
        try{
            var user = bookUserRepository.findByUsername(username).orElseThrow(() -> new Exception("User not found"));

            return true;
        }
        catch(Exception e){
            System.out.println(e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    public void addUser(BookUser bookUser){
        bookUserRepository.save(bookUser);
    }

    public List<BookUser> findUsersByDepartment(String department){
        return bookUserRepository.findByDepartmentIgnoreCaseContaining(department).orElse(new ArrayList<>());
    }

    public List<BookUser> findUsersByName(String name){
        return bookUserRepository.findByNameIgnoreCaseContaining(name).orElse(new ArrayList<>());
    }
}