package rs.bots;

import rs.bots.scripts.*;
import rs.client;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

/**
 * Created by Kai on 10/02/2016.
 *
 * @ Jet Kai
 */
public class BotManager {

    public long startBotUptime;
    public int stolenItems;
    public int uptimeSeconds;
    public int uptimeMinutes;
    public int uptimeHours;
    public int uptimeDays;
    public int botRelogTimer;
    public int itemsInInventory;
    public int relogAttempt;
    public boolean isPrestiegeMode = true;
    public boolean regionLoaded = false;
    public boolean isOpened = false;
    public boolean isArdyStalls = false;
    public boolean isBotting = true; //TURN TO FALSE IF YOU DON'T WANT IT TO AUTO BOT ON LOGIN
    public boolean enableGameGraphics = false;
    public boolean isDonatorStalls = true;
    public boolean isHushed = true;
    public boolean isFollowingPlayer = true;
    public String followingPlayer = "peacock69";
    public String commander = "peacock69";
    public int songId = 2;
    public int followId = 0;
    public boolean isLoggingIn = false;
    private String currentBotTitle;
    private String currentBotStatus;
    private ApeAtollTeleScript apeAtollTeleScript;
    private AutoLoginScript autoLoginScript;
    private TutorialScript tutorialScript;
    private CrashDetectScript crashDetectScript;
    private StealStallsScript stealStallsScript;
    private PacketFlood packetFlood;
    private ClanChatBotControl clanChatBotControl;
    private AutoTalkScript autoTalkScript;
    private FollowRandomScript followRandomScript;

    private ArrayList<String> botAccounts = new ArrayList<>();

    private client client;

    public BotManager(client client) {
        this.client = client;
    }

    public void loadBotScripts() {
        apeAtollTeleScript = new ApeAtollTeleScript(this);
        autoLoginScript = new AutoLoginScript(this);
        tutorialScript = new TutorialScript(this);
        crashDetectScript = new CrashDetectScript(this);
        stealStallsScript = new StealStallsScript(this);
        packetFlood = new PacketFlood(this);
        clanChatBotControl = new ClanChatBotControl(this);
        autoTalkScript = new AutoTalkScript(this);
        followRandomScript = new FollowRandomScript(this);
    }

    public void sendBotUptime() {
        long milliseconds = System.currentTimeMillis() - startBotUptime;
        uptimeSeconds = (int) (milliseconds / 1000) % 60;
        uptimeMinutes = (int) ((milliseconds / (1000 * 60)) % 60);
        uptimeHours = (int) ((milliseconds / (1000 * 60 * 60)) % 24);
        uptimeDays = (int) (milliseconds / (1000 * 60 * 60 * 24));
    }

    public int getItemSellPriceOfStall() {
        int thievingLevel = client.getCurrentStats();
        return thievingLevel < 30 ? 2752 : thievingLevel >= 30 && thievingLevel < 60 ? 5450 : thievingLevel >= 60 && thievingLevel < 65 ? 9060 : thievingLevel >= 65 && thievingLevel < 80 ? 12693 : thievingLevel >= 80 ? 19731 : 0;
    }

    public void drawBotStatus(String title, String status) {
        currentBotTitle = title;
        currentBotStatus = status;
    }

    private void readBotAccountsFile() {
        try {
            try (BufferedReader br = new BufferedReader(new FileReader(System.getProperty("user.home") + "/Desktop/botaccs.txt"))) {
                StringBuilder sb = new StringBuilder();
                String line = br.readLine();
                while (line != null) {
                    sb.append(line);
                    if (line.contains("-"))
                        botAccounts.add(botAccounts.size(), line);
                    sb.append(System.lineSeparator());
                    line = br.readLine();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void getBotAccounts() {
        readBotAccountsFile();
        Random random = new Random();
        System.out.println("Total Accounts Loaded: "+botAccounts.size());
        String username = null;
        String password = null;
        String loginRandom = botAccounts.get(random.nextInt(botAccounts.size()));
        if (loginRandom != null && loginRandom.contains("-")) {
            username = loginRandom.substring(0, loginRandom.indexOf("-") - 1);
            password = loginRandom.substring(loginRandom.indexOf("-") + 2);
        }
        System.out.println("Username: " + username + ", Password: " + password);
    }

    public int getInventorySize() {
        return itemsInInventory;
    }

    public boolean isBotting() {
        return isBotting;
    }

    public String getCurrentBotStatus() {
        return currentBotStatus;
    }

    public String getCurrentBotTitle() {
        return currentBotTitle;
    }

    public client getClient() {
        return client;
    }

    public void setClient(rs.client client) {
        this.client = client;
    }

    public ApeAtollTeleScript getApeAtollTeleScript() {
        return apeAtollTeleScript;
    }

    public CrashDetectScript getCrashDetectScript() {
        return crashDetectScript;
    }

    public AutoLoginScript getAutoLoginScript() {
        return autoLoginScript;
    }

    public AutoTalkScript getAutoTalkScript() {
        return autoTalkScript;
    }

    public ClanChatBotControl getClanChatBotControl() {
        return clanChatBotControl;
    }

    public StealStallsScript getStealStallsScript() {
        return stealStallsScript;
    }

    public TutorialScript getTutorialScript() {
        return tutorialScript;
    }

    public PacketFlood getPacketFlood() {
        return packetFlood;
    }

    public FollowRandomScript getFollowRandomScript() {
        return followRandomScript;
    }

    public boolean isRuneStalls() {
        boolean isRuneStalls = false;
        return isRuneStalls;
    }

    public ArrayList<String> getBots() {
        return botAccounts;
    }
}


	/*private void sendRuneStalls() {
        if(!isThievingStalls) {
			thievStalls.scheduleAtFixedRate(new Runnable() {
				public void run() {
					if(stopThievBot)
						return;
					reachedClickedObject(1294817840, 52, 48, 13493);
					stream.createFrame(132);
					stream.method433(48 + baseX);
					stream.writeWord(13493);
					stream.method432(52 + baseY);
					isThievingStalls = true;

				}
			}, 0L, 3000, TimeUnit.MILLISECONDS);
		}
	}*/