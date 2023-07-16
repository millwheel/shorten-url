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
}
