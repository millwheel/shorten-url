package project.shortlink.service;

import project.shortlink.entity.Link;
import project.shortlink.repository.LinkRepository;

import java.util.Optional;

public class LinkServiceTimestamp implements LinkService{

    private final Base62Service base62Service;
    private final LinkRepository linkRepository;

    public LinkServiceTimestamp(Base62Service base62Service, LinkRepository linkRepository) {
        this.base62Service = base62Service;
        this.linkRepository = linkRepository;
    }

    @Override
    public String createShortLink(String originalUrl) {
        // This service doesn't check if original url exists in DB.
        long currentTime = System.currentTimeMillis();
        String shortId = base62Service.encode(currentTime);


        return
    }

    @Override
    public Optional<Link> checkShortLink(String shortId) {
        return linkRepository.findById(shortId);
    }
}
