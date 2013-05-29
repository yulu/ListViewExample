package com.example.listviewexample;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {
	private String[] values = new String[]{"Android", "iPhone", "WindowsMobile",
			"Blackberry", "WebOS", "Ubuntu", "Windows7", "Max OS X", "Linux",
			"Android", "OS/2"
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		
		final ListView listview = (ListView)findViewById(R.id.listview);
		final ArrayList<String> list = new ArrayList<String>();
		for(int i = 0; i <values.length; ++i){
			list.add(values[i]);
		}
		
		/**
		 * self defined adapter
		 */
		final StableArrayAdapter adapter = new StableArrayAdapter(this, 
				android.R.layout.simple_expandable_list_item_1, list);
		listview.setAdapter(adapter);

		/**
		 * listener
		 */
		listview.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
			
			@SuppressLint("NewApi")
			@Override
			public boolean onItemLongClick(AdapterView<?> parent, final View view, int position,
					long id) {
				final String item = (String) parent.getItemAtPosition(position);

				list.remove(item);
				adapter.notifyDataSetChanged();
				view.setAlpha(1);
				return true;				
			}
		});
		
		listview.setOnItemClickListener(new OnItemClickListener(){
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id){
				Toast.makeText(getApplicationContext(), "Click ListItem Number "+position, 
						Toast.LENGTH_SHORT).show();
				
			}
		});
		
	}

	private class StableArrayAdapter extends ArrayAdapter<String>{
		private final Context context;
		HashMap<String, Integer> mIdMap = new HashMap<String, Integer>();
		
		public StableArrayAdapter(Context context, int textViewResourceId,
				List<String> objects){
			super(context, textViewResourceId, objects);
			this.context = context;
			for(int i = 0; i < objects.size(); ++i){
				mIdMap.put(objects.get(i), i);
			}
		}
		
		@Override
		public View getView(int position, View convertView, ViewGroup parent){
			/**
			 * inflate the layout and get element references
			 */
			LayoutInflater inflater = (LayoutInflater)context.getSystemService(
					Context.LAYOUT_INFLATER_SERVICE);
			View rowView = inflater.inflate(R.layout.rowlayout, parent, false);
			TextView textViewTitle = (TextView)rowView.findViewById(R.id.title);
			TextView textViewDate = (TextView)rowView.findViewById(R.id.date);
			ImageView imageView = (ImageView)rowView.findViewById(R.id.logo);
			
			/**
			 * set the view elements from the data
			 */
			textViewTitle.setText(getItem(position));
			textViewDate.setText(R.string.date);
			imageView.setImageResource(R.drawable.ic_launcher);
			
			return rowView;
			
		}
		
		@Override
		public long getItemId(int position){
			String item = getItem(position);
			return mIdMap.get(item);
		}
		
		@Override
		public boolean hasStableIds(){
			return true;
		}
	}

}
