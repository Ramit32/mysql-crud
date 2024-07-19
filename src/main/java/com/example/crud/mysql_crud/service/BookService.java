package com.example.crud.mysql_crud.service;

import com.example.crud.mysql_crud.entity.Book;
import com.example.crud.mysql_crud.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
public class BookService{

    @Autowired
    private BookRepository bookRepository;

    public Optional<Book> getBookById(Long id) {
        Optional<Book> optionalBook = bookRepository.findById(id);
        if (optionalBook.isPresent() && !optionalBook.get().getIsDeleted())
            return optionalBook;
        else {
            throw new RuntimeException("Book not found or has been deleted");
        }
    }

    public List<Book> getAllBooks() {
        List<Book> allBooks =bookRepository.findAll();
        List<Book> books =new ArrayList<>();
        for(Book book:allBooks){
            if(!book.getIsDeleted()){
                books.add(book);
            }
        }
        return books;
    }

    public Book save(Book book){
//        book.setId(createUniqueId(book));
       return bookRepository.save(book);
    }

    public Book update(Book book){
        if(bookRepository.existsById(book.getId())){
           return bookRepository.save(book);
        }else{
            throw new RuntimeException("Book cannot be updated. id not Found");
        }

    }

    public void deleteBook(Long id){
        if(bookRepository.existsById(id)){
            bookRepository.deleteById(id);
        }else{
            throw new RuntimeException("The book with given id doesn't exist");
        }

    }

    public void softDelete(Book book){
            book.setIsDeleted(true);
            bookRepository.save(book);
    }
    public Book undoDelete(Book book){
        book.setIsDeleted(false);
        return bookRepository.save(book);
    }


    public Long createUniqueId(Book book) {
        Long id = 0L;
        Random random = new Random();
        return book.getTitle().length() + book.getAuthor().length() + random.nextLong(0, 9999999);
    }
}
