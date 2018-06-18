package lbx.xtoollib.res;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.ParcelFileDescriptor;
import android.provider.MediaStore;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

import lbx.xtoollib.XTools;


/**
 * @author lbx
 * @date 2016/7/18.
 */
public class UriUtil {

    private static UriUtil INSTANCE;

    public static UriUtil getInstance() {
        if (INSTANCE == null) {
            synchronized (UriUtil.class) {
                if (INSTANCE == null) {
                    INSTANCE = new UriUtil();
                }
            }
        }
        return INSTANCE;
    }

    private UriUtil() {
    }

    public File uriToFile(Context context, Uri uri) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            return uriToFile70(context, uri);
        } else {
            return uriToFile44(context, uri);
        }
    }

    /**
     * 7.0以上
     *
     * @param context
     * @param uri
     * @return
     */
    private File uriToFile70(Context context, Uri uri) {
        if (uri == null) {
            return null;
        }
        FileInputStream input = null;
        FileOutputStream output = null;
        try {
            ParcelFileDescriptor pfd = context.getContentResolver().openFileDescriptor(uri, "r");
            if (pfd == null) {
                return null;
            }
            input = new FileInputStream(pfd.getFileDescriptor());
            String tempFilename = File.createTempFile("image", "tmp", context.getCacheDir()).getAbsolutePath();
            output = new FileOutputStream(tempFilename);
            int read;
            byte[] bytes = new byte[4096];
            while ((read = input.read(bytes)) != -1) {
                output.write(bytes, 0, read);
            }
            return new File(tempFilename);
        } catch (Exception ignored) {

            ignored.getStackTrace();
        } finally {
            XTools.CloseUtil().close(input);
            XTools.CloseUtil().close(output);
        }
        return null;
    }

    /**
     * 4.4以上
     *
     * @param context
     * @param uri
     * @return
     */
    private File uriToFile44(final Context context, final Uri uri) {
        if (null == uri) {
            return null;
        }
        final String scheme = uri.getScheme();
        String data = null;
        if (scheme == null) {
            data = uri.getPath();
        } else {
            if (ContentResolver.SCHEME_FILE.equals(scheme)) {
                data = uri.getPath();
            } else if (ContentResolver.SCHEME_CONTENT.equals(scheme)) {
                Cursor cursor = context.getContentResolver().query(uri, new String[]{MediaStore.Images.ImageColumns.DATA}, null, null, null);
                if (null != cursor) {
                    if (cursor.moveToFirst()) {
                        int index = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
                        if (index > -1) {
                            data = cursor.getString(index);
                        }
                    }
                    cursor.close();
                }
            }
        }
        return new File(data);
    }

    public Bitmap uri2Bitmap(Context context, Uri uri) {
        try {
            // 读取uri所在的图片
            return MediaStore.Images.Media.getBitmap(context.getContentResolver(), uri);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public Uri raw2Uri(int rawId) {
        return Uri.parse("android.resource://" + XTools.getApplicationContext().getPackageName() + "/" + rawId);
    }
}
