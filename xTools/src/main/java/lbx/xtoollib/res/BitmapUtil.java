package lbx.xtoollib.res;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.PixelFormat;
import android.graphics.drawable.Drawable;
import android.media.MediaMetadataRetriever;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.util.Base64;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;

/**
 * @author lbx
 * @date 2016/7/5.
 */
public class BitmapUtil {

    private static BitmapUtil INSTANCE;

    public static BitmapUtil getInstance() {
        if (INSTANCE == null) {
            synchronized (BitmapUtil.class) {
                if (INSTANCE == null) {
                    INSTANCE = new BitmapUtil();
                }
            }
        }
        return INSTANCE;
    }

    private BitmapUtil() {
    }

    /**
     * 图片允许最大空间   单位：KB
     */
    public Bitmap zoomBmp(Bitmap bitMap, double maxKb) {
        Bitmap bitmap = null;
        //将bitmap放至数组中，意在bitmap的大小（与实际读取的原文件要大）
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitMap.compress(Bitmap.CompressFormat.PNG, 100, baos);
        byte[] b = baos.toByteArray();
        //将字节换成KB
        double mid = b.length / 1024;
        //判断bitmap占用空间是否大于允许最大空间  如果大于则压缩 小于则不压缩
        if (mid > maxKb) {
            //获取bitmap大小 是允许最大大小的多少倍
            double i = mid / maxKb;
            //开始压缩  此处用到平方根 将宽带和高度压缩掉对应的平方根倍
            //（1.保持刻度和高度和原bitmap比率一致，压缩后也达到了最大大小占用空间的大小）
            bitmap = zoomBmp(bitMap, (int) (bitMap.getWidth() / Math.sqrt(i)),
                    (int) (bitMap.getHeight() / Math.sqrt(i)));
            return bitmap;
        }
        return bitMap;
    }

    public Bitmap zoomBmp(Bitmap bitmap, int newWidth, int newHeight) {
        // 获取这个图片的宽和高
        float width = bitmap.getWidth();
        float height = bitmap.getHeight();
        // 创建操作图片用的matrix对象
        Matrix matrix = new Matrix();
        // 计算宽高缩放率
        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;
        // 缩放图片动作
        matrix.postScale(scaleWidth, scaleHeight);
        return Bitmap.createBitmap(bitmap, 0, 0, (int) width,
                (int) height, matrix, true);
    }

    public Bitmap zoomBmp(Bitmap bitmap, int newWidth) {
        // 获取这个图片的宽和高
        float width = bitmap.getWidth();
        float height = bitmap.getHeight();
        // 创建操作图片用的matrix对象
        Matrix matrix = new Matrix();
        // 计算宽高缩放率
        float scaleWidth = ((float) newWidth) / width;
        double newHeight = newWidth / width * height;
        float scaleHeight = ((float) newHeight) / height;
        // 缩放图片动作
        matrix.postScale(scaleWidth, scaleHeight);
        return Bitmap.createBitmap(bitmap, 0, 0, (int) width,
                (int) height, matrix, true);
    }

