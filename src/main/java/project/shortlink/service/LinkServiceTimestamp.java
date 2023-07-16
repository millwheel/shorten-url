package project.shortlink.service;

import project.shortlink.entity.Link;
import project.shortlink.repository.LinkRepository;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.time.LocalDateTime;
import java.util.Optional;

public class LinkServiceTimestamp implements LinkService{

    private final Base62Service base62Service;
    private final LinkRepository linkRepository;

    public LinkServiceTimestamp(Base62Service base62Service, LinkRepository linkRepository) {
        this.base62Service = base62Service;
        this.linkRepository = linkRepository;
    }

    @Override
    public String createShortLink(String originalUrl) throws UnknownHostException {
        // This service doesn't check if original url exists in DB.
        // Use current time.
        long currentTime = System.currentTimeMillis();

        // Use current server address.
        String hostAddress = InetAddress.getLocalHost().getHostAddress();
        String[] eachNumbers = hostAddress.split("\\.");
        long serverNumber = Long.parseLong(String.join("", eachNumbers));

        // Combine current time and server address to create unique number
        long createdNumber = currentTime + serverNumber;

        // Base 62 encoding create alphanumeric short id
        String shortId = base62Service.encode(createdNumber);
        String now = LocalDateTime.now().toString();
        Link link = new Link(shortId, originalUrl, now);
        return linkRepository.create(link);
    }

    @Override
    public Optional<Link> checkShortLink(String shortId) {
        return linkRepository.findById(shortId);
    }
}
