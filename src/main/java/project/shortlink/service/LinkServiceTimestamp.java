package project.shortlink.service;

import project.shortlink.entity.Link;

import java.util.Optional;

public class LinkServiceTimestamp implements LinkService{
    @Override
    public String createShortLink(String originalUrl) {
        return null;
    }

    @Override
    public Optional<Link> checkShortLink(String shortId) {
        return Optional.empty();
    }
}
