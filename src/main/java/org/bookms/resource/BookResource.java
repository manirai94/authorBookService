package org.bookms.resource;

import com.codahale.metrics.annotation.Timed;
import com.google.inject.Inject;
import io.dropwizard.hibernate.UnitOfWork;
import org.bookms.model.Book;
import org.bookms.service.BookService;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("/book")
@Produces(MediaType.APPLICATION_JSON)
public class BookResource {

    private final BookService bookService;

    @Inject
    public BookResource(BookService bookService) {
        this.bookService = bookService;
    }

    @GET
    @Timed
    @UnitOfWork
    @Path("/{id}")
    public List<Book> findBookOfAuthor(@PathParam("id") int id) {

        return bookService.findBookForAuthor(id);
    }

    @GET
    @Timed
    @UnitOfWork
    @Path("/findAll")
    public List<Book> findAllBooks() {

        return bookService.getListOfBooks();
    }

    @POST
    @Timed
    @UnitOfWork
    @Path("/save/{id}")
    public Book save( @PathParam("id") int id,Book book) {
        Book bookObj = bookService.save(book,id);

        return bookObj;
    }
}
