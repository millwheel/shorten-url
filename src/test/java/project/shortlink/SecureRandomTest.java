package project.shortlink;

import org.junit.jupiter.api.Test;

import java.security.SecureRandom;

class SecureRandomTest {

    @Test
    void SecureRandomTest(){
        SecureRandom secureRandom = new SecureRandom();
        long number = Integer.toUnsignedLong(secureRandom.nextInt());
        System.out.println(number);
    }
}
