package comp5216.sydney.edu.au.todolist;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import static comp5216.sydney.edu.au.todolist.MainActivity.MESSAGE;
import static comp5216.sydney.edu.au.todolist.MainActivity.POSITION;
/***
 * @auther Akash
 *
 * Update or Add new item Activity
 */
public class UpdateActivity extends AppCompatActivity {
    int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);

        EditText editText = (EditText) findViewById(R.id.txtUpdate);
        Button btnSave = (Button) findViewById(R.id.btnSave);

        // get value from intend
        editText.setText(getIntent().getStringExtra(MESSAGE));
        position = getIntent().getIntExtra(POSITION, -1);

        //save button
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                EditText editText = (EditText) findViewById(R.id.txtUpdate);
                // if no text is present
                if (editText.getText().toString().isEmpty()) {
                    // bank value
                    AlertDialog.Builder builder = new AlertDialog.Builder(UpdateActivity.this);
                    builder.setTitle("Blank value").setMessage("Please enter valid value");
                    builder.create().show();
                } else {
                    String message = editText.getText().toString();
                    Intent intent = new Intent();
                    intent.putExtra(MESSAGE, message);
                    intent.putExtra(POSITION, position);
                    setResult(Activity.RESULT_OK, intent);
                    finish();
                }
            }
        });

        Button btnCancel = (Button) findViewById(R.id.btnCancel);
        // cancel button click
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(UpdateActivity.this);

                builder.setTitle(R.string.close)
                        .setMessage(R.string.close_msg)
                        .setPositiveButton(R.string.yes, new
                                DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        Intent intent = new Intent();
                                        setResult(Activity.RESULT_CANCELED, intent);
                                        finish();
                                    }
                                })
                        .setNegativeButton(R.string.No, new
                                DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                    }
                                });
                builder.create().show();
            }
        });
    }
}
