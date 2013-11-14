package yong.android.scheduler;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Hashtable;
import java.util.Vector;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.example.ch07.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {

	public static final int REQUEST_CODE_INPUTSCHEDULE = 1001;
	Button nextMonth;
	Button prevMonth;
	Button saveSchedule;
	TextView monthText;
	CalandarAdapter adapter;
	ScheduleAdapter sAdapter;
	GridView calendar;
	ListView scheduleList;
	String setMonth = null;
	String datekey =null;
	int selectedDay = 0;
	int selectedSchedule = -1;
	Vector<String> tempList;
	Vector<String> zeroList;
	Hashtable<String, Vector<String>> schedule;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		
		tempList = new Vector<String>();
		zeroList = new Vector<String>();
		schedule = new Hashtable<String,Vector<String>>();
		nextMonth = (Button)findViewById(R.id.monthNext);
		prevMonth = (Button)findViewById(R.id.monthPrevious);
		monthText = (TextView)findViewById(R.id.monthText);
		calendar = (GridView)findViewById(R.id.calendar);
		scheduleList = (ListView)findViewById(R.id.scheduleList);
		sAdapter = new ScheduleAdapter(getApplicationContext());
		adapter = new CalandarAdapter(getApplicationContext(),schedule);
		drawText();
		scheduleList.setOnItemClickListener(new OnItemClickListener() 
		{

			@Override
			public void onItemClick(AdapterView parent, View v, int position, long id) {
				// TODO Auto-generated method stub
				selectedSchedule = (int) sAdapter.getItemId(position);
			}
		});
		calendar.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView parent, View v, int position, long id) 
			{
				selectedSchedule = -1;
				adapter.setSelectedPosition(position);
				selectedDay = ((Integer)adapter.getItem(position)).intValue();
				calendar.setAdapter(adapter);
				datekey = adapter.getmYear()+""+adapter.getmMonth()+adapter.getItem(position);
				Log.d("데이타 키", datekey);
				if(schedule.get(datekey)!=null)
				{
					sAdapter.setList(schedule.get(datekey));
				}
				else
				{
					sAdapter.setList(zeroList);
				}
				scheduleList.setAdapter(sAdapter);
			}
		});
		
		nextMonth.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Log.d("들어옴1", ""+adapter.getmMonth());
				// TODO Auto-generated method stub
				if(adapter.getmMonth()==12)
				{
					adapter.setmMonth(1);
					adapter.setmYear((adapter.getmYear()+1));
					adapter.calculateDateInfo();
				}
				else
				{
					adapter.setmMonth((adapter.getmMonth()+1));
					adapter.calculateDateInfo();
				}
				datekey=null;
				drawText();
			}
		});
		prevMonth.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Log.d("들어옴2", ""+adapter.getmMonth());
				// TODO Auto-generated method stub
				if(adapter.getmMonth()==1)
				{
					adapter.setmMonth(12);
					adapter.setmYear((adapter.getmYear()-1));
					adapter.calculateDateInfo();
				}
				else
				{
					adapter.setmMonth((adapter.getmMonth()-1));
					adapter.calculateDateInfo();
				}
				datekey=null;
				drawText();
			}
		});
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		if(requestCode == REQUEST_CODE_INPUTSCHEDULE)
		{
			String time = data.getExtras().getString("time"); //데이타 받기
			String schedule2 = data.getExtras().getString("schedule"); //스케쥴 받기
			
			if(schedule.get(datekey) != null)
			{
				String data1 = time + " : " +schedule2;
				Log.d(datekey+"의 값", schedule.get(datekey).get(0));
				schedule.get(datekey).add(data1);
			}
			else
			{
				String data1 = time + " : " +schedule2;
				tempList = new Vector<String>();
				tempList.add(data1);
				schedule.put(datekey,tempList);
			}
			sAdapter.setList(schedule.get(datekey));
			scheduleList.setAdapter(sAdapter);
			Toast.makeText(getApplicationContext(), "key : "+datekey+"  "+schedule.get(datekey).get(0), Toast.LENGTH_LONG).show();
		}
		//super.onActivityResult(requestCode, resultCode, data);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		int cursor = item.getItemId();
		switch(cursor)
		{
			case R.id.inputSch:
				if(datekey!=null)
				{
					Intent intent = new Intent(getBaseContext(),ScheduleDialogActivity.class);
					intent.putExtra("date", datekey);
					startActivityForResult(intent,REQUEST_CODE_INPUTSCHEDULE);
				} 
				else
				{
					Toast.makeText(this, "날짜를 선택해 주세요", Toast.LENGTH_LONG).show();
				}
				break;
			case R.id.deleteSchedule:
				if(selectedSchedule < 0)
				{
					Toast.makeText(this, "일정이 선택되지 않았습니다.", Toast.LENGTH_LONG).show();
				}
				else
				{
					sAdapter.deleteSchedule(selectedSchedule);
				}
				//sAdapter.setList(schedule.get(datekey));
				scheduleList.setAdapter(sAdapter);
				break;
			default:
				break;
		}
		return super.onOptionsItemSelected(item);
	}
	private void drawText()
	{
		adapter.setSelectedPosition(-1);
		setMonth = adapter.getmYear()+"년 "+adapter.getmMonth() +"월";
		monthText.setText(setMonth);
		calendar.setAdapter(adapter);
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
