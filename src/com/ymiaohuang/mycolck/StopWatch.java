package com.ymiaohuang.mycolck;
/*
 * 秒表，实现秒表的计时功能。
 * “开始计时”按钮后 屏幕上显示的时间从0：0.0开始计时。最小单位为100毫秒。
 * “暂停”按钮：暂停计时。“重置”屏幕显示的时间重置为0：0.0 。
 *
 * Handler，Timer，TimerTask的运用。
 *
 */
import java.util.Timer;
import java.util.TimerTask;
import android.content.Context;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class StopWatch extends LinearLayout implements OnClickListener{

	

	private Button btnStart,btnReset;
	private TextView tvMinute,tvSecond,tvMs;//显示在屏幕上的数字，都是TextView。
	private TextView tvStatus;//显示计时的状态。
	private int ms,s,m,count;//用显示到屏幕上。
	private Timer timer = null;
	private TimerTask timerTask = null;
	
	public StopWatch(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}
	public StopWatch(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public StopWatch(Context context) {
		super(context);
	}
	
	protected void onFinishInflate() {
		super.onFinishInflate();
		initView();
	}
	//初始化
	private void initView(){
		
		ms = 0;
		s = 0;
		m = 0;
		count = 0;
		
		tvMinute = (TextView) findViewById(R.id.tvminute);
		tvSecond = (TextView) findViewById(R.id.tvsecond);
		tvMs = (TextView) findViewById(R.id.tvms);
		
		btnStart = (Button) findViewById(R.id.btnstart);
		btnReset = (Button) findViewById(R.id.btnreset);
		tvStatus = (TextView) findViewById(R.id.tvstatus);
		
		btnStart.setOnClickListener(this);
		btnReset.setOnClickListener(this);
	}

	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btnstart:
			
			//切换同一个按钮，“开始”“暂时”功能。
			if(count%2 == 0){
				ms = Integer.parseInt(tvMs.getText().toString());
				btnStart.setText(R.string.pause);
				tvStatus.setText(R.string.countting);
				count++;
				startTime();
			}else{
				btnStart.setText(R.string.start);
				tvStatus.setText(R.string.pause);
				count--;
				puaseTime();
			}
			break;

		case R.id.btnreset:
			reSet();
			break;
		}
	}
	//消息处理器，能接收本对象发出的消息。
	private Handler mHanlder = new Handler(){
		public void handleMessage(android.os.Message msg) {
			startTime();
			tvMs.setText(ms+"");
			tvSecond.setText(s+"");
			tvMinute.setText(m+"");
		};
	};
	//开始计时
	public void startTime(){
		timer = new Timer();//计时器类
		timerTask = new TimerTask(){//计时计划类，用于定时执行run中的代码。

			public void run() {
				System.out.println(ms++);
				
				if(ms == 10){
					ms = 0;
					s++;
					if(s == 60){
						s = 0;
						m++;
					}
				}
				//发送消息通知Handler
				mHanlder.sendEmptyMessage(0);
			}
			
		};
		timer.schedule(timerTask, 100);//开启定时器，每100毫秒执行一次。
	}
	public void puaseTime(){
		
		timer.cancel();//“暂停”时，取消timer，停止计时。
	}
	public void reSet(){//重置
		
		tvStatus.setText(R.string.ready);
		timer.cancel();
		btnStart.setText(R.string.start);
		
		ms = 0;
		s = 0;
		m = 0;
		count = 0;
		
		tvMs.setText(ms+"");
		tvSecond.setText(s+"");
		tvMinute.setText(m+"");
	}

}
