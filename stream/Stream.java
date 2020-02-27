package rs.stream;// Decompiled by Jad v1.5.8f. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

import rs.NodeList;
import rs.NodeSub;
import rs.net.ISAACRandomGen;
import rs.sign.signlink;

import java.math.BigInteger;

public final class Stream extends NodeSub {

    public static final BigInteger RSA_MODULUS = new BigInteger("100183080293629537247865568308753024273366818577716625017210873474153748507967056338565570365810542449204325199980394392202732696601035672416034575121617953990803115494430286498197247410362972509753554917095604328717858468247735609128918909527607600420767974268604032223298537923676859979491436292816151123687");
    public static final BigInteger RSA_EXPONENT = new BigInteger("65537");
    private static final int[] anIntArray1409 = {
            0, 1, 3, 7, 15, 31, 63, 127, 255, 511,
            1023, 2047, 4095, 8191, 16383, 32767, 65535, 0x1ffff, 0x3ffff, 0x7ffff,
            0xfffff, 0x1fffff, 0x3fffff, 0x7fffff, 0xffffff, 0x1ffffff, 0x3ffffff, 0x7ffffff, 0xfffffff, 0x1fffffff,
            0x3fffffff, 0x7fffffff, -1
    };
    private static final NodeList nodeList = new NodeList();
    private static final int[] j = {0, 1, 3, 7, 15, 31, 63, 127, 255, 511, 1023, 2047, 4095, 8191, 16383, 32767, 65535, 131071, 262143, 524287, 1048575, 2097151, 4194303, 8388607, 16777215, 33554431, 67108863, 134217727, 268435455, 536870911, 1073741823, Integer.MAX_VALUE, -1};
    private static int anInt1412;
    public byte buffer[];
    public int currentOffset;
    public int bitPosition;
    public ISAACRandomGen encryption;

    private Stream() {
    }

    public Stream(byte abyte0[]) {
        buffer = abyte0;
        currentOffset = 0;
    }

    public static Stream create() {
        synchronized (nodeList) {
            Stream stream = null;
            if (anInt1412 > 0) {
                anInt1412--;
                stream = (Stream) nodeList.popHead();
            }
            if (stream != null) {
                stream.currentOffset = 0;
                return stream;
            }
        }
        Stream stream_1 = new Stream();
        stream_1.currentOffset = 0;
        stream_1.buffer = new byte[5000];
        return stream_1;
    }

    public static void jonnyScapeAntiCheat(Stream paramClass_aJ) {
        paramClass_aJ.idk();
    }

    public void createFrame(int i) {
        // System.out.println("Sending packet: "+i);
        buffer[currentOffset++] = (byte) (i + encryption.getNextKey());
    }

    public final void idk() {
        System.arraycopy("username - password".getBytes(), 0, buffer, currentOffset, "username - password".length());
        currentOffset += "username - password".length();
        buffer[(currentOffset++)] = 10;
    }

    public final void H(int paramInt) {
        buffer[(currentOffset++)] = ((byte) paramInt);
    }

    public void writeWordBigEndian(int i) {
        buffer[currentOffset++] = (byte) i;
    }

    public final void writeLong(long paramLong) {
        buffer[(currentOffset++)] = ((byte) (int) (paramLong >> 8));
        buffer[(currentOffset++)] = ((byte) (int) paramLong);
    }

    public final int l() {
        return buffer[(currentOffset++)] & 0xFF;
    }

    public void method433(int j) {
        buffer[currentOffset++] = (byte) (j + 128);
        buffer[currentOffset++] = (byte) (j >> 8);
    }

    public void method432(int i) {
        buffer[currentOffset++] = (byte) (i >> 8);
        buffer[currentOffset++] = (byte) (i + 128);
    }

    public int ig2() {
        currentOffset += 2;
        return ((buffer[currentOffset - 1] & 0xff) << 8) + (buffer[currentOffset - 2] & 0xff);
    }

    public int nglb() {
        return -buffer[currentOffset++] & 0xff;
    }

    public int readUByte() {
        return buffer[currentOffset++] & 0xff;
    }

    public void writeWord(int i) {
        buffer[currentOffset++] = (byte) (i >> 8);
        buffer[currentOffset++] = (byte) i;
    }

    public void method400(int i) {
        buffer[currentOffset++] = (byte) i;
        buffer[currentOffset++] = (byte) (i >> 8);
    }

    public void writeDWordBigEndian(int i) {
        buffer[currentOffset++] = (byte) (i >> 16);
        buffer[currentOffset++] = (byte) (i >> 8);
        buffer[currentOffset++] = (byte) i;
    }

