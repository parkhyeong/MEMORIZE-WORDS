package com.example.admin_voca;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Voca_Note_Form extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        List<Voca_E> vocaWords = new ArrayList<Voca_E>();

        setContentView(R.layout.activity_voca_note);

        Button button_next = findViewById(R.id.button_next);
        Button button_back=findViewById(R.id.button_back);
        Button button_showList=findViewById(R.id.button_showList);
        Button button_goBack=findViewById(R.id.button_goBack);

        CheckBox checkBox_showAnswerRate=findViewById(R.id.checkBox_showAnswerRate);

        RadioButton radioButton_H=findViewById(R.id.radioButton_H);
        RadioButton radioButton_M=findViewById(R.id.radioButton_M);
        RadioButton radioButton_E=findViewById(R.id.radioButton_E);

        TextView textView_difficulty=findViewById(R.id.textView_difficulty);
        TextView textView_word=findViewById(R.id.textView_word);
        TextView textView_meaning=findViewById(R.id.textView_meaning);
        TextView textView_answerRate=findViewById(R.id.textView_answerRate);

        int[] wordCursor = {0,0};   // [0]는 단어 목록에서의 위치
                                    // [1]은 현재 선택된 난이도 (선택안함:0, 하:1, 중:2, 상:3)

        try {
            Scanner scanner = new Scanner(new File("/data/data/com.example.admin_voca/words.txt"));
            // 파일에 저장되는 형식은 영어/뜻1,뜻2,뜻,...,뜻n/출제횟수/틀린횟수/난이도(★~★★★)

            while (scanner.hasNextLine()) {
                String[] line = scanner.nextLine().split("/");
                Voca_E vocaWord = new Voca_E();
                vocaWord.setWord(line[0]);
                vocaWord.setwMean(line[1].split(","));
                // 단어 뜻이 여러개일 경우를 고려해 구분자 ','로 한번 더 분리
                vocaWord.setqTimes(Integer.parseInt(line[2]));
                vocaWord.setwTimes(Integer.parseInt(line[3]));
                vocaWord.setDifficulty(line[4]);
                vocaWords.add(vocaWord);
            }
            scanner.close();

        } catch (FileNotFoundException e) {
            Toast.makeText(Voca_Note_Form.this, e.toString(), Toast.LENGTH_LONG).show();
            // 파일을 여는 과정에서 오류가 발생하면 토스트 메시지를 띄움
        }

        Voca_E currentVocaWord = vocaWords.get(wordCursor[0]);
        textView_word.setText(currentVocaWord.getWord());
        textView_meaning.setText(currentVocaWord.getwMean()[0]);
        for (int i = 1; i< currentVocaWord.getwMean().length; i++) {
            textView_meaning.setText(textView_meaning.getText()+", "+ currentVocaWord.getwMean()[i]);
        }
        textView_answerRate.setText("정답률 " + ((int)((1.0- (float)currentVocaWord.getwTimes()/ (float)currentVocaWord.getqTimes())*100)) + "%");
        textView_difficulty.setText(currentVocaWord.getDifficulty());

        button_next.setOnClickListener(new View.OnClickListener() {
            int[] wordCursor;
            Voca_E currentVocaWord = new Voca_E();

            @Override
            public void onClick(View v) {
                while (true) {
                    if (++wordCursor[0] > vocaWords.size()-1) {
                        wordCursor[0] = 0;
                    }
                    currentVocaWord = vocaWords.get(wordCursor[0]);

                    if (wordCursor[1] == currentVocaWord.getDifficulty().length() || wordCursor[1] == 0) {
                        textView_word.setText(currentVocaWord.getWord());
                        textView_meaning.setText(currentVocaWord.getwMean()[0]);
                        for (int i = 1; i < currentVocaWord.getwMean().length; i++) {
                            textView_meaning.setText(textView_meaning.getText() + ", " + currentVocaWord.getwMean()[i]);
                        }
                        textView_answerRate.setText("정답률 " + ((int)((1.0- (float)currentVocaWord.getwTimes()/ (float)currentVocaWord.getqTimes())*100)) + "%");
                        textView_difficulty.setText(currentVocaWord.getDifficulty());
                        break;
                    }
                }
            }

            public View.OnClickListener setParams(int[] wordCursor) {
                this.wordCursor = wordCursor;
                return this;
            }
        }.setParams(wordCursor));

        button_back.setOnClickListener(new View.OnClickListener() {
            int[] wordCursor;
            Voca_E currentVocaWord = new Voca_E();

            @Override
            public void onClick(View v) {
                while (true) {
                    if (--wordCursor[0] < 0) {
                        wordCursor[0] = vocaWords.size() - 1;
                    }
                    currentVocaWord = vocaWords.get(wordCursor[0]);

                    if (wordCursor[1] == currentVocaWord.getDifficulty().length() || wordCursor[1] == 0) {
                        textView_word.setText(currentVocaWord.getWord());
                        textView_meaning.setText(currentVocaWord.getwMean()[0]);
                        for (int i = 1; i < currentVocaWord.getwMean().length; i++) {
                            textView_meaning.setText(textView_meaning.getText() + ", " + currentVocaWord.getwMean()[i]);
                        }
                        textView_answerRate.setText("정답률 " + ((int)((1.0- (float)currentVocaWord.getwTimes()/ (float)currentVocaWord.getqTimes())*100)) + "%");
                        textView_difficulty.setText(currentVocaWord.getDifficulty());
                        break;
                    }
                }
            }

            public View.OnClickListener setParams(int[] wordCursor) {
                this.wordCursor = wordCursor;
                return this;
            }
        }.setParams(wordCursor));

        button_showList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), Voca_Note_List_Form.class));
            }
        });

        button_goBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

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

        radioButton_H.setOnClickListener(new View.OnClickListener() {
            int[] wordCursor;

            @Override
            public void onClick(View v) {
                wordCursor[1] = 3;
            }
            public View.OnClickListener setParams(int[] wordCursor) {
                this.wordCursor = wordCursor;
                return this;
            }
        }.setParams(wordCursor));

        radioButton_M.setOnClickListener(new View.OnClickListener() {
            int[] wordCursor;

            @Override
            public void onClick(View v) {
                wordCursor[1] = 2;
            }
            public View.OnClickListener setParams(int[] wordCursor) {
                this.wordCursor = wordCursor;
                return this;
            }
        }.setParams(wordCursor));

        radioButton_E.setOnClickListener(new View.OnClickListener() {
            int[] wordCursor;

            @Override
            public void onClick(View v) {
                wordCursor[1] = 1;
            }
            public View.OnClickListener setParams(int[] wordCursor) {
                this.wordCursor = wordCursor;
                return this;
            }
        }.setParams(wordCursor));
    }

}

