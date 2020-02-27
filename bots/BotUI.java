package rs.bots;

import rs.RSApplet;
import rs.RSFrame;
import rs.client;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Kai on 20/02/2016.
 *
 * @ Jet Kai
 */
public class BotUI extends RSApplet {

    JMenuBar jMenuBar;

    BotUI() {
        super();
    }

    public static void main(String args[]) {
        BotUI botUI = new BotUI();
        //botUI.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        botUI.setVisible(true);
        botUI.createMenus();
        botUI.setSize(new Dimension(765, 503));
        botUI.setLayout(new BorderLayout());
        client client = new client();
        client.startClient();
        botUI.add(new RSFrame(botUI, 400, 400), BorderLayout.CENTER);
    }

    private void createMenus() {
        jMenuBar = new JMenuBar();
        JMenu menu = new JMenu("Scripts");
        JMenu menu2 = new JMenu("Stop Scripts");
        JMenu menu3 = new JMenu("Check For Updates");
        JMenu menu4 = new JMenu("Add Bot +");
        jMenuBar.add(menu);
        jMenuBar.add(menu2);
        jMenuBar.add(menu3);
        jMenuBar.add(menu4);
        // setJMenuBar(jMenuBar);
    }
}
