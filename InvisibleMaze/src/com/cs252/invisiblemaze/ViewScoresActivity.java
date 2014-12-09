package com.cs252.invisiblemaze;



import java.sql.*;
import java.util.List;

import android.support.v7.app.ActionBarActivity;
import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

public class ViewScoresActivity extends ActionBarActivity {
	private static final String DRIVER = "com.mysql.jdbc.Driver";
	private static final String URL = "jdbc:mysql://localhost/invisiblemaze";
	
	private static final String USER = "root";
	private static final String PASS = "12345";
	
	@SuppressLint("NewApi") @Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_view_scores);
		MySQLiteHelper db = new MySQLiteHelper(this);
		System.out.println(db.getDatabaseName());
		db.addScore(new Score("Charles",30));
		db.addScore(new Score("Charles",50));
		db.addScore(new Score("Charles",40));
		db.addScore(new Score("Charles",20));
		db.addScore(new Score("Charles",60));
		db.addScore(new Score("Charles",40));

		List<Score> list = db.getAllScores();
		
		System.out.println(db.getAllScores());
		//db.getScore(0);
		int i = 1;
		while(i<5){
		db.getScore(i);
		i++;
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.view_scores, menu);
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
