package com.mfturkcan.addressbooksystemrest.services;

import com.mfturkcan.addressbooksystemrest.dtos.ControllerResponse;
import com.mfturkcan.addressbooksystemrest.models.BookUser;
import com.mfturkcan.addressbooksystemrest.repositories.BookUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class BookUserService {
    private final BookUserRepository bookUserRepository;
    private static int PAGE_SIZE = 5;

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

    public List<BookUser> getAll(int page, String sorting){
        Sort sort = Sort.by(sorting);
        Pageable pageable = PageRequest.of(page, PAGE_SIZE, sort);
        System.out.println(pageable);
        return bookUserRepository.findAll(pageable).getContent();
    }

    public ResponseEntity<?> getCount(){
        int count = bookUserRepository.findAll().size();

        return ResponseEntity.ok(new ControllerResponse(HttpStatus.OK.toString(), count));
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

    public ResponseEntity<?> getBookUserByUsername(String username){
        BookUser bookUser;
        try{
            bookUser = bookUserRepository.findByUsername(username).orElseThrow(()-> new Exception("User not found with username:"+ username));

            return ResponseEntity.ok(bookUser);
        }catch (Exception e){
            System.out.println(e.getMessage());
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ControllerResponse(HttpStatus.NOT_FOUND.toString(), e.getMessage()));
        }
    }

    public ResponseEntity<?> addBookUser(BookUser bookUser){
        try{
            bookUserRepository.save(bookUser);
            return ResponseEntity.ok().build();
        }catch (Exception e){
            System.out.println(e.getMessage());
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ControllerResponse(HttpStatus.NOT_FOUND.toString(), e.getMessage()));
        }
    }

    public ResponseEntity<?> updateUser(BookUser bookUserDto,String username, Authentication authentication){
        try{
            BookUser user = this.findUserByUsername(bookUserDto.getUsername());
            var isEqual = authentication.getAuthorities().toArray()[0].toString().equals("ROLE_HUMAN RESOURCES");
            if(isEqual || username.equals(authentication.getName())){
                System.out.println("change");
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
                return ResponseEntity.ok().build();
            }
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();

        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ControllerResponse(HttpStatus.NOT_FOUND.toString(), e.getMessage()));
        }
    }

    public ResponseEntity<?> deleteUserByUsername(String username){
        try{
            bookUserRepository.deleteByUsername(username);
            return ResponseEntity.ok().build();
        }
        catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }
}