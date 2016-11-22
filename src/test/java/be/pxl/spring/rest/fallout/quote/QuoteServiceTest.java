package be.pxl.spring.rest.fallout.quote;

import be.pxl.spring.rest.fallout.author.Author;
import be.pxl.spring.rest.fallout.author.AuthorService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InOrder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import static be.pxl.spring.rest.fallout.quote.QuoteTestBuilder.aQuote;

@RunWith(MockitoJUnitRunner.class)
public class QuoteServiceTest {

    @Mock
    private QuoteRepository quoteRepositoryMock;
    @Mock
    private AuthorService authorServiceMock;

    @InjectMocks
    private QuoteService quoteService;

   /* @Test
    public void createQuote_SavesQuoteBeforeSavingAuthor() throws Exception {
        Quote quote = aQuote().withAuthor("Hannibal").withQuotation("I love it when a plan comes together").build();

        quoteService.createQuote(quote);

        InOrder inOrder = Mockito.inOrder(quoteRepositoryMock, authorServiceMock);
        inOrder.verify(quoteRepositoryMock).save(quote);
        inOrder.verify(authorServiceMock).create("Hannibal");
    }*/

    @Test
    public void createQuote_SavesAuthorBeforeSavingQuote() throws Exception {
        Quote quote = aQuote().withAuthor("Hannibal").withQuotation("I love it when a plan comes together").build();

        quoteService.createQuote(quote);

        InOrder inOrder = Mockito.inOrder(authorServiceMock, quoteRepositoryMock);
        inOrder.verify(authorServiceMock).create("Hannibal");
        inOrder.verify(quoteRepositoryMock).save(quote);
    }
}