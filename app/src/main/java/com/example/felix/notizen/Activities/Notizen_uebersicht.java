package com.example.felix.notizen.activities;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.felix.notizen.ActionBarCallback;
import com.example.felix.notizen.R;
import com.example.felix.notizen.SQLManagerContract;
import com.example.felix.notizen.cust_views.SingleNoteOverviewView;
import com.example.felix.notizen.objects.Note;
import com.example.felix.notizen.objects.Note_Notification;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Felix "nepumuk" Wiemann on 24.05.2016
 * as part of Notizen
 * TODO documentation
 */
public class Notizen_uebersicht extends AppCompatActivity  {

    @SuppressWarnings("unused")
    private static final String EDIT_NOTE_NOTE_TOEDIT = "EDIT_NOTE_NOTE_TOEDIT";
    private static final String LOG_TAG = "Notizen_uebersicht" ;
    List<Note> superNotes;
    private boolean isFabOpen = false;
    FloatingActionButton fabAddNewNote, fabAddNewNoteText, fabAddNewNoteImage, fabAddNewNoteDraw;
    private Animation fab_open, fab_close, rotate_forward, rotate_backward;
    @SuppressWarnings("unused")
    Intent intentNewNoteActivity;
    ActionMode myActionMode;
    ActionMode.Callback myCallback;
    SQLManagerContract sqlContract;
    Intent intent;
    @SuppressWarnings("unused")
    private static final int REQUEST_CODE = 10;
    public static final int REQUEST_CODE_NEW_NOTE = 20;
    @SuppressWarnings("unused")
    public static final String REQUEST_STRING_SWITCH_NOTE_MODE  = "REQUEST_STRING_SWITCH_NOTE_MODE";
    public static final int REQUEST_CODE_EDIT_NOTE = 30;
    View contentView;
    public static final String EDIT_NOTE_ADAPTER_POSITION = "EDIT_NOTE_ADAPTER_POSITION";
    ListView listViewNote;
    public NoteListAdapter ListViewAdapter = new NoteListAdapter();
    private static final String LOG_TAG_NotizenÜbersicht = "notizenÜbersicht";
    public static String strRequestCode = "requestCode";

    @Override
    @SuppressWarnings("ConstantConditions")
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(LOG_TAG, Thread.currentThread().getStackTrace()[2].getMethodName());
        setContentView(R.layout.activity_notizen_uebersicht);
        // Warning (may produce null-pointer) suppressed
        contentView = this.findViewById(android.R.id.content).getRootView();
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        intent = new Intent(this, newNoteActivity.class);

