package androidx.constraintlayout.core.motion.utils;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.Socket;

public class Utils {
    public static void log(String tag, String value) {
        PrintStream printStream = System.out;
        printStream.println(tag + " : " + value);
    }

    public static void loge(String tag, String value) {
        PrintStream printStream = System.err;
        printStream.println(tag + " : " + value);
    }

    public static void socketSend(String str) {
        try {
            OutputStream out = new Socket("127.0.0.1", 5327).getOutputStream();
            out.write(str.getBytes());
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static int clamp(int c) {
        int c2 = (c & ((c >> 31) ^ -1)) - 255;
        return (c2 & (c2 >> 31)) + 255;
    }

    public int getInterpolatedColor(float[] value) {
        int r = clamp((int) (((float) Math.pow((double) value[0], 0.45454545454545453d)) * 255.0f));
        int g = clamp((int) (((float) Math.pow((double) value[1], 0.45454545454545453d)) * 255.0f));
        return (clamp((int) (value[3] * 255.0f)) << 24) | (r << 16) | (g << 8) | clamp((int) (((float) Math.pow((double) value[2], 0.45454545454545453d)) * 255.0f));
    }

    public static int rgbaTocColor(float r, float g, float b, float a) {
        int ir = clamp((int) (r * 255.0f));
        int ig = clamp((int) (g * 255.0f));
        return (clamp((int) (255.0f * a)) << 24) | (ir << 16) | (ig << 8) | clamp((int) (b * 255.0f));
    }

    public static void logStack(String msg, int n) {
        StackTraceElement[] st = new Throwable().getStackTrace();
        String s = " ";
        int n2 = Math.min(n, st.length - 1);
        for (int i = 1; i <= n2; i++) {
            StackTraceElement stackTraceElement = st[i];
            s = s + " ";
            PrintStream printStream = System.out;
            printStream.println(msg + s + (".(" + st[i].getFileName() + ":" + st[i].getLineNumber() + ") " + st[i].getMethodName()) + s);
        }
    }

    public static void log(String str) {
        StackTraceElement s = new Throwable().getStackTrace()[1];
        PrintStream printStream = System.out;
        printStream.println((".(" + s.getFileName() + ":" + s.getLineNumber() + ") " + s.getMethodName()) + " " + str);
    }
}
