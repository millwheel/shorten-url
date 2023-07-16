package project.shortlink;

import org.junit.jupiter.api.Test;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.security.SecureRandom;

class EpochTimeAndLocalIpTest {

    @Test
    void epochTimeTest(){
        long ms = System.currentTimeMillis();
        System.out.println(ms);
        String binaryString = Long.toBinaryString(ms);
        System.out.println(binaryString);
    }

    @Test
    void localIpTest() throws UnknownHostException {
        String hostAddress = InetAddress.getLocalHost().getHostAddress();
        String[] eachNumbers = hostAddress.split("\\.");
        String onlyNumbers = String.join("", eachNumbers);
        int serverNumber = Integer.parseInt(onlyNumbers);
        System.out.println(serverNumber);
    }

    @Test
    void createUniqueNumber() throws UnknownHostException {
        String currentTime = Long.toString(System.currentTimeMillis());
        int serverNumber = 1;
        SecureRandom secureRandom = new SecureRandom();
        short random = (short) secureRandom.nextInt(Short.MAX_VALUE + 1);
        long createdNumber = Long.parseLong(currentTime + serverNumber + random);
        System.out.println(createdNumber);
    }
}
