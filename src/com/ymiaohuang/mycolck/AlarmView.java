package com.ymiaohuang.mycolck;
/*
 * 
 * 1.闹钟的添加
 * 这里用到了ListView .. 用到ArrayAdapter，Calendar。
 * 
 * 2.通过SharedPreferences存储数据
 * SharedPreferences接口主要负责读取应用程序的Preferences数据，
 * 通过key_value键值对存入数据。
 * 通过SharedPreferences的实例对象获取Editor完成对数据的写出。
 * 最后Editor.commit()关闭资源。
 * 
 * */
import java.util.Calendar;

import android.app.TimePickerDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TimePicker;

public class AlarmView extends LinearLayout {

	private Button btnAddAlarm;//添加闹钟
	private ListView lvAlarmList;//闹钟列表
	private ArrayAdapter<myAlarm> adapter; 
	public static final String KEY_ALARM_LIST = "alarmList";
	//此构造函数由XML调用。
	public AlarmView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}
	//程序调用
	public AlarmView(Context context) {
		super(context);
	}
	//当View和它的所有子对象从XML中导入之后，调用此方法
	protected void onFinishInflate() {
		super.onFinishInflate();
		
		btnAddAlarm = (Button) findViewById(R.id.btnAddAlarm);
		lvAlarmList = (ListView) findViewById(R.id.lvAlarmList);
		
		readSavedAlarmList();
		
		btnAddAlarm.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				addAlarm();
			}
		});
		//获取一个List适配器
		adapter = new ArrayAdapter<myAlarm>(getContext(), android.R.layout.simple_list_item_1);
		lvAlarmList.setAdapter(adapter);
		
	}
	
	public void addAlarm(){
		//Calendar是抽象类，只能通过getInstance获取。默认获取的Calendar时间为当前时间。
		//可以通过c.set(Calendar.HOUR_OF_DAY, hour)来设置时间。
		Calendar c = Calendar.getInstance();
		
		//日期对话框，TimePickerDialog（Cotext，callback，默认显示的时间，默认显示的时间，24小时制）.show
		new TimePickerDialog(getContext(), new TimePickerDialog.OnTimeSetListener() {
			//TimePickerDialog的设置按钮。
			public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
				//判断设置的闹钟的时间，如何小于现在，则设为明天的时间。否则为设置的闹钟时间。
				Calendar setTime = Calendar.getInstance();
				setTime.set(Calendar.HOUR_OF_DAY,hourOfDay);
				setTime.set(Calendar.MINUTE,minute);
				
				Calendar nowTime = Calendar.getInstance();
				if(setTime.getTimeInMillis()<=nowTime.getTimeInMillis()){
					setTime.setTimeInMillis(setTime.getTimeInMillis()+24*60*60*1000);//延迟一天。
				}
				
				adapter.add(new myAlarm(setTime.getTimeInMillis()));
				saveAlarmList();
			}
		}, c.get(Calendar.HOUR_OF_DAY), c.get(Calendar.MINUTE), true).show();
		
	}
	
	private void saveAlarmList(){
		//获取Editor
		//getCount()获取Item的数量，getItem(i)获取Item中的对象。
		//将数据put。
		Editor editor = getContext().getSharedPreferences(AlarmView.class.getName(), Context.MODE_PRIVATE).edit();
		StringBuffer sb = new StringBuffer();
		
		for (int i = 0; i < adapter.getCount(); i++) {
			sb.append(adapter.getItem(i).getTime()).append(",");
		}
		editor.putString(KEY_ALARM_LIST,sb.toString().substring(0, sb.length()-1));
		editor.commit();
	}
	//闹钟的读取，未完成。。。
	private void readSavedAlarmList(){
		SharedPreferences sp = getContext().getSharedPreferences(AlarmView.class.getName(), Context.MODE_PRIVATE);
		String content = sp.getString(KEY_ALARM_LIST, null);

		if (content!=null) {
			String[] timeStrings = content.split(",");
			for (int i = 0; i < timeStrings.length; i++) {
				
				//adapter.add(new myAlarm(Long.parseLong(timeStrings[i])));
				//将读取到的闹钟写到List上，此句执行出错。未解决。
				
			}
			
		}
	}
	//自定义一个闹钟类。
	private static class myAlarm{
		private long time = 0;
		private String timeLable = "";
		private Calendar c;
		public myAlarm(long time){
			this.time = time;
			c = Calendar.getInstance();
			c.setTimeInMillis(time);
			timeLable = c.get(Calendar.HOUR_OF_DAY)+":"+c.get(Calendar.MINUTE);
			
		}
		//List上的显示会调用该类的toString（）方法；
		public String toString() {
			return getTimeLable();
		}
		public long getTime(){
			return time;
		}
		public String getTimeLable(){
			return timeLable;
			
		}
	}

}
