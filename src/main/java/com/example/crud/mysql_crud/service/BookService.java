package com.example.crud.mysql_crud.service;

import com.example.crud.mysql_crud.entity.Book;
import com.example.crud.mysql_crud.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
public class BookService{

    @Autowired
    private BookRepository bookRepository;

    public Optional<Book> getBookById(Long id){
        if(bookRepository.existsById(id)){
            return  bookRepository.findById(id);
        }else{
            throw new RuntimeException("Book not found");
        }
    }
    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    public Book save(Book book){
//        book.setId(createUniqueId(book));
       return bookRepository.save(book);
    }

    public Book update(Book book){
        if(bookRepository.existsById(book.getId())){
           return bookRepository.save(book);
        }else{
            throw new RuntimeException("Book cannot be uodated. Id not Found");
        }

    }

    public void deleteBook(Long id){
        if(bookRepository.existsById(id)){
            bookRepository.deleteById(id);
        }else{
            throw new RuntimeException("The book with given id doesnot exist");
        }

    }
    public Long createUniqueId(Book book) {
        Long id = 0L;
        Random random = new Random();
        return book.getTitle().length() + book.getAuthor().length() + random.nextLong(0, 9999999);
    }

}
