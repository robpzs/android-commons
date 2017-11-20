package com.robpzs.androidcommons.sample;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.robpzs.androidcommons.recyclerview.HeaderViewAdapter;
import com.robpzs.androidcommons.recyclerview.MultiRecyclerViewAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Sample Activity.
 *
 * @author robpzs
 */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        HeaderViewAdapter header = new HeaderViewAdapter(R.layout.list_header, "Header 1");

        List<RecyclerView.Adapter> adapters = new ArrayList<>(1);
        adapters.add(header);
        MultiRecyclerViewAdapter adapter = new MultiRecyclerViewAdapter(adapters);

        RecyclerView recycler = findViewById(R.id.recycler_view);
        recycler.setLayoutManager(new GridLayoutManager(this, 2));
        recycler.setAdapter(adapter);
    }
}
