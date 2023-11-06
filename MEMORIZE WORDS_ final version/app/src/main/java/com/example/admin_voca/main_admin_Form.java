package com.example.admin_voca;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.content.Intent;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;


public class main_admin_Form extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_admin_form);

        //영단어 추가 버튼 클릭시 액티비티 전환
        Button add_button = (Button) findViewById(R.id.voca_add_button);
        add_button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), voca_add.class );
                startActivity(intent);
            }
        });

        //영단어 제거 버튼 클릭시 미구현 출력
        Button del_add_button = (Button) findViewById(R.id.voca_del_button);
        del_add_button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "미구현 기능입니다.", Toast.LENGTH_SHORT).show();
            }
        });

        //회원정보 조회 버튼 클릭시 미구현 출력
        Button information = (Button) findViewById(R.id.information_inquiry_button);
        information.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "미구현 기능입니다.", Toast.LENGTH_SHORT).show();
            }
        });

        //단어장 버튼 클릭시 미구현 출력
        Button voca = (Button) findViewById(R.id.voca_note);
        voca.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "미구현 기능입니다.", Toast.LENGTH_SHORT).show();
            }
        });

        //로그아웃 버튼 클릭시 액티비티 전환
        Button logout = (Button) findViewById(R.id.Logout);
        logout.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(main_admin_Form.this);
                builder.setTitle("로그아웃");
                builder.setMessage("정말 로그아웃 하시겠습니까?");
                builder.setPositiveButton("예", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(main_admin_Form.this, Login_Form.class);
                        startActivity(intent);
                    }
                });
                builder.setNegativeButton("아니요",null);
                builder.create().show();
            }
        });
    }
    @Override
    public boolean onKeyDown(int keycode, KeyEvent event) {
        AlertDialog.Builder builder = new AlertDialog.Builder(main_admin_Form.this);
        builder.setTitle("로그아웃");
        builder.setMessage("정말 로그아웃 하시겠습니까?");
        builder.setPositiveButton("예", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(main_admin_Form.this, Login_Form.class);
                startActivity(intent);
            }
        });
        builder.setNegativeButton("아니요",null);
        builder.create().show();
        return true;
    }
}