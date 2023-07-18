package project.shortlink.timer;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.atomic.AtomicInteger;

public class Scheduler {
    public static final AtomicInteger serialNumber = new AtomicInteger(0);
    public void workTimer(){
        Timer scheduler = new Timer();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                serialNumber.set(0);
            }
        };
        scheduler.scheduleAtFixedRate(task, 0, 100);
    }
}
