package com.android001.common.app.utils;

import java.io.File;
import java.io.FileInputStream;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

/**
* class design：
* @author android001
* created at 2017/7/5 下午2:41
*/

public class ApkTool
{

    public static String getApkFileSFCrc32(String sourceDir) {
        long crc = 0xffffffff;

        try {
            File f = new File(sourceDir);
            ZipFile z = new ZipFile(f);
            Enumeration<? extends ZipEntry> zList = z.entries();
            ZipEntry ze;
            while (zList.hasMoreElements()) {
                ze = zList.nextElement();
                if (ze.isDirectory()
                        || ze.getName().toString().indexOf("META-INF") == -1
                        || ze.getName().toString().indexOf(".SF") == -1) {
                    continue;
                } else {
                    crc = ze.getCrc();
                    break;
                }
            }
            z.close();
        } catch (Exception e) {
        }
        return Long.toHexString(crc);
    }

    public static String getFileMD5(String sourceDir) {
        File file = new File(sourceDir);
        if (!file.isFile()) {
            return null;
        }
        MessageDigest digest;
        FileInputStream in;
        byte buffer[] = new byte[1024];
        int len;
        try {
            digest = MessageDigest.getInstance("MD5");
            in = new FileInputStream(file);
            while ((len = in.read(buffer, 0, 1024)) != -1) {
                digest.update(buffer, 0, len);
            }
            in.close();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        BigInteger bigInt = new BigInteger(1, digest.digest());
        return bigInt.toString(16);
    }

}
