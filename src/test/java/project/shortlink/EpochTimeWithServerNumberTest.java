package project.shortlink;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.scheduling.annotation.Scheduled;

import java.net.UnknownHostException;
import java.security.SecureRandom;
import java.util.concurrent.atomic.AtomicInteger;

@SpringBootTest
public class EpochTimeWithServerNumberTest {

    private static AtomicInteger serialNumber = new AtomicInteger(0);

    @Value("${host.number}")
    private String serverNumber;

    @Test
    void createUniqueNumber() throws UnknownHostException {
        String currentTime = Long.toString(System.currentTimeMillis());
        String serialNow = Long.toString(serialNumber.getAndIncrement());
        long createdNumber = Long.parseLong(currentTime + serverNumber + serialNow);
        System.out.println("Server number is ---------------------------");
        System.out.println(createdNumber);
    }

    @Scheduled(fixedDelay = 1)
    public void resetSerialNumber(){
        serialNumber.set(0);
    }

}
