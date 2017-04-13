package com.example.felix.notizen.cust_views;

import android.annotation.TargetApi;
import android.app.FragmentManager;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.text.format.DateFormat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.felix.notizen.R;
import com.example.felix.notizen.SQLManagerContract;
import com.example.felix.notizen.Activities.Notizen_uebersicht;
import com.example.felix.notizen.cust_listeners.CustomTouchListener;
import com.example.felix.notizen.Fragments.ChangeCategoryDialog;
import com.example.felix.notizen.Objects.Note;
import com.example.felix.notizen.Objects.Note_Category;

import java.util.Date;

/**
 * Created by Felix "nepumuk" Wiemann on 07.02.2016
 * as part of Notizen
 *
 */
public class SingleNoteOverviewView extends RelativeLayout implements ChangeCategoryDialog.ChangeCategoryDialogListener {
    public Note mNote;
    private static final String LOG_TAG = "SingleNoteOverView";
    @SuppressWarnings("unused")
    private static final String LOG_TAG_myViewLayout = "SingleNoteOverview";
    ChangeCategoryDialog dialog;
    ViewHolder mViewHolder = new ViewHolder();
    FragmentManager mfragmentManager;
    LayoutInflater mInflater;
    Notizen_uebersicht.NoteListAdapter mnoteListAdapter;

