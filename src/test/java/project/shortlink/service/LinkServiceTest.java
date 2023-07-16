package project.shortlink.service;

import project.shortlink.entity.Link;
import project.shortlink.repository.LinkRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.net.UnknownHostException;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
class LinkServiceTest {

    private final LinkService linkService;
    private final LinkRepository linkRepository;

    @Autowired
    LinkServiceTest(LinkService linkService, LinkRepository linkRepository) {
        this.linkService = linkService;
        this.linkRepository = linkRepository;
    }


    @Test
    void createShortLinkTest() throws UnknownHostException {
        // given
        String pattern = "\\w+";
        String url = "https://www.thisissamplehttpurl.com/this/is/fake/url";
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
        String url = "https://www.thisissamplehttpurl.com/this/is/fake/url";
        String shortId = linkService.createShortLink(url);
        // when
        Optional<Link> link = linkService.checkShortLink(shortId);
        // then
        assertThat(link.get().getOriginalUrl()).isEqualTo(url);
        linkRepository.deleteById(shortId);
    }
}