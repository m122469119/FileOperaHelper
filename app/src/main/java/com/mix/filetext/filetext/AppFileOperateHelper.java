package com.mix.filetext.filetext;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Environment;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;

/**
 * Des:       AppFileOperateHelper
 * Create by: m122469119
 * On:        2018/7/16 16:41
 * Email:     122469119@qq.com
 */
public class AppFileOperateHelper {

    private static Context sContext;

    private static int sBufferSize = 8192;

    private AppFileOperateHelper() {

    }

    public static synchronized void init(Context context) {
        if (context != null) {
            sContext = context.getApplicationContext();
        }
    }

    public static Context getContext() {
        return sContext;
    }

    /**
     * 获取文件的存储文件夹,会随着App删除而删除
     * /data/data/com.mix.filetext.filetext/files/
     **/
    public static String getAppFilesDirPath() {
        return getContext().getFilesDir().getAbsolutePath() + File.separator;

    }

    /**
     * 获取文件路径
     *
     * @param fileName
     * @return
     */
    public static String getAppFilesDirFilePath(String fileName) {
        return getAppFilesDirPath() + fileName;
    }


    /**
     * 获取媒体文件的保存路径
     * /storage/emulated/0/百城招聘/
     * /data/data/包名/files/百城招聘/
     *
     * @return
     */
    public static String getAppMediaDirPath() {
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            File externalStorageAppImgDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES + File.separator
                    + getAppName(getContext()));
            if (!externalStorageAppImgDir.exists()) {
                boolean mkdirs = externalStorageAppImgDir.mkdirs();
                if (!mkdirs) {
                    File appMediaDir = new File(getContext().getFilesDir().getAbsolutePath()
                            + File.separator
                            + Environment.DIRECTORY_PICTURES
                            + File.separator
                            + getAppName(getContext()));
                    if (!appMediaDir.exists()) {
                        appMediaDir.mkdirs();
                    }
                    return appMediaDir.getAbsolutePath() + File.separator;
                }
            }
            return externalStorageAppImgDir.getAbsolutePath() + File.separator;

        } else {
            File appMediaDir = new File(getContext().getFilesDir().getAbsolutePath()
                    + File.separator
                    + Environment.DIRECTORY_PICTURES
                    + File.separator
                    + getAppName(getContext()));
            if (!appMediaDir.exists()) {
                appMediaDir.mkdirs();
            }
            return appMediaDir.getAbsolutePath() + File.separator;

        }
    }

    public static String getAppMediaDirFilePath(String fileName) {
        return getAppMediaDirPath() + fileName;
    }


    /**
     * 从文件中获取字符串
     *
     * @param fileName
     * @return
     */
    public static String getStringFromFilesDirFile(String fileName) {
        File strFile = new File(getAppFilesDirFilePath(fileName));
        FileInputStream inputStream = null;
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        try {
            inputStream = new FileInputStream(strFile);
            byte[] buffer = new byte[sBufferSize];
            int len;
            while ((len = inputStream.read(buffer, 0, sBufferSize)) != -1) {
                byteArrayOutputStream.write(buffer, 0, len);
            }
            return byteArrayOutputStream.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        } finally {
            close(inputStream, byteArrayOutputStream);
        }

    }


    /**
     * 将字符串写入文件(带删除)
     *
     * @param content
     * @param fileName
     */
    public static boolean writeStringToFilesDirFile(String content, String fileName) {
        File saveFile = new File(getAppFilesDirFilePath(fileName));
        if (saveFile.exists()) {
            saveFile.delete();
        }
        BufferedWriter bufferedWriter = null;
        try {
            bufferedWriter = new BufferedWriter(new FileWriter(saveFile));
            bufferedWriter.write(content);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            close(bufferedWriter);
        }
    }


    /**
     * 拷贝文件
     *
     * @param copyFilePath 文件路径
     * @param saveFileName 新存储的文件名
     * @return
     */
    public boolean copyFile(String copyFilePath, String saveFileName) {
        BufferedInputStream inputStream = null;
        BufferedOutputStream outputStream = null;
        try {
            inputStream = new BufferedInputStream(new FileInputStream(new File(copyFilePath)));
            outputStream = new BufferedOutputStream(new FileOutputStream(new File(getAppFilesDirFilePath(saveFileName))));
            byte[] buffer = new byte[sBufferSize];
            int len;
            while ((len = inputStream.read(buffer, 0, sBufferSize)) != -1) {
                outputStream.write(buffer, 0, len);
            }
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        } finally {
            close(inputStream, outputStream);
        }
    }

    /**
     * 拷贝文件
     *
     * @param copyFile     文件对象
     * @param saveFileName 文件路径
     * @return
     */
    public static boolean copyFile(File copyFile, String saveFileName) {
        BufferedInputStream inputStream = null;
        BufferedOutputStream outputStream = null;
        try {
            inputStream = new BufferedInputStream(new FileInputStream(copyFile));
            outputStream = new BufferedOutputStream(new FileOutputStream(new File(getAppFilesDirFilePath(saveFileName))));
            byte[] buffer = new byte[sBufferSize];
            int len;
            while ((len = inputStream.read(buffer, 0, sBufferSize)) != -1) {
                outputStream.write(buffer, 0, len);
            }
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        } finally {
            close(inputStream, outputStream);
        }

    }


    /**
     * 文件拷贝到文件
     *
     * @param srcFile  文件对象
     * @param saveFile 新文件对象
     * @return
     */
    public static boolean copyFile(final File srcFile, final File saveFile) {
        BufferedInputStream inputStream = null;
        BufferedOutputStream outputStream = null;
        try {
            inputStream = new BufferedInputStream(new FileInputStream(srcFile));
            outputStream = new BufferedOutputStream(new FileOutputStream(saveFile));
            byte[] buffer = new byte[sBufferSize];
            int len;
            while ((len = inputStream.read(buffer, 0, sBufferSize)) != -1) {
                outputStream.write(buffer, 0, len);
            }
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        } finally {
            close(inputStream, outputStream);
        }

    }


    /**
     * assets文件拷贝到内部存储
     *
     * @param assetsName
     * @param saveFileName
     */
    public static void copyFileFromAssetsToFilesDirFile(String assetsName, String saveFileName) {
        File saveFile = new File(getAppFilesDirFilePath(saveFileName));
        if (!saveFile.exists()) {
            InputStream inputStream = null;
            BufferedOutputStream outputStream = null;
            try {
                inputStream = getContext().getResources().getAssets().open(assetsName);
                outputStream = new BufferedOutputStream(new FileOutputStream(saveFile));
                byte[] buffer = new byte[sBufferSize];
                int len;
                while ((len = inputStream.read(buffer, 0, sBufferSize)) != -1) {
                    outputStream.write(buffer, 0, len);
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                close(inputStream, outputStream);
            }
        }
    }


    /**
     * 保存bitmap并返回Url (一般用于生成图片并上传)
     *
     * @param mBitmap
     * @param filename
     * @return
     */
    public static String saveBitmap(Bitmap mBitmap, String filename) {
        File bitmapSavePath = new File(getAppMediaDirFilePath(filename));
        if (bitmapSavePath.exists()) {
            bitmapSavePath.delete();
        }
        FileOutputStream outputStream = null;
        try {
            outputStream = new FileOutputStream(bitmapSavePath);
            mBitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);
            outputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } finally {
            close(outputStream);
        }
        return bitmapSavePath.getAbsolutePath();
    }


    /**
     * 删除文件
     *
     * @param filePath
     * @return
     */
    public static boolean deleteFile(final String filePath) {
        return deleteFile(new File(filePath));
    }

    /**
     * 删除文件
     *
     * @param file
     * @return
     */
    public static boolean deleteFile(final File file) {
        return file != null && (!file.exists() || file.isFile() && file.delete());
    }

    /**
     * 关闭流
     *
     * @param closeables
     */
    public static void close(Closeable... closeables) {
        if (closeables == null || closeables.length == 0)
            return;
        for (Closeable closeable : closeables) {
            if (closeable != null) {
                try {
                    closeable.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }


    /**
     * 获取应用名称
     *
     * @param context
     * @return
     */
    public static String getAppName(Context context) {
        PackageInfo packageInfo = getPackageInfo(context);
        if (packageInfo != null) {
            return packageInfo.applicationInfo.loadLabel(context.getPackageManager()).toString();
        }
        return "";
    }


    /**
     * 获取应用程序信息
     *
     * @param context
     * @return
     */
    public static PackageInfo getPackageInfo(Context context) {
        try {
            return context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }


}
