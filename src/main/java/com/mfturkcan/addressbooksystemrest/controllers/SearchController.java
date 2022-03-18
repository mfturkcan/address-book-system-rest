package com.mfturkcan.addressbooksystemrest.controllers;

import com.mfturkcan.addressbooksystemrest.models.BookUser;
import com.mfturkcan.addressbooksystemrest.services.BookUserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path = "/api/search")
public class SearchController {
    public SearchController(BookUserService bookUserService) {
        this.bookUserService = bookUserService;
    }

    private final BookUserService bookUserService;

    @GetMapping(path = "/name/{name}")
    public List<BookUser> getBookUsersByName(@PathVariable String name){
        return bookUserService.findUsersByName(name);
    }

    @GetMapping(path = "/department/{department}")
    public List<BookUser> getBookUsersByDepartment(@PathVariable String department){
        return bookUserService.findUsersByDepartment(department);
    }
}