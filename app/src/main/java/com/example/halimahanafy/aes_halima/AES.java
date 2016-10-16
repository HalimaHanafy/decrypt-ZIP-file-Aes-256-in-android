package com.example.halimahanafy.aes_halima;

import android.os.Environment;
import android.util.Base64;

import org.bouncycastle.crypto.BlockCipher;
import org.bouncycastle.crypto.InvalidCipherTextException;
import org.bouncycastle.crypto.engines.RijndaelEngine;
import org.bouncycastle.crypto.paddings.PaddedBufferedBlockCipher;
import org.bouncycastle.crypto.paddings.ZeroBytePadding;
import org.bouncycastle.crypto.params.KeyParameter;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;

class AES {
    public static byte[] ivBytes = {0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,
            0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00};

    public static void main(String[] args) throws InvalidCipherTextException {
        String key = "ddv21sd5dv56sd51";
        String data = "com.egy.me";
        encrypt(key, data);


        ////////////////////


    }


    public static void writeFile(byte[] data, String fileName) throws IOException {
        File extStore = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
        FileOutputStream out = new FileOutputStream(extStore+"/"+fileName);
        out.write(data);
        out.close();
    }


    public static String encrypt(String key, String data) throws InvalidCipherTextException {


        byte[] givenKey = key.getBytes(Charset.forName("ASCII"));
        final int keysize = 256;
        byte[] keyData = new byte[keysize / Byte.SIZE];
        System.arraycopy(givenKey, 0, keyData, 0, Math.min(givenKey.length, keyData.length));
        KeyParameter keyParameter = new KeyParameter(keyData);
        BlockCipher rijndael = new RijndaelEngine(256);
        ZeroBytePadding c = new ZeroBytePadding();
        PaddedBufferedBlockCipher pbbc = new PaddedBufferedBlockCipher(rijndael, c);
        pbbc.init(true, keyParameter);
        byte[] plaintext = data.getBytes(Charset.forName("UTF8"));
        byte[] ciphertext = new byte[pbbc.getOutputSize(plaintext.length)];
        int offset = 0;
        offset += pbbc.processBytes(plaintext, 0, plaintext.length, ciphertext, offset);
        offset += pbbc.doFinal(ciphertext, offset);
        String s = Base64.encodeToString(ciphertext, Base64.DEFAULT);
        return s;
    }
    public static String decrypt(String key,String data) throws InvalidCipherTextException {

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

        return new String(decrypted, Charset.forName("UTF8")).replaceAll("\\x00+$", "");

    }

    public static String decryptFile(String key,String data) throws InvalidCipherTextException, IOException {

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

        writeFile(fileContentBase64,"zzs.zip");

        return DecrtptedData;
    }

    private static byte[] decodeBase64(String fileContent) throws UnsupportedEncodingException {

        // Receiving side
        byte[] data = Base64.decode(fileContent, Base64.DEFAULT);
//        String text = new String(data, "UTF-8");
        return data;

    }



}