package rs;

// Decompiled by Jad v1.5.8f. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

import rs.stream.Stream;

@SuppressWarnings("all")
public final class TextInput {

    /*
     * JonnyScape's String Decoder
     */

    private static final char[] decodedString = new char[100];

   /* public static String decodeToString(int i, Stream stream) {
        int j = 0;
        int k = -1;
        for (int l = 0; l < i; l++) {
            int i1 = stream.readUnsignedByte();
            int j1 = i1 >> 4 & 0xf;
            if (k == -1) {
                if (j1 < 13)
                    decodedString[j++] = validChars[j1];
                else
                    k = j1;
            } else {
                decodedString[j++] = validChars[((k << 4) + j1) - 195];
                k = -1;
            }
            j1 = i1 & 0xf;
            if (k == -1) {
                if (j1 < 13)
                    decodedString[j++] = validChars[j1];
                else
                    k = j1;
            } else {
                decodedString[j++] = validChars[((k << 4) + j1) - 195];
                k = -1;
            }
        }
        boolean flag1 = true;
        for (int k1 = 0; k1 < j; k1++) {
            char c = decodedString[k1];
            if (flag1 && c >= 'a' && c <= 'z') {
                decodedString[k1] += '\uFFE0';
                flag1 = false;
            }
            if (c == '.' || c == '!' || c == '?' || c == '|')
                flag1 = true;
        }
        return new String(decodedString, 0, j);
    }*/
    private static final Stream stream = new Stream(new byte[100]);
    private static final char[] validChars = {' ', 'e', 't', 'a', 'o', 'i', 'h', 'n', 's', 'r', 'd', 'l', 'u', 'm', 'w', 'c', 'y', 'f', 'g', 'p', 'b', 'v', 'k', 'x', 'j', 'q', 'z', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', ' ', '!', '?', '.', ',', ':', ';', '(', ')', '-', '&', '*', '\\', '\'', '@', '#', '+', '=', '$', '%', '"', '[', ']', '_', '/', '<', '>'};

   /* public static void appendToStream(String s, Stream stream) {
        if (s.length() > 80)
            s = s.substring(0, 80);
        s = s.toLowerCase();
        int i = -1;
        for (int j = 0; j < s.length(); j++) {
            char c = s.charAt(j);
            int k = 0;
            for (int l = 0; l < validChars.length; l++) {
                if (c != validChars[l])
                    continue;
                k = l;
                break;
            }

            if (k > 12)
                k += 195;
            if (i == -1) {
                if (k < 13)
                    i = k;
                else
                    stream.writeWordBigEndian(k);
            } else if (k < 13) {
                stream.writeWordBigEndian((i << 4) + k);
                i = -1;
            } else {
                stream.writeWordBigEndian((i << 4) + (k >> 4));
                i = k & 0xf;
            }
        }
        if (i != -1)
            stream.writeWordBigEndian(i << 4);
    }*/

    public static String decodeToString(int i, Stream stream) {
        int j = 0;
        int k = -1;
        for (int l = 0; l < i; l++) {
            int i1 = stream.readJonnyScapeTextStream();
            decodedString[j++] = validChars[i1];
        }
        boolean flag1 = true;
        for (int k1 = 0; k1 < j; k1++) {
            char c = decodedString[k1];
            if (flag1 && c >= 'a' && c <= 'z') {
                decodedString[k1] += '\uFFE0';
                flag1 = false;
            }
            if (c == '.' || c == '!' || c == '?' || c == '|')
                flag1 = true;
        }
        return new String(decodedString, 0, j);
    }

    public static String jonnyScapeTextDecode(int paramInt, Stream parama) {
        int i = 0;
        for (int j = 0; j < paramInt; j++) {
            int k = parama.readJonnyScapeTextStream();
            decodedString[(i++)] = validChars[k];
        }
        int j = 1;
        for (int k = 0; k < i; k++) {
            paramInt = decodedString[k];
            if ((j != 0) && (paramInt >= 97) && (paramInt <= 122)) {
                int tmp74_72 = k;
                char[] tmp74_69 = decodedString;
                tmp74_69[tmp74_72] = ((char) (tmp74_69[tmp74_72] + 65504));
                j = 0;
            }
            if ((paramInt == 46) || (paramInt == 33) || (paramInt == 63)) {
                j = 1;
            }
        }
        return new String(decodedString, 0, i);
    }

    public static void appendToStream(String s, Stream stream) {
        if (s.length() > 80)
            s = s.substring(0, 80);
        s = s.toLowerCase();
        int i = -1;
        for (int j = 0; j < s.length(); j++) {
            char c = s.charAt(j);
            int k = 0;
            for (int l = 0; l < validChars.length; l++) {
                if (c == validChars[l]) {
                    k = l;
                    break;
                }
            }
            stream.p(k);
        }
    }

    public static String processText(String s) {
        stream.currentOffset = 0;
        appendToStream(s, stream);
        int j = stream.currentOffset;
        stream.currentOffset = 0;
        return decodeToString(j, stream);
    }

}
