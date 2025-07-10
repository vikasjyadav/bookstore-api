package com.vikas.bookstore.controller;

import com.vikas.bookstore.model.Author;
import com.vikas.bookstore.repository.AuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/authors")
public class AuthorController {

    @Autowired
    private AuthorRepository authorRepo;

    @GetMapping
    public List<Author> getAllAuthors() {
        return authorRepo.findAll();
    }

    @PostMapping
    public Author createAuthor(@RequestBody Author author) {
        return authorRepo.save(author);
    }

    @GetMapping("/{id}")
    public Author getAuthorById(@PathVariable Long id) {
        return authorRepo.findById(id).orElse(null);
    }

    @PutMapping("/{id}")
    public Author updateAuthor(@PathVariable Long id, @RequestBody Author updated) {
        Author author = authorRepo.findById(id).orElse(null);
        if (author != null) {
            author.setName(updated.getName());
            return authorRepo.save(author);
        }
        return null;
    }

    @DeleteMapping("/{id}")
    public void deleteAuthor(@PathVariable Long id) {
        authorRepo.deleteById(id);
    }
}
