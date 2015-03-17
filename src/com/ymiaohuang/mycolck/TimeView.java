package com.ymiaohuang.mycolck;
/* 计时显示，在StopWatch和alarm中已有运用。
 *
 * 定时重复调用某段函数。实现时间的刷新。
 * Handler对消息的处理会新建一个子线程。
 * 利用实例化Handler类并重写handleMassage方法，在方法内部添加想要调用的方法。
 * 在handleMassage内部添加sendEmptyMessageDelayed(0, 1000)能重复调用handleMassage方法。
 * sendEmptyMessage(0)：开启handleMassage（）;
 * */
import java.text.SimpleDateFormat;
import java.util.Date;

import android.content.Context;
import android.opengl.Visibility;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

public class TimeView extends LinearLayout {
	private TextView tvTime;
	public TimeView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		
	}

	public TimeView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}
	//我们要调用的。其他两个由系统调用。
	public TimeView(Context context) {
		super(context);
		
	}
	
	//完成自定义控件被导入到XMl中后触发onFinishInflate,所以将自定义控件的子控件在此方法内设置。
	protected void onFinishInflate() {
		super.onFinishInflate();
		tvTime = (TextView) findViewById(R.id.tvTime);
		tvTime.setTextSize(70);
		timeHandler.sendEmptyMessage(0);
		
	}
	//当View的Visibility发送改变是调用，当跳转到其他View的时候，我们不需继续运算时间显示。
	protected void onVisibilityChanged(View changedView, int visibility) {
		super.onVisibilityChanged(changedView, visibility);
		//开启和关闭定时器
		if (visibility == View.VISIBLE) {
			timeHandler.sendEmptyMessage(0);//0：what的值，用于区分消息来源。
		}else{
			timeHandler.removeMessages(0);
		}
	}
	//刷新时间
	private void refreshTime(){
		SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
		Date curDate = new Date(System.currentTimeMillis());
		String str = format.format(curDate);
		tvTime.setText(str);
	}
	//设置一个定时发送器
	private Handler timeHandler = new Handler(){
		public void handleMessage(android.os.Message msg){
			refreshTime();
			if (getVisibility() == View.VISIBLE) {
				timeHandler.sendEmptyMessageDelayed(0, 1000);
			}
		}
	};
}
