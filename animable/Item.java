package rs.animable;

import rs.config.ItemDef;
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

public final class Item extends Animable {

    public int ID;
    public int x;
    public int y;
    public int anInt1559;
    public Item() {
    }

    public final Model getRotatedModel() {
        ItemDef itemDef = ItemDef.forID(ID);
        return itemDef.method201(anInt1559);
    }
}
