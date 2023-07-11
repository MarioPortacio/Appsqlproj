package com.example.appsqlproj;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;

import com.google.android.material.snackbar.Snackbar;

import java.io.ObjectInputStream;
import java.util.List;



public class TaskAdapter extends ArrayAdapter<Task> {
    private LayoutInflater inflater;
    private int resource;
    private List<Task> tasks;
    private TaskDBHelper dbHelper;

    public TaskAdapter(Context context, int resource, List<Task> tasks) {
        super(context, resource, tasks);
        this.inflater = LayoutInflater.from(context);
        this.resource = resource;
        this.tasks = tasks;
        this.dbHelper = new TaskDBHelper(context);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = inflater.inflate(resource, parent, false);
        }

        // Get the task object for this position
        final Task task = getItem(position);

        // Set the task name in the TextView
        TextView taskNameTextView = convertView.findViewById(R.id.task_name_textview);
        taskNameTextView.setText(task.getName());

        Button addButton = convertView.findViewById(R.id.add_button);
        Button deleteButton = convertView.findViewById(R.id.delete_button);
        Button hechoButton = convertView.findViewById(R.id.hecho_button);
        EditText editText = convertView.findViewById(R.id.task_edittext);
        EditText editText2 = convertView.findViewById(R.id.task_edittext2);
        EditText editText3 = convertView.findViewById(R.id.task_edittext3);

        addButton.setVisibility(View.GONE); // Hide the button
        deleteButton.setVisibility(View.VISIBLE);
        hechoButton.setVisibility(View.VISIBLE);
        //deleteButton.setEnabled(false); // Disable the button
        addButton.setEnabled(false);
        editText.setVisibility(View.GONE);
        editText.setEnabled(false);
        editText2.setVisibility(View.GONE);
        editText2.setEnabled(false);
        editText3.setVisibility(View.GONE);
        editText3.setEnabled(false);





        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Snackbar.make(v, "Deleted succesfully", Snackbar.LENGTH_SHORT).show();
                // Remove task from ListView
                tasks.remove(task);
                notifyDataSetChanged();
                // Delete task from the database
                dbHelper.remove(task.getId());
            }
        });


        hechoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Snackbar.make(v, "UPDATE1", Snackbar.LENGTH_SHORT).show();
                // Update task from ListView

                // Delete task from the database
                dbHelper.onUpdate(task.getId());
                hechoButton.setEnabled(false); //para desactivar el boton cuando se le de click y evitar nuevas fechas "realizadas"
                notifyDataSetChanged();
            }
        });




        return convertView;
    }

    public void clear() {
        tasks.clear();
        notifyDataSetChanged();
    }

    public void addAll(List<Task> tasks) {
        this.tasks.addAll(tasks);
        notifyDataSetChanged();
    }
} //TaskAdapter