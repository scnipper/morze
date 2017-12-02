package me.creese.morze.constants;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by yoba2 on 27.11.2017.
 */

public class Alphabet {




    // ASCII codes

    public static byte A_CODE = 0x41;
    public static byte B_CODE = 0x42;
    public static byte C_CODE = 0x43;
    public static byte D_CODE = 0x44;
    public static byte E_CODE = 0x45;
    public static byte F_CODE = 0x46;
    public static byte G_CODE = 0x47;
    public static byte H_CODE = 0x48;
    public static byte I_CODE = 0x49;
    public static byte J_CODE = 0x4A;
    public static byte K_CODE = 0x4B;
    public static byte L_CODE = 0x4C;
    public static byte M_CODE = 0x4D;
    public static byte N_CODE = 0x4E;
    public static byte O_CODE = 0x4F;
    public static byte P_CODE = 0x50;
    public static byte Q_CODE = 0x51;
    public static byte R_CODE = 0x52;
    public static byte S_CODE = 0x53;
    public static byte T_CODE = 0x54;
    public static byte U_CODE = 0x55;
    public static byte V_CODE = 0x56;
    public static byte W_CODE = 0x57;
    public static byte X_CODE = 0x58;
    public static byte Y_CODE = 0x59;
    public static byte Z_CODE = 0x5A;
    public static byte SPACE_WORD = 0x4;


    // latin alphabet

    public static String A = ".-";
    public static String B = "-...";
    public static String C = "-.-.";
    public static String D = "-..";
    public static String E = ".";
    public static String F = "..-.";
    public static String G = "--.";
    public static String H = "....";
    public static String I = "..";
    public static String J = ".---";
    public static String K = "-.-";
    public static String L = ".-..";
    public static String M = "--";
    public static String N = "-.";
    public static String O = "---";
    public static String P = ".--.";
    public static String Q = "--.-";
    public static String R = ".-.";
    public static String S = "...";
    public static String T = "-";
    public static String U = "..-";
    public static String V = "...-";
    public static String W = ".--";
    public static String X = "-..-";
    public static String Y = "-.--";
    public static String Z = "--..";
    public static String SPACE = " ";

    // numbers

    public static String _0 = "-----";
    public static String _1 = ".----";
    public static String _2 = "..---";
    public static String _3 = "...--";
    public static String _4 = "....-";
    public static String _5 = ".....";
    public static String _6 = "-....";
    public static String _7 = "--...";
    public static String _8 = "---..";
    public static String _9 = "----.";

    // special

    public static String dot = "......";
    public static String comma = ".-.-.-";
    public static String colon = "---...";
    public static String semicolon = "-.-.-.";
    public static String bracket = "-.--.-";
    public static String apostrophe = ".----.";
    public static String quotes = ".-..-.";
    public static String dash = "-....-";
    public static String slash = "-..-.";
    public static String question = "..--..";
    public static String exclamation = "--..--";
    public static String section = "-...-";
    public static String error = "........";
    public static String at = ".--.-.";
    public static String end = "..-.-";


    public static Map<Character, String> cirilic = new HashMap<Character, String>();


    public static String SOS = S+SPACE+O+SPACE+S;
}
