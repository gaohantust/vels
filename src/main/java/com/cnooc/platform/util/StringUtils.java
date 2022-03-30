package com.cnooc.platform.util;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.*;
import java.math.BigInteger;
import java.security.KeyFactory;
import java.security.MessageDigest;
import java.security.spec.RSAPrivateKeySpec;
import java.security.spec.RSAPublicKeySpec;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 描述：
 *
 * @author TONG
 * @Version v1.0
 * @date 2020/11/9 13:29
 */
public class StringUtils {
    public static boolean isEmpty(String str) {
        if (str == null) {
            return true;
        }
        if (str.trim().equals("")) {
            return true;
        }
        return false;
    }

    public static boolean isNotEmpty(String str) {
        return !isEmpty(str);
    }

    public static boolean isInteger(String s) {
        boolean rtn = validByRegex("^[-+]{0,1}\\d*$", s);
        return rtn;
    }

    public static boolean isEmail(String s) {
        boolean rtn = validByRegex(
                "(\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*)*", s);
        return rtn;
    }

    public static boolean isMobile(String s) {
        boolean rtn = validByRegex(
                "^(((13[0-9]{1})|(15[0-9]{1})|(17[0-9]{1})|(18[0-9]{1}))+\\d{8})$",
                s);
        return rtn;
    }

    public static boolean isPhone(String s) {
        boolean rtn = validByRegex(
                "(0[0-9]{2,3}\\-)?([2-9][0-9]{6,7})+(\\-[0-9]{1,4})?", s);
        return rtn;
    }

    public static boolean validByRegex(String regex, String input) {
        Pattern p = Pattern.compile(regex, 2);
        Matcher regexMatcher = p.matcher(input);
        return regexMatcher.find();
    }

    public static String getMD5(String str) throws Exception {
        try {
            // 生成一个MD5加密计算摘要
            MessageDigest md = MessageDigest.getInstance("MD5");
            // 计算md5函数
            md.update(str.getBytes());
            // digest()最后确定返回md5 hash值，返回值为8为字符串。因为md5 hash值是16位的hex值，实际上就是8位的字符
            // BigInteger函数则将8位的字符串转换成16位hex值，用字符串来表示；得到字符串形式的hash值
            return new BigInteger(1, md.digest()).toString(16);
        } catch (Exception e) {
            throw new Exception("MD5加密出现错误");
        }
    }

    public static String maxDES(String password) {
        String encryptedPassword = "";
        byte[] bytes = encData(password);
        for (int i = 0; i < bytes.length; i++) {
            int b = bytes[i];
            String hex = Integer.toHexString(b).toUpperCase();
            hex = hex.replaceAll("FFFFFF", "");
            hex = (hex.length() < 2) ? "0" + hex : hex;
            encryptedPassword += hex;
        }
        return encryptedPassword;
    }
    public static String unMaxDES(String password){
        byte[] bytes = StrToBytes(password);
        byte[] encryptVal = null;
        try {
            encryptVal = buildCipher(false).doFinal(bytes);
        } catch (Exception e) {
            e.printStackTrace();
        }
        String decryptVal = new String(encryptVal);
        return decryptVal;
    }
    public static byte[] StrToBytes(String str) {
        int len = str.length();
        if (len == 0 || len % 2 == 1)
            return null;
        byte[] b = new byte[len / 2];
        for (int i = 0; i < str.length(); i += 2) {
            b[i / 2] = (byte) Integer.decode("0x" + str.substring(i, i + 2))
                    .intValue();
        }
        return b;
    }

    public static synchronized byte[] encData(String in) {
        byte[] temp = in.getBytes();
        temp = pad(temp);
        byte[] encryptVal = null;
        try {
            encryptVal = buildCipher(false).doFinal(temp);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return encryptVal;
    }

    private static byte[] pad(byte[] in) {
        return pad(in, 8);
    }

    private static byte[] pad(byte[] in, int padLen) {
        if (padLen == 0) {
            return in;
        }
        int inlen = in.length;
        int outlen = inlen;
        int rem = inlen % padLen;
        if (rem > 0) {
            outlen = inlen + padLen - rem;
        }
        byte[] out = new byte[outlen];
        for (int xx = 0; xx < inlen; ++xx) {
            out[xx] = in[xx];
        }
        return out;
    }

    private static Cipher buildCipher(boolean is) throws Exception {
        Cipher cipher = null;
        String key = "Sa#qk5usfmMI-@2dbZP9`jL3";
        String spec = "beLd7$lB";
        int cryptMode = 1;
        DESedeKeySpec keySpec = new DESedeKeySpec(key.getBytes());
        SecretKeyFactory factory = SecretKeyFactory.getInstance("DESede");
        SecretKey secretKey = factory.generateSecret(keySpec);
        IvParameterSpec ivSpec = new IvParameterSpec(spec.getBytes());
        String transformation = "DESede/CBC/PKCS5Padding";
        cipher = Cipher.getInstance(transformation);
        cipher.init(cryptMode, secretKey, ivSpec);
        return cipher;
    }
    public static String decToHex(int dec) {
        StringBuilder hexdecimalNumber = new StringBuilder();
        int length = 8;
        int[] arrayNumber = new int[length];
        for (int i = 0; i < length; i++) {
            arrayNumber[i] = (dec & (0xF << i * 4)) >>> i * 4;
        }

        for (int i = 1; i >= 0; i--) {
            switch (arrayNumber[i]) {
                case 0:
                    hexdecimalNumber.append("0");
                    break;
                case 10:
                    hexdecimalNumber.append("A");
                    break;
                case 11:
                    hexdecimalNumber.append("B");
                    break;
                case 12:
                    hexdecimalNumber.append("C");
                    break;
                case 13:
                    hexdecimalNumber.append("D");
                    break;
                case 14:
                    hexdecimalNumber.append("E");
                    break;
                case 15:
                    hexdecimalNumber.append("F");
                    break;
                default:
                    hexdecimalNumber.append(Integer.toString(arrayNumber[i]));
            }
        }
        return hexdecimalNumber.toString();
    }

    public static void main(String[] args) throws Exception {
        String str = "123456";
        System.out.println(maxDES(str));
        String str1="5C6E5BBD744F37B2A4F342FA1241DB14E236F626E47CC7E7";
        //System.out.println(unMaxDES(str1));
    }
}
