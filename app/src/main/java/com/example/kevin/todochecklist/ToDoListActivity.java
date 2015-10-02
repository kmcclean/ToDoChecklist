package com.example.kevin.todochecklist;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.SparseBooleanArray;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class ToDoListActivity extends AppCompatActivity {

    Button mAddButton;
    Button mDeleteButton;
    ListView mToDoList;
    EditText mNewToDoItem;
    ArrayAdapter<String> mToDoArrayAdapter;
    ArrayList<String> mToDoArrayList = new ArrayList<String>();


    //this fetches the text that the user has written.
    public String getTextViewText (){
        String newTask = mNewToDoItem.getText().toString();
        return newTask;
    }

    //this resets the EditText to blank.
    public void resetTextView(){
        mNewToDoItem.setText("");
    }

    //this adds items to the list and updates the adapter.
    public void addItemToList(String newTask){
        mToDoArrayList.add(newTask);
        mToDoArrayAdapter.notifyDataSetChanged();
    }


    //this iterates through the list of tasks, and finds the integer location of the ones which are checked.
    //once it finds them, it stores them in reverse order, and then deletes them from the bottom to the top.
    //it does it that way in order to make sure that when the list changes size, it won't affect the items that still need to be deleted.
    public void deleteTask(){
        ArrayList<Integer> deleteList = new ArrayList<Integer>();
        for (int i=0; i<mToDoList.getCount(); i++){
            if(mToDoList.isItemChecked(i)){
                deleteList.add(0, i);
            }
        }
        for(int i: deleteList){
            mToDoArrayList.remove(i);
        }
        mToDoArrayAdapter.notifyDataSetChanged();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_to_do_list);
        mAddButton = (Button)findViewById(R.id.addButton);
        mDeleteButton = (Button)findViewById(R.id.deleteButton);
        mToDoList = (ListView)findViewById(R.id.todoListView);
        mNewToDoItem = (EditText)findViewById(R.id.newToDoEditText);

        mToDoArrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_checked, mToDoArrayList);
        mToDoList.setAdapter(mToDoArrayAdapter);
        mToDoList.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);

        mAddButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String task = getTextViewText();
                addItemToList(task);
                resetTextView();
            }
        });

        mDeleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteTask();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_to_do_list, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