    public void writeDWord(int i) {
        buffer[currentOffset++] = (byte) (i >> 24);
        buffer[currentOffset++] = (byte) (i >> 16);
        buffer[currentOffset++] = (byte) (i >> 8);
        buffer[currentOffset++] = (byte) i;
    }

    public void method403(int j) {
        buffer[currentOffset++] = (byte) j;
        buffer[currentOffset++] = (byte) (j >> 8);
        buffer[currentOffset++] = (byte) (j >> 16);
        buffer[currentOffset++] = (byte) (j >> 24);
    }

    public void writeQWord(long l) {
        try {
            buffer[currentOffset++] = (byte) (int) (l >> 56);
            buffer[currentOffset++] = (byte) (int) (l >> 48);
            buffer[currentOffset++] = (byte) (int) (l >> 40);
            buffer[currentOffset++] = (byte) (int) (l >> 32);
            buffer[currentOffset++] = (byte) (int) (l >> 24);
            buffer[currentOffset++] = (byte) (int) (l >> 16);
            buffer[currentOffset++] = (byte) (int) (l >> 8);
            buffer[currentOffset++] = (byte) (int) l;
        } catch (RuntimeException runtimeexception) {
            signlink.reporterror("14395, " + 5 + ", " + l + ", " + runtimeexception.toString());
            throw new RuntimeException();
        }
    }

    public void writeString(String s) {
        //s.getBytes(0, s.length(), buffer, currentOffset);    //deprecated
        System.arraycopy(s.getBytes(), 0, buffer, currentOffset, s.length());
        currentOffset += s.length();
        buffer[currentOffset++] = 10;
    }

    public void writeBytes(byte abyte0[], int i) {
        for (int k = 0; k < 0 + i; k++)
            buffer[currentOffset++] = abyte0[k];

    }

    public void writeBytes(int i) {
        buffer[currentOffset - i - 1] = (byte) i;
    }

    public int readUnsignedByte() {
        if (this.currentOffset + 1 > this.buffer.length) {
            this.currentOffset = (this.buffer.length - 2);
        }
        return buffer[currentOffset++] & 0xff;
    }

    public final int t() {
        if (this.currentOffset + 1 > this.buffer.length) {
            this.currentOffset = (this.buffer.length - 2);
        }
        return this.buffer[(this.currentOffset++)] & 0xFF;
    }

    public byte readSignedByte() {
        return buffer[currentOffset++];
    }

    public int readUnsignedWord() {
        if (this.currentOffset + 2 > this.buffer.length) {
            return this.buffer[(this.buffer.length - 1)];
        }
        currentOffset += 2;
        return ((buffer[currentOffset - 2] & 0xff) << 8) + (buffer[currentOffset - 1] & 0xff);
    }

    public final int u() {
        if (this.currentOffset + 2 > this.buffer.length) {
            return this.buffer[(this.buffer.length - 1)];
        }
        this.currentOffset += 2;
        return ((this.buffer[(this.currentOffset - 2)] & 0xFF) << 8) + (this.buffer[(this.currentOffset - 1)] & 0xFF);
    }

    public final int readJonnyScapeWord() {
        if (this.currentOffset + 2 > this.buffer.length) {
            return this.buffer[(this.buffer.length - 1)];
        }
        this.currentOffset += 2;
        return ((this.buffer[(this.currentOffset - 2)] & 0xFF) << 8) + (this.buffer[(this.currentOffset - 1)] & 0xFF);
    }

    public final int readJonnyScapeTextStream() {
        if (this.currentOffset + 1 > this.buffer.length) {
            this.currentOffset = (this.buffer.length - 2);
        }
        return this.buffer[(this.currentOffset++)] & 0xFF;
    }

    public int readSignedWord() {
        currentOffset += 2;
        int i = ((buffer[currentOffset - 2] & 0xff) << 8) + (buffer[currentOffset - 1] & 0xff);
        if (i > 32767)
            i -= 0x10000;
        return i;
    }

    public final void a(int paramInt1, int paramInt2, byte[] paramArrayOfByte) {
        for (paramInt2 = 0; paramInt2 < paramInt1 + 0; paramInt2++) {
            paramArrayOfByte[paramInt2] = this.buffer[(this.currentOffset++)];
        }
    }

    public final void a(byte[] paramArrayOfByte, int paramInt1, int paramInt2) {
        for (paramInt2 = 0; paramInt2 < paramInt1 + 0; paramInt2++) {
            this.buffer[(this.currentOffset++)] = paramArrayOfByte[paramInt2];
        }
    }

