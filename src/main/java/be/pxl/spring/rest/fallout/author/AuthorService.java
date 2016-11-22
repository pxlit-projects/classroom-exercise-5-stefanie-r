package be.pxl.spring.rest.fallout.author;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AuthorService {

    @Autowired
    private AuthorRepository authorRepository;
@Transactional
    public Author create(String authorName) {
        Author author = authorRepository.findByName(authorName);
        if (author == null) {
            authorRepository.save(new Author(authorName));
        }
        return author;
    }
}
