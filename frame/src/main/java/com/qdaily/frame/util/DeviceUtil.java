package com.qdaily.frame.util;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.os.Build;
import android.provider.Settings;
import android.text.TextUtils;

import com.qdaily.frame.log.QLog;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Created by yuelinghui on 17/8/2.
 */

public class DeviceUtil {
    private static final String EMULATOR_ANDROID_ID = "9774d56d682e549c";
    private static final String[] BAD_SERIAL_PATTERNS = {"1234567", "abcdef", "dead00beef"};

    public static int getAppVersion(Context context) {
        try {
            PackageInfo info = context.getPackageManager().getPackageInfo(context.getPackageName(),0);
            return info.versionCode;
        } catch (Exception e) {

        }
        return 1;
    }

    public static Map<String, String> getClientInfo(Context mContext) {
        Map<String, String> map = new HashMap<>();
        map.put("Product", Build.PRODUCT);
        map.put("CPU_ABI", Build.CPU_ABI);
        map.put("TAGS", Build.TAGS);
        map.put("VERSION_CODES.BASE", Build.VERSION_CODES.BASE + "");
        map.put("MODEL", Build.MODEL);
        map.put("SDK_INT", Build.VERSION.SDK_INT + "");
        map.put("VERSION.RELEASE", Build.VERSION.RELEASE);
        map.put("DEVICE", Build.DEVICE);
        map.put("DISPLAY", Build.DISPLAY);

        map.put("BRAND", Build.BRAND);
        map.put("BOARD", Build.BOARD);
        map.put("FINGERPRINT", Build.FINGERPRINT);
        map.put("ID", Build.ID);
        map.put("MANUFACTURER", Build.MANUFACTURER);
        map.put("USER", Build.USER);
//        map.put("width", LocalDisplay.SCREEN_WIDTH_PIXELS + "");
//        map.put("height", LocalDisplay.SCREEN_HEIGHT_PIXELS + "");
//        map.put("NetworkType", getManager(NetworkStatusManager.class).getNetworkTypeName());
        return map;
    }

    public static String readDeviceId(Context context) {
        String deviceId;
        String androidSerialId = null;
        try {
            // try to get device serial number
            androidSerialId = Build.SERIAL;
        } catch (NoSuchFieldError ignored) {
            QLog.e("DeviceIdGenerator", ignored.getMessage());
        }

        if (!TextUtils.isEmpty(androidSerialId) && !Build.UNKNOWN.equals(androidSerialId) && !isBadSerial(androidSerialId)) {
            deviceId = androidSerialId;
        } else {
            // try to use Settings.Secure.ANDROID_ID
            // could be different after factory reset
            String androidSecureId = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
            if (!TextUtils.isEmpty(androidSecureId) && !EMULATOR_ANDROID_ID.equals(androidSecureId) && !isBadDeviceId(androidSecureId)
                    && androidSecureId.length() == EMULATOR_ANDROID_ID.length()) {
                deviceId = androidSecureId;
            } else {
                deviceId = SoftInstallationId.id(context);
            }
            deviceId = UUID.nameUUIDFromBytes(deviceId.getBytes()).toString();
        }
//        return UUID.nameUUIDFromBytes(deviceId.getBytes()).toString();
        return deviceId;
    }

    /**
     * Class generates id for current application installation
     */
    private static class SoftInstallationId {
        private static String sID = null;
        private static final String INSTALLATION = "INSTALLATION";

        public synchronized static String id(Context context) {
            if (sID == null) {
                File installation = new File(context.getFilesDir(), INSTALLATION);
                try {
                    if (!installation.exists()) {
                        writeInstallationFile(installation);
                    }
                    sID = readInstallationFile(installation);
                } catch (Exception epicFail) {
                    throw new RuntimeException(epicFail);
                }
            }
            return sID;
        }

        private static String readInstallationFile(File installation) throws IOException {
            RandomAccessFile f = new RandomAccessFile(installation, "r");
            try {
                byte[] bytes = new byte[(int) f.length()];
                f.readFully(bytes);
                f.close();
                return new String(bytes);
            } finally {
                f.close();
            }
        }

        private static void writeInstallationFile(File installation) throws IOException {
            FileOutputStream out = null;
            try {
                out = new FileOutputStream(installation);
                String id = UUID.randomUUID().toString();
                out.write(id.getBytes());
            } finally {
//                IOUtils.closeStream(out);
                if (out != null) {
                    try {
                        out.close();
                    } catch (IOException e) {
                        QLog.i("IOUtils", "Failed to close OutputStream", e);
                    }
                }
            }
        }
    }

    private static boolean isBadDeviceId(String id) {
        // empty or contains only spaces or 0
        return TextUtils.isEmpty(id) || TextUtils.isEmpty(id.replace('0', ' ').replace('-', ' ').trim());
    }

    private static boolean isBadSerial(String id) {
        if (!TextUtils.isEmpty(id)) {
            id = id.toLowerCase();
            for (String pattern : BAD_SERIAL_PATTERNS) {
                if (id.contains(pattern)) {
                    return true;
                }
            }
        } else {
            return true;
        }
        return false;
    }
}
