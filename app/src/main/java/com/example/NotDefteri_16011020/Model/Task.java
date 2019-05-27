package com.example.NotDefteri_16011020.Model;

public class Task
{
    private String id, title, description, date, time;

    public Task()
    {

    }

    public Task(String id, String title, String description, String date, String time)
    {
        this.id = id;
        this.title = title;
        this.description = description;
        this.date = date;
        this.time = time;
    }

    public String getDescription()
    {
        return description;
    }

    public String getDate()
    {
        return date;
    }

    public String getTime()
    {
        return time;
    }

    public String getId()
    {
        return id;
    }

    public String getTitle()
    {
        return title;
    }

}
