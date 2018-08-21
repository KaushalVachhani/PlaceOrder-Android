package com.example.vachhani.place_order.Data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Environment;

import com.mobandme.ada.ObjectContext;
import com.mobandme.ada.ObjectSet;
import com.mobandme.ada.exceptions.AdaFrameworkException;

import java.io.File;

public class DataContext extends ObjectContext {

    final static String DATABASE_FOLDER  = "%s/db/";
    final static String DATABASE_NAME    = "kaushal1.db";
    final static int    DATABASE_VERSION = 1;

    public ObjectSet<TableCart> userObjectSet;


    public DataContext(Context pContext) {
        super(pContext, DATABASE_NAME, DATABASE_VERSION);
        // super(pContext, String.format("%s%s", getDataBaseFolder(), DATABASE_NAME), DATABASE_VERSION);
        initializeContext();
    }

    @Override
    protected void onPopulate(SQLiteDatabase pDatabase, int action) {
        try {
            //AppLogger.info("On DB Populate:" + action);
        }
        catch (Exception e) {
            // ExceptionsHelper.manage(getContext(), e);
        }
    }

    @Override
    protected void onError(Exception pException) {
        //ExceptionsHelper.manage(getContext(), pException);
    }

    private void initializeContext() {
        try {
            initializeObjectSets();

            //Enable DataBase Transactions to be used by the Save process.
            this.setUseTransactions(true);

            //Enable the creation of DataBase table indexes.
            this.setUseTableIndexes(true);

            //Enable LazyLoading capabilities.
            //this.useLazyLoading(true);

            //Set a custom encryption algorithm.
            this.setEncryptionAlgorithm("AES");

            //Set a custom encryption master pass phrase.
            this.setMasterEncryptionKey("com.celebso.app.items");

            //Initialize ObjectSets instances.
//            initializeObjectSets();

        } catch (Exception e) {
            //ExceptionsHelper.manage(e);
        }
    }

    private static String getDataBaseFolder() {
        String folderPath = "";
        try {
            folderPath = String.format(DATABASE_FOLDER, Environment.getExternalStorageDirectory().getAbsolutePath());
            File dbFolder = new File(folderPath);
            if (!dbFolder.exists()) {
                dbFolder.mkdirs();
            }
        } catch (Exception e) {
            //ExceptionsHelper.manage(e);
        }
        return folderPath;
    }

    private void initializeObjectSets() throws AdaFrameworkException {

        userObjectSet=new ObjectSet<TableCart>(TableCart.class,this);

    }
}

