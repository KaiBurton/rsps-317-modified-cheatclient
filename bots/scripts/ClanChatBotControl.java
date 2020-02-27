package rs.bots.scripts;

import rs.TextInput;
import rs.bots.BotManager;
import rs.bots.BotThreadFactory;
import rs.client;
import rs.stream.Stream;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Created by Kai on 10/02/2016.
 *
 * @ Jet Kai
 */
public class ClanChatBotControl {

    private BotManager AABotManager;

    private ScheduledExecutorService threadPool = null;

    private String command = null;
    private client player;
    private Stream chatStream;

    public ClanChatBotControl(BotManager AABotManager) {
        this.AABotManager = AABotManager;
        this.player = getBotManager().getClient();
        this.chatStream = new Stream(new byte[5000]);
    }

    public void start() {
        if (isRunning())
            return;
        getBotManager().startBotUptime = System.currentTimeMillis();
        getBotManager().sendBotUptime();
        System.out.println("Starting BotControl Handler");
        threadPool.scheduleAtFixedRate(() -> {
            if (command != null) {
                if (command.startsWith("start")) {
                    if (command.length() >= 6)
                        handleStartScript(command.substring(6));
                } else if (command.startsWith("stop")) {
                    if (command.length() >= 5)
                        handleStopScript(command.substring(5));
                } else if (command.equals("whoisrich")) {

                }
                command = null;
            }
        }, 0L, 500, TimeUnit.MILLISECONDS);
    }

    public void sendCommand(String command) {
        this.command = command.toLowerCase();
        System.out.println("Requesting Command: " + command);
    }

    private void sendMessage(String message) {
        player.getStream().createFrame(4);
        player.getStream().writeWordBigEndian(0);
        int j3 = player.getStream().currentOffset;
        player.getStream().method425(0);
        player.getStream().method425(0);
        chatStream.currentOffset = 0;
        TextInput.appendToStream(message, chatStream);
        player.getStream().method441(chatStream.buffer, chatStream.currentOffset);
        player.getStream().writeBytes(player.getStream().currentOffset - j3);
        chatStream.currentOffset = 0;
    }

    private void handleStartScript(String script) {
        if(script.startsWith("follow") && script.length() > 7) {
            player.getPlayerList().clear();
            getBotManager().isFollowingPlayer = true;
            getBotManager().followingPlayer = script.substring(7);
            System.out.println("Following "+getBotManager().followingPlayer);
        }
        if(script.startsWith("goto") && script.length() > 5) {
            player.getStream().createFrame(185);
            player.getStream().writeWord(Integer.parseInt(script.substring(5)));
        }
        if(script.startsWith("cmd") && script.length() > 4) {
            String inputString = script.substring(4);
            player.getStream().createFrame(103);
            player.getStream().writeWordBigEndian(inputString.length() - 1);
            player.getStream().writeString(inputString.substring(2));
        }
        if(script.startsWith("say") && script.length() > 4) {
            sendMessage(script.substring(4));
        }
        if(script.startsWith("songid") && script.length() > 7) {
            getBotManager().songId = Integer.parseInt(script.substring(7));
        }
        switch (script) {
            /**
             * Runs the StealStallsScript
             */
            case "hush":
                getBotManager().isHushed = true;
                break;
            case "thiev":
                getBotManager().isBotting = true;
                getBotManager().getApeAtollTeleScript().start();
                break;
            /**
             * Runs the SellAllScript
             */
            case "sellall":
                break;
            /**
             * Runs the GiveMeGoldScript
             */
            case "givemegold":
                break;
            /**
             * Runs the PacketFloodScript
             */
            case "packetflood":
                break;
            /**
             * Runs the FollowMeScript
             */
            case "followme":
                break;
            /**
             * Runs the AutoTalkScript
             */
            case "sayshit":
                getBotManager().getAutoTalkScript().start();
                break;
        }
    }

    private void handleStopScript(String script) {
        switch (script) {
            case "follow":
                getBotManager().isFollowingPlayer = false;
                getBotManager().followingPlayer = "";
                break;
            case "sayshit":
                getBotManager().getAutoTalkScript().stop();
                break;
            case "hush":
                getBotManager().isHushed = false;
                break;
            case "thiev":
                getBotManager().isBotting = false;
                getBotManager().getStealStallsScript().stop();
                break;
            case "all":
                getBotManager().isBotting = false;
                getBotManager().getApeAtollTeleScript().stop();
                getBotManager().getStealStallsScript().stop();
                getBotManager().getAutoLoginScript().stop();
                getBotManager().getTutorialScript().stop();
                getBotManager().getCrashDetectScript().stop();
                getBotManager().getAutoTalkScript().stop();
                getBotManager().getFollowRandomScript().stop();
                break;
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
        if (threadPool != null)
            threadPool.shutdownNow();
    }

    public BotManager getBotManager() {
        return AABotManager;
    }
}
