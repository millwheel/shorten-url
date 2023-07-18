package project.shortlink.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import project.shortlink.entity.Link;
import project.shortlink.repository.LinkRepository;
import project.shortlink.timer.Scheduler;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
@Service
public class LinkServiceTimestamp implements LinkService{

    @Value("${host.number}")
    private String serverNumber;

    private final Base62Service base62Service;

    private final LinkRepository linkRepository;

    public LinkServiceTimestamp(Base62Service base62Service, LinkRepository linkRepository) {
        this.base62Service = base62Service;
        this.linkRepository = linkRepository;
    }

    @Override
    public String createShortLink(String originalUrl) {
        // Use current time.
        String currentTime = Long.toString(System.currentTimeMillis());
        // User atomic serial number.
        AtomicInteger serialNumber = Scheduler.serialInteger;
        String serialNow = Long.toString(serialNumber.getAndIncrement());
        // Combine current time and server address to create unique number
        long createdNumber = Long.parseLong(currentTime + serverNumber + serialNow);
        // Base 62 encoding create alphanumeric short id
        String shortId = base62Service.encode(createdNumber);
//        String now = LocalDateTime.now().toString();
//        Link link = new Link(shortId, originalUrl, now);
        return shortId;
    }

    @Override
    public Optional<Link> checkShortLink(String shortId) {
        return linkRepository.findById(shortId);
    }

}
