package com.example.felix.notizen;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

/**
 * Created by Felix "nepumuk" Wiemann on 24.05.2016
 * as part of Notizen
 * Part of {@link com.example.felix.notizen.Note} describing the category the user wants to place the note in
 */
public class Note_Category implements Parcelable {
    private static final String LOG_TAG = "Note_Category" ;
    private String m_CatName;
    private String m_CatDesc;
    private int m_CatColor;
    private int m_ID;

    /**
     * full constructor
     * @param mCatName Name of category
     * @param mCatDesc description of cat
     * @param m_CatColor color of cat
     * @param m_id id of cat
     */
    public Note_Category(String mCatName, String mCatDesc, int m_CatColor, int m_id) {
        Log.i(LOG_TAG, Thread.currentThread().getStackTrace()[2].getMethodName());
        this.m_CatName = mCatName;
        this.m_CatDesc = mCatDesc;
        this.m_CatColor = m_CatColor;
        m_ID = m_id;
    }

    /**
     * empty constructor, creating defaul category
     */
    public Note_Category() {
        Log.i(LOG_TAG, Thread.currentThread().getStackTrace()[2].getMethodName());
        this.m_CatName = "default";
        this.m_CatDesc = "default";
        this.m_CatColor = -65000;
        this.m_ID=1;
    }

    /**
     * get the name of the cat
     * @return name of cat
     */
    public String getM_CatName() {
        Log.i(LOG_TAG, Thread.currentThread().getStackTrace()[2].getMethodName());
        return m_CatName;
    }

    /**
     * set name of category
     * !! to store new data-set in database, you have to call it manually!!
     * @param m_CatName new name of cat
     */
    public void setM_CatName(String m_CatName) {
        Log.i(LOG_TAG, Thread.currentThread().getStackTrace()[2].getMethodName());
        this.m_CatName = m_CatName;
    }

    /**
     * get description of cat
     * @return desc of cat
     */
    public String getM_CatDesc() {
        Log.i(LOG_TAG, Thread.currentThread().getStackTrace()[2].getMethodName());
        return m_CatDesc;
    }

    /**
     * set desc of cat
     * !! to store new data-set in database, you have to call it manually!!
     * @param m_CatDesc new description
     */
    public void setM_CatDesc(String m_CatDesc) {
        Log.i(LOG_TAG, Thread.currentThread().getStackTrace()[2].getMethodName());
        this.m_CatDesc = m_CatDesc;
    }

    /**
     * get the color of the category
     * @return color of category as integer
     */
    public int getM_CatColor() {
        Log.i(LOG_TAG, Thread.currentThread().getStackTrace()[2].getMethodName());
        return m_CatColor;
    }

    /**
     * set new color of category
     * !! to store new data-set in database, you have to call it manually!!
     * @param m_CatColor new color
     */
    public void setM_CatColor(int m_CatColor) {
        Log.i(LOG_TAG, Thread.currentThread().getStackTrace()[2].getMethodName());
        this.m_CatColor = m_CatColor;
    }

    /**
     * get the id of the category
     * @return id
     */
    public int getM_ID() {
        Log.i(LOG_TAG, Thread.currentThread().getStackTrace()[2].getMethodName());
        return m_ID;
    }

    /**
     * set id
     * DO NOT WRITE NEW ID IN DATABASE, DB GOES CORRUPT
     * @param m_ID new id
     */
    public void setM_ID(int m_ID) {
        Log.i(LOG_TAG, Thread.currentThread().getStackTrace()[2].getMethodName());
        this.m_ID = m_ID;
    }

    /**
     * TODO what does this?, Why do i need it? Part of Parcelable
     * @return some int describing the contents
     */
    @Override
    public int describeContents() {
        Log.i(LOG_TAG, Thread.currentThread().getStackTrace()[2].getMethodName());
        return 0;
    }

    /**
     *  Create a note based on a parcel
     * @param in parcel
     */
    protected Note_Category(Parcel in) {
        Log.i(LOG_TAG, Thread.currentThread().getStackTrace()[2].getMethodName());
        this.m_ID=in.readInt();
        this.m_CatName=in.readString();
        this.m_CatDesc=in.readString();
        this.m_CatColor=in.readInt();
    }

    /**
     * write instance to parcel
     * @param dest destination parcel
     * @param flags TODO
     */
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        Log.i(LOG_TAG, Thread.currentThread().getStackTrace()[2].getMethodName());
        dest.writeInt(this.m_ID);
        dest.writeString(this.m_CatName);
        dest.writeString(this.m_CatDesc);
        dest.writeInt(this.m_CatColor);
    }

    /**
     * Needed to be able to parcel the note_category
     */
    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        /**
         * creates a category out of a parcel
         * @param in parcel
         * @return note
         */
        public Note_Category createFromParcel(Parcel in) {
            Log.i(LOG_TAG, Thread.currentThread().getStackTrace()[2].getMethodName());
            return new Note_Category(in);
        }

        /**
         * creates an array of categories
         * @param size of array
         * @return array categories of size
         */
        public Note_Category[] newArray(int size) {
            Log.i(LOG_TAG, Thread.currentThread().getStackTrace()[2].getMethodName());
            return new Note_Category[size];
        }
    };
}
