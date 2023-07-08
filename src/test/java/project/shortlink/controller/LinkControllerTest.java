package project.shortlink.controller;

import project.shortlink.DTO.UrlRequestForm;
import project.shortlink.DTO.UrlResponseForm;
import project.shortlink.repository.LinkRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
class LinkControllerTest {

    private final LinkController linkController;
    private final LinkRepository linkRepository;

    @Autowired
    LinkControllerTest(LinkController linkController, LinkRepository linkRepository) {
        this.linkController = linkController;
        this.linkRepository = linkRepository;
    }

    @Test
    void createShortLinkWithHttpsTest() {
        // given
        String pattern = "\\w+";
        String urlHttps = "https://www.thisissamplehttpurl.com/this/is/fake/url";
        UrlRequestForm urlRequestForm = new UrlRequestForm();
        urlRequestForm.setUrl(urlHttps);
        // when
        UrlResponseForm urlResponseForm = linkController.createShortLink(urlRequestForm);
        String shortId = urlResponseForm.getData().getShortId();
        String url = urlResponseForm.getData().getUrl();
        // then
        assertTrue(shortId.matches(pattern));
        assertThat(url).isEqualTo(urlHttps);
        linkRepository.deleteById(shortId);
    }

    @Test
    void createShortLinkWithHttpTest() {
        // given
        String pattern = "\\w+";
        String urlHttp = "http://www.thisissamplehttpurl.com/this/is/fake/url";
        UrlRequestForm urlRequestForm = new UrlRequestForm();
        urlRequestForm.setUrl(urlHttp);
        // when
        UrlResponseForm urlResponseForm = linkController.createShortLink(urlRequestForm);
        String shortId = urlResponseForm.getData().getShortId();
        String url = urlResponseForm.getData().getUrl();
        // then
        assertTrue(shortId.matches(pattern));
        assertThat(url).isEqualTo(urlHttp);
        linkRepository.deleteById(shortId);
    }

    @Test
    void createShortLinkWithWrongUrlErrorTest() {
        // given
        String pattern = "\\w+";
        String wrongUrl = "thisissamplehttpurl.com/this/is/fake/url";
        UrlRequestForm urlRequestForm = new UrlRequestForm();
        urlRequestForm.setUrl(wrongUrl);
        // when
        // then
        assertThatThrownBy(()-> linkController.createShortLink(urlRequestForm))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("잘못된 URL 형식");
    }
    @Test
    void checkShortLinkTest() {
        // given
        String inputUrl = "https://www.thisissamplehttpurl.com/this/is/fake/url";
        UrlRequestForm urlRequestForm = new UrlRequestForm();
        urlRequestForm.setUrl(inputUrl);
        String shortId = linkController.createShortLink(urlRequestForm).getData().getShortId();
        // when
        UrlResponseForm urlResponseForm = linkController.checkShortLink(shortId);
        // then
        assertThat(urlRequestForm.getUrl()).isEqualTo(inputUrl);
        linkRepository.deleteById(shortId);
    }

    @Test
    void checkShortLinkWithWrongShortIdTest() {
        // given
        String shortId = "-";
        // when
        // then
        assertThatThrownBy(()-> linkController.checkShortLink(shortId))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("잘못된 short id 형식");
    }

    @Test
    void checkNonExistingShortLinkTest() {
        // given
        String shortId = "13959999999";
        // when
        // then
        assertThatThrownBy(()-> linkController.checkShortLink(shortId))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("URL 정보가 조회되지 않음");
    }
}