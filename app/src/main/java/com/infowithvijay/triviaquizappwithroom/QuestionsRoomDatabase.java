package com.infowithvijay.triviaquizappwithroom;


import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

@Database(entities = {Questions.class},version = 2)
public abstract class QuestionsRoomDatabase extends RoomDatabase {


    private static QuestionsRoomDatabase INSTANCE;


    public abstract QuestionsDao wordDao();



    public static synchronized QuestionsRoomDatabase getInstance(final Context context){

        if (INSTANCE == null){
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                    QuestionsRoomDatabase.class,"questions_database")
                    .fallbackToDestructiveMigration()
                    .addCallback(RoomDBCallback)
                    .build();
        }

        return INSTANCE;
    }

    private static Callback RoomDBCallback = new Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);

            new PopulateDbAsyncTask(INSTANCE).execute();

        }
    };


    private static class PopulateDbAsyncTask extends AsyncTask<Void,Void,Void>{


        private QuestionsDao wordDao;


        private PopulateDbAsyncTask(QuestionsRoomDatabase db)
        {
            wordDao = db.wordDao();
        }
        @Override
        protected Void doInBackground(Void... voids) {


            wordDao.insert(new Questions(" API 21 is for what ?","Lollipop","Nought","Oreo","Android","Lollipop"));
            wordDao.insert(new Questions(" PC full is ? ","Lollipop","Personal Computer","Oreo","Android","Personal Computer"));
            wordDao.insert(new Questions(" Firefox is what ?","Virus","Nought","Browser","Android","Browser"));
            wordDao.insert(new Questions(" API 25 is for what ?","Lollipop","Nought","Oreo","Android","Nought"));

            wordDao.insert(new Questions("Which of the following is a chat engine?","Google Bol","Yahoo Talk","Rediif Messenger","None of these","None of these"));
            wordDao.insert(new Questions("Which of the following is an input device?","Plotter","Printer","Monitor","Scanner","Scanner"));
            wordDao.insert(new Questions("HTML is used to create -","Operating System","High Level Program","Web-Server","Web Page","Web Page"));

            wordDao.insert(new Questions("Which is the fastest memory in computer","RAM","ROM","Cache","Hard Drive","Cache"));
            wordDao.insert(new Questions("What is the name for a webpage address? ","Directory","Protocol","URL","Domain","URL"));
            wordDao.insert(new Questions("Which of the following is not an input device?","Microphone","Keyboard","Mozilla firefox","Mouse","Mozilla firefox"));



            return null;
        }
    }


}
