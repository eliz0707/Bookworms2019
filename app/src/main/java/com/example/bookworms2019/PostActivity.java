package com.example.bookworms2019;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class PostActivity extends AppCompatActivity {

    private static final String TAG = "PostActivity";

    EditText editModule, editPrice, editType, editGrade;
    String username;
    Button submit, back;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);

        editModule = (EditText) findViewById(R.id.tvModule);
        editPrice = (EditText) findViewById(R.id.tvPrice);
        editType = (EditText) findViewById(R.id.tvType);
        editGrade = (EditText) findViewById(R.id.tvGrade);
        submit = (Button) findViewById(R.id.btnSubmit);
        back = (Button)findViewById(R.id.btnBack);
        username = UserDetails.username;
        //database reference pointing to root of database
        databaseReference = FirebaseDatabase.getInstance().getReference("posts");

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addPosts();
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: directing back to the Home Page.");
                goBack();
            }
        });
    }
    private void goBack() {
        Intent homeActivity = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(homeActivity);
        finish();
    }

    public void addPosts(){
        String postModule = editModule.getText().toString();
        String postPrice = editPrice.getText().toString();
        String postType = editType.getText().toString();
        String postGrade = editGrade.getText().toString();

        if (!TextUtils.isEmpty(postModule) && !TextUtils.isEmpty(postPrice) && !TextUtils.isEmpty(postType) && !TextUtils.isEmpty(postGrade)){

            String id = databaseReference.push().getKey();

            Posts posts = new Posts(id, postModule, postPrice, postType, postGrade, username);

            databaseReference.child(id).setValue(posts);
            editModule.setText("");
            editPrice.setText("");
            editType.setText("");
            editGrade.setText("");

            startActivity(new Intent(PostActivity.this, MainActivity.class));

        } else {
            Toast.makeText(PostActivity.this, "Please fill in all fields!", Toast.LENGTH_SHORT).show();
        }

    }
}
