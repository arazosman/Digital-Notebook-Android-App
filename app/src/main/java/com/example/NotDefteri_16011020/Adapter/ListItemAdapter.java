package com.example.NotDefteri_16011020.Adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.NotDefteri_16011020.MainActivity;
import com.example.NotDefteri_16011020.Model.Task;
import com.example.NotDefteri_16011020.R;

import java.util.List;

class ListItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnCreateContextMenuListener
{
    ItemClickListener itemClickListener;
    TextView item_title, item_description, item_date, item_time;

    public ListItemViewHolder(@NonNull View itemView)
    {
        super(itemView);
        itemView.setOnClickListener(this);
        itemView.setOnCreateContextMenuListener(this);

        item_title = itemView.findViewById(R.id.item_title);
        item_description = itemView.findViewById(R.id.item_description);
        item_date = itemView.findViewById(R.id.item_date);
        item_time = itemView.findViewById(R.id.item_time);
    }

    public void setItemClickListener(ItemClickListener itemClickListener)
    {
        this.itemClickListener = itemClickListener;
    }

    @Override
    public void onClick(View v)
    {
        itemClickListener.onClick(v, getAdapterPosition(), false);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo)
    {
        menu.setHeaderTitle("Select the action");
        menu.add(0, 0, getAdapterPosition(), "DELETE");
    }
}

public class ListItemAdapter extends RecyclerView.Adapter <ListItemViewHolder>
{
    MainActivity mainActivity;
    List<Task> taskList;

    public ListItemAdapter(MainActivity mainActivity, List<Task> taskList)
    {
        this.mainActivity = mainActivity;
        this.taskList = taskList;
    }

    @NonNull
    @Override
    public ListItemViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i)
    {
        LayoutInflater inflater = LayoutInflater.from(mainActivity.getBaseContext());
        View view = inflater.inflate(R.layout.list_item, viewGroup, false);
        return new ListItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ListItemViewHolder listItemViewHolder, final int i)
    {
        listItemViewHolder.item_title.setText(taskList.get(i).getTitle());
        listItemViewHolder.item_description.setText(taskList.get(i).getDescription());
        listItemViewHolder.item_date.setText(taskList.get(i).getDate());
        listItemViewHolder.item_time.setText(taskList.get(i).getTime());

        listItemViewHolder.setItemClickListener(new ItemClickListener()
        {
            @Override
            public void onClick(View view, int position, boolean isLongClick)
            {
                /*mainActivity.title.setText(taskList.get(i).getTitle());
                mainActivity.description.setText(taskList.get(i).getDescription());
                mainActivity.date.setText(taskList.get(i).getDate());
                mainActivity.time.setText(taskList.get(i).getTime());

                mainActivity.isUpdate = true;
                mainActivity.idUpdate = taskList.get(i).getId();*/
            }
        });
    }

    @Override
    public int getItemCount()
    {
        return taskList.size();
    }
}
