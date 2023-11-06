package com.example.admin_voca;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.admin_voca.Function.DB_Sign_UP_C;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class Voca_Test_Form extends AppCompatActivity {
    int version = 1;
    DB_Sign_UP_C helper;
    SQLiteDatabase database;

        @Override
        protected void onCreate (Bundle savedInstanceState){
            super.onCreate(savedInstanceState);
            Intent intent = getIntent();
            User_E user = (User_E) intent.getSerializableExtra("user");
            String id = user.getUid();


            List<Voca_E> vocaWords = new ArrayList<Voca_E>();

            setContentView(R.layout.activity_main);

            Button button_next = findViewById(R.id.button_next); //다음버튼
            Button button_result= findViewById(R.id.button_result);
            button_result.setVisibility(View.INVISIBLE); //결과버튼 비활성화

            EditText text_in= findViewById(R.id.text_in);   //텍스트 입력

            CheckBox checkBox_showAnswerRate=findViewById(R.id.checkBox_showAnswerRate);

            TextView question_number =findViewById(R.id.question_number);

            TextView textView_difficulty = findViewById(R.id.textView_difficulty); //난이도
            TextView textView_word = findViewById(R.id.textView_word);   //단어
            //TextView textView_meaning = findViewById(R.id.textView_meaning); //단어뜻

            TextView textView_answerRate = findViewById(R.id.textView_answerRate); //정답률
            textView_answerRate.setVisibility(View.INVISIBLE); //정답률 뷰 비활성화

            Button exit_button = findViewById(R.id.exit_button);
            int[] wordCursor = {0, 0};   // [0]는 단어 목록에서의 위치
            // [1]은 현재 선택된 난이도 (선택안함:0, 하:1, 중:2, 상:3)

            int [] count = {0}; //맞춘개수를 셈

            try {
                Scanner scanner = new Scanner(new File("/data/data/com.example.admin_voca/words.txt"));
                // 파일에 저장되는 형식은 영어/뜻1,뜻2,뜻,...,뜻n/출제횟수/틀린횟수/난이도(★~★★★)

                while (scanner.hasNextLine()) {
                    String[] line = scanner.nextLine().split("/");
                    Voca_E vocaWord = new Voca_E();
                    vocaWord.setWord(line[0]); //한 단어 저장
                    vocaWord.setwMean(line[1].split(",")); //하나의 단어뜻 저장
                    // 단어 뜻이 여러개일 경우를 고려해 구분자 ','로 한번 더 분리

                    vocaWord.setqTimes(Integer.parseInt(line[2]));
                    vocaWord.setwTimes(Integer.parseInt(line[3]));
                    vocaWord.setDifficulty(line[4]);
                    vocaWords.add(vocaWord); //arraylist추가
                }
                scanner.close();

            } catch (FileNotFoundException e) {
                Toast.makeText(Voca_Test_Form.this, e.toString(), Toast.LENGTH_LONG).show();
                // 파일을 여는 과정에서 오류가 발생하면 토스트 메시지를 띄움
            }
            //Collections.shuffle(vocaWords); //list 섞기

            //랜덤함수
            int a[]=new int[20];
            Random r= new Random();

            for(int i=0; i<20; i++)
            {
                a[i]=r.nextInt(vocaWords.size());
                for(int j=0; j<i; j++)
                {
                    if(a[i]==a[j])
                    {
                        i--;
                    }
                }
            }

            Voca_E currentVocaWord = vocaWords.get(a[0]); //현재 단어 위치
            textView_word.setText(currentVocaWord.getWord());


            textView_answerRate.setText("정답률 " + ((int) ((1.0 - (float) currentVocaWord.getwTimes() / (float) currentVocaWord.getqTimes()) * 100)) + "%");
            textView_difficulty.setText(currentVocaWord.getDifficulty());



            button_next.setOnClickListener(new View.OnClickListener() {
                int[] wordCursor;
                Voca_E currentVocaWord = new Voca_E();

                Wrong_Answer_E currentwrongWord = new Wrong_Answer_E();

                Voca_Test_Wrongwords_Store Voca_Test_Wrongwords_Store =new Voca_Test_Wrongwords_Store();

                AlertDialog.Builder check_box = new AlertDialog.Builder(Voca_Test_Form.this);

                int i = 1; //문항
                int [] count; //맞춘갯수 카운트
                int check = 0; //정답이면 1, 틀리면 0
                int z=0; //처음으로 버튼클릭=0 ,두번째부터 1

                @Override
                public void onClick(View v) {


                    //처음으로버튼눌렀는지 검사
                    if (z==0) {
                        currentVocaWord = vocaWords.get(a[0]);
                        z=1;
                    }

                    if(text_in.getText().toString().replace(" ", "").equals("")) {
                        Toast.makeText(Voca_Test_Form.this,"정답을 입력해주세요.", Toast.LENGTH_LONG).show();
                        return;
                    }

                    //정답인지 확인
                    for (int i = 0; i < currentVocaWord.getwMean().length; i++) {
                        if(text_in.getText().toString().equals(currentVocaWord.getwMean()[i])) {
                            count[0]+=1;
                            check=1;
                            break;
                        }
                    }


                    //단어뜻 스트링 저장
                    String s=currentVocaWord.getwMean()[0];
                    for (int i = 1; i< currentVocaWord.getwMean().length; i++) {

                        s = s +", "+currentVocaWord.getwMean()[i];
                        }

                    if (check==1) { //정답처리
                        currentVocaWord.setqTimes(currentVocaWord.getqTimes()+1); //출제횟수 증가
                        check_box.setTitle("정답입니다."); //제목
                        check_box.setMessage(currentVocaWord.getWord()+"\n"+ s); // 메시지
                        check=0;
                    }

                    else { //틀린단어처리
                        currentVocaWord.setqTimes(currentVocaWord.getqTimes()+1); //출제횟수 증가
                        currentVocaWord.setwTimes(currentVocaWord.getwTimes()+1); //오답횟수 증가


                        //오답노트 저장
                        Voca_Test_Wrongwords_Store.store(id, s, currentwrongWord, currentVocaWord);
                        /*
                        try {
                            //오답노트 저장
                            FileWriter fout = new FileWriter("/data/data/com.example.admin_voca/wrongwords.txt",true);
                            BufferedReader br = new BufferedReader(new FileReader("/data/data/com.example.admin_voca/wrongwords.txt"));
                            String sr=s.replace(" ", ""); //틀린단어 뜻을 저장하기위해 띄어쓰기를 없앰

                            currentwrongWord.setWord(currentVocaWord.getWord()); //틀린단어를 wrong_Answer_E에 담음
                            currentwrongWord.setwMean(currentVocaWord.getwMean()); //틀린뜻을 wrong_Answer_E에 담음

                            String line;

                            //오답노트파일에 데이터 중복제거
                            while (true) {
                                while ((line = br.readLine()) != null) {
                                    line=line.substring(0,line.indexOf("/")); //파일저장된 한영단어읽기
                                    if (currentwrongWord.getWord().equals(line)) {
                                        break;   //오답노트에 중복된단어가 있으면 break로 빠져나옴

                                    }
                                }

                                if (currentwrongWord.getWord().equals(line)) {  //중복된단어는 완전히 while에서 빠져나옴
                                    break;
                                }
                                    fout.write(currentwrongWord.getWord() + "/" + sr + "\n");
                                        break;
                            }

                            fout.close();
                            br.close();





                        }catch(IOException e){
                            Toast.makeText(Voca_Test_Form.this, e.toString(), Toast.LENGTH_LONG).show();
                        }
                        */

                        check_box.setTitle("오답입니다."); //제목
                        check_box.setMessage(currentVocaWord.getWord() + "\n" + s); // 메시지

                    }

                    //대화로그 표시후 확인버튼 누를시 다음단어 넘어감
                    check_box.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            i+=1;   //문항 증가
                            question_number.setText(Integer.toString(i)+"번");


                            //문제 20개만 출력후 결과화면 생성
                            if(i==21) {
                                button_next.setVisibility(View.INVISIBLE);
                                button_result.setVisibility(View.VISIBLE);
                                textView_word.setText("수고하셨습니다.\n  당신의 점수는\n  두근두근~ ");
                                textView_difficulty.setVisibility(View.INVISIBLE);
                                textView_answerRate.setVisibility(View.INVISIBLE);
                                question_number.setVisibility(View.INVISIBLE);
                                text_in.setVisibility(View.INVISIBLE);
                                checkBox_showAnswerRate.setVisibility(View.INVISIBLE);
                                helper = new DB_Sign_UP_C(Voca_Test_Form.this, DB_Sign_UP_C.tableName, null, version);
                                database = helper.getWritableDatabase();
                                helper.updateData(database,id,count[0],count[0],count[0]);

                                //단어장 갱신 *렉원인
                                try{
                                    FileWriter qout = new FileWriter("/data/data/com.example.admin_voca/words.txt");

                                    for(int ct=0; ct<vocaWords.size(); ct++) {
                                        currentVocaWord=vocaWords.get(ct);
                                        String sr2 = currentVocaWord.getwMean()[0]; //단어뜻저장
                                        for (int ct2 = 1; i < currentVocaWord.getwMean().length; ct2++) {
                                            sr2= sr2 +","+currentVocaWord.getwMean()[ct2];
                                        }

                                        qout.write(currentVocaWord.getWord() + "/" + sr2 + "/" + currentVocaWord.getqTimes() + "/" +
                                                currentVocaWord.getwTimes() + "/" + currentVocaWord.getDifficulty() + "\n");
                                    }
                                    qout.close();

                                }catch(IOException e){
                                    Toast.makeText(Voca_Test_Form.this, "단어장갱신 오류", Toast.LENGTH_LONG).show();
                                }


                            }

                            if(i<21) { //20번문제때 버튼을 누르면 거기까지만 읽음, (쓸대없이 21번문제가 나오면서 종료되서 넣음)
                                currentVocaWord=vocaWords.get(a[i-1]); //커서값 위치로 현재위치변경
                                text_in.setText("");
                                textView_word.setText(currentVocaWord.getWord());

                                textView_answerRate.setText("정답률 " + ((int) ((1.0 - (float) currentVocaWord.getwTimes() /
                                        (float) currentVocaWord.getqTimes()) * 100)) + "%");
                                textView_difficulty.setText(currentVocaWord.getDifficulty());

                            }

                        }

                    });
                    check_box.setCancelable(false); //모달창
                    check_box.show();



                }

                //외부에있던 wordcursoer를 내부인 nextbutton으로 넘김
                public View.OnClickListener setParams(int[] wordCursor, int [] count) {
                    this.wordCursor = wordCursor;
                    this.count=count;
                    return this;
                }
            }.setParams(wordCursor,count));


