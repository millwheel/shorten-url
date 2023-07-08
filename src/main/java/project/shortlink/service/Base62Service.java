package project.shortlink.service;

import org.springframework.stereotype.Service;

@Service
public class Base62Service {

    private static final String BASE62 = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";

    public String encode(long id) {
        StringBuilder shortID = new StringBuilder();
        while (id > 0) {
            int remain = (int) (id % 62);
            shortID.append(BASE62.charAt(remain));
            id /= 62;
        }
        return shortID.toString();
    }

    public long decode(String shortUrl) {
        long id = 0;
        long power = 1;
        for (int i = 0; i < shortUrl.length(); i++) {
            id += BASE62.indexOf(shortUrl.charAt(i)) * power;
            power *= 62;
        }
        return id;
    }

}
