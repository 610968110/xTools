package lbx.xtoollib.base;

import android.content.Context;
import android.databinding.ViewDataBinding;
import android.view.View;

import java.util.List;

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
 * @date 2018/9/28.
 */

public abstract class BaseDefaultDataAdapter<M, N extends ViewDataBinding> extends BaseDataAdapter<M, N, BaseDataAdapter.BaseHolder> {

    public BaseDefaultDataAdapter(Context context, List<M> list) {
        super(context, list);
    }

    @Override
    public BaseHolder getHolder(View view, ViewDataBinding binding) {
        return new BaseHolder(view, binding);
    }
}
