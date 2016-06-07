package com.example.felix.notizen;

import android.annotation.SuppressLint;
import android.app.DialogFragment;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Felix "nepumuk" Wiemann on 05.02.2016
 * as part of Notizen
 * dialog fragment for changing the category of a {@link com.example.felix.notizen.Note}
 */
public class ChangeCategoryDialog extends DialogFragment implements NoteCategoryChangeFragment.OnFragmentInteractionListener {
    /**
     * Tag for logging purposes
      */
    private final static String LOG_TAG_ChangeCatDia = "ChangeCategoryDialog";
    private SQLManagerContract sqlManagerContract;
    /**
     * View of the fragment
     */
    private View thisContent;
    /**
     *
     */
    public ChangeCategoryDialogListener mListener;

    /**
     * on table row long click
     * delete selected category
     */
    View.OnLongClickListener onTableRowLongClickListener = new View.OnLongClickListener() {
        @Override
        public boolean onLongClick(View v) {
            TableRow tv = (TableRow) v;
            TextView id = (TextView) tv.getVirtualChildAt(3);
            sqlManagerContract.deleteNote_Category(sqlManagerContract.getNoteCategory(Integer.decode(id.getText().toString())));
            updateData();
            Log.d("lcl","lcl");
            return true;
        }
    };

    /**
     * on table row click
     * select selected category
     */
    View.OnClickListener onTableRowClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            TableRow tv = (TableRow) v;
            TextView id = (TextView) tv.getVirtualChildAt(3);
            Note_Category nct = sqlManagerContract.getNoteCategory(Integer.decode(id.getText().toString()));
            mListener.onFinishEditDialog(nct);
            dismiss();
        }
    };

    /**
     * create a new instance of ChangeCategoryDialog from a
     * {@link com.example.felix.notizen.SingleNoteOverviewView} instance for adding the interfaceconnection
     * @param SingleNoteOverviewView to add
     * @return new instance
     */
    public static ChangeCategoryDialog newInstance(SingleNoteOverviewView SingleNoteOverviewView) {
        ChangeCategoryDialog f = new ChangeCategoryDialog();
        f.mListener= SingleNoteOverviewView;
        Log.d(LOG_TAG_ChangeCatDia, "newInstance");
        return f;
    }


    /**
     * empty constructor
     */
    public ChangeCategoryDialog() {
        Log.d(LOG_TAG_ChangeCatDia, "empty Constr");
        // Empty constructor required for DialogFragment
    }

    /**
     * on creating view
     * @param inflater layout inflater
     * @param container .
     * @param savedInstanceState .
     * @return created view
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d(LOG_TAG_ChangeCatDia, "onCreateView");
        getDialog().setTitle(R.string.TitleCreateNewCategory);
        // inflate
        @SuppressLint("InflateParams") final View view = inflater.inflate(com.example.felix.noteoverview.R.layout.fragment_change_category, null);
        // save current view
        thisContent=view;
        ViewGroup.LayoutParams params = getDialog().getWindow().getAttributes();
        params.width = ViewGroup.LayoutParams.WRAP_CONTENT;
        params.height = ViewGroup.LayoutParams.WRAP_CONTENT;
        getDialog().getWindow().setAttributes((android.view.WindowManager.LayoutParams) params);
        sqlManagerContract = new SQLManagerContract(getActivity());
        // update data
        updateData();
        return view;
    }

    /**
     * Interface method called on fragment result
     * @param note_category created
     */
    @Override
    public void onFragmentNewCategoryResult(Note_Category note_category) {
        // add to SQL
        sqlManagerContract.addCategory(note_category);
        // refresh
        updateData();

    }

    /**
     * on click listener for the button create new category
     */
    View.OnClickListener newCategoryButtonClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            handleOnClickNewCatButton();
        }
    };

    /**
     * handle the on click on the new category button
     */
    private void handleOnClickNewCatButton(){
        NoteCategoryChangeFragment nCCF=new NoteCategoryChangeFragment();
        nCCF.setContext(this);
        nCCF.show(getFragmentManager(),"nCCF");
    }


    /**
     * update all data displayed in the table row
     */
    private void updateData(){
        TableLayout tableLayout = (TableLayout) thisContent.findViewById(R.id.TableLayout);
        // open all cats from database
        /*
      List of all categories
     */
        List<Note_Category> note_categories = sqlManagerContract.getAllCategories();
        // empty tablelayout
        tableLayout.removeAllViews();
        for (Note_Category n: note_categories
            ) {
            // create views
            TableRow tableRow = new TableRow(getActivity());
            TextView tvName = new TextView(getActivity());
            TextView tvDesc = new TextView(getActivity());
            ImageView ivColor = new ImageView(getActivity());
            TextView tvID = new TextView(getActivity());
            // TODO apply style from R.values.change_cat_frag_text_view and .change_cat_frag_image_view
            // format views
            tvDesc.setText(n.getM_CatDesc());
            ivColor.setBackgroundColor(n.getM_CatColor());
            tvID.setText(String.valueOf(n.getM_ID()));
            tvID.setVisibility(View.INVISIBLE);

            tableRow.setMinimumHeight(60);
            tableRow.setMinimumWidth(240);


            ivColor.setMinimumHeight(50);
            ivColor.setMinimumWidth(50);

            tvDesc.setMinimumHeight(40);
            tvDesc.setMinimumWidth(150);
            tvDesc.setGravity(Gravity.CENTER);

            tvName.setMinimumHeight(40);
            tvName.setMinimumWidth(150);
            tvName.setGravity(Gravity.CENTER);
            tvName.setText(n.getM_CatName());


            // add to tablerow
            tableRow.removeAllViews();
            tableRow.addView(ivColor, 0);
            tableRow.addView(tvName,1);
            tableRow.addView(tvDesc,2);
            tableRow.addView(tvID, 3);
            tableRow.setOnClickListener(onTableRowClickListener);
            tableRow.setOnLongClickListener(onTableRowLongClickListener);
            // add table row
            tableLayout.addView(tableRow, tableLayout.getChildCount());
        }
        thisContent.findViewById(R.id.CreateNewCatButtnStart).setOnClickListener(newCategoryButtonClickListener);
    }

    /**
     *
     */
    public interface ChangeCategoryDialogListener {
        void onFinishEditDialog(Note_Category noteCategory);
    }






}
