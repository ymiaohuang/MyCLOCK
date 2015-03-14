package com.ymiaohuang.mycolck;

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
	private TextView tvMinute,tvSecond,tvMs;
	private TextView tvStatus;
	private int ms,s,m,count;
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
	
	public void startTime(){
		timer = new Timer();
		timerTask = new TimerTask(){

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
				
				mHanlder.sendEmptyMessage(0);
			}
			
		};
		timer.schedule(timerTask, 100);
	}
	public void puaseTime(){
		
		timer.cancel();
	}
	public void reSet(){
		
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
