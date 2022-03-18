package com.mfturkcan.addressbooksystemrest.controllers;

import com.mfturkcan.addressbooksystemrest.dtos.AuthenticationResponse;
import com.mfturkcan.addressbooksystemrest.dtos.BookUserDto;
import com.mfturkcan.addressbooksystemrest.models.BookUser;
import com.mfturkcan.addressbooksystemrest.repositories.BookUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/book_user")
public class BookUserController {
    private final BookUserRepository bookUserRepository;

    @Autowired
    public BookUserController(BookUserRepository bookUserRepository){
        this.bookUserRepository = bookUserRepository;
    }

    @GetMapping
    public List<BookUser> getAll(){
        return bookUserRepository.findAll();
    }

    @GetMapping(path = "{id}")
    public ResponseEntity<?> getBookUser(@PathVariable int id){
        BookUser bookUser;
        try{
            bookUser = bookUserRepository.findById(id).orElseThrow(()-> new Exception("User not found with id:"+ id));

            return ResponseEntity.ok(bookUser);
        }catch (Exception e){
            System.out.println(e.getMessage());
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    // post user -> if human resource
    @PostMapping
    public ResponseEntity addUser(@RequestBody BookUser bookUser){
        try{
            bookUserRepository.save(bookUser);
            return ResponseEntity.ok().build();
        }catch (Exception e){
            System.out.println(e.getMessage());
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PatchMapping(path = "{id}")
    public ResponseEntity updateUser(@RequestBody BookUserDto bookUser, @PathVariable int id, Authentication authentication){
        BookUser user = bookUserRepository.findByUsername(authentication.getName()).orElseThrow();

        if(user.getId() == id || authentication.getAuthorities().contains("ROLE_HUMAN_RESOURCES")){
            user.setDepartment(bookUser.getDepartment());
            user.setEmail(bookUser.getEmail());
            user.setName(bookUser.getName());
            user.setUsername(bookUser.getUsername());
            user.setOfficeNo(bookUser.getOfficeNo());
            user.setPhoneNumber(bookUser.getPhoneNumber());
            user.setPosition(bookUser.getPosition());

            bookUserRepository.save(user);
            return ResponseEntity.ok().build();
        }

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }
    // update user -> if has same id or human resource

    @DeleteMapping("{id}")
    public ResponseEntity removeBookUser(@PathVariable int id){
        bookUserRepository.deleteById(id);
        return ResponseEntity.ok().build();
    }
    // delete user -> if human resource
}