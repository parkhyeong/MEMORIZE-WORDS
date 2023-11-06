package com.example.admin_voca;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;

public class voca_add extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.voca_add_form);

        Button Go_Back = (Button) findViewById(R.id.Go_Back);
        Go_Back.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                finish();
            }
        });

        Button add_voca = (Button) findViewById(R.id.save_button);
        add_voca.setOnClickListener(new View.OnClickListener() {

            @SuppressLint("ResourceType")
            @Override
            public void onClick(View v) {
                TextView E_textView = (TextView) findViewById(R.id.english_word);
                TextView M_textView = (TextView) findViewById(R.id.word_meaning);
                final RadioGroup rg = (RadioGroup) findViewById(R.id.radioGroup);
                String English = E_textView.getText().toString();
                String Meaning = M_textView.getText().toString();


                if (English.equals("")) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(voca_add.this);
                    builder.setTitle("오류");
                    builder.setMessage("영단어나 단어뜻은 빈칸으로 입력할 수 없습니다.");
                    builder.setPositiveButton("예", null);
                    builder.create().show();
                } else if (Meaning.equals("")) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(voca_add.this);
                    builder.setTitle("오류");
                    builder.setMessage("영단어나 단어뜻은 빈칸으로 입력할 수 없습니다.");
                    builder.setPositiveButton("예", null);
                    builder.create().show();
                } else {
                    int id = rg.getCheckedRadioButtonId();
                    RadioButton rb = (RadioButton) findViewById(id);
                    String difficult = rb.getText().toString();
                    String difficultdata = "";
                    if (difficult.equals("난이도 하")) {
                        difficultdata = "★";
                    } else if (difficult.equals("난이도 중")) {
                        difficultdata = "★★";
                    } else if (difficult.equals("난이도 상")) {
                        difficultdata = "★★★";
                    } else {
                        difficultdata = "NULL";
                    }

                    voca_add_c vac = new voca_add_c();
                    vac.Add_Voca(English, Meaning, difficultdata);
                    Toast.makeText(getApplicationContext(), "단어추가가 완료되었습니다.", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }
        });

    }
    @Override
    public boolean onKeyDown(int keycode, KeyEvent event) {
        AlertDialog.Builder builder = new AlertDialog.Builder(voca_add.this);
        builder.setTitle("뒤로가기");
        builder.setMessage("정말 영단어 추가를 취소하시겠습니까?");
        builder.setPositiveButton("예", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });
        builder.setNegativeButton("아니요",null);
        builder.create().show();
        return true;
    }
}

