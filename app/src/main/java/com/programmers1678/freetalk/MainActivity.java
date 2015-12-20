package com.programmers1678.freetalk;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {
public final static String extra_chatroom = "com.programmers1678.freetalk.chatroom";
    public final static String extra_username = "com.programmers1678.freetalk.username";
    ArrayList chatRoomList = new ArrayList();
    boolean chatRoomExists = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_page);
        Firebase.setAndroidContext(this);
        Firebase roomRefs = new Firebase("https://jesusbread.firebaseio.com/");
        roomRefs.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot chatroomSnapshot : dataSnapshot.getChildren()) {
                    chatRoomList.add(chatroomSnapshot.getValue().toString());
                }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_first_page, menu);
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
    public void enterChat(View view) {
    Intent intent = new Intent(this,ChatActivity.class);
        EditText chatNameText = (EditText)findViewById(R.id.chatNameEditText);
        EditText usernameText = (EditText)findViewById(R.id.usernameEditView);
        String chatroomName = chatNameText.getText().toString();
        String username = usernameText.getText().toString();
        Switch incognitoMode = (Switch)findViewById(R.id.incognito);
        TextView error = (TextView)findViewById(R.id.errorMessage);
        boolean incognito = false;
        if(incognitoMode.isChecked()) {
            incognito = true;
        }
        for(int i = 0; i < chatRoomList.size(); i++) {
            if(chatroomName.equals(chatRoomList.get(i).toString())) {
                chatRoomExists = true;
                break;
            }
        }
        if(username.equals("")){
            error.setText("Valid Username Required");
        } else {
            intent.putExtra("extra_chatroom", chatroomName);
            intent.putExtra("extra_username", username);
            intent.putExtra("hi", incognito);
            if(chatRoomExists) {
                startActivity(intent);
            } else {
                Firebase room = new Firebase("https://jesusbread.firebaseio.com/");
                room.child(chatroomName).setValue(chatroomName);
                startActivity(intent);
            }

        }
    }
}
