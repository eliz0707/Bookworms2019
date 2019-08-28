package com.example.bookworms2019;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;

public class MyPostActivity extends AppCompatActivity {

    ListView myPostList;
    TextView noPostsText;
    ArrayList<String> al = new ArrayList<>();
    int totalPost = 0;
    ProgressDialog pd;
    DatabaseReference databaseReference;
    FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_post);

        myPostList = (ListView)findViewById(R.id.myPostList);
        noPostsText = (TextView)findViewById(R.id.noPostsText);
        fab = (FloatingActionButton)findViewById(R.id.floatingActionButton);

        pd = new ProgressDialog(MyPostActivity.this);
        pd.setMessage("Loading...");
        pd.show();

        databaseReference = FirebaseDatabase.getInstance().getReference("posts");

        String url = "https://bookworms2019-ea561.firebaseio.com/posts.json";

        StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>(){
            @Override
            public void onResponse(String s) {
                doOnSuccess(s);
            }
        },new Response.ErrorListener(){
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                System.out.println("" + volleyError);
            }
        });

        RequestQueue rQueue = Volley.newRequestQueue(MyPostActivity.this);
        rQueue.add(request);

        myPostList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                UserDetails.chatWith = al.get(position);
                startActivity(new Intent(MyPostActivity.this, ChatActivity.class));
            }
        });

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MyPostActivity.this, AccountActivity.class));
            }
        });
    }

    public void doOnSuccess(String s){
        try {
            JSONObject obj = new JSONObject(s);

            Iterator i = obj.keys();
            String key = "";

            while(i.hasNext()){
                key = i.next().toString();

                if(key.equals(UserDetails.username)) {
                    al.add(key);
                }

                totalPost++;
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        if(totalPost <=1){
            noPostsText.setVisibility(View.VISIBLE);
            myPostList.setVisibility(View.GONE);
        }
        else{
            noPostsText.setVisibility(View.GONE);
            myPostList.setVisibility(View.VISIBLE);
            myPostList.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, al));
        }

        pd.dismiss();
    }
}
