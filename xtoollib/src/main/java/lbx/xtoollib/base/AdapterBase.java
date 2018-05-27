package lbx.xtoollib.base;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;
import java.util.List;

import lbx.xtoollib.XTools;


/**
 * @author lbx
 * @date 2017/3/20.
 */

public abstract class AdapterBase<T, M extends AdapterBase.BaseHolder> extends BaseAdapter {

    private List<T> list;
    private Context context;
    private int layoutId;
    private M holder;

    public AdapterBase(Context context, List<T> list, int layoutId, M holder) {
        super();
        this.context = context;
        this.list = list;
        this.layoutId = layoutId;
        this.holder = holder;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public T getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        M holder;
        if (convertView == null) {
            convertView = XTools.UiUtil().inflate(layoutId);
            holder = this.holder;
            convertView.setTag(holder);
        } else {
            holder = (M) convertView.getTag();
        }
        T data = list.get(position);
        bindView(convertView, holder);
        setData(holder, data, list, position);
        return convertView;
    }

    public static class BaseHolder {
        public BaseHolder() {
        }
    }

    public List<T> addData(List<T> list) {
        this.list.addAll(list);
        notifyDataSetChanged();
        return this.list;
    }

    public List<T> addData(T... data) {
        for (int i = 0; i < data.length; i++) {
            this.list.add(data[i]);
        }
        notifyDataSetChanged();
        return this.list;
    }

    public List<T> setData(List<T> list) {
        this.list = new ArrayList<>();
        this.list.addAll(list);
        notifyDataSetChanged();
        return this.list;
    }

    public List<T> setData(T... data) {
        this.list = new ArrayList<>();
        for (T t : data) {
            this.list.add(t);
        }
        notifyDataSetChanged();
        return this.list;
    }

    public List<T> getList() {
        return list;
    }

    public abstract void bindView(View convertView, M holder);

    public abstract void setData(M holder, T data, List<T> list, int pos);
}
