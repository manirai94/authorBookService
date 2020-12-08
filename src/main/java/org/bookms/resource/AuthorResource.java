package org.bookms.resource;

import com.codahale.metrics.annotation.Timed;
import com.google.inject.Inject;
import io.dropwizard.hibernate.UnitOfWork;
import lombok.extern.slf4j.Slf4j;
import org.bookms.model.Author;
import org.bookms.service.AuthorService;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Slf4j
@Path("/author")
@Produces(MediaType.APPLICATION_JSON)
public class AuthorResource {

    private final AuthorService authorService;

    @Inject
    public AuthorResource(AuthorService authorService) {
        this.authorService = authorService;
    }

    @GET
    @Path("/{id}")
    @Timed
    @UnitOfWork
    public Author findPerson(@PathParam("id") int id) {
        return authorService.findById(id);
    }

    @GET
    @Timed
    @UnitOfWork
    @Path("/findAll")
    public List<Author> findAllAuthors() {

        return authorService.findAll();
    }

    @POST
    @Timed
    @UnitOfWork
    @Path("/save")
    public Author save(Author author) {

        return authorService.create(author);
    }
}
