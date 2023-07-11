package com.example.appsqlproj;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

import java.util.Calendar;
import java.util.Date;


public class MainActivity extends AppCompatActivity {
    private TaskDBHelper dbHelper;
    private TaskAdapter taskAdapter;

    private Calendar selectedDate;
    private Calendar selectedTime;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.item_task);

        dbHelper = new TaskDBHelper(this);
        ListView listView = findViewById(R.id.task_list);
        taskAdapter = new TaskAdapter(this, R.layout.item_task, dbHelper.getAllTasks());

        listView.setAdapter(taskAdapter);

        Button addButton = findViewById(R.id.add_button);
        Button dButton = findViewById(R.id.delete_button);
        Button hechoButton = findViewById(R.id.hecho_button);
        EditText taskEditText = findViewById(R.id.task_edittext);
        EditText taskEditText2 = findViewById(R.id.task_edittext2);
        EditText taskEditText3 = findViewById(R.id.task_edittext3);

        addButton.setOnClickListener(v -> {
            String taskName = taskEditText.getText().toString().trim();
            String taskD = taskEditText2.getText().toString().trim();
            String taskT = taskEditText3.getText().toString().trim();

            //Snackbar.make(v, "Added successfully", Snackbar.LENGTH_SHORT).show();
            if (selectedDate != null && selectedTime != null) {
                if (!taskName.isEmpty()) {
                    Task task = new Task(0, taskName, taskD, taskT, "0", "0");
                    dbHelper.addTask(task);
                    taskAdapter.clear();
                    taskAdapter.addAll(dbHelper.getAllTasks());
                    taskEditText.setText("");
                    taskEditText2.setText("");
                    taskEditText3.setText("");
                }else{
                    Snackbar.make(v, "Por favor, selecciona un nombre para la tarea", Snackbar.LENGTH_SHORT).show();
                    //Toast.makeText(this, "Por favor, selecciona un nombre para la tarea", Toast.LENGTH_SHORT).show();
                }
            }else{
                Snackbar.make(v, "Por favor, selecciona una fecha y hora", Snackbar.LENGTH_SHORT).show();
                //Toast.makeText(this, "Por favor, selecciona una fecha y hora", Toast.LENGTH_SHORT).show();
            }
        });




        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
        //listView.setOnItemClickListener(v -> {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Button deleteButton = view.findViewById(R.id.delete_button);
                deleteButton.setOnClickListener(v -> {
                    // Remove item from ListView
                    //Snackbar.make(v, "Confirma entrada", Snackbar.LENGTH_SHORT).show();
                    Task task = taskAdapter.getItem(position);
                    taskAdapter.remove(task);
                    // Delete item from the database
                    dbHelper.remove(task.getId());


                });
            }
        });





        EditText selectDateEt = findViewById(R.id.task_edittext2);
        EditText selectTimeEt = findViewById(R.id.task_edittext3);

        selectDateEt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog();
            }
        });

        selectTimeEt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTimePickerDialog();
            }
        });


    }//OnCreate

    private void showDatePickerDialog() {
        // Obtener la fecha actual
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
        EditText etDate = findViewById(R.id.task_edittext2);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                // Obtener la fecha seleccionada del DatePicker
                Calendar calendar = Calendar.getInstance();
                calendar.set(year, month, dayOfMonth);
                //selectedDate = calendar.getTime();
                selectedDate = calendar;
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

                //etDate.setText(""+calendar.get(Calendar.YEAR)+"/"+calendar.get(Calendar.MONTH)+"/"+calendar.get(Calendar.DAY_OF_MONTH)); //ESTE TE DA EL AÑO, MES(el mes te da -1, osea diciembre 12, te lo pone como 11, corregir eso) Y DÍA QUE CORRESPONDE, SIN CEROS A LA IZQUIERDA
                etDate.setText(""+year+"/"+monthString+"/"+dayString);

                // Hacer algo con la fecha seleccionada, como guardarla en la base de datos o mostrarla en la interfaz de usuario
                // ...
            }

        }, year, month, dayOfMonth);

        datePickerDialog.show();
    }

    private void showTimePickerDialog() {
        // Obtener la hora actual
        Calendar calendar = Calendar.getInstance();
        int hourOfDay = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);
        EditText etTime = findViewById(R.id.task_edittext3);

        TimePickerDialog timePickerDialog = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                // Obtener la hora seleccionada del TimePicker
                Calendar calendar = Calendar.getInstance();
                calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
                calendar.set(Calendar.MINUTE, minute);
                //selectedTime = calendar.getTime();
                selectedTime = calendar;
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

                //etTime.setText(""+calendar.get(Calendar.HOUR_OF_DAY)+":"+calendar.get(Calendar.MINUTE)); //esto saca el numero exacto sin ceros a la izquierda en formato 24 horas
                etTime.setText(""+hourString+":"+minuteString+ampm);
                // Hacer algo con la hora seleccionada, como guardarla en la base de datos o mostrarla en la interfaz de usuario
                // ...
            }
        }, hourOfDay, minute, false);

        timePickerDialog.show();
    }


//hasta aca los dates y times












    @Override
    protected void onResume() {
        super.onResume();
       // taskAdapter.setTasks(dbHelper.getAllTasks());
        //taskAdapter.notifyDataSetChanged();

        taskAdapter.clear();
        taskAdapter.addAll(dbHelper.getAllTasks());

    }





}//App
