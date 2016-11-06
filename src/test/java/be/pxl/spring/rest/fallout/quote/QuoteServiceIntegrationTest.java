package be.pxl.spring.rest.fallout.quote;

import be.pxl.spring.rest.fallout.author.Author;
import be.pxl.spring.rest.fallout.author.AuthorRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static be.pxl.spring.rest.fallout.quote.QuoteTestBuilder.aQuote;
import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class QuoteServiceIntegrationTest {

    @Autowired
    private QuoteRepository quoteRepository;
    @Autowired
    private AuthorRepository authorRepository;

    @Autowired
    private QuoteService quoteService;

    @Before
    public void setUp() throws Exception {
        quoteRepository.deleteAll();
        authorRepository.deleteAll();
    }

    @Test
    public void createQuote_UnexistingAuthor_AlsoCreatesAuthor() throws Exception {
        Quote newQuote = aQuote().withAuthor("Jamie").withQuotation("As ze mn pet aanraken ja dan flip ik altijd").build();

        Quote persistedQuote = quoteService.createQuote(newQuote);

        assertThat(quoteRepository.findAll()).containsOnly(persistedQuote);
        assertThat(authorRepository.findAll()).extracting(Author::getName).containsExactly("Jamie");
    }

    @Test
    public void createQuote_ExistingAuthor_DoesNotCreateAuthor() throws Exception {
        String jamie = "Jamie";
        authorRepository.save(new Author(jamie));

        Quote newQuote = aQuote().withAuthor(jamie).withQuotation("As ze mn pet aanraken ja dan flip ik altijd").build();

        Quote persistedQuote = quoteService.createQuote(newQuote);

        assertThat(quoteRepository.findAll()).containsOnly(persistedQuote);
        assertThat(authorRepository.findAll()).extracting(Author::getName).containsExactly("Jamie");
    }
}