package com.example.rediscacheapp.service;

import com.example.rediscacheapp.model.Book;

import java.util.List;

public interface BookService {
    Book findBookById(long bookId);

    List<Book> findBooks();

    Book saveBook(Book book);

    Book updateBook(long bookId, Book book);

    long deleteBook(long bookId);
}
