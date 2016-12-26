package com.yanz.machine.shinva.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by yanz on 2016-10-17.
 */

public class DataBaseHelper extends SQLiteOpenHelper {
    final String CREATE_TABLE_SQL =
            "CREATE TABLE IF NOT EXISTS s_logistics_plan(iautoId integer primary key autoincrement,ipdid,cplanCode,igxh,cstatusFlag," +
                    "cpartCode,cpartName,fquantity,creporterCode,creporterName," +
                    "dtReportDate,cdepartmentCode,cdepartmentName,creciveDepartmentCode,creciveDepartmentName," +
                    "fdistance,fmodulus,fmz,cdeliverCode,cdeliverName,dtDeliveDate,creciverCode,creciverName," +
                    "dtReciveDate,cplanerCode,cplanerName,ftotalCount,cactReciveDepartmentCode,cactReciveDepartmentName,cboxNo  )";

    private static final String name ="yanz";
    private static final int version =1;
    public DataBaseHelper(Context context){
        super(context,name,null,version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_SQL);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        System.out.println("------onUpdate Called------"+oldVersion+"--->"+newVersion);
    }

    private static DataBaseHelper mInstance=null;

    public synchronized  static  DataBaseHelper getInstance(Context context){
        if (mInstance ==null){
            mInstance = new DataBaseHelper(context);
        }
        return mInstance;
    }
}
