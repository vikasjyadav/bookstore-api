package com.vikas.bookstore.controller;

import com.vikas.bookstore.model.Book;
import com.vikas.bookstore.model.Author;
import com.vikas.bookstore.repository.BookRepository;
import com.vikas.bookstore.repository.AuthorRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

@RestController
@RequestMapping("/api/books")
public class BookController {

    @Autowired
    private BookRepository bookRepo;

    @Autowired
    private AuthorRepository authorRepo;

    // 🔍 Pagination + Sorting
    @GetMapping
    public Page<Book> getAllBooks(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(defaultValue = "id,asc") String[] sort) {

        Sort.Direction direction = Sort.Direction.fromString(sort[1]);
        Pageable pageable = PageRequest.of(page, size, Sort.by(direction, sort[0]));

        return bookRepo.findAll(pageable);
    }

    // 🆕 Create a book
    @PostMapping
    public Book createBook(@RequestBody Book book) {
        return bookRepo.save(book);
    }

    // 🔎 Get by ID
    @GetMapping("/{id}")
    public Book getBookById(@PathVariable Long id) {
        return bookRepo.findById(id).orElse(null);
    }

    // 🛠 Update
    @PutMapping("/{id}")
    public Book updateBook(@PathVariable Long id, @RequestBody Book updated) {
        Book book = bookRepo.findById(id).orElse(null);
        if (book != null) {
            book.setTitle(updated.getTitle());
            book.setPrice(updated.getPrice());
            book.setAuthor(updated.getAuthor());
            return bookRepo.save(book);
        }
        return null;
    }

    // ❌ Delete
    @DeleteMapping("/{id}")
    public void deleteBook(@PathVariable Long id) {
        bookRepo.deleteById(id);
    }
}
