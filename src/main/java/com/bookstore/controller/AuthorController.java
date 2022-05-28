package com.bookstore.controller;

import com.bookstore.dto.AuthorDto;
import com.bookstore.entity.Author;
import com.bookstore.exception.NotFoundException;
import com.bookstore.service.AuthorService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class AuthorController {
    private final AuthorService authorService;
    private final ModelMapper modelMapper;
    @GetMapping("api/bookstore/authors/{author}")
    public ResponseEntity<AuthorDto> getAuthor(@PathVariable String author){
        Optional<Author> authorInDB = authorService.findAuthor(author);
        if (!(authorInDB.isPresent())){
            throw new NotFoundException("no such author found");
        }
        AuthorDto authorDto = modelMapper.map(authorInDB.get(), AuthorDto.class);
        return ResponseEntity.ok(authorDto);

    }
}
