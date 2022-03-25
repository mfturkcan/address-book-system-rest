package com.mfturkcan.addressbooksystemrest.services;

import com.mfturkcan.addressbooksystemrest.dtos.ControllerResponse;
import com.mfturkcan.addressbooksystemrest.models.BookUser;
import com.mfturkcan.addressbooksystemrest.repositories.BookUserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest
class BookUserServiceTest {

    // Arrange -> create initials, mock methods
    // Act -> Call method, take result
    // Assert -> Assert expected and result

    @Autowired
    private BookUserService bookUserService;

    @MockBean
    private BookUserRepository bookUserRepository;

    @Test
    void isUserExists_Exists_True() {
        // Arrange
        String username = "test";
        Optional<BookUser> bookUser = Optional.ofNullable(BookUser.builder()
                .username(username)
                .build());
        when(bookUserRepository.findByUsername(username)).thenReturn(bookUser);

        // Act
        boolean isUserExists = bookUserService.isUserExists(username);

        // Assert
        assertTrue(isUserExists);
    }

    @Test
    void isUserExists_NonExists_False() {
        // Arrange
        String username = "test";
        when(bookUserRepository.findByUsername(username)).thenReturn(Optional.empty());

        // Act
        boolean isUserExists = bookUserService.isUserExists(username);

        // Assert
        assertFalse(isUserExists);
    }

    @Test
    void getAll_EmptyList_ZeroSize() {
        // Arrange
        String sortVariable = "name";
        int page = 0;
        Pageable pageable = PageRequest.of(page, 5, Sort.by(sortVariable));
        when(bookUserRepository.findAll(pageable)).thenReturn(Page.empty());

        // Act
        int size = bookUserService.getAll(page, sortVariable).size();

        // Assert
        assertEquals(0, size);
    }

    @Test
    void getAll_NonEmptyList_NonZeroSize() {
        // Arrange
        String sortVariable = "name";
        int page = 0;
        Pageable pageable = PageRequest.of(page, 5, Sort.by(sortVariable));
        List<BookUser> users = List.of(
                BookUser.builder().name("1").build(),
                BookUser.builder().name("2").build(),
                BookUser.builder().name("3").build(),
                BookUser.builder().name("4").build()
                ) ;

        Page<BookUser> books_page = new PageImpl<BookUser>(users, pageable, users.size());
        when(bookUserRepository.findAll(pageable)).thenReturn(books_page);

        // Act
        int size = bookUserService.getAll(page, sortVariable).size();

        // Assert
        assertEquals(4, size);
    }



    @Test
    void getCount_EmptyList_ReturnZero() {
        // Arrange
        when(bookUserRepository.findAll()).thenReturn(List.of());

        // Act
        int size = (Integer) ((ControllerResponse)bookUserService.getCount().getBody()).getMessage();

        // Assert
        assertEquals(0, size);
    }

    @Test
    void getCount_FilledList_ReturnSize() {
        // Arrange
        when(bookUserRepository.findAll()).thenReturn(List.of(new BookUser(), new BookUser()));

        // Act
        int size = (Integer) ((ControllerResponse)bookUserService.getCount().getBody()).getMessage();

        // Assert
        assertEquals(2, size);
    }

    @Test
    void findUserByUsername_Contains_ReturnUser() throws Exception {
        // Arrange
        String username = "test";
        var user = BookUser.builder().username("test").build();
        when(bookUserRepository.findByUsername(username)).thenReturn(Optional.of(user));

        // Act
        var result = bookUserService.findUserByUsername(username);

        // Assert
        assertEquals(user, result);
    }

    @Test
    void findUserByUsername_Empty_ThrowException() throws Exception {
        // Arrange
        String username = "test";
        when(bookUserRepository.findByUsername(username)).thenReturn(Optional.empty());

        // Act, Assert
        assertThrows(Exception.class, ()-> bookUserService.findUserByUsername(username));
    }


    // List of user with find by department
    @Test
    void findUsersByDepartment_Contains_ReturnUserList() throws Exception {
        // Arrange
        String department = "test";
        var users = List.of(BookUser.builder().department("test").build());
        when(bookUserRepository.findByDepartmentIgnoreCaseContaining(department)).thenReturn(Optional.of(users));

        // Act
        var result = bookUserService.findUsersByDepartment(department);

        // Assert
        assertEquals(users, result);
    }

    @Test
    void findUsersByDepartment_EmptyList_ThrowException() throws Exception {
        // Arrange
        String department = "test";
        when(bookUserRepository.findByDepartmentIgnoreCaseContaining(department)).thenReturn(Optional.empty());

        // Act, Assert
        assertThrows(Exception.class, ()-> bookUserService.findUsersByDepartment(department));
    }

    @Test
    void findUsersByName_Contains_ReturnUserList() throws Exception {
        // Arrange
        String name = "test";
        var users = List.of(BookUser.builder().name(name).build());
        when(bookUserRepository.findByNameIgnoreCaseContaining(name)).thenReturn(Optional.of(users));

        // Act
        var result = bookUserService.findUsersByDepartment(name);

        // Assert
        assertEquals(users, result);
    }

    @Test
    void findUsersByName_EmptyList_ThrowException() throws Exception {
        // Arrange
        String name = "test";
        when(bookUserRepository.findByDepartmentIgnoreCaseContaining(name)).thenReturn(Optional.empty());

        // Act, Assert
        assertThrows(Exception.class, ()-> bookUserService.findUsersByDepartment(name));
    }

    @Test
    void getBookUserByUsername() {
    }

    @Test
    void addBookUser() {
    }

    @Test
    void updateUser() {
    }

    @Test
    void deleteUserByUsername() {
    }
}