package com.mfturkcan.addressbooksystemrest.controllers;

import com.mfturkcan.addressbooksystemrest.dtos.AuthenticationResponse;
import com.mfturkcan.addressbooksystemrest.dtos.BookUserDto;
import com.mfturkcan.addressbooksystemrest.dtos.ControllerResponse;
import com.mfturkcan.addressbooksystemrest.models.BookUser;
import com.mfturkcan.addressbooksystemrest.repositories.BookUserRepository;
import com.mfturkcan.addressbooksystemrest.services.BookUserService;
import com.sun.jdi.event.ExceptionEvent;
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
    private final BookUserService bookUserService;

    @Autowired
    public BookUserController(BookUserRepository bookUserRepository, BookUserService bookUserService){
        this.bookUserRepository = bookUserRepository;
        this.bookUserService = bookUserService;
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
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ControllerResponse(HttpStatus.NOT_FOUND.toString(), e.getMessage()));
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
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ControllerResponse(HttpStatus.NOT_FOUND.toString(), e.getMessage()));
        }
    }

    @PatchMapping(path = "{id}")
    public ResponseEntity updateUser(@RequestBody BookUserDto bookUserDto, @PathVariable int id, Authentication authentication){

        try{
            BookUser user = bookUserService.findUserByUsername(authentication.getName());

            if(authentication.getAuthorities().contains("ROLE_HUMAN_RESOURCES") || user.getId() == id){
                bookUserService.updateUser(user, bookUserDto);
                return ResponseEntity.ok().build();
            }
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();

        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ControllerResponse(HttpStatus.NOT_FOUND.toString(), e.getMessage()));
        }
    }

    @DeleteMapping("{id}")
    public ResponseEntity removeBookUser(@PathVariable int id){
        bookUserRepository.deleteById(id);
        return ResponseEntity.ok().build();
    }
}