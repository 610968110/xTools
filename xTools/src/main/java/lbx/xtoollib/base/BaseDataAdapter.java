package lbx.xtoollib.base;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.annotation.CallSuper;
import android.support.annotation.LayoutRes;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import java.util.List;

/**
 * @author lbx
 * @date 2017/11/15.
 */

public abstract class BaseDataAdapter<M, N extends ViewDataBinding, T extends BaseDataAdapter.BaseHolder> extends RecyclerView.Adapter<T> {

    private RecyclerView mRecycleView;
    private LayoutInflater mInflater;
    private List<M> mList;

    public BaseDataAdapter(Context context, List<M> list) {
        this.mInflater = LayoutInflater.from(context);
        this.mList = list;
    }

    @Override
    public void onBindViewHolder(T holder, int position) {
        ViewDataBinding binding = holder.getBinding();
        dataBinding((N) binding, position, mList.get(position), holder);
        setClickListener(binding.getRoot(), itemClickEnable(), itemLongClickEnable(), position);
        binding.executePendingBindings();
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    @CallSuper
    @Override
    public T onCreateViewHolder(ViewGroup parent, int viewType) {
        if (parent instanceof RecyclerView) {
            mRecycleView = (RecyclerView) parent;
        }
        ViewDataBinding binding = DataBindingUtil.inflate(mInflater, itemLayout(), parent, false);
        return getHolder(binding.getRoot(), binding);
    }

    public abstract T getHolder(View view, ViewDataBinding binding);

    public abstract
    @LayoutRes
    int itemLayout();

    public abstract void dataBinding(N binding, int position, M entity, T t);

    public abstract boolean itemClickEnable();

    public abstract boolean itemLongClickEnable();

    public RecyclerView getRecycleView() {
        return mRecycleView;
    }

    public interface OnItemClickListener<M> {
        void onItemClick(RecyclerView recyclerView, int id, int position, M entity);

        void onItemLongClick(RecyclerView recyclerView, int id, int position, M entity);
    }

    protected void setClickListener(View view, boolean itemClick, boolean itemLongClick, final int position) {
        final M m = mList.get(position);
        if (itemClick) {
            view.setOnClickListener(v -> {
                RecyclerView recycleView = getRecycleView();
                OnItemClickListener<M> onItemClickListener1 = getOnItemClickListener();
                if (onItemClickListener1 != null) {
                    onItemClickListener1.onItemClick(recycleView, recycleView == null ? 0 : recycleView.getId(), position, m);
                }
            });
        } else {
            view.setOnClickListener(null);
        }
        if (itemLongClick) {
            view.setOnLongClickListener(v -> {
                RecyclerView recycleView = getRecycleView();
                OnItemClickListener<M> onItemClickListener12 = getOnItemClickListener();
                if (onItemClickListener12 != null) {
                    onItemClickListener12.onItemLongClick(recycleView, recycleView == null ? 0 : recycleView.getId(), position, m);
                }
                return true;
            });
        } else {
            view.setOnLongClickListener(null);
        }
    }

    protected void setClickListener(ViewDataBinding binding, boolean itemClick, boolean itemLongClick, final int position) {
        setClickListener(binding.getRoot(), itemClick, itemLongClick, position);
    }

    protected void setClickListener(ViewDataBinding binding, final int position) {
        setClickListener(binding, true, true, position);
    }

    protected void setClickListener(View view, final int position) {
        setClickListener(view, true, true, position);
    }

    public void setOnItemClickListener(OnItemClickListener<M> onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    private OnBroadcastClick DEFAULT_CLICK_LISTENER = new OnBroadcastClick();

    private class OnBroadcastClick implements OnItemClickListener<M> {
        @Override
        public void onItemClick(RecyclerView recyclerView, int id, int position, M entity) {

        }

        @Override
        public void onItemLongClick(RecyclerView recyclerView, int id, int position, M entity) {

        }
    }

    private OnItemClickListener<M> onItemClickListener = DEFAULT_CLICK_LISTENER;

    public OnItemClickListener<M> getOnItemClickListener() {
        return onItemClickListener;
    }

    public static class BaseHolder extends RecyclerView.ViewHolder {
        private final ViewDataBinding viewDataBinding;

        public BaseHolder(View view, ViewDataBinding viewDataBinding) {
            super(view);
            this.viewDataBinding = viewDataBinding;
        }

        public ViewDataBinding getBinding() {
            return viewDataBinding;
        }

    }

    public List<M> getList() {
        return mList;
    }

    public void setList(List<M> list) {
        this.mList = list;
    }
}
