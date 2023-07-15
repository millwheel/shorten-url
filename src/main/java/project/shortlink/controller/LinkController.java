package project.shortlink.controller;

import project.shortlink.DTO.UrlResponseForm;
import project.shortlink.DTO.UrlRequestForm;
import project.shortlink.DTO.Data;
import project.shortlink.entity.Link;
import project.shortlink.service.LinkService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Optional;

@RestController
@Slf4j
public class LinkController {

    private final LinkService linkService;

    @Autowired
    public LinkController(LinkService linkService) {
        this.linkService = linkService;
    }

    @PostMapping("/short-links")
    public UrlResponseForm createShortLink(@RequestBody UrlRequestForm urlRequestForm){
        String url = urlRequestForm.getUrl();
        if (!url.matches("https://\\S+") && !url.matches("http://\\S+")){
            throw new IllegalArgumentException("잘못된 URL 형식");
        }
        String shortId = linkService.createShortLink(url);
        Optional<Link> linkOptional = linkService.checkShortLink(shortId);
        if (linkOptional.isEmpty()){
            throw new NullPointerException("linkOptional 생성되지 않음");
        }
        Link link = linkOptional.get();
        Data data = Data.builder().shortId(link.getShortId()).url(link.getOriginalUrl()).createdAt(link.getCreateAt()).build();
        return new UrlResponseForm(data);
    }

    @GetMapping("/short-links/{short_id}")
    public UrlResponseForm checkShortLink(@PathVariable String short_id){
        if (!short_id.matches("\\w+")) {
            throw new IllegalArgumentException("잘못된 short id 형식");
        }
        Optional<Link> linkOptional = linkService.checkShortLink(short_id);
        if (linkOptional.isEmpty()){
            throw new NullPointerException("URL 정보가 조회되지 않음");
        }
        Link link = linkOptional.get();
        Data data = Data.builder().shortId(link.getShortId()).url(link.getOriginalUrl()).createdAt(link.getCreateAt()).build();
        return new UrlResponseForm(data);
    }

    @GetMapping("/redirection/{short_id}")
    @ResponseStatus(HttpStatus.FOUND)
    public void redirectOriginalUrl(@PathVariable String short_id, HttpServletResponse response) throws IOException {
        if (!short_id.matches("\\w+")) throw new IllegalArgumentException("잘못된 short id 형식");
        Optional<Link> linkOptional = linkService.checkShortLink(short_id);
        if (linkOptional.isEmpty()){
            throw new NullPointerException("URL 정보가 조회되지 않음");
        }
        response.sendRedirect(linkOptional.get().getOriginalUrl());
    }

}
