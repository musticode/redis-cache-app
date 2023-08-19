package com.example.rediscacheapp.service.impl;

import com.example.rediscacheapp.model.Book;
import com.example.rediscacheapp.repository.BookRepository;
import com.example.rediscacheapp.service.BookService;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookServiceImpl implements BookService {
    private final BookRepository bookRepository;

    public BookServiceImpl(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Override
    @Cacheable(value = "book", key = "#bookId")
    public Book findBookById(long bookId) {
        longRunningMethod();
        return bookRepository
                .findById(bookId)
                .orElseThrow(
                        ()-> new RuntimeException("No book in repo")
                );
    }

    @Override
    @Cacheable("books")
    public List<Book> findBooks() {
        longRunningMethod();
        return bookRepository.findAll();
    }

    @Override
    public Book saveBook(Book book) {
        return bookRepository.save(Book.builder()
                        .name(book.getName())
                        .author(book.getAuthor())
                        .year(book.getYear())
                        .isPublished(book.isPublished())
                .build()
        );
    }

    @Override
    @CachePut(value = "book", key = "#bookId")
    public Book updateBook(long bookId, Book book) {
        longRunningMethod();
        Book foundBook = bookRepository.findById(bookId).orElseThrow(()-> new RuntimeException("No book"));
        foundBook.setName(book.getName());
        foundBook.setAuthor(book.getAuthor());
        foundBook.setYear(book.getYear());
        foundBook.setPublished(book.isPublished());

        return bookRepository.save(foundBook);
    }

    @Override
    @CacheEvict(value = "book", key="#bookId")
    public long deleteBook(long bookId) {
        Book foundBook = bookRepository.findById(bookId).orElseThrow(()-> new RuntimeException("No book"));
        long foundBookId = foundBook.getId();
        bookRepository.delete(foundBook);
        return foundBookId;
    }
    private void longRunningMethod(){
        try {
            Thread.sleep(5000);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
