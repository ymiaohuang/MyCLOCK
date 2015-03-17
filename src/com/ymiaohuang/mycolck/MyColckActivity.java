package com.ymiaohuang.mycolck;
/*
 * 多功能时钟，使用TabHost布局。
 * 需要用到的知识：
 * TableHost布局，Handler消息处理，calendar日期类
 * */
import android.app.Activity;
import android.os.Bundle;
import android.widget.TabHost;

public class MyColckActivity extends Activity {
    private TabHost tabHost;
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        tabHost = (TabHost) findViewById(android.R.id.tabhost);
        tabHost.setup();//初始tabHost
        
        //按顺序给tabHost添加表。即添加选项卡，必须给选项卡添加指示器（标题）和内容。
        //点击选项卡，跳转到对应的View。
        tabHost.addTab(tabHost.newTabSpec("tabAlarm").setIndicator("闹钟").setContent(R.id.tabAlarm));
        tabHost.addTab(tabHost.newTabSpec("tabTime").setIndicator("时钟").setContent(R.id.tabTime));
        tabHost.addTab(tabHost.newTabSpec("tabTimer").setIndicator("计时器").setContent(R.id.tabTimer));
        tabHost.addTab(tabHost.newTabSpec("tabStopWatch").setIndicator("秒表").setContent(R.id.tabStopWatch));
    }
}
