package project.shortlink.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
    public static final AtomicInteger serialNumber = new AtomicInteger(0);
    private final Base62Service base62Service;

    private final LinkRepository linkRepository;

    @Autowired
    public LinkServiceTimestamp(Base62Service base62Service, LinkRepository linkRepository) {
        this.base62Service = base62Service;
        this.linkRepository = linkRepository;
    }

    @Override
    public String createShortLink(String originalUrl) {
        // Use current time.
        String currentTime = Long.toString(System.currentTimeMillis());
        // User atomic serial number.
        String serialNow = getSerialNumber();
        // Combine current time and server address to create unique number
        long createdNumber = Long.parseLong(currentTime + serverNumber + serialNow);
        log.info("current time = {}, serial number = {}", currentTime, serialNow);
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

    public synchronized String getSerialNumber(){
        int serialNow = serialNumber.getAndIncrement();
        if (serialNumber.get() == 4097){
            serialNumber.set(0);
        }
        return Integer.toString(serialNow);
    }

}
