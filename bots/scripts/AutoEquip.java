package rs.bots.scripts;

import rs.bots.BotManager;
import rs.bots.BotThreadFactory;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Created by Kai on 11/02/2016.
 *
 * @ Jet Kai
 */
public class AutoEquip {

    private BotManager AABotManager;

    private ScheduledExecutorService threadPool = null;

    public AutoEquip(BotManager AABotManager) {
        this.AABotManager = AABotManager;
    }

    public void start() {
        if (isRunning())
            return;
        System.out.println("Starting AutoEquipScript");
        getAABotManager().startBotUptime = System.currentTimeMillis();
        threadPool.scheduleAtFixedRate(() -> {
            if (getAABotManager().getClient().isLoggedIn() && getAABotManager().regionLoaded) {
                stop();
            }
        }, 0L, 500, TimeUnit.MILLISECONDS);
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
