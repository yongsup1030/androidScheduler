package yong.android.scheduler;

import com.example.ch07.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

public class ScheduleDialogActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		setContentView(R.layout.scheduler);
		final Intent receivedIntent = getIntent();
		Button save = (Button)findViewById(R.id.save);
		TextView when = (TextView)findViewById(R.id.when);
		when.setText(receivedIntent.getExtras().getString("date"));
		final EditText schedule = (EditText)findViewById(R.id.schedule);
		final TimePicker scheduleTime = (TimePicker)findViewById(R.id.scheduleTime);
		save.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent resultIntent = new Intent();
				String time = scheduleTime.getCurrentHour().toString() + ":" + scheduleTime.getCurrentMinute();
				resultIntent.putExtra("time",time);
				resultIntent.putExtra("schedule", schedule.getText().toString());
				setResult(1,resultIntent);
				finish();
			}
		});
		super.onCreate(savedInstanceState);
	}
	
}
