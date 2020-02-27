package rs.bots.scripts;

import rs.FakePacket;
import rs.bots.BotManager;
import rs.bots.BotThreadFactory;
import rs.client;

import java.util.Random;
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
public class ApeAtollTeleScript {

    private BotManager AABotManager;
    private FakePacket fakePacket;

    private ScheduledExecutorService threadPool = null;

    private client player;

    public ApeAtollTeleScript(BotManager AABotManager) {
        this.AABotManager = AABotManager;
        System.out.println("CLIENT = " + getAABotManager().getClient());
        this.player = getAABotManager().getClient();
        this.fakePacket = new FakePacket(player);
    }

    public void startDialogue() {
        if (isRunning())
            return;
        System.out.println("Starting Dialogue Teleport");
        getAABotManager().startBotUptime = System.currentTimeMillis();
        threadPool.scheduleAtFixedRate(() -> {
            for (int i = 3500; i < 6000; i++) {
                player.getTabInterfaceID(i);
            }
            stop();
        }, 0L, 900000, TimeUnit.MILLISECONDS);
    }

    public void start() {
        if (isRunning())
            return;
        final int[] stage = {0};
        getAABotManager().startBotUptime = System.currentTimeMillis();
        System.out.println("Starting Teleport Script");
        threadPool.scheduleAtFixedRate(() -> {
            if (player.isLoggedIn() && getAABotManager().regionLoaded) {
                int x = player.getBaseX() + (player.getPlayerCoordX() - 6 >> 7);
                int y = player.getBaseY() + (player.getPlayerCoordY() - 6 >> 7);
                if (getAABotManager().isBotting() && getAABotManager().isArdyStalls) {
                    player.getStream().createFrame(185);
                    player.getStream().writeWord(8664);
                    Timer timer = new Timer();
                    timer.schedule(new TimerTask() {
                        @Override
                        public void run() {
                            getAABotManager().getStealStallsScript().start();
                            timer.cancel();
                            timer.purge();
                        }
                    }, 3000);
                    stop();
                    return;
                } else if(getAABotManager().isBotting() && getAABotManager().isDonatorStalls) {
                    player.doWalkTo(2, 0, 0, 0, player.getPlayerPathY(), 0, 0, player.getPlayerPathY(), player.getPlayerPathX(), false, player.getPlayerPathX());
                    Timer timer = new Timer();
                    timer.schedule(new TimerTask() {
                        @Override
                        public void run() {
                            player.getStream().createFrame(185);
                            player.getStream().writeWord(29329);
                            String amountOrNameInput = "Bot Control";
                            player.getStream().createFrame(60);
                            player.getStream().writeWordBigEndian(amountOrNameInput.length() + 1);
                            player.getStream().writeString(amountOrNameInput);
                            player.doWalkTo(2, 0, 0, 0, player.getPlayerPathY(), 0, 0, player.getPlayerPathY(), player.getPlayerPathX(), false, player.getPlayerPathX());
                            timer.cancel();
                            timer.purge();
                        }
                    }, 1500);
                    Timer timer2 = new Timer();
                    timer2.schedule(new TimerTask() {
                        @Override
                        public void run() {
                            player.getStream().createFrame(185);
                            player.getStream().writeWord(/*8662*/8670);
                            getAABotManager().getFollowRandomScript().start();
                            timer2.cancel();
                            timer2.purge();
                        }
                    }, 5000);
                  /*  timer.schedule(new TimerTask() {
                        @Override
                        public void run() {
                            Random r = new Random();
                            if(r.nextInt(1) == 0)
                                player.doWalkTo(2, 0, 0, 0, player.getPlayerPathY(), 0, 0, player.getPlayerPathY() + r.nextInt(14) + 2, player.getPlayerPathX(), false, player.getPlayerPathX() - r.nextInt(14) + 2);
                            else
                                player.doWalkTo(2, 0, 0, 0, player.getPlayerPathY(), 0, 0, player.getPlayerPathY() + r.nextInt(14) + 2, player.getPlayerPathX(), false, player.getPlayerPathX() + r.nextInt(14) + 2);
                            timer.cancel();
                            timer.purge();
                        }
                    }, 5000);*/
                    stop();
                    return;
                }
                if (x == 2759 && y == 2775) {
                    getAABotManager().getStealStallsScript().start();
                    stop();
                    return;
                }
                if (getAABotManager().isBotting() && stage[0] <= 8) {
                    switch (stage[0]) {
                        case 0:
                            player.getStream().createFrame(185);
                            player.getStream().writeWord(29329);
                            stage[0]++;
                            break;
                        case 1:
                            String amountOrNameInput = "Bot Control";
                            player.getStream().createFrame(60);
                            player.getStream().writeWordBigEndian(amountOrNameInput.length() + 1);
                            player.getStream().writeString(amountOrNameInput);
                            stage[0]++;
                            break;
                        case 2:
                            player.getStream().createFrame(185);
                            player.getStream().writeWord(1164);
                            stage[0]++;
                            break;
                        case 3:
                            player.getStream().createFrame(185);
                            player.getStream().writeWord(2498);
                            stage[0]++;
                            break;
                        case 4:
                            player.getStream().createFrame(185);
                            player.getStream().writeWord(2498);
                            stage[0]++;
                            break;
                        case 5:
                            player.getStream().createFrame(185);
                            player.getStream().writeWord(2496);
                            stage[0]++;
                            break;
                        case 6:
                            stage[0]++;
                            break;
                        case 7:
                            if (x != 2793 && y != 2773)
                                stage[0] = 0;
                            else
                                stage[0]++;
                            break;
                        case 8:
                            Timer timer = new Timer();
                            timer.schedule(new TimerTask() {
                                @Override
                                public void run() {
                                    getAABotManager().getStealStallsScript().start();
                                    timer.cancel();
                                    timer.purge();
                                }
                            }, 3000);
                            stop();
                            stage[0]++;
                            break;
                    }
                } else
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
        System.out.println("STARTING A NEW THREAD");
        threadPool = Executors.newSingleThreadScheduledExecutor(new BotThreadFactory());
    }

    public void stop() {
        if (threadPool != null) {
            threadPool.shutdownNow();
            System.out.println("Shutting down scripts " + threadPool.isShutdown());
        }
    }

    public BotManager getAABotManager() {
        return AABotManager;
    }
}
