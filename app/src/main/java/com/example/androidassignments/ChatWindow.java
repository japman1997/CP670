package com.example.androidassignments;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class ChatWindow extends AppCompatActivity {

    ArrayList<String> chatText = new ArrayList<String>();
    EditText chatEdit;
    ListView chatList;
    Button chatSend;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_window);

        chatEdit = findViewById(R.id.editChat);
        chatList = findViewById(R.id.listChat);
        chatSend = findViewById(R.id.sendButton);

        final ChatAdapter messageAdapter =new ChatAdapter( this );
        chatList.setAdapter(messageAdapter);

        chatSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chatText.add(chatEdit.getText().toString());
                messageAdapter.notifyDataSetChanged();
                chatEdit.setText("");
            }
        });
    }

    class ChatAdapter extends ArrayAdapter<String>
    {

        public ChatAdapter(@NonNull Context context) {
            super(context, 0);
        }


        public int getCount(){
            return chatText.size();
        }

        public String getitem(int position){
            return chatText.get(position);
        }

        public View getView(int position, View convertView, ViewGroup parent){
            LayoutInflater inflater = ChatWindow.this.getLayoutInflater();
            View result = null ;
            if(position%2 == 0)
                result = inflater.inflate(R.layout.chat_row_incoming, null);
            else
                result = inflater.inflate(R.layout.chat_row_outgoing, null);
            TextView message = result.findViewById(R.id.message_text);
            message.setText(getitem(position));
            return result;
        }
    }
}
