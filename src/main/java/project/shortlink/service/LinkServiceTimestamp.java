package project.shortlink.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import project.shortlink.entity.Link;
import project.shortlink.repository.LinkRepository;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

public class LinkServiceTimestamp implements LinkService{

    @Value("${host.number}")
    private String serverNumber;

    private AtomicInteger serialNumber;

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
        String currentTime = Long.toString(System.currentTimeMillis());

        // User atomic serial number.
        String serialNow = Long.toString(serialNumber.getAndIncrement());

        // Combine current time and server address to create unique number
        long createdNumber = Long.parseLong(currentTime + serverNumber + serialNow);

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

    @Scheduled(fixedDelay = 1)
    public void resetSerialNumber(){
        serialNumber.set(0);
    }
}
