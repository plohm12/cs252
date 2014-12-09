package com.cs252.invisiblemaze;

import java.util.LinkedList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class MySQLiteHelper extends SQLiteOpenHelper {

	private static final int VER = 3;
	private static final String DB_NAME = "ScoreDB";
	private static final String TABLE = "scores";
	private static final String ID = "id";
	private static final String NAME = "name";
	private static final String SCORE = "score";
	private static final String[] COL = {
		ID,NAME,SCORE
	};
	public MySQLiteHelper(Context context, String name, CursorFactory factory,
			int version) {
		super(context, DB_NAME, factory, VER);
		// TODO Auto-generated constructor stub
	}

	public MySQLiteHelper(Context context) {
		// TODO Auto-generated constructor stub
		super(context,DB_NAME,null,VER);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		db.beginTransaction();
		String CREATE_SCORE_TABLE = "CREATE TABLE scores(id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, name TEXT, score INTEGER)";
		try{
		db.execSQL(CREATE_SCORE_TABLE);
		db.setTransactionSuccessful();
		System.out.println("worked?");
		}catch(SQLException e){
			e.printStackTrace();
		}finally{
			db.endTransaction();
		}

	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		db.execSQL("DROP TABLE IF EXISTS scores");
		this.onCreate(db);
				
	}
	public void addScore(Score score){
		Log.d("addScore", score.toString());
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put(NAME, score.getName());
		values.put(SCORE, score.getScore());
		db.insert(TABLE, null, values);
		
		db.close();
	}
	public Score getScore(int id){
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.query(TABLE, COL, " id = ?",new String[]{String.valueOf(id)},null,null,null,null);
		if(cursor!=null)
			cursor.moveToFirst();
		
		Score score = new Score();
		score.setId(Integer.parseInt(cursor.getString(0)));
		score.setName(cursor.getString(1));
		score.setScore(Integer.parseInt(cursor.getString(2)));
		
		Log.d("getScore("+id+")", score.toString());
		return score;
		
	}
	public List<Score> getAllScores(){
		List<Score> scores = new LinkedList<Score>();
		SQLiteDatabase db = this.getWritableDatabase();
		String query = "SELECT * FROM "+TABLE;
		Cursor cursor = db.rawQuery(query, null);
		Score score = null;
		if(cursor.moveToFirst()){
			do{
				score = new Score();
				score.setId(Integer.parseInt(cursor.getString(0)));
				score.setName(cursor.getString(1));
				score.setScore(Integer.parseInt(cursor.getString(2)));
				
			}
			while(cursor.moveToNext());
		}
		Log.d("getAllScores()",scores.toString());
		
		return scores;
		
	}
	public int updateScore(Score score){
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put("name", score.getName());
		values.put("score", score.getScore());
		
		int i = db.update(TABLE, values, ID+" =?", new String[]{String.valueOf(score.getId())});
		
				db.close();
		return i;
	}
}