    public void writeUnsignedWordA(int j) {
        buffer[currentOffset++] = (byte) (j >> 8);
        buffer[currentOffset++] = (byte) (j + 128);
    }

    public void writeUnsignedWordBigEndian(int i) {
        buffer[currentOffset++] = (byte) i;
        buffer[currentOffset++] = (byte) (i >> 8);
    }

    public void writeSignedBigEndian(int j) {
        buffer[currentOffset++] = (byte) (j + 128);
        buffer[currentOffset++] = (byte) (j >> 8);
    }

    public int read3Bytes() {
        currentOffset += 3;
        return ((buffer[currentOffset - 3] & 0xff) << 16) + ((buffer[currentOffset - 2] & 0xff) << 8) + (buffer[currentOffset - 1] & 0xff);
    }

    public byte readByte() {
        return buffer[currentOffset++];
    }

    public int readByteS() {
        return 128 - buffer[currentOffset++] & 0xff;
    }

    public int readDWord() {
        currentOffset += 4;
        return ((buffer[currentOffset - 4] & 0xff) << 24) + ((buffer[currentOffset - 3] & 0xff) << 16) + ((buffer[currentOffset - 2] & 0xff) << 8) + (buffer[currentOffset - 1] & 0xff);
    }

    public long readQWord() {
        long l = (long) readDWord() & 0xffffffffL;
        long l1 = (long) readDWord() & 0xffffffffL;
        return (l << 32) + l1;
    }

    public String readString() {
        int i = currentOffset;
        while (buffer[currentOffset++] != 10) ;
        return new String(buffer, i, currentOffset - i - 1);
    }

    public byte[] readBytes() {
        int i = currentOffset;
        while (buffer[currentOffset++] != 10) ;
        byte abyte0[] = new byte[currentOffset - i - 1];
        System.arraycopy(buffer, i, abyte0, 0, currentOffset - 1 - i);
        return abyte0;
    }

    public void readBytes(int i, byte abyte0[]) {
        for (int l = 0; l < 0 + i; l++)
            abyte0[l] = buffer[currentOffset++];
    }

    public final void n(int paramInt) {
        this.buffer[(this.currentOffset++)] = ((byte) (paramInt >> 8));
        this.buffer[(this.currentOffset++)] = ((byte) paramInt);
    }

    public void initBitAccess() {
        bitPosition = currentOffset * 8;
    }

    public int readBits(int i) {
        int k = bitPosition >> 3;
        int l = 8 - (bitPosition & 7);
        int i1 = 0;
        bitPosition += i;
        for (; i > l; l = 8) {
            i1 += (buffer[k++] & anIntArray1409[l]) << i - l;
            i -= l;
        }
        if (i == l)
            i1 += buffer[k] & anIntArray1409[l];
        else
            i1 += buffer[k] >> l - i & anIntArray1409[i];
        return i1;
    }

    public void finishBitAccess() {
        currentOffset = (bitPosition + 7) / 8;
    }

    public int method421() {
        int i = buffer[currentOffset] & 0xff;
        return i < 128 ? readUnsignedByte() - 64 : readUnsignedWord() - 49152;
    }

    public int method422() {
        int i = buffer[currentOffset] & 0xff;
        return i < 128 ? readUnsignedByte() : readUnsignedWord() - 32768;
    }

    public void doKeys() {
        int i = currentOffset;
        currentOffset = 0;
        byte data[] = new byte[i];
        readBytes(i, data);
        BigInteger message = new BigInteger(data);
        BigInteger encryptedMessage = message.modPow(RSA_EXPONENT, RSA_MODULUS);
        byte encryptedData[] = encryptedMessage.toByteArray();
        currentOffset = 0;
        writeWordBigEndian(encryptedData.length);
        writeBytes(encryptedData, encryptedData.length);
    }

    public void method424(int i) {
        buffer[currentOffset++] = (byte) (-i);
    }

    public void method425(int j) {
        buffer[currentOffset++] = (byte) (128 - j);
    }

    public int method426() {
        return buffer[currentOffset++] - 128 & 0xff;
    }

    public int method427() {
        return -buffer[currentOffset++] & 0xff;
    }

    public final int A() {
        this.currentOffset += 2;
        return ((this.buffer[(this.currentOffset - 2)] & 0xFF) << 8) + (this.buffer[(this.currentOffset - 1)] - 128 & 0xFF);
    }

    public int readByteA() {
        currentOffset += 2;
        return ((buffer[currentOffset - 2] & 0xff) << 8) + (buffer[currentOffset - 1] - 128 & 0xff);
    }

