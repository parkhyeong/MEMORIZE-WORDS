package com.example.admin_voca.Function;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DB_Sign_UP_C extends SQLiteOpenHelper {
    public static final String tableName = "Users";
    public static final String adminTable = "Admin";
    public DB_Sign_UP_C(Context context, String name, SQLiteDatabase.CursorFactory factory, int version){
        super(context, name, factory, version);
    }
    public void onCreate(SQLiteDatabase db) {
        Log.i("tag","db 생성_db가 없을때만 최초로 실행함");
        createTable(db);

    }
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){

    }
    public void createTable(SQLiteDatabase db){
        String sql = "create table "+ tableName + "(uId text, uPw text," +
                "uName text, uTel text, uBirth text, uNickName text, uEmail text," +
                "uTargetScore int, H_Score real, M_Score real, L_Score real)";
        String sql1 = "create table "+ adminTable + "(aId text, aPw text)";
        try {
            db.execSQL(sql);
            db.execSQL(sql1);
        }catch (SQLException e){
        }
    }
    public void insertUser(SQLiteDatabase db, String id, String pw, String name, String tel, String birth,
                           String nickname, String email, String targetscore){
        Log.i("tag","회원가입을 했을때 실행함");
        db.beginTransaction();
        try {
            String sql = "INSERT INTO " + tableName + "(uId, uPw, uName, uTel, uBirth, " +
                    "uNickName, uEmail, uTargetScore)" + "values('"+ id +"', '"+pw+"', '"+name+"', '"+tel+"'," +
                    "'"+birth+"', '"+nickname+"', '"+email+"', '"+targetscore+"' )";
            db.execSQL(sql);
            db.setTransactionSuccessful();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            db.endTransaction();
        }
    }
    public void insertAdmin(SQLiteDatabase db, String id, String pw){
        db.beginTransaction();
        try {
            String sql = "INSERT INTO " + adminTable + "(aId, aPw)" + "values('"+ id +"', '"+pw+"')";
            db.execSQL(sql);
            db.setTransactionSuccessful();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            db.endTransaction();
        }
    }
    public void updateData(SQLiteDatabase db, String id,double H_Score, double M_Score, double L_Score){
        db.beginTransaction();
        try {
            String sqlUpdate = "UPDATE tableName set H_Score" +H_Score+ "set M_Score"+M_Score+"set L_Score WHERE uId="+id;
            db.execSQL(sqlUpdate);
            db.setTransactionSuccessful();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            db.endTransaction();
        }
    }
}

