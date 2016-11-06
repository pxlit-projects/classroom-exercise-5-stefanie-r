package be.pxl.spring.rest.fallout.author;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AuthorRepositoryIntegrationTest {

    @Autowired
    private AuthorRepository repository;

    @Before
    public void setUp() throws Exception {
        repository.deleteAll();
    }

    @After
    public void tearDown() throws Exception {
        repository.deleteAll();
    }

    @Test
    public void canPersistAnAuthor() throws Exception {
        Author newAuthor = new Author("Jamie");

        repository.save(newAuthor);

        assertThat(repository.findAll()).extracting(Author::getName).containsOnly("Jamie");
    }
}