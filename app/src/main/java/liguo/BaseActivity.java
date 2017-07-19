package liguo;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;

import liguo.application.AppManager;
import liguo.application.BaseApplication;


/**
 * Created by Extends on 2016/2/22 0022.
 */
public abstract class BaseActivity extends AppCompatActivity {
    private View statusBarView;
    private static final int INVALID_VAL = -2;
    private static final int COLOR_DEFAULT = Color.parseColor("#003c474c");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        config(savedInstanceState);
        ViewGroup contentView = (ViewGroup) this.findViewById(android.R.id.content);
        statusBarView = new View(this);

        ViewGroup.LayoutParams lp = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                BaseApplication.Companion.getStatusBarHeight());
        contentView.addView(statusBarView, lp);
        compat();
        AppManager.getInstance().addActivity(this);
        initView(savedInstanceState);
        initData();
        registEventBus();
    }


    //如果需要在子类中接收EventBus的消息，需要重写这两个方法
    public void registEventBus(){}
    public void unRegistEventBus(){}

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unRegistEventBus();
        AppManager.getInstance().finishActivity(this);
    }

    /**
     * 配置信息，如setContentView方法可以写在这里
     */
    protected abstract void config(@Nullable Bundle savedInstanceState);

    /**
     * 初始化控件
     */
    protected abstract void initView(@Nullable Bundle savedInstanceState);

    /**
     * 初始化数据
     */
    protected abstract void initData();

    /**
     * 更改状态栏颜色
     * @param statusColor
     */
    public void compat(int statusColor)
    {
        statusBarView.setBackgroundColor(statusColor);
    }
    /**
     * 更改状态栏颜色
     */
    public void compat()
    {
        compat(Color.parseColor("#00000000"));
    }
}
