package org.bookms.resource;

import com.squarespace.jersey2.guice.JerseyGuiceUtils;
import io.dropwizard.testing.junit.ResourceTestRule;
import org.bookms.model.Author;
import org.bookms.model.Book;
import org.bookms.service.BookService;
import org.junit.After;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;
import org.mockito.ArgumentCaptor;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.verify;

public class BookResourceTest {

    static {
        JerseyGuiceUtils.install((s, serviceLocator) -> null);
    }

    private static final BookService bookService = mock(BookService.class);
    private ArgumentCaptor<Book> authorCaptor = ArgumentCaptor.forClass(Book.class);
    private Book book;

    @ClassRule
    public static final ResourceTestRule RULE=
            ResourceTestRule
                    .builder()
                    .addResource(new BookResource(bookService))
                    .build();

    @Before
    public void setUp() {
        book = new Book();
        book.setName("Book Name");
        book.setId(1);

    }

    @After
    public void tearDown() {
        reset(bookService);
    }

    @Test
    public void bookForAuthorId() {
        Book bookObj1 = new Book();
        Book bookObj2 = new Book();
        List<Book> bookList = new ArrayList<>();
        bookList.add(bookObj1);
        bookList.add(bookObj2);
        when(bookService.findBookForAuthor(1001)).thenReturn(bookList);

        final List<Book> response = RULE.target("/book/1001")
                .request(MediaType.APPLICATION_JSON).get(new GenericType<List<Book>>() {
                });

        verify(bookService).findBookForAuthor(1001);
        assertThat(response).containsAll(bookList);
    }

    @Test
    public void findAllBooksTest(){
        Book bookObj1 = new Book();
        Book bookObj2 = new Book();
        List<Book> bookList = new ArrayList<>();
        bookList.add(bookObj1);
        bookList.add(bookObj2);
        when(bookService.getListOfBooks()).thenReturn(bookList);
        final List<Book> response = RULE.target("/book/findAll")
                .request(MediaType.APPLICATION_JSON).get(new GenericType<List<Book>>() {
                });
        verify(bookService).getListOfBooks();
        assertThat(response).containsAll(bookList);
    }

    @Test
    public void saveBook(){
        when(bookService.save(any(Book.class),any(Integer.class))).thenReturn(book);
        final Response response=RULE.target("/book/save/1001")
                .request(MediaType.APPLICATION_JSON)
                .post(Entity.entity(book,MediaType.APPLICATION_JSON));
        assertThat(response.getStatusInfo()).isEqualTo(Response.Status.OK);
        verify(bookService).save(book,1001);
    }
}
