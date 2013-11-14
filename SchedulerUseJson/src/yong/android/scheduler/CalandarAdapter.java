package yong.android.scheduler;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Hashtable;
import java.util.Vector;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class CalandarAdapter extends BaseAdapter {

	private Calendar cal;
	
	private int date[] = new int[42];
	private int mYear;
	private int mMonth;
	private int mDay;
	private int firstDay;
	private int maxDay;
	private int selectedPosition = -1;
	private Hashtable<String,Vector<String>> hash;
	private String datekey;
	Context mContext;
	
	public CalandarAdapter(Context context, Hashtable<String,Vector<String>> hash)
	{
		init(context,hash);
	}
	private void init(Context context,Hashtable<String,Vector<String>> hash)
	{
		this.hash = hash;
		Log.d("시작","시작");
		cal = Calendar.getInstance();
		cal.set(Calendar.DAY_OF_MONTH, 1);
		mYear = cal.get(Calendar.YEAR);
		mMonth = cal.get(Calendar.MONTH)+1;
		calculateDateInfo();
		mContext = context;
		datekey = mYear +""+ mMonth;
		Log.d("데이트키값",datekey);
	}
	public void calculateDateInfo()
	{
		int i;
		Log.d("시작2","시작2");
		
		cal.set(Calendar.MONTH, mMonth-1);
		cal.set(Calendar.YEAR, mYear);
		datekey = mYear +""+ mMonth;
		firstDay = cal.get(Calendar.DAY_OF_WEEK)-1;
		maxDay = cal.getActualMaximum(Calendar.DATE);
		for(i=0;i<42;i++)
		{
			date[i]=0;
		}
		for(i=firstDay;i<firstDay+maxDay;i++)
		{
			date[i] = i-firstDay+1;
		}
		Log.d("끝3","끝2");
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return 7*6;
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return date[position];
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@SuppressLint("NewApi")
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		TextView text01 = null;
		String newDatekey = datekey+""+date[position];
		Log.d("뉴데이트 키값", newDatekey);
		if(convertView == null)
		{
			text01 = new TextView(mContext);
		}
		else
		{
			text01 = (TextView)convertView;
		}
		
		
		if(date[position] == 0)
		{
			text01.setText("");
		}
		else
		{
			text01.setText(""+date[position]);
		}
		if(position%7==6)
		{
			text01.setTextColor(Color.BLUE);
		}
		if(position%7==0)
		{
			text01.setTextColor(Color.RED);
		}
		if(selectedPosition == position)
		{
			text01.setBackgroundColor(Color.YELLOW);
		}
		else
		{
			text01.setBackgroundColor(Color.WHITE);
		}
		if(hash.get(newDatekey) != null)		//일정데이타 존재여부
		{
			Log.d("들어옴", "하하하하하하하핳");
			text01.setBackgroundColor(Color.RED); 
		}
		text01.setTextSize(20.0f);
		text01.setGravity(Gravity.CENTER);
		return text01;
	}
	
	//Getter Setter
	public int getSelectedPosition(){
		return selectedPosition;
	}
	public void setSelectedPosition(int selectedPosition){
		this.selectedPosition = selectedPosition;
	}
	public int getMaxDay() {
		return maxDay;
	}
	public void setMaxDay(int maxDay) {
		this.maxDay = maxDay;
	}
	public int getmYear() {
		return mYear;
	}
	public void setmYear(int mYear) {
		this.mYear = mYear;
	}
	public int getmMonth() {
		return mMonth;
	}
	public void setmMonth(int mMonth) {
		this.mMonth = mMonth;
	}
	public int getmDay() {
		return mDay;
	}
	public void setmDay(int mDay) {
		this.mDay = mDay;
	}
	public int getFirstDay() {
		return firstDay;
	}
	public void setFirstDay(int firstDay) {
		this.firstDay = firstDay;
	}
	public Calendar getCal() {
		return cal;
	}
	public void setCal(Calendar cal) {
		this.cal = cal;
	}
	
}
