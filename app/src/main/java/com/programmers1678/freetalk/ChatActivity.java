package com.programmers1678.freetalk;

import android.content.Context;
import android.content.Intent;
import android.database.DataSetObserver;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;

public class ChatActivity extends AppCompatActivity {

    ArrayList messages = new ArrayList();
    boolean incognito;
    String chatroom;
    String username;
    Firebase messageRef;
    int messageCounter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        final ListView chat = (ListView) findViewById(R.id.chatListView);
        chatroom  = getIntent().getExtras().getString("extra_chatroom");
        incognito = getIntent().getExtras().getBoolean("hi");
        messageRef = new Firebase("https://jesusbread.firebaseio.com/" + chatroom);
        username = getIntent().getExtras().getString("extra_username");
        messageRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                messageCounter = 0;
                for (DataSnapshot messageSnapshot : dataSnapshot.getChildren()) {
                    messageCounter++;
                    messages.add(messageSnapshot.getValue().toString());
                    BaseAdapter adapter = (BaseAdapter) chat.getAdapter();
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });


        chat.setAdapter(new BaseAdapter() {
            @Override
            public int getCount() {
                return messages.size();
            }

            @Override
            public Object getItem(int position) {
                return null;
            }

            @Override
            public long getItemId(int position) {
                return 0;
            }

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                LayoutInflater layoutInflater = (LayoutInflater) getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                if (convertView == null) {
                    convertView = layoutInflater.inflate(android.R.layout.simple_list_item_1, parent, false);
                }
                TextView seeTexts = (TextView) convertView.findViewById(android.R.id.text1);
                seeTexts.setText(messages.get(position).toString());
                return convertView;
            }
        });
    }
    public void sendMessage(View view) {
        chatroom = getIntent().getExtras().getString("extra_chatroom");
        messageRef = new Firebase("https://jesusbread.firebaseio.com/" + chatroom);
        username = getIntent().getExtras().getString("extra_username");
        messageCounter++;
        EditText userMessage = (EditText) findViewById(R.id.chatInput);
        String textMessage = userMessage.toString();
        String messageNumber = "message" + messageCounter;
        Firebase textRef = messageRef.child(messageNumber);
        Message texts = new Message(username, textMessage);
        textRef.setValue(texts);
        messages.add(texts.getMessage());
        userMessage.setText("");
    }

}
