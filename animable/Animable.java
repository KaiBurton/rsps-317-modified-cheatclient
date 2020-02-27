package rs.animable;

import rs.Class33;
import rs.NodeSub;
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

public class Animable extends NodeSub {

    public Class33 aClass33Array1425[];
    public int modelHeight;

    Animable() {
        modelHeight = 1000;
    }

    public void method443(int i, int j, int k, int l, int i1, int j1, int k1,
                          int l1, int i2) {
        Model model = getRotatedModel();
        if (model != null) {
            modelHeight = model.modelHeight;
            model.method443(i, j, k, l, i1, j1, k1, l1, i2);
        }
    }

    Model getRotatedModel() {
        return null;
    }
}
