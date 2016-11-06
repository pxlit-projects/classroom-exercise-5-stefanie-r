package be.pxl.spring.rest.fallout.quote;

import be.pxl.spring.rest.fallout.author.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class QuoteService {

    @Autowired
    private QuoteRepository quoteRepository;

    @Autowired
    private AuthorService authorService;

    public Quote createQuote(Quote quoteToBeCreated) {
        Quote quote = quoteRepository.save(quoteToBeCreated);
        authorService.create(quoteToBeCreated.getAuthor());
        return quote;
    }

    public List<Quote> getAll() {
        return quoteRepository.findAll();
    }

    public List<Quote> findByAuthor(String author) {
        return quoteRepository.findByAuthor(author);
    }
}
