package org.bookms.service;

import org.bookms.model.Author;

import java.util.List;

public interface AuthorService {

    Author findById(int id);

    List<Author> findAll();

    Author create(Author author);
}
