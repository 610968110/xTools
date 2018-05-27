package lbx.xtoollib.res;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;

import java.io.File;

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

    public String uriToFilePath(final Context context, final Uri uri) {
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
        return data;
    }

    public File uri2File(Activity ac, Uri uri) {
        File file = null;
        String[] proj = {MediaStore.Images.Media.DATA};
        Cursor actualimagecursor = ac.managedQuery(uri, proj, null,
                null, null);
        int actual_image_column_index = actualimagecursor
                .getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        actualimagecursor.moveToFirst();
        String img_path = actualimagecursor
                .getString(actual_image_column_index);
        file = new File(img_path);
        return file;
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
