package com.example.admin_voca;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.admin_voca.Function.DB_Sign_UP_C;

public class Login_Form extends AppCompatActivity {
    int version = 1;
    DB_Sign_UP_C helper;
    SQLiteDatabase database;
    EditText uid;
    EditText upw;
    RadioButton userl;
    RadioButton adminl;
    RadioGroup ua_login;
    Button Button_Login;
    String sql;
    Cursor cursor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_form);

        uid = (EditText) findViewById(R.id.ID_Input);
        upw = (EditText) findViewById(R.id.PW_INPUT);
        Button_Login = (Button) findViewById(R.id.login_btn);
        userl = (RadioButton)findViewById(R.id.user_l_radio);
        adminl = (RadioButton)findViewById(R.id.admin_l_radio);
        ua_login = (RadioGroup)findViewById(R.id.login_radio_G);
        helper = new DB_Sign_UP_C(Login_Form.this, DB_Sign_UP_C.tableName, null, version);
        database = helper.getWritableDatabase();

        String AdminID = "Admin";
        String AdminPW = "1234";
        sql = "SELECT aId FROM " + helper.adminTable + " WHERE aId = '" + AdminID + "'";
        cursor = database.rawQuery(sql, null);
        if (cursor.getCount() != 1) {
            helper.insertAdmin(database,AdminID, AdminPW);
        }

        ua_login.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if(checkedId == R.id.user_l_radio)
                    Toast.makeText(Login_Form.this, "회원 로그인",Toast.LENGTH_SHORT).show();
                else if(checkedId == R.id.admin_l_radio)
                    Toast.makeText(Login_Form.this, "관리자 로그인",Toast.LENGTH_SHORT).show();
            }
        });
        Button_Login.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                String id = uid.getText().toString();
                String pw = upw.getText().toString();
                if (userl.isChecked() == true) {
                    if (id.length() == 0 || pw.length() == 0) {
                        //아이디와 비밀번호는 필수 입력사항입니다.
                        Toast toast = Toast.makeText(Login_Form.this, "아이디와 비밀번호는 필수 입력사항입니다.", Toast.LENGTH_SHORT);
                        toast.show();
                        return;
                    }
                    sql = "SELECT uId FROM " + helper.tableName + " WHERE uId = '" + id + "'";
                    cursor = database.rawQuery(sql, null);

                    if (cursor.getCount() != 1) {
                        //아이디가 틀렸습니다.
                        Toast toast = Toast.makeText(Login_Form.this, "존재하지 않는 아이디입니다.", Toast.LENGTH_SHORT);
                        toast.show();
                        return;
                    }

                    sql = "SELECT uPw FROM " + helper.tableName + " WHERE uId = '" + id + "'";
                    cursor = database.rawQuery(sql, null);

                    cursor.moveToNext();
                    if (!pw.equals(cursor.getString(0))) {
                        //비밀번호가 틀렸습니다.
                        Toast toast = Toast.makeText(Login_Form.this, "비밀번호가 틀렸습니다.", Toast.LENGTH_SHORT);
                        toast.show();
                    } else {
                        sql = "SELECT * FROM " + helper.tableName + " WHERE uId = '" + id + "'";
                        cursor = database.rawQuery(sql, null);
                        cursor.moveToNext();
                        String uid = cursor.getString(cursor.getColumnIndex("uId"));
                        String upw = cursor.getString(cursor.getColumnIndex("uPw"));
                        String uname = cursor.getString(cursor.getColumnIndex("uName"));
                        String ubirth = cursor.getString(cursor.getColumnIndex("uBirth"));
                        String utel = cursor.getString(cursor.getColumnIndex("uTel"));
                        String unickname = cursor.getString(cursor.getColumnIndex("uNickName"));
                        String utargetscore = cursor.getString(cursor.getColumnIndex("uTargetScore"));
                        String uemail = cursor.getString(cursor.getColumnIndex("uEmail"));
                        User_E user_info = new User_E(uid,upw,uname,utel,ubirth,unickname,uemail,utargetscore);
                        Toast toast = Toast.makeText(Login_Form.this, "로그인성공", Toast.LENGTH_SHORT);
                        toast.show();
                        //인텐트 생성 및 호출
                        Intent intent = new Intent(getApplicationContext(), com.example.admin_voca.Main_Page_Form.class);
                        intent.putExtra("user", user_info);
                        startActivity(intent);
                        finish();
                    }
                    cursor.close();
                }
                else if(adminl.isChecked() == true) {
                    if (id.length() == 0 || pw.length() == 0) {
                        //아이디와 비밀번호는 필수 입력사항입니다.
                        Toast toast = Toast.makeText(Login_Form.this, "아이디와 비밀번호는 필수 입력사항입니다.", Toast.LENGTH_SHORT);
                        toast.show();
                        return;
                    }

                    sql = "SELECT aId FROM " + helper.adminTable + " WHERE aId = '" + id + "'";
                    cursor = database.rawQuery(sql, null);

                    if (cursor.getCount() != 1) {
                        //아이디가 틀렸습니다.
                        Toast toast = Toast.makeText(Login_Form.this, "존재하지 않는 아이디입니다.", Toast.LENGTH_SHORT);
                        toast.show();
                        return;
                    }

                    sql = "SELECT aPw FROM " + helper.adminTable + " WHERE aId = '" + id + "'";
                    cursor = database.rawQuery(sql, null);

                    cursor.moveToNext();
                    if (!pw.equals(cursor.getString(0))) {
                        //비밀번호가 틀렸습니다.
                        Toast toast = Toast.makeText(Login_Form.this, "비밀번호가 틀렸습니다.", Toast.LENGTH_SHORT);
                        toast.show();
                    } else {
                        //로그인성공
                        sql = "SELECT * FROM " + helper.adminTable + " WHERE aId = '" + id + "'";
                        cursor = database.rawQuery(sql, null);
                        cursor.moveToNext();
                        String adname = cursor.getString(cursor.getColumnIndex("aId"));
                        Toast toast = Toast.makeText(Login_Form.this, "관리자 로그인", Toast.LENGTH_SHORT);
                        toast.show();
                        //인텐트 생성 및 호출
                        Intent intent = new Intent(getApplicationContext(), main_admin.class );
                        startActivity(intent);
                        finish();
                    }
                    cursor.close();
                }
            }
        });
        Button Sign_Up = (Button) findViewById(R.id.register);
        Sign_Up.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                uid.setText("");
                upw.setText("");
                Intent intent = new Intent(Login_Form.this, com.example.admin_voca.Sign_Up_Form.class);
                startActivity(intent);
            }
        });
        Button find = (Button) findViewById(R.id.find_id_pw);
        find.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast toast = Toast.makeText(Login_Form.this, "미구현 기능입니다.", Toast.LENGTH_SHORT);
                toast.show();
            }
        });

    }
    @Override
    public void onBackPressed(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("종료하시겠습니까?");
        builder.setCancelable(false);
        builder.setPositiveButton("예", new AlertDialog.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                exit();
            }
        });
        builder.setNeutralButton("취소", new AlertDialog.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.show();
    }
    public void exit() { // 종료
        super.onBackPressed();
    }
}
