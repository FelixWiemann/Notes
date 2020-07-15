package com.example.felix.notizen;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.example.felix.notizen.objects.Notes.cTaskNote;
import com.example.felix.notizen.objects.Notes.cTextNote;
import com.example.felix.notizen.objects.Task.cBaseTask;
import com.example.felix.notizen.objects.Task.cTask;
import com.example.felix.notizen.objects.cStorageObject;
import com.example.felix.notizen.views.SwipableView;
import com.example.felix.notizen.views.adapters.BaseRecyclerAdapter;
import com.example.felix.notizen.views.adapters.CompoundAdapter;
import com.example.felix.notizen.views.adapters.OnSwipeableClickListener;
import com.example.felix.notizen.views.adapters.SwipableRecyclerAdapter;
import com.example.felix.notizen.views.adapters.TitleAdapter;

import java.util.ArrayList;
import java.util.UUID;

public class testAcitivity extends AppCompatActivity {


    private int currentEditedNoteIndex;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_acitivity);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        ArrayList<cStorageObject> itemList = new ArrayList<>();

        itemList.add(new cTextNote(UUID.randomUUID(),"title n1","message n1"));
        itemList.add(new cTextNote(UUID.randomUUID(),"title n2","message n2"));
        itemList.add(new cTextNote(UUID.randomUUID(),"title n3","message n3"));
        itemList.add(new cTextNote(UUID.randomUUID(),"title n4","message n4"));

        ArrayList<cBaseTask> tasks = new ArrayList<>();
        tasks.add(new cTask(UUID.randomUUID(),"title t1","text t1",false));
        tasks.add(new cTask(UUID.randomUUID(),"title t2","text t2",false));
        tasks.add(new cTask(UUID.randomUUID(),"title t3","text t3",true));
        tasks.add(new cTask(UUID.randomUUID(),"title t4","text t4",false));
        tasks.add(new cTask(UUID.randomUUID(),"title t5","text t5",false));
        itemList.add(new cTaskNote(UUID.randomUUID(),"title n8",tasks));


        itemList.add(new cTextNote(UUID.randomUUID(),"title n5","message n5"));
        itemList.add(new cTextNote(UUID.randomUUID(),"title n6","message n6"));
        itemList.add(new cTextNote(UUID.randomUUID(),"title n7","message n7"));

        final RecyclerView recyclerView = findViewById(R.id.recview);
        final CompoundAdapter<cStorageObject> adapter = new CompoundAdapter<>(itemList, R.layout.compound_view);
        adapter.registerAdapter(new TitleAdapter(itemList,2),R.id.titleid);
        adapter.registerAdapter(new BaseRecyclerAdapter<>(itemList,1), R.id.content);
        SwipableRecyclerAdapter<cStorageObject> swipeAdapter = new SwipableRecyclerAdapter<>(itemList,0, true);
        swipeAdapter.OnLeftClick = new OnSwipeableClickListener() {
            @Override
            public void onClick(View clickedOn, SwipableView parentView) {
                currentEditedNoteIndex = recyclerView.getChildAdapterPosition((View)parentView.getParent().getParent());
                if (currentEditedNoteIndex==-1) return;
                cStorageObject task = adapter.getItem(currentEditedNoteIndex);
                adapter.remove(task);
                adapter.notifyDataSetChanged();
                Log.d("testAcitivity", "OnLeftClick: " + task);
            }
        };
        recyclerView.addOnItemTouchListener(new RecyclerView.SimpleOnItemTouchListener(){
            @Override
            public void onTouchEvent(@NonNull RecyclerView recyclerView, @NonNull MotionEvent motionEvent) {
                Log.d("RecyclerView.SimpleOnItemTouchListener","onTouchEvent");
            }
        });
        swipeAdapter.OnMiddleClick = new OnSwipeableClickListener() {
            @Override
            public void onClick(View clickedOn, SwipableView parentView) {
                currentEditedNoteIndex = recyclerView.getChildAdapterPosition((View)parentView.getParent().getParent());
                if (currentEditedNoteIndex==-1) return;
                cStorageObject task = adapter.getItem(currentEditedNoteIndex);
                Log.d("testAcitivity", "OnMiddleClick: " + task);
            }
        };
        adapter.registerAdapter(swipeAdapter,R.id.compound_content);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(recyclerView.getContext()));
        recyclerView.addOnItemTouchListener(new RecyclerView.SimpleOnItemTouchListener() {
            boolean intercept = false;

            @Override
            public boolean onInterceptTouchEvent(@NonNull RecyclerView rv, @NonNull MotionEvent e) {
                if (!intercept) {
                    intercept = e.getAction() == MotionEvent.ACTION_DOWN;
                }
                if(intercept) {
                    boolean action = e.getAction() == MotionEvent.ACTION_UP;
                    boolean time = e.getEventTime() - e.getDownTime() < 55;
                    boolean distance = getMotionDistance(e) < 1;

                    intercept = (action || time) && distance;
                    Log.d("RecyclerView.SimpleOnItemTouchListener","action " + action + " event time " + (e.getEventTime() - e.getDownTime()) + " distance " + getMotionDistance(e));
                }
                Log.d("RecyclerView.SimpleOnItemTouchListener","onInterceptTouchEvent " + intercept);
                return intercept;
            }

            @Override
            public void onTouchEvent(@NonNull RecyclerView rv, @NonNull MotionEvent e) {
                super.onTouchEvent(rv, e);
                intercept = false;
                //Toast.makeText(getApplicationContext(),"onTouchEvent",Toast.LENGTH_SHORT).show();
                Log.d("RecyclerView.SimpleOnItemTouchListener","onTouchEvent");
            }
        });
        adapter.notifyDataSetChanged();
    }

    double getMotionDistance(MotionEvent e){
        if(e.getHistorySize() == 0) {
            return 0;
        }
        return Math.sqrt(Math.pow(e.getHistoricalX(0)-e.getX(), 2) + Math.pow(e.getHistoricalY(0)-e.getY(), 2));
    }

}
