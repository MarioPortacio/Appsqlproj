package com.example.appsqlproj;


//mejor quitar la opción de eliminar tarea? sí?
//crear ventana con tareas completadas?



/////validar en los datepicker y timepicker que el usuario no pueda poner una fecha antes y una hora antes

/////cuando la persona haga click en el boton "HECHO" entonces update a la bd en los parametros correspondientes


import java.util.Calendar;

public class Task {
    private long id;
    private String name;

    //Fecha y hora para realizar la tarea (input)
    private String date;
    private String time;

    //Fecha y hora en la que se crea la tarea (fecha y hora actual del dispositivo)
    private Calendar dateCreation;
    private Calendar timeCreation;

    //Fecha en la que la persona realiza la tarea (luego de marcarlo)
    private String dateDone;
    private String timeDone;


    public Task() {
        // Constructor vacío requerido para SQLite
    }


    public Task(long id, String name, String date, String time, String dateDone, String timeDone) {
        this.id = id;
        this.name = name;

        this.date = date;
        this.time = time;
        this.dateDone = dateDone;
        this.timeDone = timeDone;

    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }






    public String getDate() {
        return date;
    }

    public String getTime() {
        return time;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setTime(String time) {
        this.time = time;
    }








    public String getDateDone() {
        return dateDone;
    }

    public String getTimeDone() {
        return timeDone;
    }

    public void setDateDone(String dateDone) {
        this.dateDone = dateDone;
    }

    public void setTimeDone(String timeDone) {
        this.timeDone = timeDone;
    }



}//TASK

