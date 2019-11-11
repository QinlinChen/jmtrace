package cn.edu.nju.cs;

public class Demo {

    public boolean[] az = new boolean[1];
    public char[] ac = new char[1];
    public byte[] ab = new byte[1];
    public short[] as = new short[1];
    public int[] ai = new int[1];
    public long[] al = new long[1];
    public float[] af = new float[1];
    public double[] ad = new double[1];
    public String[] astr = new String[1];

    public static boolean sz;
    public static char sc;
    public static byte sb;
    public static short ss;
    public static int si;
    public static long sl;
    public static float sf;
    public static double sd;
    public static String sstr;

    public static void main(String[] args) {
        Demo.sz = true;
        Demo.sc = 'a';
        Demo.sb = 1;
        Demo.ss = 2;
        Demo.si = 3;
        Demo.sl = 4;
        Demo.sf = 5.0f;
        Demo.sd = 6.0;
        Demo.sstr = "Hello";

        Demo obj = new Demo();
        obj.az[0] = Demo.sz;
        obj.ab[0] = Demo.sb;
        obj.ac[0] = Demo.sc;
        obj.as[0] = Demo.ss;
        obj.ai[0] = Demo.si;
        obj.al[0] = Demo.sl;
        obj.af[0] = Demo.sf;
        obj.ad[0] = Demo.sd;
        obj.astr[0] = Demo.sstr;
    }

}
