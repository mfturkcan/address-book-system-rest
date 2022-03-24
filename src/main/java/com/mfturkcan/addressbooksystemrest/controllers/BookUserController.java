package com.mfturkcan.addressbooksystemrest.controllers;

import com.mfturkcan.addressbooksystemrest.models.BookUser;
import com.mfturkcan.addressbooksystemrest.services.BookUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/book_user")
public class BookUserController {
    private final BookUserService bookUserService;

    @Autowired
    public BookUserController( BookUserService bookUserService){
        this.bookUserService = bookUserService;
    }

    @GetMapping()
    public List<BookUser> getAll(
            @RequestParam(defaultValue = "0",name = "page") int page,
            @RequestParam(defaultValue = "name", name = "sort") String sort)
    {
        return bookUserService.getAll(page, sort);
    }

    @GetMapping("/size")
    public ResponseEntity<?> getSize(){
        return bookUserService.getCount();
    }

    @GetMapping(path = "{username}")
    public ResponseEntity<?> getBookUser(@PathVariable String username){
        return bookUserService.getBookUserByUsername(username);
    }

    @PostMapping
    public ResponseEntity<?> addUser(@RequestBody BookUser bookUser){
        return bookUserService.addBookUser(bookUser);
    }

    @PatchMapping(path = "{username}")
    public ResponseEntity<?> updateUser(@RequestBody BookUser bookUserDto, @PathVariable String username, Authentication authentication){
        return bookUserService.updateUser(bookUserDto, username, authentication);
    }

    @DeleteMapping("{username}")
    public ResponseEntity<?> removeBookUser(@PathVariable String username){
        return bookUserService.deleteUserByUsername(username);
    }
}