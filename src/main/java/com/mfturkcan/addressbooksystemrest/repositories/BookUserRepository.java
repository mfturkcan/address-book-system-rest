package com.mfturkcan.addressbooksystemrest.repositories;

import com.mfturkcan.addressbooksystemrest.models.BookUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookUserRepository extends JpaRepository<BookUser, Integer> {
    Optional<BookUser> findByUsername(String username);
    Optional<List<BookUser>> findByDepartmentIgnoreCaseContaining(String department);

//    @Query("Select user from book_user user where user.name like %:name%")
    Optional<List<BookUser>> findByNameIgnoreCaseContaining(String name);

    void deleteByUsername(String username);
}