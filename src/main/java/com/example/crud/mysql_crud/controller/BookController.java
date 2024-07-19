package com.example.crud.mysql_crud.controller;

import com.example.crud.mysql_crud.entity.Book;
import com.example.crud.mysql_crud.repository.BookRepository;
import com.example.crud.mysql_crud.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
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


    @GetMapping("/all")//READING
    public ResponseEntity<List<Book>> getAllBooks() {
        return new ResponseEntity<>(bookService.getAllBooks(), HttpStatus.OK);
    }
    @GetMapping("/{id}")//READING
    public ResponseEntity<?> getBookById(@PathVariable Long id) {
        try{
            Optional<Book> book =bookService.getBookById(id);
            if(book.isPresent()){
                return new ResponseEntity<Optional<Book>>(book, HttpStatus.OK);
            }else{
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
        }catch(Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }
    @PostMapping("/saveBook")//CREATING
    public ResponseEntity<Book> saveBook(@RequestBody Book book) {
        return new ResponseEntity<Book>(bookService.save(book),HttpStatus.CREATED);
    }
    @PutMapping("/updateBook")//UPDATING
    public ResponseEntity<?> updateBook(@RequestBody Book book) {
        try{
            return new ResponseEntity<>(bookService.update(book),HttpStatus.OK);
        }catch(Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }
    @DeleteMapping("/delete/{id}")//HARD_DELETE
    public ResponseEntity<String> deleteBook(@PathVariable Long id) {
        try{
            bookService.deleteBook(id);
        }catch(Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>("the entity has been deleted.",HttpStatus.OK);
    }
    @DeleteMapping("/{id}") //SOFT_DELETE
    public ResponseEntity<?> softDeleteBook(@PathVariable Long id){
      try{
          Book books;
          Optional<Book> optionalBook =bookRepository.findById(id);
          if(optionalBook.isPresent()&&!optionalBook.get().getIsDeleted()){
              books=optionalBook.get();
              bookService.softDelete(books);
              return new ResponseEntity<>("id deleted Successfully.",HttpStatus.OK);
          }else{
              return new ResponseEntity<>("id doesn't exist.",HttpStatus.NOT_FOUND);
          }

      }catch(Exception e){
          return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
      }
    }
    @DeleteMapping("/undo/{id}") //UNDO_DELETE
    public ResponseEntity<?> undoDeleteBook(@PathVariable Long id){
        try{
            Book book;
            Optional<Book> optionalBook =bookRepository.findById(id);
            if(optionalBook.isPresent()&&optionalBook.get().getIsDeleted()){
                book=optionalBook.get();
                bookService.undoDelete(book);
               return new ResponseEntity<>("id recovered Successfully.",HttpStatus.OK);
            }else{
                return new ResponseEntity<>("id already exist.",HttpStatus.FOUND);
            }
        }catch(Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}