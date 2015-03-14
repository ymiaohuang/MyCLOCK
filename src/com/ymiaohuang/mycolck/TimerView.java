package com.ymiaohuang.mycolck;

import java.util.Timer;
import java.util.TimerTask;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

public class TimerView extends LinearLayout implements OnClickListener {
	private Button btnStart, btnReset;
	private EditText etTime;
	private TextView tvStatus;
	private int time = 0, count = 0;
	private Timer timer = null;
	private TimerTask timerTask = null;

	public TimerView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public TimerView(Context context) {
		super(context);
	}

	protected void onFinishInflate() {
		super.onFinishInflate();
		initView();

	}

	private void initView() {

		etTime = (EditText) findViewById(R.id.ettime);

		btnStart = (Button) findViewById(R.id.btnstart);
		btnReset = (Button) findViewById(R.id.btnreset);
		tvStatus = (TextView) findViewById(R.id.tvstatus);

		btnStart.setOnClickListener(this);
		btnReset.setOnClickListener(this);
	}

	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btnstart:

			etTime.setCursorVisible(false);

			if (count % 2 == 0) {
				time = Integer.parseInt(etTime.getText().toString());
				btnStart.setText(R.string.pause);
				tvStatus.setText(R.string.countting);
				count++;
				startTime();
			} else {
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

	// 消息处理器，能接收本对象发出的消息。
	private Handler mHanlder = new Handler() {
		public void handleMessage(android.os.Message msg) {
			etTime.setText(msg.arg1 + "");
			startTime();
		};
	};

	public void startTime() {
		timer = new Timer();
		timerTask = new TimerTask() {

			public void run() {
				time--;
				Message message = mHanlder.obtainMessage();// 通过Handler获取消息对象。
				message.arg1 = time;// 设置该消息的参数
				mHanlder.sendMessage(message);// 发送该消息
			}

		};
		timer.schedule(timerTask, 1000);
	}

	public void puaseTime() {

		timer.cancel();
	}

	public void reSet() {
		etTime.setCursorVisible(true);
		etTime.setText("00");
		tvStatus.setText(R.string.inputtime);
		timer.cancel();
		btnStart.setText(R.string.start);
		count = 0;
	}

}
