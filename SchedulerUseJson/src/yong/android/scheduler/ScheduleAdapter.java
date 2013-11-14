package yong.android.scheduler;

import java.util.Vector;
import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class ScheduleAdapter extends BaseAdapter {

	private Vector<String> list;

	public Vector<String> getList() {
		return list;
	}

	public void setList(Vector<String> list) {
		this.list = list;
	}

	Context mContext;
	public ScheduleAdapter(Context context)
	{
		list = new Vector<String>();
		mContext = context;
	}
	
	public void deleteSchedule(int position)
	{
		list.remove(position);
	}
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return list.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		TextView text01 = null;
		if(convertView == null)
		{
			text01 = new TextView(mContext);
		}
		else
		{
			text01 = (TextView)convertView;
		}
		text01.setText(list.get(position));
		return text01;
	}
}

