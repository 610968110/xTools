package lbx.xtoollib.view;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.ActionBar;
import android.view.View;

import java.io.File;

import lbx.xtoollib.R;
import lbx.xtoollib.XIntent;
import lbx.xtoollib.XTools;
import lbx.xtoollib.base.BaseActivity;
import lbx.xtoollib.phone.GetPathFromUri4kitkat;

import static android.support.v4.content.FileProvider.getUriForFile;

/**
 * @author lbx
 *         h5调用标准接口显示图库或相机的选择框
 */
public class WebPhotoActivity extends BaseActivity {

    private File mPictureFile;
    public static final int REQUEST_CODE_CAMERA = 0x010;
    public static final int REQUEST_CODE_PICK_PHOTO = 0x011;

    public static XIntent getIntent(Context context) {
        return new XIntent(context, WebPhotoActivity.class);
    }

    @Override
    public int getLayoutID() {
        return R.layout.activity_web_phoeo;
    }

    @Override
    public void initView(View view) {
        ActionBar supportActionBar = getSupportActionBar();
        if (supportActionBar != null) {
            supportActionBar.hide();
        }
        android.app.ActionBar actionBar = getActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }
    }

    @Override
    public void initData() {

    }

    public void camera(View view) {
        takeForCamera();
    }

    public void picture(View view) {
        takeForPhoto();
    }

    public void cancel(View view) {
        if (mOnPhotoSelectListener != null) {
            mOnPhotoSelectListener.cancel();
        }
        back();
    }

    private void back() {
        finish();
        overridePendingTransition(0, 0);
    }

    @Override
    public void onBackPressed() {
        cancel(null);
    }

    /**
     * 调用相册
     */
    private void takeForPhoto() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, REQUEST_CODE_PICK_PHOTO);
    }

    /**
     * 调用相机
     */
    private void takeForCamera() {
        //图片位置
        File pFile = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), "MyPictures");
        if (!pFile.exists()) {
            pFile.mkdirs();
        }
        //拍照所存路径
        mPictureFile = new File(pFile + File.separator + "IvMG_" + String.valueOf(System.currentTimeMillis()) + ".jpg");
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        //7.0及以上
        if (Build.VERSION.SDK_INT > 23) {
            Uri contentUri = getUriForFile(this, XTools.getApplication().getPackageName() + ".fileprovider", mPictureFile);
            grantUriPermission(getPackageName(), contentUri, Intent.FLAG_GRANT_READ_URI_PERMISSION);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, contentUri);
        } else {
            //7.0以下
            intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(mPictureFile));
        }
        startActivityForResult(intent, REQUEST_CODE_CAMERA);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case REQUEST_CODE_CAMERA:
                takeCameraResult(resultCode);
                back();
                break;
            case REQUEST_CODE_PICK_PHOTO:
                takePhotoResult(resultCode, data);
                back();
                break;
            default:
                break;
        }
    }

    private void takePhotoResult(int resultCode, Intent data) {
        Uri result = data == null || resultCode != RESULT_OK ? null : data.getData();
        if (result != null) {
            String path = GetPathFromUri4kitkat.getPath(this, result);
            Uri uri = Uri.fromFile(new File(path));
            if (Build.VERSION.SDK_INT > 18) {
                if (mOnPhotoSelectListener != null) {
                    mOnPhotoSelectListener.onPhotoUrlResult18(uri);
                }
            } else {
                if (mOnPhotoSelectListener != null) {
                    mOnPhotoSelectListener.onPhotoUrlResult(uri);
                }
            }

        } else {
            if (mOnPhotoSelectListener != null) {
                mOnPhotoSelectListener.cancel();
            }
        }
    }

    private void takeCameraResult(int resultCode) {
        if (resultCode == RESULT_OK) {
            Uri uri = Uri.fromFile(mPictureFile);
            if (Build.VERSION.SDK_INT > 18) {
                if (mOnPhotoSelectListener != null) {
                    mOnPhotoSelectListener.onCameraUrlResult18(uri);
                }
            } else {
                if (mOnPhotoSelectListener != null) {
                    mOnPhotoSelectListener.onCameraUrlResult(uri);
                }
            }
        } else {
            //点击了file按钮，必须有一个返回值，否则会卡死
            if (mOnPhotoSelectListener != null) {
                mOnPhotoSelectListener.cancel();
            }
        }
    }

    public static OnPhotoSelectListener mOnPhotoSelectListener;

    public interface OnPhotoSelectListener {
        void onCameraUrlResult18(Uri uri);

        void onCameraUrlResult(Uri uri);

        void onPhotoUrlResult18(Uri uri);

        void onPhotoUrlResult(Uri uri);

        void cancel();
    }

    public static void setOnPhotoSelectListener(OnPhotoSelectListener listener) {
        WebPhotoActivity.mOnPhotoSelectListener = listener;
    }
}