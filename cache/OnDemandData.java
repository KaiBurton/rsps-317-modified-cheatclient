package rs.cache;

import rs.NodeSub;
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 


public final class OnDemandData extends NodeSub {

    public int dataType;
    public byte buffer[];
    public int ID;
    public boolean incomplete;
    public int loopCycle;
    public OnDemandData() {
        incomplete = true;
    }
}
