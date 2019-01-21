package com.example.admin.wander;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {


    // Member variables.
    RecyclerView mRecyclerView;
    AttractionAdapter mAdapter;
    ArrayList<Attraction> mAttractionList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize the RecyclerView.
        mRecyclerView = findViewById(R.id.recyclerView);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Initialize the ArrayList that will contain the data.
        mAttractionList = new ArrayList<>();

        // add some items to our list
        mAttractionList.add(
                new Attraction(R.drawable.college_logo,"logo","our Logo"
        ));
        mAttractionList.add(
                new Attraction(R.drawable.img_badminton,"badminton",
                        "this is badminton"
                ));
        // Initialize the adapter and set it to the RecyclerView.
        mAdapter =  new AttractionAdapter(this, mAttractionList);

        mRecyclerView.setAdapter(mAdapter);
    }
}
