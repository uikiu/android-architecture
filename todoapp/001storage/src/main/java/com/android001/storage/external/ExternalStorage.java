package com.android001.storage.external;

import android.content.Context;
import android.os.Build;
import android.os.Environment;
import android.text.TextUtils;


import com.android001.storage.AppHolder;

import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.charset.Charset;

public class ExternalStorage {

    private ExternalStorage() {
    }

    //静态内部类饿汉式持有对象。
    private static class LazyHolder {
        private static final ExternalStorage INSTANCE = new ExternalStorage();
    }

    public static ExternalStorage getInstance() {
        return LazyHolder.INSTANCE;
    }

    /*
    * 检查内置存储卡是否可读可写
    */
    public boolean isExternalStorageWritable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            return true;
        }
        return false;
    }


    /**
     * 写入数据
     *
     * @return
     */
    public boolean writeExternalFile(String fileName, String content) {
        synchronized (ExternalStorage.class) {
            FileOutputStream fos = null;
            try {
                //创建file
                File currentDir = null;
                if (Build.VERSION.SDK_INT >= 19) {
                    currentDir = AppHolder.getContext().getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS);//19<---->android4.4
                } else {
                    currentDir = new File(Environment.getExternalStorageDirectory() + "/Documents");
                }
                File file = new File(currentDir, fileName);
                if (!file.exists()) file.createNewFile();//文件不存在则创建
                //写入文件out,int
                fos = new FileOutputStream(file);
                fos.write(content.getBytes());
                fos.flush();
                return true;
            } catch (IOException e) {
                return false;
            } catch (Exception e) {
                return false;
            } finally {
                try {
                    if (fos != null) {
                        fos.close();
                        return true;
                    }
                } catch (Exception e) {
                    return false;
                }
            }

        }
    }

    /**
     * 读取数据
     *
     * @return
     */
    public String readExternalFile(String fileName) {
        synchronized (ExternalStorage.class) {

            String content = null;
            try {
                content = null;
                File currentDir = null;
                if (Build.VERSION.SDK_INT >= 19) {
                    currentDir = AppHolder.getContext().getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS);//19<---->android4.4
                } else {
                    currentDir = new File(Environment.getExternalStorageDirectory() + "/Documents");
                }
                File file = new File(currentDir, fileName);
                if (!file.exists()) return null;//文件不存在，则返回null
                //===
                content =  readContentFromFile(AppHolder.getContext(),fileName);
                //===
            }  catch (Exception e) {
                e.printStackTrace();
            } finally {
                return content;
            }

        }
    }

    public String readContentFromFile(Context context, String fileName) {
        String content = null;
        try {
            FileInputStream in = context.openFileInput(fileName);
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            int len = -1;
            byte[] buffer = new byte[128];
            while ((len = in.read(buffer)) != -1) {
                out.write(buffer, 0, len);
                out.flush();
            }
            out.close();
            in.close();

            content = new String(out.toByteArray());
        } catch (FileNotFoundException e) {
            return "";
        } catch (IOException e) {
            return "";
        } catch (Exception e) {
            return "";
        }
        return content;
    }


    public boolean safeClose(Closeable c) {
        synchronized (ExternalStorage.class) {
            if (c != null) {
                try {
                    c.close();
                    return true;
                } catch (Exception e) {

                }
            }
            return false;
        }
    }

    public File getWriteFile(String fileName) {
        synchronized (ExternalStorage.class) {
            if (!isExternalStorageWritable())
                return null;

            File currentDir = null;
            if (Build.VERSION.SDK_INT >= 19) {
                currentDir = AppHolder.getContext().getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS);//19<---->android4.4
            } else {
                currentDir = new File(Environment.getExternalStorageDirectory() + "/Documents");
            }
            File file = new File(currentDir, fileName);
            return file;
        }
    }

    public boolean write(CharSequence from, File to, Charset charset) {
        synchronized (ExternalStorage.class) {
            if (from == null || to == null || charset == null) {
                throw new NullPointerException();
            }

            FileOutputStream out = null;
            Writer writer = null;
            boolean result = true;

            try {
                out = new FileOutputStream(to);
                writer = new OutputStreamWriter(out, charset).append(from);
            } catch (IOException e) {
                return false;
            } finally {
                if (writer == null) {
                    safeClose(out);
                }
                result &= safeClose(writer);
            }

            return result;
        }
    }

    //-----以上为外部私有，以下为外部共有
    //需要非空判断
    public File getHideFile(String hideFileName){
        File file = null;
        try {
             file =new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),hideFileName);
            if (!file.exists()) file.createNewFile();
            return file;
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            return file;
        }

    }

    public String readExternalPublicFile(String fileName) {
        synchronized (ExternalStorage.class) {

            String content = null;
            try {
                content = null;
//                File currentDir = null;
//                if (Build.VERSION.SDK_INT >= 19) {
//                    currentDir = AppHolder.getContext().getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS);//19<---->android4.4
//                } else {
//                    currentDir = new File(Environment.getExternalStorageDirectory() + "/Documents");
//                }
//                File file = new File(currentDir, fileName);
//                if (!file.exists()) return null;//文件不存在，则返回null
                File file = getHideFile(fileName);
                if (!file.exists()) return null;//文件不存在，则返回null

                //===
                FileInputStream in = new FileInputStream(file);
                ByteArrayOutputStream out = new ByteArrayOutputStream();
                int len = -1;
                byte[] buffer = new byte[128];
                while ((len = in.read(buffer)) != -1) {
                    out.write(buffer, 0, len);
                    out.flush();
                }
                out.close();
                in.close();

                content = new String(out.toByteArray());
                //===
            }  catch (Exception e) {
                e.printStackTrace();
            } finally {
                return content;
            }

        }
    }


}
