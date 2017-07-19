package liguo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


public abstract class BaseFragment extends Fragment {
    public View view;

    public static final int STATE_NONE = 0;
    public static final int STATE_REFRESH = 1;
    public static final int STATE_LOADMORE = 2;
    public static final int STATE_NOMORE = 3;
    public static final int STATE_PRESSNONE = 4;// 正在下拉但还没有到刷新的状态
    public static int mState = STATE_NONE;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = config(inflater, container, savedInstanceState);
        initView( inflater,  container,  savedInstanceState);
        initData();
        registEventBus();
        return view;
    }

    //如果需要在子类中接收EventBus的消息，需要重写这两个方法
    public void registEventBus(){}
    public void unRegistEventBus(){}


    @Override
    public void onDestroyView() {
        unRegistEventBus();
        super.onDestroyView();
    }

    /**
     * 配置信息，如setContentView方法可以写在这里
     */
    protected abstract View config(LayoutInflater inflater, ViewGroup container, @Nullable Bundle savedInstanceState);

    /**
     * 初始化控件
     */
    protected abstract void initView(LayoutInflater inflater, ViewGroup container, @Nullable Bundle savedInstanceState);

    /**
     * 初始化数据
     */
    protected abstract void initData();
}
