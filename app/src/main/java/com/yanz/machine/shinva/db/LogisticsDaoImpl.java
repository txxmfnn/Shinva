package com.yanz.machine.shinva.db;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.yanz.machine.shinva.entity.SLogisticsPlan;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yanz on 2016-10-17.
 */

public class LogisticsDaoImpl {
    private DataBaseHelper dbHelper;
    public LogisticsDaoImpl(Context context){
        this.dbHelper = new DataBaseHelper(context);
    }
    public void save(SLogisticsPlan logisticsPlan){
        //插入记录
        SQLiteDatabase db = dbHelper.getWritableDatabase();//取得数据库操作
        db.execSQL("insert into s_logistics_plan(cplanCode,igxh,cpartName,cpartCode,fquantity,iautoId) values(?,?,?,?,?,?) ",
                new Object[]{logisticsPlan.getCplanCode(),logisticsPlan.getIgxh(),logisticsPlan.getCpartName(),logisticsPlan.getCpartCode(),logisticsPlan.getFquantity(),logisticsPlan.getIautoId()});
        Log.e("meng","保存的记录:"+logisticsPlan.getCplanCode()+"@@"+logisticsPlan.getCpartName());
        db.close();
    }
    public List<SLogisticsPlan> findAll(){
        //查找存储的全部物流计划
        List<SLogisticsPlan> list = new ArrayList<SLogisticsPlan>();
        SLogisticsPlan logisticsPlan = null;
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from s_logistics_plan",null);
        while (cursor.moveToNext()){
            logisticsPlan = new SLogisticsPlan();
            logisticsPlan.setCplanCode(cursor.getString(cursor.getColumnIndex("cplanCode")));
            logisticsPlan.setIgxh(cursor.getInt(cursor.getColumnIndex("igxh")));
            logisticsPlan.setCpartName(cursor.getString(cursor.getColumnIndex("cpartName")));
            logisticsPlan.setCpartCode(cursor.getString(cursor.getColumnIndex("cpartCode")));
            logisticsPlan.setFquantity(cursor.getDouble(cursor.getColumnIndex("fquantity")));
            list.add(logisticsPlan);
        }
        db.close();
        return list;
    }
    public void deleteAll(){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        String delSql = "delete from s_logistics_plan";
        db.execSQL(delSql);
        db.close();
    }
    public void deleteById(SLogisticsPlan item){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        String delSql = "delete from s_logistics_plan where cplanCode = '"+item.getCplanCode()
                +"' and igxh = "+item.getIgxh()+" and fquantity = "+item.getFquantity();
        System.out.println(delSql);
        db.execSQL(delSql);
        db.close();
    }

}
