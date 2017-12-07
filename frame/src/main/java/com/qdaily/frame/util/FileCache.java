package com.qdaily.frame.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.os.StatFs;

import com.qdaily.frame.core.NetListener;
import com.qdaily.frame.managercenter.MManager;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.OutputStream;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by yuelinghui on 17/8/10.
 */

public class FileCache extends MManager{


    /**
     * 图片存储路径
     */
    private String sImageDIR = null;

    /**
     * 序列化类储路径
     */
    private String sObjectDIR = null;

    private String sFileDIR = null;

    /**
     * 后缀名
     */
    private static final String WHOLESALE_CONV = "";

    /**
     * 存储空间预定大小
     */
    private static final int CACHE_SIZE = 10;

    /**
     * 剩余存储空间预定大小
     */
    private static final int FREE_CACHE_SIZE = 10;

    private static final int MB = 1024 * 1024;
    /**
     * 对象互斥锁
     */
    private static byte[] sObjectLock = new byte[0];

    private static byte[] sFileLock = new byte[0];

    @Override
    public void onManagerInit(Context context) {
        super.onManagerInit(context);
        sImageDIR = Environment.getExternalStorageDirectory().getAbsolutePath()+"/Download" + "/image";
        sObjectDIR = Environment.getExternalStorageDirectory().getAbsolutePath()+"/Download" + "/object";
        sFileDIR = Environment.getExternalStorageDirectory().getAbsolutePath()+"/Download" + "/file";
        removeImageCache(sImageDIR);
    }

    /** 从缓存中获取图片 **/
    public Bitmap getImage(final String url) {
        // 图片路径
        String path = sImageDIR + "/" + convertUrlToFileName(url);
        File file = new File(path);
        if (!file.exists()) {
            return null;
        }

        Bitmap bmp = null;
        try {
            bmp = BitmapFactory.decodeFile(path);
            if (bmp == null) {
                file.delete();
            } else {
                updateFileTime(path);
            }
        } catch (Exception e) {
            file.delete();
        }

        return bmp;
    }

    /**
     * 将图片存入文件缓存
     */
    public void setImage(String url, Bitmap bm) {
        if (bm == null) {
            return;
        }

        // 创建文件夹
        File dirFile = new File(sImageDIR);
        if (!dirFile.exists()) {
            dirFile.mkdirs();
        }

        // 判断原有文件是否存在，有则忽略修改
        String path = sImageDIR + "/" + convertUrlToFileName(url);
        File file = new File(path);
        if (file.exists()) {
            if (!file.delete()) {
                return;
            }
        }

        // 创建临时文件，写完之后切换为指定文件
        File tmpFile = new File(path + ".tmp");
        if (tmpFile.exists()) {
            if (!tmpFile.delete()) {
                return;
            }
        }

        try {
            tmpFile.createNewFile();
        } catch (IOException e) {
            tmpFile.delete();
            return;
        }

        OutputStream outStream = null;
        try {
            outStream = new FileOutputStream(tmpFile);
            bm.compress(Bitmap.CompressFormat.PNG, 100, outStream);
            outStream.flush();
            outStream.close();

            tmpFile.renameTo(file);
        } catch (Exception e) {
            tmpFile.delete();
        } finally {
            if (outStream != null) {
                try {
                    outStream.flush();
                } catch (Exception e) {
                }
                try {
                    outStream.close();
                } catch (Exception e) {
                }
            }
        }
    }

    /**
     * 异步存储图像数据
     */
    public void setImageAsync(final String url, final Bitmap bm) {

        Observable.create(new Observable.OnSubscribe<Object>() {
            @Override
            public void call(Subscriber<? super Object> subscriber) {
                setImage(url,bm);
                subscriber.onCompleted();
            }
        }).subscribeOn(Schedulers.io());
    }

    public void setFile(String fileUrl, InputStream inputStream) {
        byte[] buf = new byte[2048];
        FileOutputStream fos = null;
        // 判断原有文件是否存在，有则忽略修改
        String path = sFileDIR + "/" + convertUrlToFileName(fileUrl);

        // 创建临时文件，写完之后切换为指定文件
        File tmpFile = new File(path + ".tmp");
        if (tmpFile.exists()) {
            if (!tmpFile.delete()) {
                return;
            }
        }

        try {
            tmpFile.createNewFile();
        } catch (IOException e) {
            tmpFile.delete();
            return;
        }
        int len = 0;
        try {
            fos = new FileOutputStream(tmpFile);
            while ((len = inputStream.read(buf)) != -1) {
                fos.write(buf,0,len);
            }
            fos.flush();
        } catch (Exception e) {
            tmpFile.delete();
        } finally {
            try {
                if (inputStream != null) {
                    inputStream.close();
                }
            } catch (Exception e) {

            }
            try {
                if (fos != null) {
                    fos.close();
                }
            } catch (Exception e) {

            }
        }
    }

    public File getFile(String fileName) {
        synchronized (sFileLock) {
            File file = null;
            // 文件位置
            fileName = sFileDIR + "/" + convertUrlToFileName(fileName);
            file = new File(fileName);

            if (!file.exists()) {
                return null;
            }
            return file;
        }
    }

