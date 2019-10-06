package com.example.androidassignments;

import android.content.ClipData;
import android.content.DialogInterface;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class TestToolbar extends AppCompatActivity {


    int check = 0;
    String text = null;
    EditText editText2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_toolbar);
//        Toolbar toolbar = findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });



    }
    public boolean onCreateOptionsMenu(Menu m){
        getMenuInflater().inflate(R.menu.toolbar_menu,m);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem mi){
        int id = mi.getItemId();
        switch (id){
            case R.id.action_one:
                Log.d("Toolbar","Option 1 Selected");
                View viewById = findViewById(R.id.fab);
                Snackbar sn;
                if (check==0){sn = Snackbar.make(viewById,"Button 1",Snackbar.LENGTH_LONG);}
                else
                    sn = Snackbar.make(viewById,text,Snackbar.LENGTH_LONG);
                    sn.show();
                break;

            case R.id.action_two:
                Log.d("Toolbar","Option 2 Selected");
                AlertDialog.Builder builder = new AlertDialog.Builder(TestToolbar.this);
                builder.setTitle(R.string.pick_color);
// Add the buttons
                builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        finish();
                    }
                });
                builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // User cancelled the dialog
                    }
                });
// Create the AlertDialog
                AlertDialog dialog = builder.create();
                dialog.show();

                break;

            case R.id.action_three:
                AlertDialog.Builder customDialog = new AlertDialog.Builder(this);
// Get the layout inflater
                LayoutInflater inflater = this.getLayoutInflater();

                final View view = inflater.inflate(R.layout.dialog, null);

// Inflate and set the layout for the dialog
// Pass null as the parent view because its going in the dialog layout
                customDialog.setView(view)
                        // Add action buttons
                        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int id) {

                                EditText edit = (EditText)(view.findViewById(R.id.username22));
                                text = edit.getText().toString();
                                check=1;

                            }
                        })
                        .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                            }
                        });
                dialog = customDialog.create();
                dialog.show();
                break;
            case R.id.settingid:
                Toast.makeText(this, "Version 1.0 by Japjeet Singh", Toast.LENGTH_SHORT).show();

        }
        return true;
    }


}
