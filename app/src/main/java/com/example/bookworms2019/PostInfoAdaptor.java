package com.example.bookworms2019;

import android.app.Activity;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewpager.widget.ViewPager;

import java.util.List;

public class PostInfoAdaptor extends ArrayAdapter<Posts> {
    private Activity context;
    private List<Posts>postsList;

    public PostInfoAdaptor(Activity context, List<Posts>postsList){
        super(context, R.layout.list_view, postsList);
        this.context = context;
        this.postsList = postsList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View listView = inflater.inflate(R.layout.list_view, null, true);

        TextView moduleCode = (TextView)listView.findViewById(R.id.moduleCode);
        TextView price = (TextView)listView.findViewById(R.id.price);
        TextView type = (TextView)listView.findViewById(R.id.type);
        TextView grade = (TextView)listView.findViewById(R.id.grade);
        TextView seller = (TextView)listView.findViewById(R.id.seller);

        Posts posts = postsList.get(position);
        moduleCode.setText(posts.getModuleCode());
        price.setText(posts.getPrice());
        type.setText(posts.getType());
        grade.setText(posts.getGrade());
        seller.setText(posts.getUsername());

        return listView;
    }
}
