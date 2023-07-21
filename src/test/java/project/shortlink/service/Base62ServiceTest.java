package project.shortlink.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.security.SecureRandom;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
class Base62ServiceTest {

    private final Base62Service base62Service;

    @Autowired
    Base62ServiceTest(Base62Service base62Service) {
        this.base62Service = base62Service;
    }

    @Test
    void createRandomNumberTest(){
        // given
        SecureRandom secureRandom = new SecureRandom();
        // when
        long number = Integer.toUnsignedLong(secureRandom.nextInt());
        // then
        assertThat(number).isLessThan(Integer.MAX_VALUE * 2L);
        assertThat(number).isGreaterThanOrEqualTo(0);
    }

    @Test
    void encodeNumberToBase62Test(){
        // given
        SecureRandom secureRandom = new SecureRandom();
        long number = Integer.toUnsignedLong(secureRandom.nextInt());
        String pattern = "[a-zA-Z0-9]+";
        // when
        System.out.println(number);
        String encoded = base62Service.encode(number);
        System.out.println(encoded);
        // then
        assertTrue(encoded.matches(pattern));
    }

    @Test
    void encodeAndDecodeTest(){
        // given
        SecureRandom secureRandom = new SecureRandom();
        long number = Integer.toUnsignedLong(secureRandom.nextInt());
        // when
        String encoded = base62Service.encode(number);
        long decoded = base62Service.decode(encoded);
        // then
        assertThat(decoded).isEqualTo(number);
    }
}