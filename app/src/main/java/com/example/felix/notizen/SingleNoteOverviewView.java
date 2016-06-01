package com.example.felix.notizen;

import android.annotation.TargetApi;
import android.app.FragmentManager;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * Created by Felix "nepumuk" Wiemann on 07.02.2016
 * as part of Notizen
 *
 */
public class SingleNoteOverviewView extends RelativeLayout implements ChangeCategoryDialog.ChangeCategoryDialogListener{
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

    private void updateData() {
        Log.i(LOG_TAG, Thread.currentThread().getStackTrace()[2].getMethodName());
        mViewHolder.mCheckBoxNoteDone.setChecked(mNote.TaskDone);
        mViewHolder.mTextViewDate.setText(String.valueOf(mNote.TaskDueDate));
        mViewHolder.mTextViewTitle.setText(mNote.NoteName);
        if(mNote.NoteCategory!=null)
            mViewHolder.mImageViewCategory.setBackgroundColor(mNote.NoteCategory.getM_CatColor());
        mNote.updateDB(new SQLManagerContract(getContext()));
    }

    public void initViews() {
        Log.i(LOG_TAG, Thread.currentThread().getStackTrace()[2].getMethodName());
        mViewHolder.mCheckBoxNoteDone = (CheckBox) this.findViewById(com.example.felix.noteoverview.R.id.cbTaskDone);
        mViewHolder.mImageViewCategory = (ImageView) this.findViewById(com.example.felix.noteoverview.R.id.ivCategory);
        mViewHolder.mTextViewDate = (TextView) this.findViewById(com.example.felix.noteoverview.R.id.tvNoteDateDue);
        mViewHolder.mTextViewTitle = (TextView) this.findViewById(com.example.felix.noteoverview.R.id.tvNoteName);
       // mViewHolder.mImageViewCategory.setBackgroundColor(mNote.NoteCategory);
        mViewHolder.mImageViewCategory.setOnClickListener(onClickListener);
        mViewHolder.mCheckBoxNoteDone.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                mNote.TaskDone = buttonView.isChecked();
                updateData();
            }
        });
        invalidate();
    }

    @Override
    public void onFinishEditDialog(Note_Category note_category) {
        Log.i(LOG_TAG, Thread.currentThread().getStackTrace()[2].getMethodName());
        mNote.NoteCategory=note_category;
        updateData();
    }

    @SuppressWarnings("unused")
    private void init(Context context, AttributeSet attrs) {
        Log.i(LOG_TAG, Thread.currentThread().getStackTrace()[2].getMethodName());
    }

    public class ViewHolder {
        public CheckBox mCheckBoxNoteDone;
        public TextView mTextViewTitle;
        public TextView mTextViewDate;
        public ImageView mImageViewCategory;
    }
}
