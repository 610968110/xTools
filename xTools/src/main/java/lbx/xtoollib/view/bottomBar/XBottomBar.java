package lbx.xtoollib.view.bottomBar;

import android.content.Context;
import android.content.res.Resources;
import android.support.annotation.ColorRes;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutCompat;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import lbx.xtoollib.R;
import lbx.xtoollib.XTools;
import lbx.xtoollib.base.BaseFragment;
import lbx.xtoollib.bean.FragmentInfo;

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

public class XBottomBar extends LinearLayout {

    private LinearLayout mBottomLayout;
    private Context mContext;
    private Toolbar mToolbar;
    private TextView mTitleView;
    private ViewPager mViewPager;
    private XBottomBarItemView mFirstItemView;
    private IPageSelectListener mListener;
    private int mSelectTextColor;
    private int mNormalTextColor;
    private int mDefaultPos;
    private List<XBottomBarItemView> mItemList = new ArrayList<>();

    public XBottomBar(Context context) {
        this(context, null);

    }

    public XBottomBar(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public XBottomBar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        addView(inflate(mContext, R.layout.layout_bar_main, null),
                new LinearLayoutCompat.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.MATCH_PARENT));
        init();
    }

    private void init() {
        mBottomLayout =  findViewById(R.id.ll_bar_main);
    }

    public void bind(ViewPager viewPager, boolean smooth, int defaultPos) {
        bind(viewPager, (TextView) null, smooth, defaultPos);
    }

    public void bind(ViewPager viewPager, TextView textView, boolean smooth, int defaultPos) {
        bind(viewPager, null, textView, smooth, defaultPos);
    }

    public void bind(ViewPager viewPager, Toolbar toolbar, boolean smooth, int defaultPos) {
        bind(viewPager, toolbar, null, smooth, defaultPos);
    }

    public <T extends Toolbar> void bind(ViewPager viewPager, T toolbar, TextView textView, boolean smooth, int defaultPos) {
        FragmentPagerAdapter adapter = (FragmentPagerAdapter) viewPager.getAdapter();
        if (adapter == null) {
            throw new IllegalStateException("adapter may be null");
        }
        mToolbar = toolbar;
        mTitleView = textView;
        mDefaultPos = defaultPos;
        mViewPager = viewPager;
        addItemView(viewPager, smooth);
    }

    public void setNoticeCount(int index, int count) {
        mItemList.get(index).setNoticeCount(count);
    }

    public void showNoticePoint(int index) {
        mItemList.get(index).showNoticePoint();
    }

    public void cancelNoticePoint(int index) {
        mItemList.get(index).cancelNoticePoint();
    }

    private void addItemView(final ViewPager pager, final boolean smooth) {
        final FragmentPagerAdapter adapter = (FragmentPagerAdapter) pager.getAdapter();
        clear();
        final int count = adapter.getCount();
        LinearLayoutCompat.LayoutParams params = new LinearLayoutCompat.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.width = XTools.WindowUtil().getScreenWidth() / count;
        for (int i = 0; i < count; i++) {
            final XBottomBarItemView itemView = new XBottomBarItemView(mContext);
            itemView.setTextColor(mNormalTextColor, mSelectTextColor);
            final FragmentInfo fragmentInfo = getFragmentInfo(adapter, i);
            if (i == 0) {
                itemView.getImage().setSelected(true);
                itemView.setTag(i);
                selectItem(itemView, fragmentInfo);
            }
            itemView.set(XTools.ResUtil().getDrawable(fragmentInfo.getDrawableId()), XTools.ResUtil().getString(fragmentInfo.getNameId()));
            final int finalI = i;
            itemView.setOnClickListener(v -> {
                pager.setCurrentItem(finalI, smooth);
                XBottomBarItemView barItemView = (XBottomBarItemView) mBottomLayout.getChildAt(finalI);
                barItemView.setTag(finalI);
                selectItem(barItemView, fragmentInfo);
                if (mFirstItemView == null) {
                    mFirstItemView = (XBottomBarItemView) mBottomLayout.getChildAt(0);
                    mFirstItemView.getImage().setSelected(false);
                }
            });
            mItemList.add(itemView);
            mBottomLayout.addView(itemView, params);
            if (i == mDefaultPos) {
                itemView.performClick();
            }
        }
    }

    private void clear() {
        mBottomLayout.removeAllViews();
    }

    private void selectItem(XBottomBarItemView barItemView, FragmentInfo fragmentInfo) {
        if (mListener == null) {
            mListener = IPageSelectListener.DEFAULT_LISTENER;
        }
        clearTextColor();
        barItemView.select(true);
        int title = fragmentInfo.getNameId();
        int pos = (int) barItemView.getTag();
        if (mToolbar != null) {
            mToolbar.setTitle(title);
        }
        if (mTitleView != null) {
            mTitleView.setText(title);
        }
        mListener.onPageSelect(pos, fragmentInfo);
    }

    private void clearTextColor() {
        for (int i = 0; i < mBottomLayout.getChildCount(); i++) {
            XBottomBarItemView itemView = (XBottomBarItemView) mBottomLayout.getChildAt(i);
            itemView.select(false);
        }
    }

    private FragmentInfo getFragmentInfo(FragmentPagerAdapter adapter, int i) {
        BaseFragment item = (BaseFragment) adapter.getItem(i);
        return item.getFragmentInfo();
    }

    public void setTextStateColor(@ColorRes int normalColor, @ColorRes int selectColor) {
        Resources resources = mContext.getResources();
        mNormalTextColor = resources.getColor(normalColor);
        mSelectTextColor = resources.getColor(selectColor);
    }

    public void setPageSelectListener(IPageSelectListener listener) {
        this.mListener = listener;
    }

    public void selectPage(int position) {
        mItemList.get(position).performClick();
    }
}
