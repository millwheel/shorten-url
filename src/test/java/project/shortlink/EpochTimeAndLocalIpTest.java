package project.shortlink;

import org.junit.jupiter.api.Test;

import java.net.InetAddress;
import java.net.UnknownHostException;

class EpochTimeAndLocalIpTest {

    @Test
    void EpochTimeTest(){
        long ms = System.currentTimeMillis();
        System.out.println(ms);
    }

    @Test
    void LocalIpTest() throws UnknownHostException {
        String hostAddress = InetAddress.getLocalHost().getHostAddress();
        String[] eachNumbers = hostAddress.split("\\.");
        String onlyNumbers = String.join("", eachNumbers);
        int serverNumber = Integer.parseInt(onlyNumbers);
        System.out.println(serverNumber);
    }
}
