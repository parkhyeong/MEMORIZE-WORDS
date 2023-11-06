package com.example.admin_voca;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.admin_voca.Function.DB_Sign_UP_C;

public class Sign_Up_Form extends AppCompatActivity {
    int version = 1;
    DB_Sign_UP_C helper;
    SQLiteDatabase database;
    String sql;
    Cursor cursor;
    EditText user_id, user_pw, user_name, user_tel, user_birth, user_nickname, user_email, user_targetS;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_up_form);
        user_id = (EditText) findViewById(R.id.ID_TB_R);
        user_pw = (EditText) findViewById(R.id.PW_TB_R);
        user_name = (EditText) findViewById(R.id.Name_TB_R);
        user_tel = (EditText) findViewById(R.id.tel_TB_R);
        user_birth = (EditText) findViewById(R.id.birth_TB_R);
        user_nickname = (EditText) findViewById(R.id.nickname_TB_R);
        user_email = (EditText) findViewById(R.id.email_TB_R);
        user_targetS = (EditText) findViewById(R.id.targetS_TB_R);

        Button insert_btn = (Button)findViewById(R.id.regist_btn_R);
        helper = new DB_Sign_UP_C(Sign_Up_Form.this, DB_Sign_UP_C.tableName, null, version);
        database = helper.getWritableDatabase();
        insert_btn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v){
                String id = user_id.getText().toString();
                String pw = user_pw.getText().toString();
                String name = user_name.getText().toString();
                String tel = user_tel.getText().toString();
                String birth = user_birth.getText().toString();
                String nickname = user_nickname.getText().toString();
                String email = user_email.getText().toString();
                String targetS = user_targetS.getText().toString();

                if(id.length() == 0 || pw.length() == 0 || name.length() == 0 ||
                nickname.length() == 0 || email.length() == 0) {
                    Toast toast = Toast.makeText(Sign_Up_Form.this, "필수입력 사항이 다 입력되지 않았습니다.", Toast.LENGTH_SHORT);
                    toast.show();
                    return;
                }
                sql = "SELECT uId FROM "+ helper.tableName + " WHERE uId = '" + id + "'";
                cursor = database.rawQuery(sql, null);
                if(cursor.getCount() != 0){
                    //존재하는 아이디입니다.
                    Toast toast = Toast.makeText(Sign_Up_Form.this, "존재하는 아이디입니다.", Toast.LENGTH_SHORT);
                    toast.show();
                }else{
                    helper.insertUser(database,id,pw, name, tel, birth, nickname, email, targetS);
                    Toast toast = Toast.makeText(Sign_Up_Form.this, "가입이 완료되었습니다. 로그인을 해주세요.", Toast.LENGTH_SHORT);
                    toast.show();
                    Intent intent = new Intent(getApplicationContext(), Login_Form.class);
                    startActivity(intent);
                    finish();
                }
            }
        });
    }
    @Override
    public void onBackPressed(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("회원가입을 취소하면 입력한 데이터는 저장되지 않습니다. 정말로 종료하시겠십까?");
        builder.setCancelable(false);
        builder.setPositiveButton("예", new AlertDialog.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                EditText id = (EditText) findViewById(R.id.ID_TB_R);
                EditText pw = (EditText) findViewById(R.id.PW_TB_R);
                EditText name = (EditText) findViewById(R.id.Name_TB_R);
                EditText tel = (EditText) findViewById(R.id.tel_TB_R);
                EditText birth = (EditText) findViewById(R.id.birth_TB_R);
                EditText nickname = (EditText) findViewById(R.id.nickname_TB_R);
                EditText email = (EditText) findViewById(R.id.email_TB_R);
                EditText targets = (EditText) findViewById(R.id.targetS_TB_R);
                id.setText("");
                pw.setText("");
                name.setText("");
                tel.setText("");
                birth.setText("");
                nickname.setText("");
                email.setText("");
                targets.setText("");
                back();
            }
        });
        builder.setNeutralButton("취소", new AlertDialog.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.show();
    }
    public void back() { // 종료
        super.onBackPressed();
    }
}
