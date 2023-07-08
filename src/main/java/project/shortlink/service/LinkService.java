package project.shortlink.service;

import project.shortlink.entity.Link;

import java.util.Optional;

public interface LinkService {

    String createShortLink(String originalUrl);
    Optional<Link> checkShortLink(String shortId);
}
