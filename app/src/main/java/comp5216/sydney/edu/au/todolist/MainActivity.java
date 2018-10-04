package comp5216.sydney.edu.au.todolist;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static comp5216.sydney.edu.au.todolist.ToDoItemDB.getDatabase;
/***
 * @author Akash
 *
 * Main Activity
 */
public class MainActivity extends AppCompatActivity {

    public static final int EDIT_ITEM_REQUEST_CODE = 2017;
    public static final int NEW_ITEM_REQUEST_CODE = 2018;
    public static final String MESSAGE = "MESSAGE";
    public static final String POSITION = "POSITION";

    ListView listView;
    ArrayList<ToDoListItem> toDoListItems;
    ToDoItemArrayAdapter toDoItemArrayAdapter;
    Button btnAddItem;
    Intent updateIntent;
    ToDoItemDao toDoItemDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        listView = (ListView) findViewById(R.id.lstView);
        toDoListItems = new ArrayList<>();

        // read data from Database
        ToDoItemDB database = getDatabase(this.getApplication().getApplicationContext());
        toDoItemDao = database.toDoItemDao();
        readFromDatabase();

        // setting list view with adapter
        toDoItemArrayAdapter = new ToDoItemArrayAdapter(this, toDoListItems);




        listView.setAdapter(toDoItemArrayAdapter);

        //create intent for update page
        updateIntent = new Intent(this, UpdateActivity.class);


        btnAddItem = (Button) findViewById(R.id.btnAddItem);
        // ADD NEW BUTTON CLICK
        btnAddItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateIntent.putExtra(MESSAGE, "");
                updateIntent.putExtra(POSITION, -1);
                startActivityForResult(updateIntent, NEW_ITEM_REQUEST_CODE);
            }
        });
        // List view button clicked
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long rowId) {
                Log.i("MainActivity", "Clicked " + position);
                ToDoListItem content = toDoListItems.get(position);
                openUpdatePage(content.getInformation(), position);

            }
        });
        //List view long clicked Listener
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int
                    position, long rowId) {
                Log.i("MainActivity", "Long Clicked item " + position);
                // Delete the item
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle(R.string.delete_title)
                        .setMessage(R.string.delete_msg)
                        .setPositiveButton(R.string.delete, new
                                DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        toDoListItems.remove(position); // Remove item from the ArrayList
                                        toDoItemArrayAdapter.notifyDataSetChanged(); // Notify listView adapter
                                        saveToDatabase();
                                    }
                                })
                        .setNegativeButton(R.string.cancel, new
                                DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialogInterface, int i) {

                                    }
                                })
                        .setNeutralButton(R.string.update, new
                                DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        ToDoListItem itemConent = toDoListItems.get(position);
                                        openUpdatePage(itemConent.getInformation(), position);
                                    }
                                });
                builder.create().show();
                return true;
            }
        });

    }

    private void openUpdatePage(String message, int position) {
        updateIntent.putExtra(MESSAGE, message);
        updateIntent.putExtra(POSITION, position);
        startActivityForResult(updateIntent, EDIT_ITEM_REQUEST_CODE);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // Check request and response code then add or update item.

        if (requestCode == EDIT_ITEM_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                //Update Item
                String editedItem = data.getExtras().getString(MESSAGE);
                int position = data.getIntExtra(POSITION, -1);
                ToDoListItem con = toDoListItems.remove(position);
                con.setInformation(editedItem);
                con.setLastUpdateTime(new Date());
                Log.i("Updated Item in list:", editedItem );
                Toast.makeText(this, "updated:" + editedItem, Toast.LENGTH_SHORT).show();
                toDoListItems.add(0,con);
                // add updated item to first position as it is latest
                saveToDatabase();
                toDoItemArrayAdapter.notifyDataSetChanged();
            }
        }
        if (requestCode == NEW_ITEM_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                // Add new item
                String newItem = data.getExtras().getString(MESSAGE);
                // add new item to first position as it is latest
                toDoListItems.add(0,new ToDoListItem(newItem,new Date(),new Date()));
                Toast.makeText(this, "Added :" + newItem, Toast.LENGTH_SHORT).show();
                saveToDatabase();
                toDoItemArrayAdapter.notifyDataSetChanged();
            }
        }
    }


    /***
     * read data from database in order of date
     */
    private void readFromDatabase() {
        try {
            //Use asynchronous task run in backgroud
            new AsyncTask<Void, Void, Void>() {
                @Override
                protected Void doInBackground(Void... voids) {
                    //read toDoListItems from database using room
                    List<ToDoListItem> itemsFromDB = toDoItemDao.listAll();
                    if (itemsFromDB != null) {
                        toDoListItems.addAll(itemsFromDB);
                    }
                    return null;
                }
            }.execute().get();
        } catch (Exception ex) {
            Log.e("readFromDB", ex.getStackTrace().toString());
        }
    }
    /***
     * Add data to database
     */
    private void saveToDatabase() {
        //Use asynchronous task run in backgroud
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                //delete all toDoListItems and re-insert
                toDoItemDao.deleteAll();
                for (ToDoListItem todo : toDoListItems) {
                    toDoItemDao.insert(todo);
                    Log.i("SaveToDb", todo.toString());
                }
                return null;
            }
        }.execute();
    }



}
