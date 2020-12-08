package org.bookms.resource;

import com.squarespace.jersey2.guice.JerseyGuiceUtils;
import io.dropwizard.testing.junit.ResourceTestRule;
import io.dropwizard.testing.junit5.DropwizardExtensionsSupport;
import io.dropwizard.testing.junit5.ResourceExtension;
import org.bookms.model.Author;
import org.bookms.service.AuthorService;
import org.junit.After;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

public class AuthorResourceTest {

    static {
        JerseyGuiceUtils.install((s, serviceLocator) -> null);
    }

    private static final AuthorService authorService = mock(AuthorService.class);
    private ArgumentCaptor<Author> authorCaptor = ArgumentCaptor.forClass(Author.class);
    private Author author;

    public AuthorResourceTest() {
    }

    @ClassRule
    public static final ResourceTestRule RULE=
            ResourceTestRule
                    .builder()
                    .addResource(new AuthorResource(authorService))
                    .build();


    @Before
    public void setUp() {
    author = new Author();
    author.setName("Full Name");
    author.setId(1);

    }

    @After
    public void tearDown() {
        reset(authorService);
    }

@Test
    public void authorById() {
        when(authorService.findById(1)).thenReturn(author);
        Author authorById=
                RULE.target("/author/1")
                        .request(MediaType.APPLICATION_JSON)
                        .get(Author.class);

        assertThat(authorById.getId()).isEqualTo(author.getId());
        verify(authorService).findById(1);
    }

    @Test
    public void authors() {
        Author authorObj1 = new Author();
        Author authorObj2 = new Author();
        List<Author> authorList = new ArrayList<>();
        authorList.add(authorObj1);
        authorList.add(authorObj2);

      when(authorService.findAll()).thenReturn(authorList);
        final List<Author> response = RULE.target("/author/findAll")
                .request(MediaType.APPLICATION_JSON).get(new GenericType<List<Author>>() {
                });
        verify(authorService).findAll();
        assertThat(response).containsAll(authorList);

    }

    @Test
       public void saveAuthor(){
       when(authorService.create(any(Author.class))).thenReturn(author);
       final Response response=RULE.target("/author/save")
        .request(MediaType.APPLICATION_JSON)
               .post(Entity.entity(author,MediaType.APPLICATION_JSON));
        assertThat(response.getStatusInfo()).isEqualTo(Response.Status.OK);
        verify(authorService).create(authorCaptor.capture());
        assertThat(authorCaptor.getValue()).isEqualTo(author);


}

}