/*
                    Toast.makeText(MainActivity.this,count+"확인을 눌르셨습니다.",Toast.LENGTH_SHORT).show();
 */

            checkBox_showAnswerRate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (checkBox_showAnswerRate.isChecked()) {
                        textView_answerRate.setVisibility(View.VISIBLE);
                    }
                    else {
                        textView_answerRate.setVisibility(View.INVISIBLE);
                    }
                }
            });

            //결과보기 버튼 이벤트
            button_result.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertDialog.Builder dlg = new AlertDialog.Builder(Voca_Test_Form.this);
                    dlg.setTitle("결과"); //제목
                    dlg.setMessage("점수: " + (count[0]*100/20)+"점\n" +
                            "문제수: 20"+"\n"
                    +"맞춘개수: "+count[0]+"\n"+
                            "틀린개수: "+ (20-count[0])); // 메시지
                    dlg.show();

                }
            });

            exit_button.setOnClickListener(new View.OnClickListener(){
                public void onClick(View v) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(Voca_Test_Form.this);
                    builder.setTitle("정말 시험을 포기하고 나가겠습니까?");
                    builder.setCancelable(false);
                    builder.setPositiveButton("예", new AlertDialog.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            finish();
                        }
                    });
                    builder.setNeutralButton("아니요", new AlertDialog.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });
                    builder.show();
                }

            });
        }
        public void onBackPressed(){
        AlertDialog.Builder builder = new AlertDialog.Builder(Voca_Test_Form.this);
        builder.setTitle("정말 시험을 포기하고 나가겠습니까?");
        builder.setCancelable(false);
        builder.setPositiveButton("예", new AlertDialog.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });
        builder.setNeutralButton("아니요", new AlertDialog.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.show();
    }


}
