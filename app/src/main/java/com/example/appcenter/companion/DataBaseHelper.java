package com.example.appcenter.companion;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by appcenter on 6/6/17.
 */
public class DataBaseHelper extends SQLiteOpenHelper{



    private static String DB_NAME = "SQLiteDatabase.db";
    public static final String VIDEO_DATA_TABLE_NAME = "VIDEO_DATA";

    private SQLiteDatabase myDataBase;

    private final Context myContext;
    //The Android's default system path of your application database.
    private static String DB_PATH;
    //Change the version number other than the current one to copy the new database.
    private int currentDatabaseVersionNumber = 2;
    private final String DATABASE_VERSION_KEY="com.appcenter.companion.DATABASE_VERSION_KEY";
    /**
     * Constructor
     * Takes and keeps a reference of the passed context in order to access to the application assets and resources.
     * @param context
     */
    public DataBaseHelper(Context context) {

        super(context, DB_NAME, null, 1);
        this.myContext = context;

        DB_PATH = "/data/data/"+myContext.getApplicationContext().getPackageName()+"/databases/";
    }

    /**
     * Creates a empty database on the system and rewrites it with your own database.
     * */
    public void createDataBase(SharedPreferences preferences) throws IOException{

        boolean dbExist = checkDataBase();

        int previousVersion = preferences.getInt(DATABASE_VERSION_KEY,-1);

        if(dbExist&&previousVersion==currentDatabaseVersionNumber){
            //do nothing - database already exist
        }else{
            SharedPreferences.Editor editor = preferences.edit();
            editor.putInt(DATABASE_VERSION_KEY,currentDatabaseVersionNumber);
            editor.commit();
            Log.e("COPY","COPY DATABASE");
            //By calling this method and empty database will be created into the default system path
            //of your application so we are gonna be able to overwrite that database with our database.
            this.getReadableDatabase();

            try {

                copyDataBase();

            } catch (IOException e) {

                throw new Error("Error copying database");

            }
        }

    }

    /**
     * Check if the database already exist to avoid re-copying the file each time you open the application.
     * @return true if it exists, false if it doesn't
     */
    private boolean checkDataBase(){

        SQLiteDatabase checkDB = null;

        try{
            String myPath = DB_PATH + DB_NAME;
            checkDB = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);

        }catch(SQLiteException e){

            //database does't exist yet.

        }

        if(checkDB != null){

            checkDB.close();

        }

        return checkDB != null ? true : false;
    }

    /**
     * Copies your database from your local assets-folder to the just created empty database in the
     * system folder, from where it can be accessed and handled.
     * This is done by transfering bytestream.
     * */
    private void copyDataBase() throws IOException{

        //Open your local db as the input stream
        InputStream myInput = myContext.getAssets().open(DB_NAME);

        // Path to the just created empty db
        String outFileName = DB_PATH + DB_NAME;

        //Open the empty db as the output stream
        OutputStream myOutput = new FileOutputStream(outFileName);

        //transfer bytes from the inputfile to the outputfile
        byte[] buffer = new byte[1024];
        int length;
        while ((length = myInput.read(buffer))>0){
            myOutput.write(buffer, 0, length);
        }

        //Close the streams
        myOutput.flush();
        myOutput.close();
        myInput.close();

    }

    public void openDataBase() throws SQLException{

        //Open the database
        String myPath = DB_PATH + DB_NAME;
        myDataBase = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READWRITE);

    }

    @Override
    public synchronized void close() {

        if(myDataBase != null)
            myDataBase.close();

        super.close();

    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public List<String[]> getVideoData()
    {
        Cursor c = myDataBase.rawQuery("select * from VIDEO_DATA",null);
        List<String[]> videoDataList = new ArrayList<String[]>();
        if (c.moveToFirst()) {
            while ( !c.isAfterLast() ) {
                String videoDataListItem[] = {c.getString(0),c.getString(1),c.getString(2),c.getString(3),c.getInt(4)+"",c.getString(5),c.getString(6)};
                videoDataList.add(videoDataListItem);
                c.moveToNext();
            }
        }
        c.close();
        return videoDataList;
    }
    public void setVideoAsBookmarked(String _id,int value)
    {
        myDataBase.execSQL("update VIDEO_DATA SET isBookmarked="+value+" where _id='"+_id+"'");
    }
    public List<String[]> getIdentifyUnansweredQuestions()
    {
        Cursor c = myDataBase.rawQuery("select * from IDENTIFY_DATA where isAnswered = 0;",null);
        List<String[]> identifyDataList = new ArrayList<String[]>();
        if(c.moveToFirst()){
            while (!c.isAfterLast())
            {
                //Look schema of database for this
                String identifyDataListItem[] = {
                        //ID,qPicture,qPictureCourtesy,correctAnswers,options1,2,3,4
                        c.getString(0),c.getString(1),c.getString(2),c.getString(3),c.getString(4),c.getString(5),c.getString(6),c.getString(7)
                };
                identifyDataList.add(identifyDataListItem);
                c.moveToNext();
            }
        }
        c.close();
        return  identifyDataList;
    }
    public void setQuestionAsAnswered(String qID)
    {
        myDataBase.execSQL("update IDENTIFY_DATA SET isAnswered=1 where _id="+qID);
    }
    public long getAnsweredQuestionsCount()
    {
        return myDataBase.compileStatement("select count(*) from IDENTIFY_DATA where isAnswered = 1;").simpleQueryForLong();

    }
    public long getTotalQuestionsCount()
    {
        return myDataBase.compileStatement("select count(*) from IDENTIFY_DATA;").simpleQueryForLong();
    }
    public void clearAllAnsweredQuestions()
    {
        myDataBase.execSQL("update IDENTIFY_DATA SET isAnswered=0 where isAnswered =1;");
    }

    public SQLiteDatabase getSQLiteDatabaseObject()
    {
            return myDataBase;
    }
    // Add your public helper methods to access and get content from the database.
    // You could return cursors by doing "return myDataBase.query(....)" so it'd be easy
    // to you to create adapters for your views.

}