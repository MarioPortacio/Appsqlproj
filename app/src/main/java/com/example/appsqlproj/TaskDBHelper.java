package com.example.appsqlproj;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class TaskDBHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "tasks.db";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_NAME = "tasks";
    private static final String COLUMN_ID = "_id";
    private static final String COLUMN_TITLE = "name";
    private static final String COLUMN_DATE = "date";
    private static final String COLUMN_TIME = "time";
    private static final String COLUMN_DATE_DONE = "date_done";
    private static final String COLUMN_TIME_DONE = "time_done";

    public TaskDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
    //db.execSQL("CREATE TABLE tasks (_id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT)");

        String createTableQuery = "CREATE TABLE " + TABLE_NAME + "("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_TITLE + " TEXT,"
                + COLUMN_DATE + " TEXT,"
                + COLUMN_TIME + " TEXT,"
                + COLUMN_DATE_DONE+ " TEXT,"
                + COLUMN_TIME_DONE + " TEXT"
                + ")";
        db.execSQL(createTableQuery);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS tasks");
        onCreate(db);
    }


    public void onUpdate(long id) {
        SQLiteDatabase db = getWritableDatabase();
        String string =String.valueOf(id);
        Calendar calendar = Calendar.getInstance();

        int hourOfDay = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);

        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH); //el cero es enero
        int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);


        String monthString;
        String dayString;
        if(month<9){//detalles esteticos mes
            month = month+1;
            monthString = "0"+month;
        }else{
            month = month+1;
            monthString = ""+month;
        }

        if(dayOfMonth<10){//detalles esteticos día
            dayString = "0"+dayOfMonth;
        }else{
            dayString = ""+dayOfMonth;
        }





        String hourString;
        String minuteString;
        String ampm;
        int auxHourDay = hourOfDay;

        if(hourOfDay>12){
            auxHourDay = hourOfDay - 12;
            ampm = "pm";
        }else{
            if(hourOfDay == 0){
                ampm = "am";
                auxHourDay = 12;
            }else if(hourOfDay == 12){
                ampm = "pm";
            }else{
                ampm = "am";
            }

        }

        if(auxHourDay<10){
            hourString = "0"+auxHourDay;
        }else{
            hourString = ""+auxHourDay;
        }

        if(minute<10){
            minuteString = "0"+minute;
        }else{
            minuteString = ""+minute;
        }






        String dateD = ""+year+"/"+monthString+"/"+dayString;
        String timeD = ""+hourString+":"+minuteString+ampm;

        db.execSQL("UPDATE tasks SET date_done='" +dateD+ "', time_done='" +timeD+ "' WHERE _id = '" +string+ "'");

        //ejemplo update:
        //update usuarios set nombre='Marceloduarte', clave='Marce'
        //  where nombre='Marcelo';
    }

    public void addTask(Task task) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        //values.put("COLUMN_TITLE", task.getName());
        values.put("name", task.getName());
        values.put("date", task.getDate());
        values.put("time", task.getTime());
        values.put("date_done", task.getDateDone());
        values.put("time_done", task.getTimeDone());
        db.insert("tasks", null, values);
    }

    public List<Task> getAllTasks() {
        List<Task> taskList = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.query("tasks", null, null, null, null, null, null);
        if (cursor.moveToFirst()) {
            do {
                long taskId = cursor.getLong(cursor.getColumnIndexOrThrow("_id"));
                String taskName = cursor.getString(cursor.getColumnIndexOrThrow("name"));
                String taskDate = cursor.getString(cursor.getColumnIndexOrThrow("date"));
                String taskTime = cursor.getString(cursor.getColumnIndexOrThrow("time"));
                String taskDate_D = cursor.getString(cursor.getColumnIndexOrThrow("date_done"));
                String taskTime_D = cursor.getString(cursor.getColumnIndexOrThrow("time_done"));

                //long taskId = cursor.getLong(cursor.getColumnIndex("COLUMN_ID"));
                //String taskName = cursor.getString(cursor.getColumnIndex("COLUMN_TITLE"));
                Task task = new Task(taskId, taskName, taskDate, taskTime, taskDate_D, taskTime_D);
                taskList.add(task);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return taskList;
    }

    public void onDelete() {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("DELETE FROM tasks");
    }


    public void deleteTask(Task task) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME, COLUMN_ID + "=?", new String[]{String.valueOf(task.getId())});
        db.close();
    }

    public void remove(long id){
        SQLiteDatabase db = getWritableDatabase();
        String string =String.valueOf(id);
        db.execSQL("DELETE FROM tasks WHERE _id = '" +string+ "'");
    }




}



