package project.shortlink.service;

import lombok.extern.slf4j.Slf4j;
import project.shortlink.entity.Link;
import project.shortlink.repository.LinkRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import project.shortlink.timer.Scheduler;

import java.net.UnknownHostException;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.Timer;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicReference;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@Slf4j
class LinkServiceTest {

    private final LinkService linkService;
    private final LinkRepository linkRepository;

    private final String url = "https://www.thisissamplehttpurl.com/this/is/fake/url";

    @Autowired
    LinkServiceTest(LinkService linkService, LinkRepository linkRepository) {
        this.linkService = linkService;
        this.linkRepository = linkRepository;
    }

    @Test
    void createShortLinkTest() throws UnknownHostException {
        // given
        String pattern = "\\w+";
        // when
        String shortId = linkService.createShortLink(url);
        Optional<Link> link = linkRepository.findById(shortId);
        // then
        assertTrue(shortId.matches(pattern));
        assertThat(link.get().getShortId()).isEqualTo(shortId);
        linkRepository.deleteById(shortId);
    }

    @Test
    void checkShortLink() throws UnknownHostException {
        // given
        String shortId = linkService.createShortLink(url);
        // when
        Optional<Link> link = linkService.checkShortLink(shortId);
        // then
        assertThat(link.get().getOriginalUrl()).isEqualTo(url);
        linkRepository.deleteById(shortId);
    }

    @Test
    void multiThreadTest() throws InterruptedException {
        int threadCount = 1000;
        ExecutorService executorService = Executors.newFixedThreadPool(32);
        CountDownLatch countDownLatch = new CountDownLatch(threadCount);
        AtomicReference<Set<String>> shortIdStorage = new AtomicReference<>(new HashSet<>());

        for (int i = 0; i < threadCount; i++){
            executorService.submit(() -> {
                try{
                    String shortId = linkService.createShortLink(url);
                    shortIdStorage.get().add(shortId);
                }catch (Exception e){
                    throw new RuntimeException(e);
                }finally {
                    countDownLatch.countDown();
                }
            });
        }
        countDownLatch.await();
        linkRepository.deleteAll();
        assertThat(shortIdStorage.get().size()).isEqualTo(5000);
    }

}