    public View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Log.i(LOG_TAG, Thread.currentThread().getStackTrace()[2].getMethodName());
            dialog.show(mfragmentManager, "TAG");
        }
    };

    @SuppressWarnings("unused")
    public SingleNoteOverviewView getInstance() {
        Log.i(LOG_TAG, Thread.currentThread().getStackTrace()[2].getMethodName());
        return this;
    }

    @SuppressWarnings("unused")
    public CustomTouchListener customTouchListener = new CustomTouchListener(this.getContext());


    public SingleNoteOverviewView(Context context) {
        super(context);
        Log.i(LOG_TAG, Thread.currentThread().getStackTrace()[2].getMethodName());
        //inflate XML resource and attach
        inflateLayout(context);
    }

    public SingleNoteOverviewView(Context context, AttributeSet attrs) {
        super(context, attrs);
        Log.i(LOG_TAG, Thread.currentThread().getStackTrace()[2].getMethodName());
        init(context, attrs);
        inflateLayout(context);
    }

    public SingleNoteOverviewView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        Log.i(LOG_TAG, Thread.currentThread().getStackTrace()[2].getMethodName());
        init(context, attrs);
        inflateLayout(context);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @SuppressWarnings("unused")
    public SingleNoteOverviewView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        Log.i(LOG_TAG, Thread.currentThread().getStackTrace()[2].getMethodName());
        init(context, attrs);
        inflateLayout(context);
    }

    public SingleNoteOverviewView(Context context, FragmentManager fragmentManager, Notizen_uebersicht.NoteListAdapter noteListAdapter) {
        super(context);
        Log.i(LOG_TAG, Thread.currentThread().getStackTrace()[2].getMethodName());
        dialog = ChangeCategoryDialog.newInstance(this);
        mnoteListAdapter = noteListAdapter;
        mfragmentManager = fragmentManager;
        inflateLayout(context);

    }

    private void inflateLayout(Context context){
        Log.i(LOG_TAG, Thread.currentThread().getStackTrace()[2].getMethodName());
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mInflater.inflate(com.example.felix.noteoverview.R.layout.note_overview_layout, this);
       // this.setOnTouchListener(customTouchListener);
        initViews();
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        Log.i(LOG_TAG, Thread.currentThread().getStackTrace()[2].getMethodName());
       // initViews();
        updateData();
    }

    public void setNote(Note n) {
        Log.i(LOG_TAG, Thread.currentThread().getStackTrace()[2].getMethodName());
        if (n==null){
            n= new Note();
        }
        mNote = n;
        updateData();
    }

    // TODO dont update db if not needed
    private void updateData() {
        Log.i(LOG_TAG, Thread.currentThread().getStackTrace()[2].getMethodName());
        mViewHolder.mCheckBoxNoteDone.setChecked(mNote.isTaskDone());
        mViewHolder.mTextViewDate.setText(
                DateFormat.format("dd/MM/yyyy",
                        new Date(mNote.getNoteLastChangedDate())).toString()); //TODO change back to due date
        mViewHolder.mTextViewTitle.setText(mNote.getNoteName());
        if (mNote.getNoteCategory() != null)
            mViewHolder.mImageViewCategory.setBackgroundColor(mNote.getNoteCategory().getM_CatColor());
        mNote.updateDB(new SQLManagerContract(getContext()));
        setImportance();
    }

    private void setImportance() {
        switch (mNote.getNoteImportance()) {
            case 1:
                mViewHolder.mImageViewPriority.setImageResource(R.drawable.importance1);
                break;
            case 2:
                mViewHolder.mImageViewPriority.setImageResource(R.drawable.importance2);
                break;
            case 3:
                mViewHolder.mImageViewPriority.setImageResource(R.drawable.importance3);
                break;
            case 4:
                mViewHolder.mImageViewPriority.setImageResource(R.drawable.importance4);
                break;
            case 0:
                mViewHolder.mImageViewPriority.setImageResource(R.drawable.importance0);
                break;
        }
    }

    public void initViews() {
        Log.i(LOG_TAG, Thread.currentThread().getStackTrace()[2].getMethodName());
        mViewHolder.mCheckBoxNoteDone = (CheckBox) this.findViewById(com.example.felix.noteoverview.R.id.cbTaskDone);
        mViewHolder.mImageViewCategory = (ImageView) this.findViewById(com.example.felix.noteoverview.R.id.ivCategory);
        mViewHolder.mTextViewDate = (TextView) this.findViewById(com.example.felix.noteoverview.R.id.tvNoteDateDue);
        mViewHolder.mTextViewTitle = (TextView) this.findViewById(com.example.felix.noteoverview.R.id.tvNoteName);
        mViewHolder.mImageViewPriority = (ImageView) this.findViewById(R.id.ivPriority);
       // mViewHolder.mImageViewCategory.setBackgroundColor(mNote.NoteCategory);
        mViewHolder.mImageViewCategory.setOnClickListener(onClickListener);
        mViewHolder.mImageViewPriority.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                cycleImportance();
            }
        });
        mViewHolder.mCheckBoxNoteDone.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                mNote.setTaskDone(buttonView.isChecked(), true);
                updateData();
            }
        });
        invalidate();
    }

    private void cycleImportance() {
        if ((mNote.getNoteImportance()) == 5) {
            mNote.setNoteImportance(0, true);
        } else {
            mNote.setNoteImportance((mNote.getNoteImportance() + 1), true);
        }
        updateData();
    }

    @Override
    public void onFinishEditDialog(Note_Category note_category) {
        Log.i(LOG_TAG, Thread.currentThread().getStackTrace()[2].getMethodName());

        if (mNote.getNoteCategory() == note_category) {
            // since no change, do not update db
            mNote.setNoteCategory(note_category, false);
        } else {
            // change -> update DB
            mNote.setNoteCategory(note_category, true);
        }
        updateData();
    }

    @SuppressWarnings("unused")
    private void init(Context context, AttributeSet attrs) {
        Log.i(LOG_TAG, Thread.currentThread().getStackTrace()[2].getMethodName());
    }

    boolean clicked = false;

    public void setClicked(boolean b) {
        // if clicked AND click again, still clicked
        if (clicked && b) {
            clicked = false;
            ((RelativeLayout) mViewHolder.mCheckBoxNoteDone.getParent()).setBackgroundColor(Color.parseColor("#dddddd"));
        } else {
            clicked = true;
            ((RelativeLayout) mViewHolder.mCheckBoxNoteDone.getParent()).setBackgroundColor(0xffffff);
        }

    }

    public class ViewHolder {
        public CheckBox mCheckBoxNoteDone;
        public TextView mTextViewTitle;
        public TextView mTextViewDate;
        public ImageView mImageViewCategory;
        public ImageView mImageViewPriority;
    }


}
