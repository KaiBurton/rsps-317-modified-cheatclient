package rs.bots.scripts;

import com.sun.management.OperatingSystemMXBean;
import rs.bots.BotManager;
import rs.bots.BotThreadFactory;
import rs.client;

import javax.management.MBeanServerConnection;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.lang.management.ManagementFactory;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Created by Kai on 10/02/2016.
 *
 * @ Jet Kai
 */
public class CrashDetectScript {

    private BotManager AABotManager;

    private ScheduledExecutorService threadPool = null;

    private client player;

    public CrashDetectScript(BotManager AABotManager) {
        this.AABotManager = AABotManager;
        this.player = getAABotManager().getClient();
    }

    public void start() {
        if (isRunning())
            return;
        getAABotManager().botRelogTimer = 0;
        System.out.println("Starting ClientShutdownManager");
        threadPool.scheduleAtFixedRate(() -> {
            if (getAABotManager().botRelogTimer >= 60) {
                getAABotManager().isOpened = false;
                sendRelaunchClient();
                getAABotManager().botRelogTimer = 0;
            } else if(getAABotManager().botRelogTimer >= -15 && getAABotManager().botRelogTimer <= -5)
                player.autoLogin();
            getAABotManager().botRelogTimer++;
        }, 0L, 1000, TimeUnit.MILLISECONDS);
    }

    public void sendRelaunchClient() {
        if (!getAABotManager().isOpened) {
            getAABotManager().isOpened = true;
            try {
                Desktop.getDesktop().open(new File(System.getProperty("user.home") + "/Desktop/WaitTimer.bat"));
            } catch (IOException e) {
                e.printStackTrace();
            }
            System.exit(0);
        }
    }

    private boolean isRunning() {
        if (threadPool != null && !threadPool.isShutdown())
            return true;
        startThread();
        return false;
    }

    private void startThread() {
        threadPool = Executors.newSingleThreadScheduledExecutor(new BotThreadFactory());
    }

    public void stop() {
        if (threadPool != null) {
            threadPool.shutdownNow();
            threadPool = null;
        }
    }

    public BotManager getAABotManager() {
        return AABotManager;
    }
}
