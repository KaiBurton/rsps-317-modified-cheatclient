package rs;// Decompiled by Jad v1.5.8f. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

import java.awt.*;

public final class RSFrame extends Frame {

    private final RSApplet rsApplet;
    private MenuBar jMenuBar;

    public RSFrame(RSApplet RSApplet_, int i, int j) {
        createMenu();
        rsApplet = RSApplet_;
        setTitle("JonnyScape Cheat Client 1.0 By Kai");
        setResizable(true);
        setVisible(true);

        setState(Frame.ICONIFIED);
        //toFront();
        setSize(i + 8, j + 28);
    }

    private void createMenu() {
       /* jMenuBar = new MenuBar();
        Menu menu = new Menu("Scripts");
        Menu menu2 = new Menu("Stop Scripts");
        Menu menu3 = new Menu("Check For Updates");
        Menu menu4 = new Menu("Add Bot +");
        menu4.addActionListener(e -> addBot());
        jMenuBar.add(menu);
        jMenuBar.add(menu2);
        jMenuBar.add(menu3);
        jMenuBar.add(menu4);
        setMenuBar(jMenuBar);*/
    }

    public void addBot() {
        System.out.println("Adding Bot");
        Menu menu = new Menu("Bot 2");
        jMenuBar.add(menu);
        menu.addActionListener(e -> {

        });
        setMenuBar(jMenuBar);
    }

    public Graphics getGraphics() {
        Graphics g = super.getGraphics();
        g.translate(4, 24);
        return g;
    }

    public void update(Graphics g) {
        rsApplet.update(g);
    }

    public void paint(Graphics g) {
        rsApplet.paint(g);
    }
}
