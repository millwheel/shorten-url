package project.shortlink.timer;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.atomic.AtomicInteger;

public class Scheduler {
    public static final AtomicInteger serialInteger = new AtomicInteger(0);
    public void workTimer(){
        Timer scheduler = new Timer();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                serialInteger.set(0);
            }
        };
        scheduler.scheduleAtFixedRate(task, 0, 5);
    }
}
