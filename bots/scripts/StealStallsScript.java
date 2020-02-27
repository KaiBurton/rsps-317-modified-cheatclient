package rs.bots.scripts;

import rs.FakePacket;
import rs.bots.BotManager;
import rs.bots.BotThreadFactory;
import rs.client;
import rs.config.ObjectDef;

import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Created by Kai on 10/02/2016.
 *
 * @ Jet Kai
 */
public class StealStallsScript {

    private BotManager AABotManager;

    private ScheduledExecutorService threadPool = null;

    private FakePacket fakePacket;
    private client player;

    private boolean isRunning = false;

    public StealStallsScript(BotManager AABotManager) {
        this.AABotManager = AABotManager;
        this.player = getAABotManager().getClient();
        this.fakePacket = new FakePacket(player);
    }

    public void start() {
        if (isRunning())
            return;
        final int[] bankAllDelay = {0};
        getAABotManager().botRelogTimer = 0;
        getAABotManager().startBotUptime = System.currentTimeMillis();
        getAABotManager().sendBotUptime();
        System.out.println("Starting ApeAtollStallScriptHandler");
        threadPool.scheduleAtFixedRate(() -> {
            if (getAABotManager().isBotting()) {
                if (getAABotManager().getInventorySize() >= 27 && bankAllDelay[0] == 0 && getAABotManager().getInventorySize() >= 27 && player.isLoggedIn() && getAABotManager().regionLoaded) {
                    fakePacket.sendBank();
                    bankAllDelay[0] = 4;
                }
                if (player.isLoggedIn() && getAABotManager().regionLoaded)
                    getAABotManager().sendBotUptime();
                if (getAABotManager().isRuneStalls())
                    sendRuneStall();
                else
                    sendStealStalls();
                if (bankAllDelay[0] > 0)
                    bankAllDelay[0]--;
            } else
                stop();
        }, 0L, 500, TimeUnit.MILLISECONDS);
    }

    private void sendRuneStall() {
        if (player.isLoggedIn() && getAABotManager().regionLoaded) {
            getAABotManager().drawBotStatus("Ape Atoll Stalls 1.0", "Thieving " + ObjectDef.forID(13493).name);
            //int[] localObjectCoords = player.objectManager.getLocalObjectCoords(13493);
            player.getStream().createFrame(132);
            player.getStream().method433(48 + player.getBaseX());
            player.getStream().writeWord(13493);
            player.getStream().method432(52 + player.getBaseY());
        }
    }

