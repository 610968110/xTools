package lbx.xtoollib.view.bottomBar;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import lbx.xtoollib.R;


/**
 * .  ┏┓　　　┏┓
 * .┏┛┻━━━┛┻┓
 * .┃　　　　　　　┃
 * .┃　　　━　　　┃
 * .┃　┳┛　┗┳　┃
 * .┃　　　　　　　┃
 * .┃　　　┻　　　┃
 * .┃　　　　　　　┃
 * .┗━┓　　　┏━┛
 * .    ┃　　　┃        神兽保佑
 * .    ┃　　　┃          代码无BUG!
 * .    ┃　　　┗━━━┓
 * .    ┃　　　　　　　┣┓
 * .    ┃　　　　　　　┏┛
 * .    ┗┓┓┏━┳┓┏┛
 * .      ┃┫┫　┃┫┫
 * .      ┗┻┛　┗┻┛
 *
 * @author lbx
 * @date 2017/11/10.
 */

public class XBottomBarItemView extends FrameLayout {

    private Context mContext;
    private ImageView mImage;
    private TextView mUnReadMsgView;
    private LinearLayout mItemLayout;
    private TextView mName;
    private int mSelectTextColor;
    private int mNormalTextColor;
    private boolean isSelect;
    private static final int ONLY_POINT = -1;
    private static final int COUNT_MAX = 99;
    private static final int COUNT_MIN = 0;

    public XBottomBarItemView(Context context) {
        this(context, null);
    }

    public XBottomBarItemView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public XBottomBarItemView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        addView(inflate(mContext, R.layout.item_bottom_bar, null),
                new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.MATCH_PARENT));
        init();
    }

    private void init() {
        mItemLayout = findViewById(R.id.ll_bottombar_item_layout);
        mImage = findViewById(R.id.iv_bottom_item);
        mName = findViewById(R.id.tv_bottom_item);
        mUnReadMsgView = findViewById(R.id.tv_unread);
        if (mNormalTextColor != 0 && mSelectTextColor != 0) {
            mName.setText(isSelect ? mSelectTextColor : mNormalTextColor);
        }
    }

    public void showNoticePoint() {
        setNoticeCount(ONLY_POINT);
    }

    public void cancelNoticePoint() {
        setNoticeCount(0);
    }

    public void setNoticeCount(int count) {
        String s = "";
        if (count == ONLY_POINT) {
            s = "";
        } else if (count >= COUNT_MAX) {
            s = "···";
        } else if (count < COUNT_MIN) {
            count = COUNT_MIN;
        } else {
            s = String.valueOf(count);
        }
        mUnReadMsgView.setText(s);
        mUnReadMsgView.setVisibility(count == COUNT_MIN ? GONE : VISIBLE);
    }

    public ImageView getImage() {
        return mImage;
    }

    public LinearLayout getItemLayout() {
        return mItemLayout;
    }

    public TextView getName() {
        return mName;
    }

    public void set(Drawable drawable, String name) {
        mImage.setImageDrawable(drawable);
        mName.setText(name);
        if (mNormalTextColor != 0) {
            mName.setTextColor(isSelect ? mSelectTextColor : mNormalTextColor);
        }
    }

    public void select(boolean select) {
        isSelect = select;
        if (select) {
            mImage.setFocusable(true);
            mImage.setFocusableInTouchMode(true);
            mImage.requestFocus();
            mImage.setSelected(true);
            mImage.requestFocusFromTouch();
            if (mSelectTextColor != 0) {
                mName.setTextColor(mSelectTextColor);
            }
        } else {
            if (mNormalTextColor != 0) {
                mName.setTextColor(mNormalTextColor);
            }
            mImage.setSelected(false);
        }
    }

    public void setTextColor(int normalTextColor, int selectTextColor) {
        this.mNormalTextColor = normalTextColor;
        this.mSelectTextColor = selectTextColor;
    }
}
