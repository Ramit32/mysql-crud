package com.example.crud.mysql_crud.controller;

import com.example.crud.mysql_crud.entity.Book;
import com.example.crud.mysql_crud.repository.BookRepository;
import com.example.crud.mysql_crud.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/books")
public class BookController {

    private final BookRepository bookRepository;

    @Autowired
    public BookController(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }
    @Autowired
    private BookService bookService;

    @GetMapping("/all")
    public ResponseEntity<List<Book>> getAllBooks() {
        return new ResponseEntity<>(bookService.getAllBooks(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getBookById(@PathVariable Long id) {
        Optional<Book> book;

        try{
            book =bookService.getBookById(id);
        }catch(Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(book, HttpStatus.OK);
    }

    @PostMapping("/saveBook")
    public ResponseEntity<Book> saveBook(@RequestBody Book book) {
        return new ResponseEntity<Book>(bookService.save(book),HttpStatus.CREATED);
    }


    @PutMapping("/updateBook")
    public ResponseEntity<?> updateBook(@RequestBody Book book) {
        try{
            return new ResponseEntity<>(bookService.update(book),HttpStatus.OK);
        }catch(Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteBook(@PathVariable Long id) {
        try{
            bookService.deleteBook(id);
        }catch(Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>("the entity has been deleted",HttpStatus.OK);
    }
}