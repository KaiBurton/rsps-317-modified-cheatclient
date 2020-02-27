package rs.bots.scripts;

import rs.bots.BotManager;
import rs.bots.BotThreadFactory;
import rs.client;

import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Created by Kai on 10/02/2016.
 *
 * @ Jet Kai
 */
public class AutoLoginScript {

    private BotManager AABotManager;

    private ScheduledExecutorService threadPool = null;

    private client player;

    public AutoLoginScript(BotManager AABotManager) {
        this.AABotManager = AABotManager;
        this.player = getAABotManager().getClient();
    }

    public void start() {
        if (isRunning())
            return;
        threadPool.scheduleAtFixedRate(() -> {
            Random random = new Random();
            String loginRandom = getAABotManager().getBots().get(random.nextInt(getAABotManager().getBots().size()));
            String username = null;
            String password = null;
            if (loginRandom != null && !loginRandom.contains("null") && loginRandom.contains("-")) {
                username = loginRandom.substring(0, loginRandom.indexOf("-") - 1);
                password = loginRandom.substring(loginRandom.indexOf("-") + 2);
            } else
                System.out.println("Something is wrong with the loginnames & passwords");
            if (!player.isLoggedIn() && !getAABotManager().regionLoaded && getAABotManager().isBotting()) {
                if (!getAABotManager().isLoggingIn) {
                    if (username != null) {
                        System.out.println("Logging in with " + username + ", " + password);
                        player.login(username, password, false);
                    }
                }
            } else
                stop();
        }, 0L, 1, TimeUnit.SECONDS);
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