    private void sendStealStalls() {
        if (player.isLoggedIn() && getAABotManager().regionLoaded) {
            isRunning = true;
            int thievingLevel = player.getCurrentStats();
            int thievingExp = player.getCurrentExp();
            int object = thievingLevel < 30 ? 4875 : thievingLevel >= 30 && thievingLevel < 60 ? 4874 : thievingLevel >= 60 && thievingLevel < 65 ? 4876 : thievingLevel >= 65 && thievingLevel < 80 ? 4877 : thievingLevel >= 80 && thievingLevel <= 99 ? 4878 : 0;
            int coordX;
            int coordY;
            if(getAABotManager().isDonatorStalls) {
                object = 4706;
                coordX = 2517;
                coordY = 3862;
            } else if (!getAABotManager().isArdyStalls && !getAABotManager().isDonatorStalls) {
                coordX = thievingLevel < 30 ? 2768 : thievingLevel >= 30 && thievingLevel < 60 ? 2781 : thievingLevel >= 60 && thievingLevel < 65 ? 2753 : thievingLevel >= 65 && thievingLevel < 80 ? 2757 : thievingLevel >= 80 && thievingLevel <= 99 ? 2759 : 0;
                coordY = thievingLevel < 30 ? 2789 : thievingLevel >= 30 && thievingLevel < 60 ? 2794 : thievingLevel >= 60 && thievingLevel < 65 ? 2775 : thievingLevel >= 65 && thievingLevel < 80 ? 2769 : thievingLevel >= 80 && thievingLevel <= 99 ? 2774 : 2774;
            } else {
                coordX = thievingLevel >= 1 && thievingLevel < 50 ? 2656 : thievingLevel >= 50 && thievingLevel <= 99 ? 2657 : 0;
                coordY = thievingLevel >= 1 && thievingLevel < 50 ? 3302 : thievingLevel >= 50 && thievingLevel <= 99 ? 3314 : 0;
                object = thievingLevel >= 1 && thievingLevel < 50 ? 2560 : thievingLevel >= 50 && thievingLevel <= 99 ? 2565 : 0;
            }
            int[] localObjectCoords = player.getObjectManager().getLocalObjectCoords(player, object);
            if (localObjectCoords == null)
                return;
            int x = player.getBaseX() + (player.getPlayerCoordX() - 6 >> 7);
            int y = player.getBaseY() + (player.getPlayerCoordY() - 6 >> 7);
            if (thievingLevel == 99 && getAABotManager().isPrestiegeMode && !getAABotManager().isDonatorStalls) {
                fakePacket.sendUnequipAll();
                fakePacket.sendSkillPrestiege();
                //fakePacket.equipAllItemsInInventory();
                //fakePacket.sendBank();
            }
            Random r = new Random();
            if (x > 2785 && x < 2840 && !getAABotManager().isArdyStalls) {
                player.doWalkTo(2, 0, 0, 0, player.getPlayerPathY(), 0, 0, player.getPlayerPathY() + r.nextInt(6) + 2, player.getPlayerPathX(), false, player.getPlayerPathX() - 8);
                getAABotManager().drawBotStatus("Walking To Ladder", "Thieving " + ObjectDef.forID(object).name);
            } else if ((x > 2774 && x < 2840) && thievingLevel >= 60 && !getAABotManager().isArdyStalls) {
                player.doWalkTo(2, 0, 0, 0, player.getPlayerPathY(), 0, 0, player.getPlayerPathY() - r.nextInt(4) + 2, player.getPlayerPathX(), false, player.getPlayerPathX() - 8);
                getAABotManager().drawBotStatus("Walking Next To Stall", "Thieving " + ObjectDef.forID(object).name);
            } else {
                if (object != 4876 && y == coordY + 1 && x == coordX && !getAABotManager().isDonatorStalls && !getAABotManager().isArdyStalls) {
                    fakePacket.sendStealFromStall(object, coordY, coordX);
                } else if (object == 4876 && y == coordY && x == coordX + 1 && !getAABotManager().isDonatorStalls && !getAABotManager().isArdyStalls) {
                        fakePacket.sendStealFromStall(object, coordY, coordX);
                } else if(getAABotManager().isDonatorStalls && y != coordY + 2 && x != coordX) {
                    player.reachClickedObject(object, localObjectCoords[0] + 2, localObjectCoords[1], false);
                } else if(getAABotManager().isDonatorStalls && y == coordY + 2 && x == coordX) {
                    fakePacket.sendStealFromStall(object, coordY, coordX);
                } else if(getAABotManager().isArdyStalls && y == coordY - 1 && x == coordX) {
                    fakePacket.sendStealFromStall(object, coordY, coordX);
                } else if(!getAABotManager().isDonatorStalls && object == 4876) {
                    player.reachClickedObject(object, localObjectCoords[0], localObjectCoords[1] + 1, false);
                } else if(getAABotManager().isArdyStalls && !getAABotManager().isDonatorStalls) {
                    player.reachClickedObject(object, localObjectCoords[0] - 1, localObjectCoords[1], false);
                } else
                    player.reachClickedObject(object, localObjectCoords[0] + 1, localObjectCoords[1], false);
                getAABotManager().drawBotStatus("Ape Atoll Stalls 1.0", "Thieving " + ObjectDef.forID(object).name);
            }
        }
    }

    private boolean isRunning() {
        if (threadPool != null && !threadPool.isShutdown())
            return true;
        startThread();
        return false;
    }

    public boolean isRunningScript() {
        return isRunning;
    }

    private void startThread() {
        System.out.println("Creating the theiving thread.");
        threadPool = Executors.newSingleThreadScheduledExecutor(new BotThreadFactory());
    }

    public void stop() {
        if (threadPool != null)
            threadPool.shutdownNow();
        isRunning = false;
    }

    public BotManager getAABotManager() {
        return AABotManager;
    }
}
