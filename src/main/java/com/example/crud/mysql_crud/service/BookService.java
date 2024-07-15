package com.example.crud.mysql_crud.service;

import com.example.crud.mysql_crud.entity.Book;
import com.example.crud.mysql_crud.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService{

    @Autowired
    private BookRepository bookRepository;

    public Book getBookById(Long id){
        return bookRepository.findById(id).orElse(null);//JPA method
    }
    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }
}
