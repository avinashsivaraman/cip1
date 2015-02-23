package com.example.preview;




import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import android.support.v7.app.ActionBarActivity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends ActionBarActivity {

	ScrollView scroll;
	TextView textview;
	int y;
	Toast toast;
	//ScrollView scroll= (ScrollView)findViewById(R.id.scroll);
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		scroll= (ScrollView)findViewById(R.id.scroll);
		Button next = (Button)findViewById(R.id.next);        
		next.setOnClickListener(nextListener);
		Button prev = (Button)findViewById(R.id.prev);        
		prev.setOnClickListener(prevListener);
		y=0;
		textview=(TextView)findViewById(R.id.link);
		
		
        
		
		
		
		
		
		
		
		/*
		File sdcard = Environment.getExternalStorageDirectory();

		//Get the text file
		File file = new File(sdcard,"file.txt");

		//Read text from file
		StringBuilder text = new StringBuilder();

		try {
		    BufferedReader br = new BufferedReader(new FileReader(file));
		    String line;

		    while ((line = br.readLine()) != null) {
		        text.append(line);
		        text.append('\n');
		    }
		    br.close();
		}
		catch (IOException e) {
		    //You'll need to add proper error handling here
		}

		//Find the view by its id
		TextView tv = (TextView)findViewById(R.id.text0);

		//Set the text
		tv.setText(text);*/
    }

    
    private OnClickListener nextListener = new OnClickListener() {
        public void onClick(View v) {
          //Log.d(logtag,"onClick() called - start button");              
          y+=1050;
          scroll.smoothScrollTo(0,y);
          int p=(y+1050)/1050;
          String text="Next Page:"+p;
          try{
          toast.cancel();
          }
          catch(Exception e)
          {}
          toast=Toast.makeText(getApplicationContext(),text, Toast.LENGTH_SHORT);
          toast.show();
        }

	};
	
	private OnClickListener prevListener = new OnClickListener() {
        public void onClick(View v) {
          //Log.d(logtag,"onClick() called - start button");              
          
          y-=1050;
          if(y<0)
        	  y=0;
          scroll.smoothScrollTo(0, y);
          int p=(1050+y)/1050;
          String text="Next Page:"+p;
          try{
              toast.cancel();
              }
              catch(Exception e)
              {}
          toast=Toast.makeText(getApplicationContext(),text, Toast.LENGTH_SHORT);
          toast.show();
        }

	};

	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}