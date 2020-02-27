package rs.bots.scripts;

import rs.FakePacket;
import rs.bots.BotManager;
import rs.bots.BotThreadFactory;
import rs.client;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Created by Kai on 10/02/2016.
 *
 * @ Jet Kai
 */
public class SellAllScript {

    private BotManager AABotManager;

    private ScheduledExecutorService threadPool = null;

    public SellAllScript(BotManager AABotManager) {
        this.AABotManager = AABotManager;
        client player = getAABotManager().getClient();
        FakePacket fakePacket = new FakePacket(player);
    }

    public void start() {
        if (isRunning())
            return;
        getAABotManager().startBotUptime = System.currentTimeMillis();
        getAABotManager().sendBotUptime();
        System.out.println("Starting ApeAtollStallScriptHandler");
        threadPool.scheduleAtFixedRate(() -> {
            if (getAABotManager().isBotting()) {

            } else {
                System.out.println("Shutting down ApeAtoll Bot");
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
        if (threadPool != null)
            threadPool.shutdownNow();
    }

    public BotManager getAABotManager() {
        return AABotManager;
    }
}
