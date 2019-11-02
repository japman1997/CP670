package com.example.androidassignments;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.Editable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.ArrayList;

import static com.example.androidassignments.ChatDatabaseHelper.KEY_ID;
import static com.example.androidassignments.ChatDatabaseHelper.KEY_MESSAGE;
import static com.example.androidassignments.ChatDatabaseHelper.TABLE_Name;

public class ChatWindow extends AppCompatActivity {

    ArrayList<String> chatText = new ArrayList<String>();
    EditText chatEdit;
    ListView chatList;
    Button chatSend;
    ChatDatabaseHelper chatDatabaseHelper;
    public SQLiteDatabase db;
    private static final String ACTIVITY_NAME = "ChatWindow";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_window);

        chatEdit = findViewById(R.id.editChat);
        chatList = findViewById(R.id.listChat);
        chatSend = findViewById(R.id.sendButton);

        final ChatAdapter messageAdapter = new ChatAdapter(this);
        chatList.setAdapter(messageAdapter);

        chatDatabaseHelper = new ChatDatabaseHelper(getApplicationContext());

        try {
            db = chatDatabaseHelper.getWritableDatabase();
        } catch (Exception e) {
            e.printStackTrace();
            Log.i("Exception", "DB not registered");
        }

        try {
            Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_Name, null);
            if (cursor.getCount() >= 1) { // UserName Not Exist
                cursor.moveToFirst();
                while (!cursor.isAfterLast()) {
                    String text = cursor.getString(cursor.getColumnIndex(KEY_MESSAGE));
                    Log.i(ACTIVITY_NAME, "SQL MESSAGE:" + text);
                    chatText.add(text);
                    Log.i(ACTIVITY_NAME, "Cursor’s  column count =" + cursor.getColumnCount());
                    for(int i=0;i<cursor.getCount();i++) {
                        Log.i(ACTIVITY_NAME, "Cursor’s  column name =" + cursor.getColumnName(i));
                    }
                    cursor.moveToNext();
                }
                cursor.close();
            }
            Log.i("Cursor", "works");
        } catch (Exception e) {
            e.printStackTrace();
            Log.i("Exception", "Cursor exception");
        }

        chatSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String textToInput = chatEdit.getText().toString();
                chatText.add(textToInput);
                messageAdapter.notifyDataSetChanged();
                chatEdit.setText("");

                ContentValues cValues = new ContentValues();
                cValues.put(KEY_MESSAGE, textToInput);
                try {
                    long result = db.insert(TABLE_Name, "NullPlaceholder", cValues);
                    Log.i("result", String.valueOf(result));
                    Toast.makeText(getApplicationContext(), "Info sved", Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                    e.printStackTrace();
                    Log.i("Exception", "Exception");

                }

            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        db.close();
    }

    class ChatAdapter extends ArrayAdapter<String> {

        public ChatAdapter(@NonNull Context context) {
            super(context, 0);
        }


        public int getCount() {
            return chatText.size();
        }

        public String getitem(int position) {
            return chatText.get(position);
        }

        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = ChatWindow.this.getLayoutInflater();
            View result = null;
            if (position % 2 == 0)
                result = inflater.inflate(R.layout.chat_row_incoming, null);
            else
                result = inflater.inflate(R.layout.chat_row_outgoing, null);
            TextView message = result.findViewById(R.id.message_text);
            message.setText(getitem(position));
            return result;
        }
    }
}
