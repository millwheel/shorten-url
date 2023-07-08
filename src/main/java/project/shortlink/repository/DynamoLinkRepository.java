package project.shortlink.repository;

import project.shortlink.entity.Link;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class DynamoLinkRepository implements LinkRepository{

    private final SpringDataDynamoLinkRepository repository;

    public DynamoLinkRepository(SpringDataDynamoLinkRepository repository) {
        this.repository = repository;
    }

    @Override
    public String create(Link link) {
        Link saved = repository.save(link);
        return saved.getShortId();
    }

    @Override
    public Optional<Link> findById(String shortId) {
        return repository.findById(shortId);
    }

    @Override
    public Optional<Link> findByOriginalUrl(String originalUrl) {
        return repository.findByOriginalUrl(originalUrl);
    }

    @Override
    public void deleteById(String shortId) {
        repository.deleteById(shortId);
    }
}
