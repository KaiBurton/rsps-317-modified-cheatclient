package rs.bots.scripts;

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
 * Created by Kai on 02/05/2016.
 *
 * @ Jet Kai
 */
public class FollowRandomScript {

    private BotManager AABotManager;

    private ScheduledExecutorService threadPool = null;

    private client player;

    private Stream chatStream;

    private int currentLine;

    String[] justinBieber = {"Oh Whoa", "Oh Whoa", "Oh Whoa",
            "You know you love me, I know you care",
            "Just shout whenever, and I'll be there",
            "You are my love, you are my heart",
            "And we will never, ever , ever be apart",
            "Are we an item? Girl, quit playing",
            "\"Were just friends,\" what are you saying",
            "Said \"there's another,\" and looked right in my eyes",
            "My first love broke my heart for the first time",
            "And I was like baby, baby, baby, oh",
            "Like baby, baby, baby, oh",
            "Like baby, baby, baby, oh",
            "I thought you'd always be mine, mine1",
            "Baby, baby, baby, oh",
            "Like baby, baby, baby, no",
            "Like baby, baby, baby, oh",
            "I thought you'd always be mine, mine",
            "For you, I would have done whatever",
            "And I just can't believe we aren't together",
            "And I want to play it cool, but I'm losing you",
            "I'll buy you anything, I'll buy you diamond ring",
            "And I'm in pieces, baby fix me",
            "And just shake me till you wake me from this bad dream",
            "I'm going down, down, down, down",
            "And I just can't believe my first love won't be around",
            "And I'm like baby, baby, baby, oh",
            "Like baby, baby, baby, no",
            "Like baby, baby, baby, oh",
            "I thought you'd always be mine, mine",
            "Baby, baby, baby, oh",
            "Like baby, baby, baby, no",
            "Like baby, baby, baby, oh",
            "I thought you'd always be mine, mine",
            "When I was 13, I had my first love",
            "There was nobody that compared to my baby",
            "And nobody came between us who could ever come above",
            "She had me going crazy, oh I was starstruck",
            "She woke me up daily, don't need no Starbucks",
            "She made my heart pound",
            "I skip a beat when I see her in the street",
            "And at school on the playground",
            "But I really want to see her on the weekend",
            "She knows she got me dazing 'cause she was so amazing",
            "And now my heart is breaking but I just keep on saying",
            "Baby, baby, baby, oh",
            "Like baby, baby, baby, no",
            "Like baby, baby, baby, oh",
            "I thought you'd always be mine, mine",
            "Baby, baby, baby, oh",
            "Like baby, baby, baby, no",
            "Like baby, baby, baby, oh",
            "I thought you'd always be mine, mine",
            "I'm all gone",
            "(Yeah, yeah, yeah)",
            "(Yeah, yeah, yeah)",
            "Now I'm all gone",
            "(Yeah, yeah, yeah)",
            "(Yeah, yeah, yeah)",
            "Now I'm all gone",
            "Now I'm all gone, gone, gone, gone",
            "I'm gone"};

    private String[] adeleHello = new String[]{"Hello, it's me, I was wondering",
    "If after all these years you'd like to meet to go over everything",
    "They say that time's supposed to heal, yeah",
    "But I ain't done much healing",
    "Hello, can you hear me?",
    "I'm in California dreaming about who we used to be",
    "When we were younger and free",
    "I've forgotten how it felt before the world fell at our feet",
    "There's such a difference between us",
    "And a million miles",
    "Hello from the other side",
    "I must've called a thousand times",
    "To tell you I'm sorry, for everything that I've done",
    "But when I call you never seem to be home",
    "Hello from the outside",
    "At least I can say that I've tried",
    "To tell you I'm sorry, for breaking your heart",
    "But it don't matter, it clearly doesn't tear you apart anymore",
    "Hello, how are you?",
    "It's so typical of me to talk about myself, I'm sorry",
    "I hope that you're well",
    "Did you ever make it out of that town where nothing ever happened?",
    "It's no secret",
    "That the both of us are running out of time",
    "So hello from the other side",
    "I must've called a thousand times",
    "To tell you I'm sorry, for everything that I've done",
    "But when I call you never seem to be home",
    "Hello from the outside",
    "At least I can say that I've tried ",
    "To tell you I'm sorry, for breaking your heart",
    "But it don't matter, it clearly doesn't tear you apart anymore",
    "Ooh, anymore",
    "Ooh, anymore",
    "Ooh, anymore",
    "Anymore...",
    "Hello from the other side",
    "I must've called a thousand times",
    "To tell you I'm sorry, for everything that I've done",
    "But when I call you never seem to be home",
    "Hello from the outside",
    "At least I can say that I've tried",
    "To tell you I'm sorry, for breaking your heart",
    "But it don't matter, it clearly doesn't tear you apart anymore"};

