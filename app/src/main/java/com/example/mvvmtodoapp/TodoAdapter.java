package com.example.mvvmtodoapp;

import android.app.Dialog;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class TodoAdapter extends RecyclerView.Adapter<TodoAdapter.ViewHolder> {

    private Context context;
    ArrayList<TodoModel> list;
    TodoViewModel todoViewModel;
    private onItemClickHandler clickHandler;

    public <todovm> TodoAdapter(Context context, ArrayList<TodoModel> list, onItemClickHandler clickHandler) {
        this.context = context;
        this.list = list;
        this.clickHandler = clickHandler;
    }

    @NonNull
    @Override
    public TodoAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.todo_item, parent, false), clickHandler);
    }

    @Override
    public void onBindViewHolder(@NonNull TodoAdapter.ViewHolder holder, int position) {
        holder.tvTitle.setText(list.get(position).getTitle());
        holder.tvdesc.setText(list.get(position).getDescription());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView tvTitle, tvdesc;
        ImageView delete;
        private onItemClickHandler clickHandler;

        public ViewHolder(@NonNull View itemView, onItemClickHandler clickHandler) {
            super(itemView);

            this.clickHandler = clickHandler;
            tvTitle = itemView.findViewById(R.id.textView2);
            tvdesc = itemView.findViewById(R.id.textView3);
            delete = itemView.findViewById(R.id.imageView);

            itemView.setOnClickListener(this);
            delete.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.imageView:
                    Log.d("vbv", "onClick: Delete");
                    clickHandler.onDelete(getAdapterPosition());
                    break;
                default:
                    Log.d("vbv", "onClick: container");
                    clickHandler.onItemClick(getAdapterPosition());
                    break;
            }
        }
    }

    interface onItemClickHandler {
        void onItemClick(int position);
        void onDelete(int position);
    }
}
