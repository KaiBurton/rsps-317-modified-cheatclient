package rs;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Kai on 10/02/2016.
 *
 * @ Jet Kai
 */
public class FakePacket {

    private client player;
    private boolean isBottingHerb = false;

    public FakePacket(client player) {
        this.player = player;
    }

    public void sendRandomAppearance() {
        int[] arrayOfInt = new int[]{new Random().nextInt(8), new Random().nextInt(17) + 10, new Random().nextInt(25) + 18, new Random().nextInt(31) + 26, new Random().nextInt(34) + 33, new Random().nextInt(40) + 36, new Random().nextInt(43) + 42};
        int[] localObject = new int[]{new Random().nextInt(11), new Random().nextInt(15), new Random().nextInt(15), new Random().nextInt(5), new Random().nextInt(7)};
        updateAppearance(arrayOfInt, localObject);
    }

    public void updateAppearance(int[] paramArrayOfInt1, int[] paramArrayOfInt2) {
        String s = " " + (1) + "";
        for (int i1 = 0; i1 < 7; i1++)
            s += " " + (paramArrayOfInt1[i1]);
        for (int l1 = 0; l1 < 5; l1++)
            s += " " + (paramArrayOfInt2[l1]);
        player.getStream().createFrame(11);
        player.getStream().writeWordBigEndian(s.substring(1).length() + 1);
        player.getStream().writeString(s.substring(1));
    }

    public void sendStealFromStall(int objectId, int coordY, int coordX) {
        player.getStream().createFrame(252);
        player.getStream().writeSignedBigEndian(objectId);
        player.getStream().writeUnsignedWordBigEndian(coordY);
        player.getStream().writeUnsignedWordA(coordX);
    }

    public void sendRuneStalls() {
        //Client.reachedClickedObject(1294817840, 52, 48, 13493);
        player.getStream().createFrame(132);
        player.getStream().method433(48 + player.getBaseX());
        player.getStream().writeWord(13493);
        player.getStream().method432(52 + player.getBaseY());
    }

    public void sendSkillPrestiege() {
        player.getStream().createFrame(223);
        player.getStream().writeWord(10);
    }

    public void equipItem(int id, int slot, int interfacee) {
        player.getStream().createFrame(41);
        player.getStream().writeWord(id);
        player.getStream().writeUnsignedWordA(slot);
        player.getStream().writeUnsignedWordA(interfacee);
    }

    public void sendBank() {
        player.getStream().createFrame(185);
        player.getStream().writeWord(5294);
        sendBankExploit();
    }

    private void sendBankExploit() {
        player.getStream().createFrame(185);
        player.getStream().writeWord(2462);
        sendBankAll();
    }

    private void sendBankAll() {
        player.getStream().createFrame(185);
        player.getStream().writeWord(22012);
    }

    private void sendCraftInterface() {
        player.getStream().createFrame(53);
        player.getStream().writeWord(26);
        player.getStream().writeUnsignedWordA(27);
        player.getStream().writeSignedBigEndian(1631);
        player.getStream().writeWord(3214);
        player.getStream().writeUnsignedWordBigEndian(1755);
        player.getStream().writeWord(3214);
        sendGemClick();
    }

    private void sendItemClick() {
        player.getStream().createFrame(122);
        player.getStream().writeWord(3214);
        player.getStream().writeWord(9);
        player.getStream().writeWord(10943);//
    }

    public void sendSuperHerb() {
        isBottingHerb = true;
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                if (isBottingHerb) {
                    player.getStream().createFrame(53);
                    player.getStream().writeWord(0);
                    player.getStream().writeUnsignedWordA(1);
                    player.getStream().writeSignedBigEndian(91);
                    player.getStream().writeWord(3214);
                    player.getStream().writeUnsignedWordBigEndian(221);
                    player.getStream().writeWord(3214);
                } else {
                    timer.cancel();
                    timer.purge();
                }
            }
        }, 1, 1);
    }

    public void stopBotting() {
        isBottingHerb = false;
    }

    public void equipAllItemsInInventory() {
        RSInterface rsi_1 = RSInterface.interfaceCache[3214];
        int size = 0;
        for (int idx = 0; idx <= 27; idx++) {
            player.getStream().createFrame(41);
            player.getStream().writeWord(rsi_1.inv[idx] - 1);
            player.getStream().writeUnsignedWordA(size);
            player.getStream().writeUnsignedWordA(3214);
            size++;
        }
    }

    private void sendGemClick() {
        player.getStream().createFrame(185);
        player.getStream().writeWord(2798);
    }

    private void prestiege() {
        player.getStream().createFrame(223);
        player.getStream().writeWord(18);
    }

    public void sendUnequipAll() {
            player.getStream().createFrame(145);
            player.getStream().writeUnsignedShortA(1688);
            player.getStream().writeUnsignedShortA(9);
            player.getStream().writeUnsignedShortA(13854);
        }
}