    private String[] excah = new String[]{"Excah is King!", "Cane... Ain't got NUFFIN on my boy Excah."};

    private String[] snoopDogg = new String[]{"La da da da dah",
    "It's the motherfuckin' D O double G",
    "La da da da dah",
    "You know I'm mobbin' with the DRE",
    "You know who's back up in this motherfucker",
    "What what what what?",
    "Blaze it up, blaze it up",
    "Top dogg, bite me all, nigga burn the shit up",
    "DPGC my nigga turn that shit up",
    "CPT, LBC, yeah we hookin' back up",
    "And when they bang this in the club baby you got to get up",
    "Thug niggaz drug dealers yeah they givin' it up",
    "Lowlife, yo' life, boy we livin' it up",
    "Takin' chances while we dancin' in the party fo' sho'",
    "Slip my hoe a forty-fo' and she got in the back do'",
    "Bitches lookin' at me strange but you know I don't care",
    "Step up in this motherfucker just a swangin' my hair",
    "Bitch quit talkin', crip walk if you down with the set",
    "Take a bullet with some dick and take this dope from this jet",
    "Out of town, put it down for the father of rap",
    "And if yo' ass get cracked, bitch shut yo' trap",
    "Come back, get back, that's the part of success",
    "If you believe in the X you'll be relievin' your stress",
    "La da da da dah",
    "It's the motherfuckin' DRE",
    "La da da da dah",
    "You know I'm mobbin' with the DO double G",
    "Straight off the fuckin' streets of CPT",
    "King of the beats you ride to 'em in your fleet",
    "Or Coupe Deville rollin' on dubs",
    "How you feelin' whoopty, whoop nigga whut?",
    "Dre and Snoop chronic'ed out in the 'llac",
    "With Doc in the back, sippin' on 'gnac",
    "Clip in the strap, dippin' through hoods",
    "Compton, Long Beach, Inglewood",
    "South Central out to the Westside",
    "It's California love, this California bud got a nigga gang of pub",
    "I'm on one, I might bail up in the Century Club",
    "With my jeans on, and my team strong",
    "Get my drink on, and my smoke on",
    "Then go home with, somethin' to poke on",
    "Loc it's on for the two triple oh",
    "Comin' real, it's the next episode",
    "Hold up, hey",
    "For my niggaz who be thinkin' we soft",
    "We don't, play",
    "We gon' rock it 'til the wheels fall off",
    "Hold up, hey",
    "For my niggaz who be actin' too bold",
    "Take a, seat",
    "Hope you ready for the next episode",
    "Hey",
    "Smoke weed everday"};


    public FollowRandomScript(BotManager AABotManager) {
        this.AABotManager = AABotManager;
        this.player = getAABotManager().getClient();
        this.chatStream = new Stream(new byte[5000]);
    }

    public void start() {
        if (isRunning())
            return;
        final int[] TalkTime = {0};
        final int[] FollowTime = {0};
        System.out.println("Starting TalkBot");
        threadPool.scheduleAtFixedRate(() -> {
            if(TalkTime[0] == 3) {
                singSong();
                TalkTime[0] = 0;
            }
            if(FollowTime[0] == 2) {
                if(player.getPlayerList() != null)
                    player.getPlayerList().clear();
            }
            if(FollowTime[0] == 3) {
                followRandomPlayer();
                FollowTime[0] = 0;
            }
            TalkTime[0]++;
            FollowTime[0]++;
        }, 0L, 1, TimeUnit.SECONDS);
    }

    private void followRandomPlayer() {
        int randomPlayer = 0;
        if(player.getPlayerList() != null && getAABotManager().isFollowingPlayer) {
            randomPlayer = getAABotManager().followId;
        } else if(player.getPlayerList() != null && player.getPlayerList().size() != 0 && !getAABotManager().isFollowingPlayer) {
            Random r = new Random();
            int randomSize = r.nextInt(player.getPlayerList().size());
            randomPlayer = Integer.parseInt(String.valueOf(player.getPlayerList().get(randomSize)));
        }
        if(randomPlayer != 0) {
            player.getStream().createFrame(73);
            System.out.println("Following "+randomPlayer);
            player.getStream().writeUnsignedShort(randomPlayer);
        }
    }

    private void singSong() {
        if(!getAABotManager().isHushed) {
            if (currentLine < justinBieber.length && getAABotManager().songId == 0) {
                sendMessage(justinBieber[currentLine]);
                currentLine++;
            } else if (currentLine < adeleHello.length && getAABotManager().songId == 1) {
                sendMessage(adeleHello[currentLine]);
                currentLine++;
            } else if (currentLine < snoopDogg.length && getAABotManager().songId == 2) {
                sendMessage(snoopDogg[currentLine]);
                currentLine++;
            } else if (currentLine < excah.length && getAABotManager().songId == 3) {
                sendMessage(excah[currentLine]);
                currentLine++;
            } else
                currentLine = 0;
        }
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
