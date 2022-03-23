package com.mfturkcan.addressbooksystemrest.services;

import com.mfturkcan.addressbooksystemrest.dtos.BookUserDto;
import com.mfturkcan.addressbooksystemrest.models.BookUser;
import com.mfturkcan.addressbooksystemrest.repositories.BookUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
            BookUser user = bookUserRepository.findByUsername(username).orElseThrow(() -> new Exception("User not found"));

            return true;
        }
        catch(Exception e){
            System.out.println(e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    @Transactional
    public void addUser(BookUser bookUser){
        bookUserRepository.save(bookUser);
    }

    public BookUser findUserByUsername (String username) throws Exception{
        return  bookUserRepository.findByUsername(username).orElseThrow(() -> new Exception("User not found with username : "+ username));
    }

    public List<BookUser> findUsersByDepartment(String department){
        return bookUserRepository.findByDepartmentIgnoreCaseContaining(department).orElse(new ArrayList<>());
    }

    public List<BookUser> findUsersByName(String name){
        return bookUserRepository.findByNameIgnoreCaseContaining(name).orElse(new ArrayList<>());
    }

    public void updateUser(BookUser user, BookUser bookUserDto){
        user.setDepartment(bookUserDto.getDepartment());
        user.setEmail(bookUserDto.getEmail());
        user.setName(bookUserDto.getName());
        user.setUsername(bookUserDto.getUsername());
        user.setOfficeNo(bookUserDto.getOfficeNo());
        user.setPhoneNumber(bookUserDto.getPhoneNumber());
        user.setPosition(bookUserDto.getPosition());
        user.setRole(bookUserDto.getRole());

        user.getTimeTable().clear();
        user.getTimeTable().addAll(bookUserDto.getTimeTable());


        bookUserRepository.save(user);
    }
}