package project.shortlink.repository;

import project.shortlink.entity.Link;

import java.util.Optional;

public interface LinkRepository {
    String create(Link link);
    Optional<Link> findById(String shortId);
    Optional<Link> findByOriginalUrl(String originalUrl);
    void deleteById(String shortId);
}
