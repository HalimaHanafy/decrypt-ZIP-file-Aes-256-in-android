package com.example.halimahanafy.aes_halima;

import android.os.Environment;
import android.util.Base64;

import org.bouncycastle.crypto.BlockCipher;
import org.bouncycastle.crypto.InvalidCipherTextException;
import org.bouncycastle.crypto.engines.RijndaelEngine;
import org.bouncycastle.crypto.paddings.PaddedBufferedBlockCipher;
import org.bouncycastle.crypto.paddings.ZeroBytePadding;
import org.bouncycastle.crypto.params.KeyParameter;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;

/**
 * Created by halimahanafy on 16/10/16.
 */

public class Decrypt {

    static String PATH= Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)+"/";
    static String KEY="fa03ada189aa8d097c86bed2ba74f7a1";


    public static String decryptFile(String filename) {
        try {

            String data= getFileContent(PATH +filename);
            String decryptedData=decrypt(KEY,data);
            byte[] decryptecData_decode64=decodeBase64(decryptedData);
            String decryptedFileLink=creatFile(decryptecData_decode64,"zzs.zip");

            return decryptedFileLink;

        } catch (Exception e) {
            e.printStackTrace();
            return e.getMessage();
        }
    }


    public static String decrypt(String key,String data) throws InvalidCipherTextException, IOException {

        byte[] givenKey = key.getBytes(Charset.forName("ASCII"));
        final int keysize = 256;
        byte[] keyData = new byte[keysize / Byte.SIZE];
        System.arraycopy(givenKey, 0, keyData, 0, Math.min(givenKey.length, keyData.length));
        KeyParameter keys = new KeyParameter(keyData);
        BlockCipher rijndael = new RijndaelEngine(256);
        ZeroBytePadding c = new ZeroBytePadding();
        PaddedBufferedBlockCipher pbbc = new PaddedBufferedBlockCipher(rijndael, c);
        byte[] dataaa = Base64.decode(data, Base64.DEFAULT);
        pbbc.init(false, keys);
        byte[] decrypted = new byte[pbbc.getOutputSize(dataaa.length)];

        int offset = 0;
        offset += pbbc.processBytes(dataaa, 0, dataaa.length, decrypted, offset);
        try {
            offset += pbbc.doFinal(decrypted, offset);
        } catch (InvalidCipherTextException e) {
            e.printStackTrace();
        }

        String DecrtptedData = new String(decrypted, Charset.forName("UTF8")).replaceAll("\\x00+$", "");

        byte[] fileContentBase64 = decodeBase64(DecrtptedData);


        return DecrtptedData;
    }

    public static String creatFile(byte[] data, String fileName) throws IOException {
        File extStore = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
        FileOutputStream out = new FileOutputStream(extStore+"/"+fileName);
        out.write(data);
        out.close();
        return extStore+"/"+fileName;
    }

    private static byte[] decodeBase64(String fileContent) throws UnsupportedEncodingException {

        // Receiving side
        byte[] data = Base64.decode(fileContent, Base64.DEFAULT);
//        String text = new String(data, "UTF-8");
        return data;

    }

    public String MD5(String md5) {
        try {
            java.security.MessageDigest md = java.security.MessageDigest.getInstance("MD5");
            byte[] array = md.digest(md5.getBytes());
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < array.length; ++i) {
                sb.append(Integer.toHexString((array[i] & 0xFF) | 0x100).substring(1,3));
            }
            return sb.toString();
        } catch (java.security.NoSuchAlgorithmException e) {
        }
        return null;
    }



    public static String getFileContent (String filePath) throws Exception {
        File fl = new File(filePath);

        FileInputStream fin = new FileInputStream(fl);
        String ret = convertStreamToString(fin);
        //Make sure you close all streams.
        fin.close();
        return ret;
    }

    public static String convertStreamToString(InputStream is) throws Exception {
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();
        String line = null;
        while ((line = reader.readLine()) != null) {
            sb.append(line).append("\n");
        }
        reader.close();
        return sb.toString();
    }



}
