package be.pxl.spring.rest.fallout.quote;

import be.pxl.spring.rest.fallout.author.Author;
import be.pxl.spring.rest.fallout.author.AuthorRepository;
import be.pxl.spring.rest.fallout.author.AuthorService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import static be.pxl.spring.rest.fallout.quote.QuoteTestBuilder.aQuote;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
public class QuoteServiceTransactionalityTest {

    @Autowired
    private QuoteRepository quoteRepository;
    @Autowired
    private AuthorRepository authorRepository;
    @MockBean
    private AuthorService authorServiceMock;

    @Autowired
    private QuoteService quoteService;

    @Before
    public void setUp() throws Exception {
        quoteRepository.deleteAll();
        authorRepository.deleteAll();
    }

    @Test
    public void createQuote_WhenAuthorServiceThrowsException_QuoteIsNotPersisted() throws Exception {
        String jamie = "Jamie";
        Quote newQuote = aQuote().withAuthor(jamie).withQuotation("As ze mn pet aanraken ja dan flip ik altijd").build();

        when(authorServiceMock.create(jamie)).thenThrow(UnsupportedOperationException.class);

        try {
            quoteService.createQuote(newQuote);
        } catch (UnsupportedOperationException uoe) {
            //nom nom nom
        }

        assertThat(quoteRepository.findAll()).isEmpty();
    }
}