    public Bitmap uri2Bmp(Uri uri, Context context) {
        try {
            // 读取uri所在的图片
            return MediaStore.Images.Media.getBitmap(context.getContentResolver(), uri);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public String bitmap2StrByBase64(Bitmap bit) {
        return bitmap2StrByBase64(bit, 100);
    }

    public String bitmap2StrByBase64(Bitmap bit, int percent) {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        //参数100表示不压缩
        bit.compress(Bitmap.CompressFormat.PNG, percent, bos);
        byte[] bytes = bos.toByteArray();
        return Base64.encodeToString(bytes, Base64.DEFAULT);
    }

    public Bitmap strByBase642Bitmap(String bmp) {
        byte[] bytes = Base64.decode(bmp.getBytes(), Base64.DEFAULT);
        return byte2Bmp(bytes);
    }

    public Bitmap byteByBase642Bitmap(byte[] bytes) {
        byte[] b = Base64.decode(bytes, Base64.DEFAULT);
        return byte2Bmp(b);
    }

    public Bitmap base642Bitmap(String base64String) {
        return base642Bitmap(Base64.decode(base64String, Base64.DEFAULT));
    }

    public Bitmap base642Bitmap(byte[] bytes) {
        byte[] b = Base64.decode(bytes, Base64.DEFAULT);
        return BitmapFactory.decodeByteArray(b, 0, b.length);
    }

    /**
     * 图片转byte[]
     *
     * @return byte[]
     */
    public byte[] bmp2byte(Bitmap bitmap) {
        return bmp2byte(bitmap, 100);
    }

    public byte[] bmp2byte(Bitmap bitmap, int quality) {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, quality, out);
        try {
            out.flush();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return out.toByteArray();
    }

    public Bitmap byte2Bmp(byte[] temp) {
        if (temp != null) {
            return BitmapFactory.decodeByteArray(temp, 0, temp.length);
        } else {
            return null;
        }
    }

    @SuppressWarnings("unused")
    public Bitmap getLocalVideoImage(Context context, String filePath) {
        return getLocalVideoImage(context, 1 * 1000 * 2000, filePath);
    }

    @SuppressWarnings("unused")
    public Bitmap getLocalVideoImage(Context mContext, int timeMill, String filePath) {
        if (filePath.length() > 0) {
            MediaMetadataRetriever rev = new MediaMetadataRetriever();
            File video = new File(filePath);
            try {
                rev.setDataSource(mContext, Uri.fromFile(video));
                return rev.getFrameAtTime(timeMill, MediaMetadataRetriever.OPTION_CLOSEST_SYNC);
            } catch (Exception e) {
                return null;
            }
        } else {
            return null;
        }
    }

    @TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
    public static Bitmap getHttpVideoImage(String url, int width, int height) {
        Bitmap bitmap = null;
        MediaMetadataRetriever retriever = new MediaMetadataRetriever();
        int kind = MediaStore.Video.Thumbnails.MINI_KIND;
        try {
            if (Build.VERSION.SDK_INT >= 14) {
                retriever.setDataSource(url, new HashMap<String, String>());
            } else {
                retriever.setDataSource(url);
            }
            bitmap = retriever.getFrameAtTime();
        } catch (IllegalArgumentException ex) {
            // Assume this is a corrupt video file
            ex.printStackTrace();
        } catch (RuntimeException ex) {
            // Assume this is a corrupt video file.
            ex.printStackTrace();
        } finally {
            try {
                retriever.release();
            } catch (RuntimeException ex) {
                // Ignore failures while cleaning up.
                ex.printStackTrace();
            }
        }
        if (kind == MediaStore.Images.Thumbnails.MICRO_KIND && bitmap != null) {
            bitmap = ThumbnailUtils.extractThumbnail(bitmap, width, height,
                    ThumbnailUtils.OPTIONS_RECYCLE_INPUT);
        }
        return bitmap;
    }

    /**
     * @param bitmap     原图
     * @param edgeLength 希望得到的正方形部分的边长
     * @return 缩放截取正中部分后的位图。
     */
    public static Bitmap centerSquareScaleBitmap(Bitmap bitmap, int edgeLength) {
        if (null == bitmap || edgeLength <= 0) {
            return null;
        }
        Bitmap b = bitmap;
        int widthOrg = bitmap.getWidth();
        int heightOrg = bitmap.getHeight();
        if (widthOrg > edgeLength && heightOrg > edgeLength) {
            //压缩到一个最小长度是edgeLength的bitmap
            int longerEdge = (int) (edgeLength * Math.max(widthOrg, heightOrg) / Math.min(widthOrg, heightOrg));
            int scaledWidth = widthOrg > heightOrg ? longerEdge : edgeLength;
            int scaledHeight = widthOrg > heightOrg ? edgeLength : longerEdge;
            Bitmap scaledBitmap;
            try {
                scaledBitmap = Bitmap.createScaledBitmap(bitmap, scaledWidth, scaledHeight, true);
            } catch (Exception e) {
                return null;
            }
            //从图中截取正中间的正方形部分。
            int xTopLeft = (scaledWidth - edgeLength) / 2;
            int yTopLeft = (scaledHeight - edgeLength) / 2;
            try {
                b = Bitmap.createBitmap(scaledBitmap, xTopLeft, yTopLeft, edgeLength, edgeLength);
                scaledBitmap.recycle();
            } catch (Exception e) {
                return null;
            }
        }
        return b;
    }

    public Bitmap drawableToBitmap(Drawable drawable) {
        // 取 drawable 的长宽
        int w = drawable.getIntrinsicWidth();
        int h = drawable.getIntrinsicHeight();
        // 取 drawable 的颜色格式
        Bitmap.Config config = drawable.getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888
                : Bitmap.Config.RGB_565;
        // 建立对应 bitmap
        Bitmap bitmap = Bitmap.createBitmap(w, h, config);
        // 建立对应 bitmap 的画布
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, w, h);
        // 把 drawable 内容画到画布中
        drawable.draw(canvas);
        return bitmap;
    }
}
