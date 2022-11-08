package com.example.mvvmtodoapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements TodoAdapter.onItemClickHandler {
    
    ImageButton addTodo;
    RecyclerView recyclerView;
    TodoViewModel todoViewModel;
    TodoAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        addTodo = findViewById(R.id.imageButton);
        recyclerView = findViewById(R.id.recyclerView);

        todoViewModel = new ViewModelProvider(this, new TodoFactory(getApplication())).get(TodoViewModel.class);

        //App k start hote hi todotask dikhne chaiye
        showNotes();

        addTodo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Dialog dialog = new Dialog(MainActivity.this);

                dialog.setContentView(R.layout.dialog_box);
                // dialog box poora bada hoker screen par dikhe uske liye important code
                dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

                EditText etTitle, etDesc;
                Button add;

                etTitle = dialog.findViewById(R.id.etTitle);
                etDesc = dialog.findViewById(R.id.etDesc);
                add = dialog.findViewById(R.id.buttonAdd);

                add.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        TodoModel model = new TodoModel();
                        String title = etTitle.getText().toString();
                        String desc = etDesc.getText().toString();
                        model.setTitle(title);
                        model.setDescription(desc);
                        if (!title.isEmpty() && !desc.isEmpty()) {
                            // add data to Database
                            todoViewModel.insertTodos(model);
                            // show data from DB to recyclerView
                            Toast.makeText(MainActivity.this, "Task Added Successfully", Toast.LENGTH_SHORT).show();
                            showNotes();// added todotask ko recyclerview me dikhane ke liye
                            dialog.dismiss();
                        }else {
                            Toast.makeText(MainActivity.this, "All Fields Must be filled", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

                dialog.show();
            }
        });
    }

    private void showNotes() {
        todoViewModel.getAllTodos.observe(this, (List<TodoModel> todoModelList) -> {
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            adapter = new TodoAdapter(MainActivity.this, (ArrayList<TodoModel>) todoModelList, this);
            recyclerView.setAdapter(adapter);
        });
    }

    @Override
    public void onItemClick(int position) {
        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_box);
        // dialog box poora bada hoker screen par dikhe uske liye important code
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        EditText etTitle, etDesc;
        Button add;

        etTitle = dialog.findViewById(R.id.etTitle);
        etDesc = dialog.findViewById(R.id.etDesc);
        add = dialog.findViewById(R.id.buttonAdd);
        add.setText("Update");

        //fill edittext with values
        etTitle.setText(todoViewModel.getAllTodos.getValue().get(position).getTitle());
        etDesc.setText(todoViewModel.getAllTodos.getValue().get(position).getDescription());
        //click function on update button
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String title = etTitle.getText().toString();
                String desc = etDesc.getText().toString();
                if (!title.isEmpty() && !desc.isEmpty()) {
                    // update tododata to Database
                    TodoModel model1 = new TodoModel();
                    model1.setId(todoViewModel.getAllTodos.getValue().get(position).getId());
                    model1.setTitle(title);
                    model1.setDescription(desc);

                    todoViewModel.updateTodos(model1); // call updatetodo method from viewmodel

                    // show data from DB to recyclerView
                    Toast.makeText(getApplicationContext(), "Task Added Successfully", Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                }else {
                    Toast.makeText(getApplicationContext(), "All Fields Must be filled", Toast.LENGTH_SHORT).show();
                }
            }
        });

        dialog.show();
        Toast.makeText(this, "Updated", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDelete(int position) {
        todoViewModel.deleteTodos(todoViewModel.getAllTodos.getValue().get(position).getId());
    }
}