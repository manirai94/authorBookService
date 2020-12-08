package org.bookms.service.impl;

import com.google.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.bookms.dao.AuthorDAO;
import org.bookms.dao.BookDAO;
import org.bookms.model.Author;
import org.bookms.model.Book;
import org.bookms.service.BookService;

import java.util.List;

@Slf4j
public class BookServiceImpl implements BookService {

    private final BookDAO bookDAO;
    private final AuthorDAO authorDAO;

    @Inject
    public BookServiceImpl(BookDAO bookDAO, AuthorDAO authorDAO) {
        this.bookDAO = bookDAO;
        this.authorDAO = authorDAO;
    }

    @Override
    public List<Book> findBookForAuthor(int id) {
        return bookDAO.findBookForAuthor(id);
    }

    @Override
    public Book findById(int id) {
        return bookDAO.findById(id);
    }

    @Override
    public Book save(Book bookObj,int id) {
        Author authorObj = authorDAO.findById(id);
        bookObj.setAuthor(authorObj);
        return bookDAO.save(bookObj);
    }

    @Override
    public List<Book> getListOfBooks() {
        return bookDAO.getListOfBooks();
    }
}
