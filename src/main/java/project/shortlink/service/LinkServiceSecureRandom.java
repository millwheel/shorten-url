package project.shortlink.service;

import project.shortlink.entity.Link;
import project.shortlink.repository.LinkRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
@Slf4j
public class LinkServiceSecureRandom implements LinkService{

    private final Base62Service base62Service;
    private final LinkRepository linkRepository;

    @Autowired
    public LinkServiceSecureRandom(Base62Service base62Service, LinkRepository linkRepository) {
        this.base62Service = base62Service;
        this.linkRepository = linkRepository;
    }

    @Override
    public String createShortLink(String originalUrl) {
        // Check if the original url already exist in DB.
        Optional<Link> byOriginalUrl = linkRepository.findByOriginalUrl(originalUrl);
        if(byOriginalUrl.isPresent()){
            return byOriginalUrl.get().getShortId();
        }
        // Use secure random of java.
        SecureRandom secureRandom = new SecureRandom();
        long randomLong;
        String shortId;
        // If DB has short id in DB already, Create shortId again with SecureRandom.
        do {
            randomLong = Integer.toUnsignedLong(secureRandom.nextInt());
            // Base 62 encoding create alphanumeric short id
            shortId = base62Service.encode(randomLong);
        } while (linkRepository.findById(shortId).isPresent());
        String now = LocalDateTime.now().toString();
        Link link = new Link(shortId, originalUrl, now);
        return linkRepository.create(link);
    }

    @Override
    public Optional<Link> checkShortLink(String shortId) {
        return linkRepository.findById(shortId);
    }

}