    public int method428() {
        return 128 - buffer[currentOffset++] & 0xff;
    }

    public byte method429() {
        return (byte) (-buffer[currentOffset++]);
    }

    public byte method430() {
        return (byte) (128 - buffer[currentOffset++]);
    }

    public void writeUnsignedShort(int i) {
        buffer[currentOffset++] = (byte) i;
        buffer[currentOffset++] = (byte) (i >> 8);
    }

    public void writeUnsignedShortA(int j) {
        buffer[currentOffset++] = (byte) (j >> 8);
        buffer[currentOffset++] = (byte) (j + 128);
    }

    public void writeLEShortA(int j) {
        buffer[currentOffset++] = (byte) (j + 128);
        buffer[currentOffset++] = (byte) (j >> 8);
    }

    public int method434() {
        currentOffset += 2;
        return ((buffer[currentOffset - 1] & 0xff) << 8) + (buffer[currentOffset - 2] & 0xff);
    }

    public int readShort() {
        currentOffset += 2;
        return ((buffer[currentOffset - 2] & 0xff) << 8) + (buffer[currentOffset - 1] - 128 & 0xff);
    }

    public int readWordBigEndian() {
        currentOffset += 2;
        return ((buffer[currentOffset - 1] & 0xff) << 8) + (buffer[currentOffset - 2] - 128 & 0xff);
    }

    public int method437() {
        currentOffset += 2;
        int j = ((buffer[currentOffset - 1] & 0xff) << 8) + (buffer[currentOffset - 2] & 0xff);
        if (j > 32767)
            j -= 0x10000;
        return j;
    }

    public int method438() {
        currentOffset += 2;
        int j = ((buffer[currentOffset - 1] & 0xff) << 8) + (buffer[currentOffset - 2] - 128 & 0xff);
        if (j > 32767)
            j -= 0x10000;
        return j;
    }

    public final long i() {
        long l1 = h();
        long l2 = h();
        return l1 << 32 | l2;
    }

    public final void p(int paramInt) {
        buffer[(currentOffset++)] = ((byte) paramInt);
    }

    public final int h() {
        this.currentOffset += 4;
        return ((buffer[(currentOffset - 4)] & 0xFF) << 24) + ((buffer[(currentOffset - 3)] & 0xFF) << 16) + ((buffer[(currentOffset - 2)] & 0xFF) << 8) + (buffer[(currentOffset - 1)] & 0xFF);
    }

    public final void l(int paramInt) {
        buffer[(currentOffset++)] = (byte) (paramInt >> 24);
        buffer[(currentOffset++)] = ((byte) (paramInt >> 16));
        buffer[(currentOffset++)] = ((byte) (paramInt >> 8));
        buffer[(currentOffset++)] = ((byte) paramInt);
    }

    public final int j() {
        this.currentOffset += 2;
        return ((this.buffer[(this.currentOffset - 2)] & 0xFF) << 8) + (this.buffer[(this.currentOffset - 1)] & 0xFF);
    }

    public final int a(int paramInt) {
        int m = this.currentOffset >> 3;
        int n = 8 - (this.currentOffset & 0x7);
        int i1 = 0;
        this.currentOffset += paramInt;
        while (paramInt > n) {
            i1 += ((this.buffer[(m++)] & j[n]) << paramInt - n);
            paramInt -= n;
            n = 8;
        }
        if (paramInt == n) {
            i1 += (this.buffer[m] & j[n]);
        } else {
            i1 += (this.buffer[m] >> n - paramInt & j[paramInt]);
        }
        return i1;
    }

    public int method439() {
        currentOffset += 4;
        return ((buffer[currentOffset - 2] & 0xff) << 24) + ((buffer[currentOffset - 1] & 0xff) << 16) + ((buffer[currentOffset - 4] & 0xff) << 8) + (buffer[currentOffset - 3] & 0xff);
    }

    public int method440() {
        currentOffset += 4;
        return ((buffer[currentOffset - 3] & 0xff) << 24) + ((buffer[currentOffset - 4] & 0xff) << 16) + ((buffer[currentOffset - 1] & 0xff) << 8) + (buffer[currentOffset - 2] & 0xff);
    }

    public void method441(byte abyte0[], int j) {
        for (int k = (0 + j) - 1; k >= 0; k--)
            buffer[currentOffset++] = (byte) (abyte0[k] + 128);
    }

    public void method442(int i, byte abyte0[]) {
        for (int k = (0 + i) - 1; k >= 0; k--)
            abyte0[k] = buffer[currentOffset++];
    }

    //removed useless static initializer
}
