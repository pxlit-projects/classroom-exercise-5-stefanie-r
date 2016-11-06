package be.pxl.spring.rest.fallout.quote;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static java.util.stream.Collectors.toList;

@RequestMapping(MemorableQuotesController.QUOTE_BASE_URL)
@RestController
public class MemorableQuotesController {

    public static final String QUOTE_BASE_URL = "/quote";

    @Autowired
    private QuoteService quoteService;

    @Autowired
    private QuoteAssembler quoteAssembler;

    @GetMapping
    public List<QuoteR> all() {
        return quoteService
                .getAll()
                .stream()
                .map(quoteAssembler::toRepresentation)
                .collect(toList());
    }

    @GetMapping(params = {"author"})
    public List<QuoteR> byAuthor(@RequestParam("author") String author) {
        return quoteService
                .findByAuthor(author)
                .stream()
                .map(quoteAssembler::toRepresentation)
                .collect(toList());
    }

    @PostMapping
    public ResponseEntity addQuote(@RequestBody QuoteR newQuoteR) {
        Quote quote = new Quote(newQuoteR.getAuthor(), newQuoteR.getQuote());
        Quote persistedQuote = quoteService.createQuote(quote);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest().path("/{id}")
                .buildAndExpand(persistedQuote.getId()).toUri();
        return ResponseEntity.created(location).body(persistedQuote);
    }
}
