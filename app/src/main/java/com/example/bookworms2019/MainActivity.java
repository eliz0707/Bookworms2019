package com.example.bookworms2019;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    FloatingActionButton addPost, account, contacts;
    private FirebaseAuth.AuthStateListener mAuthStateListener;

    private ListView listView;
    DatabaseReference databaseReference;
    List<Posts>postsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //header of the different categories
        TextView textView1 = (TextView) findViewById(R.id.moduleCode);
        TextView textView2 = (TextView) findViewById(R.id.price);
        TextView textView3 = (TextView) findViewById(R.id.type);
        TextView textView4 = (TextView) findViewById(R.id.grade);
        textView1.setPaintFlags(textView1.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        textView2.setPaintFlags(textView2.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        textView3.setPaintFlags(textView3.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        textView4.setPaintFlags(textView4.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);

        addPost = (FloatingActionButton) findViewById(R.id.addPost);
        account = (FloatingActionButton) findViewById(R.id.account);
        contacts = (FloatingActionButton) findViewById(R.id.contacts);

        listView = findViewById(R.id.list_view);
        databaseReference = FirebaseDatabase.getInstance().getReference("posts");

        postsList = new ArrayList<>();

        addPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: directing to the posting page.");
                addPost();
            }
        });

        account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: directing to the account page.");
                accountActivity();
            }
        });

        contacts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: directing to the contacts page.");
                ChatActivity();
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.d(TAG, "attempting to direct user to the chatbox");
//                startActivity(new Intent(MainActivity.this, ChatActivity.class));
            }
        });
    }

    private void ChatActivity() {
        Intent homeActivity = new Intent(getApplicationContext(), UsersActivity.class);
        startActivity(homeActivity);
        finish();
    }

    private void accountActivity() {
        Intent homeActivity = new Intent(getApplicationContext(), AccountActivity.class);
        startActivity(homeActivity);
        finish();
    }

    @Override
    protected void onStart() {
        super.onStart();
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot postSnapshot : dataSnapshot.getChildren()) {

                    Posts post = postSnapshot.getValue(Posts.class);
                    postsList.add(post);
                }
                PostInfoAdaptor postInfoAdaptor = new PostInfoAdaptor(MainActivity.this, postsList);
                listView.setAdapter(postInfoAdaptor);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void addPost() {
        Intent homeActivity = new Intent(getApplicationContext(), PostActivity.class);
        startActivity(homeActivity);
        finish();
    }
}