        // init views
        fabAddNewNote = (FloatingActionButton) findViewById(R.id.fab_add_newNote);
        fabAddNewNoteText = (FloatingActionButton) findViewById(R.id.fab_newNote_Text);
        fabAddNewNoteImage = (FloatingActionButton) findViewById(R.id.fab_newNote_Image);
        fabAddNewNoteDraw = (FloatingActionButton) findViewById(R.id.fab_newNote_Drawing);
        fab_open = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fab_open);
        fab_close = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fab_close);
        rotate_forward = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fab_rot_forward);
        rotate_backward = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fab_rot_backward);

        initListViewNotes();
        sqlContract = new SQLManagerContract(this);
        try {
            superNotes = sqlContract.getAllNotes();
            for (Note n : superNotes
                    ) {
                ListViewAdapter.addNoteWODatabaseEntry(n);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.i(LOG_TAG, Thread.currentThread().getStackTrace()[2].getMethodName());
        try {
            myActionMode.finish();
        } catch (Exception ignored) {
        }


        if (resultCode == RESULT_OK && requestCode == REQUEST_CODE_NEW_NOTE) {
            Log.d(LOG_TAG_NotizenÜbersicht, getString(R.string.LT_nue_onActRes_AddNote));
            Bundle b = data.getExtras();
            Note n = b.getParcelable(newNoteActivity.INTENT_NEW_NOTE_ACTIVITY_PARCEL);
            addNote(n);
        }
        if (resultCode == RESULT_OK && requestCode == REQUEST_CODE_EDIT_NOTE) {
            Bundle b = data.getExtras();
            //data.getParcelableExtra(newNoteActivity.INTENT_NEW_NOTE_ACTIVITY_PARCEL);
            Note n = b.getParcelable(newNoteActivity.INTENT_NEW_NOTE_ACTIVITY_PARCEL);
            int position = b.getInt(EDIT_NOTE_ADAPTER_POSITION);
            ListViewAdapter.setNoteAtPosition(n, position);
            Log.d(LOG_TAG_NotizenÜbersicht, getString(R.string.LT_nue_onActRes_EditNote));
        }

        intent = new Intent(this, newNoteActivity.class);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        Log.i(LOG_TAG, Thread.currentThread().getStackTrace()[2].getMethodName());
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_notizen_uebersicht, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Log.i(LOG_TAG,Thread.currentThread().getStackTrace()[2].getMethodName());
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

    @Override
    public void startActivityForResult(Intent intent, int requestCode) {
        Log.i(LOG_TAG, Thread.currentThread().getStackTrace()[2].getMethodName());

        intent.putExtra(strRequestCode, requestCode);
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN) {
            ActivityOptions options = ActivityOptions.makeScaleUpAnimation(contentView,0,0,contentView.getWidth(),contentView.getHeight());
            super.startActivityForResult(intent, requestCode,options.toBundle());
        }else  {

        super.startActivityForResult(intent, requestCode);}
    }

   @Override
    public boolean onTouchEvent(MotionEvent motionEvent) {
       Log.i(LOG_TAG,Thread.currentThread().getStackTrace()[2].getMethodName());
       return super.onTouchEvent(motionEvent);

    }

    @Override
    public void onPause(){
        Log.i(LOG_TAG, Thread.currentThread().getStackTrace()[2].getMethodName());
        ListViewAdapter.updateAllNotes();
        super.onPause();
    }

    public void animateFAB() {

        if (isFabOpen) {

            fabAddNewNote.startAnimation(rotate_backward);
            fabAddNewNoteText.startAnimation(fab_close);
            fabAddNewNoteImage.startAnimation(fab_close);
            fabAddNewNoteDraw.startAnimation(fab_close);
            fabAddNewNoteText.setClickable(false);
            fabAddNewNoteImage.setClickable(false);
            fabAddNewNoteDraw.setClickable(false);
            isFabOpen = false;
            Log.d("Raj", "close");

        } else {

            fabAddNewNote.startAnimation(rotate_forward);
            fabAddNewNoteText.startAnimation(fab_open);
            fabAddNewNoteImage.startAnimation(fab_open);
            fabAddNewNoteDraw.startAnimation(fab_open);
            fabAddNewNoteText.setClickable(true);
            fabAddNewNoteImage.setClickable(true);
            fabAddNewNoteDraw.setClickable(true);
            isFabOpen = true;
            Log.d("Raj", "open");

        }
    }

    public void onFABClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.fab_add_newNote:

                animateFAB();
                break;
            case R.id.fab_newNote_Drawing:
                //animateFAB();
                Log.d("Raj", "Fab 1");
                break;
            case R.id.fab_newNote_Image:
                //animateFAB();
                startActivity(new Intent(this, act_newPhotoNote.class));
                break;
            case R.id.fab_newNote_Text:
                animateFAB();
                startActivityForResult(intent, REQUEST_CODE_NEW_NOTE);
                break;
        }
    }

    private void initListViewNotes(){
        Log.i(LOG_TAG, Thread.currentThread().getStackTrace()[2].getMethodName());
        listViewNote = (ListView) findViewById(R.id.lvNoteContainer2);
        if (listViewNote != null) {
            listViewNote.setAdapter(ListViewAdapter);
        }else throw new NullPointerException("Error while inflating Notizenübersicht");
        myCallback = new ActionBarCallback();

        AdapterView.OnItemClickListener itemClickListener = new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id){
                ((SingleNoteOverviewView) listViewNote.getChildAt(ContextMenuItemPositionClickedToDelete)).setClicked(false);
                ContextMenuItemPositionClickedToDelete = position;
                ((SingleNoteOverviewView) view).setClicked(true);
                myActionMode = startActionMode(myCallback);
            }
        };

        listViewNote.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                intent.putExtra(EDIT_NOTE_ADAPTER_POSITION, position);
                intent.putExtra(newNoteActivity.INTENT_NEW_NOTE_ACTIVITY_PARCEL, ListViewAdapter.getItem(position));
                //intent.putExtra(EDIT_NOTE_NOTE_TOEDIT,ListViewAdapter.getItem(position));
                startActivityForResult(intent, REQUEST_CODE_EDIT_NOTE);
                Log.d(LOG_TAG_NotizenÜbersicht, getString(R.string.LT_nue_ItemLongClick));
                return false;
            }
        });
        listViewNote.setOnItemClickListener(itemClickListener);
    }

    @SuppressWarnings("unused")
    public void makeText(String message) {
        Log.i(LOG_TAG,Thread.currentThread().getStackTrace()[2].getMethodName());
        Toast.makeText(this,message, Toast.LENGTH_SHORT).show();
    }

    private void addNote(Note n){
        Log.i(LOG_TAG, Thread.currentThread().getStackTrace()[2].getMethodName());
        ListViewAdapter.addNote(n);
        Note_Notification.notify(this, n);
    }

    private int ContextMenuItemPositionClickedToDelete;

    public void OnActionBarItemClick(MenuItem item){
        Log.i(LOG_TAG, Thread.currentThread().getStackTrace()[2].getMethodName());
        switch (item.getItemId()){
            case R.id.action_bar_menu_delete:
                ListViewAdapter.deleteNote(ContextMenuItemPositionClickedToDelete);
                break;
            case R.id.action_bar_menu_edit:
            case R.id.action_bar_menu_view:
                intent.putExtra(EDIT_NOTE_ADAPTER_POSITION, ContextMenuItemPositionClickedToDelete);
                intent.putExtra(newNoteActivity.INTENT_NEW_NOTE_ACTIVITY_PARCEL,ListViewAdapter.getItem(ContextMenuItemPositionClickedToDelete));
                startActivityForResult(intent, REQUEST_CODE_EDIT_NOTE);
                break;
        }
        myActionMode.finish();
    }


    /**
     * Adapter, um die Notizen auf die Listview zu bringen
     */
    public class NoteListAdapter extends BaseAdapter {

        // enthält die Notizen
        private List<Note> mNotes;

        @SuppressWarnings("unused")
        public NoteListAdapter(List<Note> notes) {
            Log.i(LOG_TAG, Thread.currentThread().getStackTrace()[2].getMethodName());
            mNotes = notes;
            superNotes=notes;

        }
        public NoteListAdapter() {
            Log.i(LOG_TAG, Thread.currentThread().getStackTrace()[2].getMethodName());
            mNotes = new ArrayList<>();
            superNotes=mNotes;
        }

        public void updateAllNotes() {
            Log.i(LOG_TAG, Thread.currentThread().getStackTrace()[2].getMethodName());
            if (mNotes==null)
                return;
            for (Note note:mNotes
                 ) {
                note.updateDB(sqlContract);
            }
            superNotes=mNotes;
        }

        public void setNoteAtPosition(Note n, int position){
            Log.i(LOG_TAG, Thread.currentThread().getStackTrace()[2].getMethodName());
            mNotes.set(position, n);
            sqlContract.updateNote(n);
            superNotes=mNotes;
            this.notifyDataSetChanged();
        }

        @Override
        public int getCount() {
            Log.i(LOG_TAG, Thread.currentThread().getStackTrace()[2].getMethodName());
            return mNotes == null ? 0 : mNotes.size();
        }

        @Override
        public Note getItem(int position) {
            Log.i(LOG_TAG, Thread.currentThread().getStackTrace()[2].getMethodName());
            return mNotes.get(position);
        }

        @Override
        public long getItemId(int position) {
            Log.i(LOG_TAG, Thread.currentThread().getStackTrace()[2].getMethodName());
            return getItem(position).get_ID();
        }

        public void addNoteWODatabaseEntry(Note n){
            Log.i(LOG_TAG, Thread.currentThread().getStackTrace()[2].getMethodName());
            mNotes.add(n);
            superNotes=mNotes;
            super.notifyDataSetChanged();
        }

        public void addNote(Note n){
            Log.i(LOG_TAG, Thread.currentThread().getStackTrace()[2].getMethodName());
            mNotes.add(n);
            super.notifyDataSetChanged();
            sqlContract.addNote(n);
            superNotes=mNotes;
        }

        @SuppressWarnings("unused")
        public List<Note> getNotes() {
            Log.i(LOG_TAG, Thread.currentThread().getStackTrace()[2].getMethodName());
            return mNotes;
        }

        public void deleteNote(int position){
            Log.i(LOG_TAG, Thread.currentThread().getStackTrace()[2].getMethodName());
            sqlContract.deleteNote(getItem(position));
            mNotes.remove(position);
            superNotes=mNotes;
            this.notifyDataSetChanged();
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            Log.i(LOG_TAG, Thread.currentThread().getStackTrace()[2].getMethodName());
           // final Note p = getItem(position);
           // return (((myViewLayout)getLayoutInflater().inflate(R.layout.note_overview_layout, parent,false)).setNote(p)).getMe();
            final Note p = getItem(position);
            if (convertView==null) {
                convertView = new SingleNoteOverviewView(parent.getContext(),getFragmentManager(),this);
                //myViewLayout mVl = (myViewLayout)convertView;
            }
            ((SingleNoteOverviewView)convertView).setNote(p);
            /*
            myViewLayout mVL = new myViewLayout(getBaseContext(),getFragmentManager(),p);
            mVL.setRelativeLayout((RelativeLayout) convertView);
            mVL.setNote(p);*/
            return convertView;
        }
    }

}
