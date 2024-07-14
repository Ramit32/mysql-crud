package com.example.crud.mysql_crud.repository;

import com.example.crud.mysql_crud.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Long> {
}
