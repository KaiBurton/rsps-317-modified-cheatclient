// Decompiled by Jad v1.5.8f. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3)
// Source File Name:   signlink.java

package rs.sign;

import java.applet.Applet;
import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.UUID;

public final class signlink
        implements Runnable {

    public static final int clientversion = 317;
    public static final RandomAccessFile[] cache_idx = new RandomAccessFile[5];
    public static final Applet mainapp = null;
    public static String uid;
    public static int storeid = 32;
    public static RandomAccessFile cache_dat = null;
    public static boolean sunjava;
    public static String dns = null;
    public static String midi = null;
    public static int midivol;
    public static int midifade;
    public static int wavevol;
    public static boolean reporterror = true;
    public static String errorname = "";
    private static boolean active;
    private static int threadliveid;
    private static InetAddress socketip;
    private static int socketreq;
    private static Socket socket = null;
    private static int threadreqpri = 1;
    private static Runnable threadreq = null;
    private static String dnsreq = null;
    private static String urlreq = null;
    private static DataInputStream urlstream = null;
    private static int savelen;
    private static String savereq = null;
    private static byte[] savebuf = null;
    private static boolean midiplay;
    private static int midipos;
    private static boolean waveplay;
    private static int wavepos;
    private signlink() {
    }

    public static void startpriv(InetAddress inetaddress) {
        threadliveid = (int) (Math.random() * 99999999D);
        if (active) {
            try {
                Thread.sleep(500L);
            } catch (Exception ignored) {
            }
            active = false;
        }
        socketreq = 0;
        threadreq = null;
        dnsreq = null;
        savereq = null;
        urlreq = null;
        socketip = inetaddress;
        Thread thread = new Thread(new signlink());
        thread.setDaemon(true);
        thread.start();
        while (!active)
            try {
                Thread.sleep(50L);
            } catch (Exception ignored) {
            }
    }

    public static String findcachedir() {
        return rs.client.cacheDir;
    }

    public static String findcachedirORIG() {
        String as[] = {
                "c:/windows/", "c:/winnt/", "d:/windows/", "d:/winnt/", "e:/windows/", "e:/winnt/", "f:/windows/", "f:/winnt/", "c:/", "~/",
                "/tmp/", "", "c:/rscache", "/rscache"
        };
        if (storeid < 32 || storeid > 34)
            storeid = 32;
        String s = ".file_store_" + storeid;
        for (String a : as)
            try {
                String s1 = a;
                if (s1.length() > 0) {
                    File file = new File(s1);
                    if (!file.exists())
                        continue;
                }
                File file1 = new File(s1 + s);
                if (file1.exists() || file1.mkdir())
                    return s1 + s + "/";
            } catch (Exception ignored) {
            }

        return null;

    }

    public static final String getUID2(String paramString) {
        File localFile = new File(paramString + "uid.dat");
        try {
            if ((!localFile.exists()) || (localFile.length() < 4L)) {
                BufferedWriter localBufferedWriter = new BufferedWriter(new FileWriter(localFile));
                localBufferedWriter.write(UUID.randomUUID().toString().replace("-", "").toUpperCase());
                localBufferedWriter.close();
            }
        } catch (Exception ignored) {
        }
        try {
            BufferedReader localBufferedReader = new BufferedReader(new FileReader(localFile));
            String str = localBufferedReader.readLine();
            localBufferedReader.close();
            if (Integer.parseInt(str) != -1) {
                str = "";
            }
            return str;
        } catch (Exception localException2) {
            try {
                return Z(localFile.getAbsolutePath());
            } catch (Exception ignored) {
            }
        }
        return "";
    }

    public static final byte[] append(String paramString) {
        FileInputStream localFileInputStream = null;
        try {
            localFileInputStream = new FileInputStream(paramString);
            byte[] arrayOfByte = new byte['?'];
            MessageDigest localMessageDigest = MessageDigest.getInstance("MD5");
            int i;
            do {
                i = localFileInputStream.read(arrayOfByte);
                if (i > 0) {
                    localMessageDigest.update(arrayOfByte, 0, i);
                }
            } while (i != -1);
            localFileInputStream.close();
            return localMessageDigest.digest();
        } catch (NoSuchAlgorithmException | IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static final String Z(String paramString) {
        byte[] arrayOfByte = append(paramString);
        String str = "";
        for (byte anArrayOfByte : arrayOfByte) {
            str = str + Integer.toString((anArrayOfByte & 0xFF) + 256, 16).substring(1);
        }
        return str;
    }

    private static int getuid(String s) {
        try {
            File file = new File(s + "uid.dat");
            if (!file.exists() || file.length() < 4L) {
                DataOutputStream dataoutputstream = new DataOutputStream(new FileOutputStream(s + "uid.dat"));
                dataoutputstream.writeInt((int) (Math.random() * 99999999D));
                dataoutputstream.close();
            }
        } catch (Exception ignored) {
        }
        try {
            DataInputStream datainputstream = new DataInputStream(new FileInputStream(s + "uid.dat"));
            int i = datainputstream.readInt();
            datainputstream.close();
            return i + 1;
        } catch (Exception _ex) {
            return 0;
        }
    }

    public static synchronized Socket opensocket(int i)
            throws IOException {
        for (socketreq = i; socketreq != 0; )
            try {
                Thread.sleep(50L);
            } catch (Exception ignored) {
            }

        if (socket == null)
            throw new IOException("could not open socket");
        else
            return socket;
    }

    public static synchronized DataInputStream openurl(String s)
            throws IOException {
        for (urlreq = s; urlreq != null; )
            try {
                Thread.sleep(50L);
            } catch (Exception ignored) {
            }

        if (urlstream == null)
            throw new IOException("could not open: " + s);
        else
            return urlstream;
    }

    public static synchronized void dnslookup(String s) {
        dns = s;
        dnsreq = s;
    }

    public static synchronized void startthread(Runnable runnable, int i) {
        threadreqpri = i;
        threadreq = runnable;
    }

    public static synchronized boolean wavesave(byte abyte0[], int i) {
        if (i > 0x1e8480)
            return false;
        if (savereq != null) {
            return false;
        } else {
            wavepos = (wavepos + 1) % 5;
            savelen = i;
            savebuf = abyte0;
            waveplay = true;
            savereq = "sound" + wavepos + ".wav";
            return true;
        }
    }

    public static synchronized boolean wavereplay() {
        if (savereq != null) {
            return false;
        } else {
            savebuf = null;
            waveplay = true;
            savereq = "sound" + wavepos + ".wav";
            return true;
        }
    }

    public static synchronized void midisave(byte abyte0[], int i) {
        if (i > 0x1e8480)
            return;
        if (savereq != null) {
        } else {
            midipos = (midipos + 1) % 5;
            savelen = i;
            savebuf = abyte0;
            midiplay = true;
            savereq = "jingle" + midipos + ".mid";
        }
    }

    public static void reporterror(String s) {
        System.out.println("Error: " + s);
    }

    public void run() {
        active = true;
        String s = findcachedir();
        uid = getUID2(s);
        try {
            File file = new File(s + "main_file_cache.dat");
            System.out.println(file.getAbsolutePath());
            if (file.exists() && file.length() > 0x3200000L)
                file.delete();
            cache_dat = new RandomAccessFile(s + "main_file_cache.dat", "rw");
            for (int j = 0; j < 5; j++)
                cache_idx[j] = new RandomAccessFile(s + "main_file_cache.idx" + j, "rw");

        } catch (Exception exception) {
            exception.printStackTrace();
        }
        for (int i = threadliveid; threadliveid == i; ) {
            if (socketreq != 0) {
                try {
                    socket = new Socket(socketip, socketreq);
                } catch (Exception _ex) {
                    socket = null;
                }
                socketreq = 0;
            } else if (threadreq != null) {
                Thread thread = new Thread(threadreq);
                thread.setDaemon(true);
                thread.start();
                thread.setPriority(threadreqpri);
                threadreq = null;
            } else if (dnsreq != null) {
                try {
                    dns = InetAddress.getByName(dnsreq).getHostName();
                } catch (Exception _ex) {
                    dns = "unknown";
                }
                dnsreq = null;
            } else if (savereq != null) {
                if (savebuf != null)
                    try {
                        FileOutputStream fileoutputstream = new FileOutputStream(s + savereq);
                        fileoutputstream.write(savebuf, 0, savelen);
                        fileoutputstream.close();
                    } catch (Exception ignored) {
                    }
                if (waveplay) {
                    String wave = s + savereq;
                    waveplay = false;
                }
                if (midiplay) {
                    midi = s + savereq;
                    midiplay = false;
                }
                savereq = null;
            } else if (urlreq != null) {
                try {
                    System.out.println("urlstream");
                    urlstream = new DataInputStream((new URL(mainapp.getCodeBase(), urlreq)).openStream());
                } catch (Exception _ex) {
                    urlstream = null;
                }
                urlreq = null;
            }
            try {
                Thread.sleep(50L);
            } catch (Exception ignored) {
            }
        }

    }

}