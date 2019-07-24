package dinn.pub_lib.utils;

import android.content.Context;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;

/**
 * 加密/解密  工具类
 * Created by SteffenDinn on 2019/1/15.
 */
public class EncodeUtils {
    private static String CHARSET_NAME = "UTF-8";

    /**********************************    MD5 加密/解密   *******************************/

    /**
     * MD5加密
     *
     * @param content     原文
     * @return
     */
    public static String encodeMD5(String content) {
        if (content == null) return "";
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            content = byteArrayToHexString(md.digest(content.getBytes(CHARSET_NAME)));
        } catch (Exception exception) {
        }
        return content;
    }

    private static String byteArrayToHexString(byte b[]) {
        StringBuffer resultSb = new StringBuffer();
        for (int i = 0; i < b.length; i++)
            resultSb.append(byteToHexString(b[i]));

        return resultSb.toString();
    }

    private static String byteToHexString(byte b) {
        int n = b;
        if (n < 0)
            n += 256;
        int d1 = n / 16;
        int d2 = n % 16;
        return hexDigits[d1] + hexDigits[d2];
    }

    private static final String hexDigits[] = {"0", "1", "2", "3", "4", "5",
            "6", "7", "8", "9", "a", "b", "c", "d", "e", "f"};

    /**********************************    AES 加密/解密   *******************************/

    /**
     * 加密
     *
     * @param content  原文
     * @param keyBytes 密钥
     * @return
     */
    public static String encodeAES(String content, String keyBytes) {
        //加密之后的字节数组,转成16进制的字符串形式输出
        byte[] b = encrypt(content, keyBytes);
        if (b == null) return "";
        return parseByte2HexStr(b);
    }

    /**
     * 解密
     *
     * @param contentAES 密文
     * @param keyBytes   密钥
     * @return
     */
    public static String decodeAES(String contentAES, String keyBytes) {
        //解密之前,先将输入的字符串按照16进制转成二进制的字节数组,作为待解密的内容输入
        byte[] b = decrypt(parseHexStr2Byte(contentAES), keyBytes);
        if (b == null) return "";
        return new String(b);
    }

    /**
     * 读取资源文件（AES解密）
     * @param context
     * @param resourceId 资源文件
     * @param keyBytes 密钥
     * @return
     */
    public static String readShaderFromRawResource(Context context, final int resourceId, String keyBytes) {
        final InputStream inputStream = context.getResources().openRawResource(
                resourceId);
        final InputStreamReader inputStreamReader;
        try {
            inputStreamReader = new InputStreamReader(inputStream, CHARSET_NAME);
            final BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String nextLine;
            final StringBuilder body = new StringBuilder();

            try {
                while ((nextLine = bufferedReader.readLine()) != null) {
                    body.append(nextLine);
                    body.append('n');
                }
            } catch (IOException e) {
                return null;
            }
            //EncodeUtils.decode是解密方法，BuildConfig.AES_KEY，为gradle中配置的key
            return EncodeUtils.decodeAES(body.toString(), keyBytes);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return "";

    }

    /**
     * 将二进制转换成16进制
     *
     * @param buf
     * @return
     */
    private static String parseByte2HexStr(byte buf[]) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < buf.length; i++) {
            String hex = Integer.toHexString(buf[i] & 0xFF);
            if (hex.length() == 1) {
                hex = '0' + hex;
            }
            sb.append(hex.toUpperCase());
        }
        return sb.toString();
    }

    /**
     * 将16进制转换为二进制
     *
     * @param hexStr
     * @return
     */
    private static byte[] parseHexStr2Byte(String hexStr) {
        if (hexStr.length() < 1)
            return null;
        byte[] result = new byte[hexStr.length() / 2];
        for (int i = 0; i < hexStr.length() / 2; i++) {
            int high = Integer.parseInt(hexStr.substring(i * 2, i * 2 + 1), 16);
            int low = Integer.parseInt(hexStr.substring(i * 2 + 1, i * 2 + 2),
                    16);
            result[i] = (byte) (high * 16 + low);
        }
        return result;
    }

    private static byte[] encrypt(String content, String password) {
        try {
            byte[] keyStr = getKey(password);
            SecretKeySpec key = new SecretKeySpec(keyStr, "AES");
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");//algorithmStr
            byte[] byteContent = content.getBytes("utf-8");
            cipher.init(Cipher.ENCRYPT_MODE, key);//   ʼ
            byte[] result = cipher.doFinal(byteContent);
            return result; //
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static byte[] decrypt(byte[] content, String password) {
        try {
            byte[] keyStr = getKey(password);
            SecretKeySpec key = new SecretKeySpec(keyStr, "AES");
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");//algorithmStr
            cipher.init(Cipher.DECRYPT_MODE, key);//   ʼ
            byte[] result = cipher.doFinal(content);
            return result; //
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static byte[] getKey(String password) {
        byte[] rByte = null;
        if (password != null) {
            rByte = password.getBytes();
        } else {
            rByte = new byte[24];
        }
        return rByte;
    }


}
