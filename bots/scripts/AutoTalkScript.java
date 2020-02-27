package rs.bots.scripts;

import rs.FakePacket;
import rs.TextInput;
import rs.bots.BotManager;
import rs.bots.BotThreadFactory;
import rs.client;
import rs.stream.Stream;

import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Created by Kai on 17/04/2016.
 *
 * @ Jet Kai
 */
public class AutoTalkScript {

    private BotManager AABotManager;

    private ScheduledExecutorService threadPool = null;

    private client player;

    private String message;

    private String[] stupidshit = new String[]{"Noww we riot!", "F**king lost all my bank what the f*ck!", "Wow... Can't believe that happened."};

    public AutoTalkScript(BotManager AABotManager) {
        this.AABotManager = AABotManager;
        this.player = getAABotManager().getClient();
    }

    public void start() {
        if (isRunning())
            return;
        threadPool.scheduleAtFixedRate(this::sendRandomMessage, 0L, 1000, TimeUnit.MILLISECONDS);
    }

    public void startMessage() {
        if (isRunning())
            return;
        threadPool.scheduleAtFixedRate(this::sendSpecificMessage, 0L, 1000, TimeUnit.MILLISECONDS);
    }

    private void sendRandomMessage() {
        Stream chatStream = new Stream(new byte[5000]);
        player.getStream().createFrame(4);
        player.getStream().writeWordBigEndian(0);
        int j3 = player.getStream().currentOffset;
        player.getStream().method425(0);
        player.getStream().method425(0);
        chatStream.currentOffset = 0;
        Random r = new Random();
        int randomMessage = r.nextInt(stupidshit.length);
        TextInput.appendToStream(stupidshit[randomMessage], chatStream);
        player.getStream().method441(chatStream.buffer, chatStream.currentOffset);
        player.getStream().writeBytes(player.getStream().currentOffset - j3);
        chatStream.currentOffset = 0;
    }

    private void sendSpecificMessage() {
        Stream chatStream = new Stream(new byte[5000]);
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
