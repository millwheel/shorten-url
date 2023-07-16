package project.shortlink.service;

import project.shortlink.entity.Link;

import java.net.UnknownHostException;
import java.util.Optional;

public interface LinkService {

    String createShortLink(String originalUrl) throws UnknownHostException;
    Optional<Link> checkShortLink(String shortId);
}
