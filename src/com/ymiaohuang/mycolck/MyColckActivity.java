package com.ymiaohuang.mycolck;
/*
 * 多功能时钟，使用TabHost布局。
 * 这里学习TabHost布局的使用。
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
        tabHost.addTab(tabHost.newTabSpec("tabAlarm").setIndicator("闹钟").setContent(R.id.tabAlarm));
        tabHost.addTab(tabHost.newTabSpec("tabTime").setIndicator("时钟").setContent(R.id.tabTime));
        tabHost.addTab(tabHost.newTabSpec("tabTimer").setIndicator("计时器").setContent(R.id.tabTimer));
        tabHost.addTab(tabHost.newTabSpec("tabStopWatch").setIndicator("秒表").setContent(R.id.tabStopWatch));
    }
}