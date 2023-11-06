package com.example.admin_voca;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class Main_Page_Form extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_page_form);

        TextView un = (TextView) findViewById(R.id.loginSuccess_T_MP);

        Intent intent = getIntent();
        User_E user = (User_E) intent.getSerializableExtra("user");
        String name =user.getUid();

        un.setText(name+"님 환영합니다.");
        Button eScore = (Button) findViewById(R.id.ExpectS);
        Button wNote = (Button) findViewById(R.id.wrongnote);
        Button eInfo = (Button) findViewById(R.id.EDIT_INFO);
        Button withdraw = (Button) findViewById(R.id.Withdraw);
        Button logout = (Button) findViewById(R.id.logout_btn_MP);
        Button word_test = (Button) findViewById(R.id.TEST_btn_MP);
        Button word_note = (Button) findViewById(R.id.NOTE_btn_MP);

        eScore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast toast = Toast.makeText(Main_Page_Form.this, "미구현 기능입니다.", Toast.LENGTH_SHORT);
                toast.show();
            }
        });
        wNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast toast = Toast.makeText(Main_Page_Form.this, "미구현 기능입니다.", Toast.LENGTH_SHORT);
                toast.show();
            }
        });
        eInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast toast = Toast.makeText(Main_Page_Form.this, "미구현 기능입니다.", Toast.LENGTH_SHORT);
                toast.show();
            }
        });
        withdraw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast toast = Toast.makeText(Main_Page_Form.this, "미구현 기능입니다.", Toast.LENGTH_SHORT);
                toast.show();
            }
        });
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { onBackPressed(); }
        });
        word_test.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), com.example.admin_voca.Voca_Test_Form.class);
                intent.putExtra("user", user);
                startActivity(intent);
            }
        });
        word_note.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Voca_Note_Form.class );
                startActivity(intent);
            }
        });

    }


    public void onBackPressed(){
        AlertDialog.Builder builder = new AlertDialog.Builder(Main_Page_Form.this);
        builder.setTitle("로그아웃 하시겠습니까?");
        builder.setCancelable(false);
        builder.setPositiveButton("예", new AlertDialog.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(Main_Page_Form.this, Login_Form.class);
                startActivity(intent);
            }
        });
        builder.setNeutralButton("취소", new AlertDialog.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.show();
    }
}