    public void setString(String fileName, String obj) {
        if (obj == null) {
            return;
        }

        // 创建根目录
        File dirFile = new File(sObjectDIR);
        if (!dirFile.exists()) {
            dirFile.mkdirs();
        }

        // 忽略原始文件是否存在，创建临时文件，写完之后再重命名为原始文件
        String path = sObjectDIR + "/" + convertUrlToFileName(fileName);

        // 创建临时文件，写完之后切换为指定文件
        File tmpFile = new File(path + ".tmp");
        if (tmpFile.exists()) {
            if (!tmpFile.delete()) {
                return;
            }
        }

        try {
            tmpFile.createNewFile();
        } catch (IOException e) {
            tmpFile.delete();
            return;
        }

        DataOutputStream out = null;
        FileOutputStream fOut = null;
        try {
            // 写入文件
            fOut = new FileOutputStream(tmpFile);
            out = new DataOutputStream(fOut);
            out.writeUTF(obj);
            out.flush();
            out.close();
            fOut.flush();
            fOut.close();

            synchronized (sObjectLock) {
                File file = new File(path);
                if (file.exists()) {
                    if (!file.delete()) {
                        tmpFile.delete();
                        return;
                    }
                }
                tmpFile.renameTo(file);
            }
        } catch (Exception e) {
            tmpFile.delete();
        } finally {
            if (out != null) {
                try {
                    out.flush();
                    out.close();
                } catch (Exception e) {
                }
            }
            if (fOut != null) {
                try {
                    fOut.flush();
                    fOut.close();
                } catch (Exception e) {
                }
            }
        }

    }

    public Object getObject(String fileName) {
        synchronized (sObjectLock) {
            Object obj = null;

            // 文件位置
            fileName = sObjectDIR + "/" + convertUrlToFileName(fileName);

            FileInputStream fIn = null;
            ObjectInputStream oIn = null;
            try {
                fIn = new FileInputStream(fileName);
                oIn = new ObjectInputStream(fIn);
                obj = oIn.readObject();
            } catch (FileNotFoundException e) {
            } catch (Exception e) {
                new File(fileName).delete();
            } finally {
                if (oIn != null) {
                    try {
                        oIn.close();
                    } catch (Exception e) {
                    }
                }
                if (fIn != null) {
                    try {
                        fIn.close();
                    } catch (Exception e) {
                    }
                }
            }

            return obj;
        }
    }

    /**
     * 异步返回数据文件数据
     *
     * @author wyqiuchunlong
     * @param fileName
     */
    public void getObjectAsync(final String fileName,
                               final NetListener<Object> subscriber) {

        Observable.just(fileName)
                .map(new Func1<String, Object>() {
                    @Override
                    public Object call(String s) {
                        return getObject(s);
                    }
                }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<Object>() {
                    @Override
                    public void call(Object obj) {
                        if (obj == null) {
                            subscriber.onError(new Throwable("没有文件"));
                            subscriber.onCompleted();
                        }
                        subscriber.onNext(obj);
                        subscriber.onCompleted();
                    }
                });
    }

    /**
     * 将url转成文件名
     */
    private String convertUrlToFileName(String url) {
        String[] strs = url.split("/");
        return strs[strs.length - 1] + WHOLESALE_CONV;
    }

    /**
     * 获取文件最后存储时间
     *
     * @author wyqiuchunlong
     * @param fileName
     * @return
     */
    public Date getObjectDate(String fileName) {
        // 文件位置
        fileName = sObjectDIR + "/" + convertUrlToFileName(fileName);

        File file = new File(fileName);
        if (!file.exists()) {
            return null;
        }
        return new Date(file.lastModified());
    }

    /**
     * 修改文件的最后修改时间
     */
    private void updateFileTime(String path) {
        File file = new File(path);
        long newModifiedTime = System.currentTimeMillis();
        file.setLastModified(newModifiedTime);
    }

    /** 计算剩余空间 **/
    @SuppressWarnings("deprecation")
    private int freeSpaceOnCache() {
        StatFs stat = new StatFs(Environment.getDataDirectory().getPath());
        double appFreeMB = ((double) stat.getAvailableBlocks() * (double) stat
                .getBlockSize()) / MB;
        return (int) appFreeMB;
    }

    /**
     * 计算存储目录下的文件大小，
     * 当文件总大小大于规定的CACHE_SIZE或者sdcard剩余空间小于FREE_SD_SPACE_NEEDED_TO_CACHE的规定
     * 那么删除40%最近没有被使用的文件
     */
    private boolean removeImageCache(String dirPath) {
        File dir = new File(dirPath);
        File[] files = dir.listFiles();
        if (files == null) {
            return true;
        }

        int dirSize = 0;
        for (int i = 0; i < files.length; i++) {
            if (files[i].getName().contains(WHOLESALE_CONV)) {
                dirSize += files[i].length();
            }
        }

        if (dirSize > CACHE_SIZE * MB || FREE_CACHE_SIZE > freeSpaceOnCache()) {
            int removeFactor = (int) ((0.4 * files.length) + 1);
            Arrays.sort(files, new FileLastModifSort());
            for (int i = 0; i < removeFactor; i++) {
                if (files[i].getName().contains(WHOLESALE_CONV)) {
                    files[i].delete();
                }
            }
        }

        if (freeSpaceOnCache() <= CACHE_SIZE) {
            return false;
        }

        return true;
    }

    /**
     * 根据文件的最后修改时间进行排序
     */
    private class FileLastModifSort implements Comparator<File> {
        public int compare(File arg0, File arg1) {
            if (arg0.lastModified() > arg1.lastModified()) {
                return 1;
            } else if (arg0.lastModified() == arg1.lastModified()) {
                return 0;
            } else {
                return -1;
            }
        }
    }
}
