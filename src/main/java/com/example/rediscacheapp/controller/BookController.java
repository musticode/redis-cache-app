package com.example.rediscacheapp.controller;

import com.example.rediscacheapp.model.Book;
import com.example.rediscacheapp.service.BookService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/books")
public class BookController {
    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }
    @PostMapping
    public ResponseEntity<Book> saveBook(@RequestBody Book book){
        return new ResponseEntity<>(bookService.saveBook(book), HttpStatus.CREATED);
    }
    @GetMapping("/{bookId}")
    public ResponseEntity<Book> findBookById(@PathVariable long bookId){
        return new ResponseEntity<>(bookService.findBookById(bookId), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<Book>> findBooks(){
        return new ResponseEntity<>(bookService.findBooks(), HttpStatus.OK);
    }

    @PutMapping("/{bookId}")
    public ResponseEntity<Book> updateBook(@PathVariable long bookId, @RequestBody Book book){
        return new ResponseEntity<>(bookService.updateBook(bookId,book), HttpStatus.OK);
    }

    @DeleteMapping("/{bookId}")
    public ResponseEntity<Long> deleteBook(@PathVariable long bookId){
        return new ResponseEntity<>(bookService.deleteBook(bookId), HttpStatus.OK);
    }

}
