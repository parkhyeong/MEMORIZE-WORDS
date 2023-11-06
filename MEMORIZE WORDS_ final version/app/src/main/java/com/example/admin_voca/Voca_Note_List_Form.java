package com.example.admin_voca;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

public class Voca_Note_List_Form extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_voca_note_list);

        List<Voca_E> vocaWords = new ArrayList<Voca_E>();
        ArrayList<HashMap<String, String>> vocaNoteList = new ArrayList<HashMap<String, String>>();

        Button button_search = findViewById(R.id.button_search);
        Button button_goBack = findViewById(R.id.button_goBack);
        ListView listView_Word = findViewById(R.id.ListView_word);
        TextView editText_search = findViewById(R.id.editText_search);

        try {
            Scanner scanner = new Scanner(new File("/data/data/com.example.admin_voca/words.txt"));
            // 파일에 저장되는 형식은 영어/뜻1,뜻2,뜻,...,뜻n/출제횟수/틀린횟수/난이도

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

                String meanings = vocaWord.getwMean()[0];
                HashMap<String, String> voca = new HashMap<String, String>();
                for (int i=1; i<vocaWord.getwMean().length; i++) {
                    meanings += "," + vocaWord.getwMean()[i];
                }
                voca.put("word", vocaWord.getWord());
                voca.put("meaning", meanings);
                vocaNoteList.add(voca);
            }
            scanner.close();

        } catch (FileNotFoundException e) {
            Toast.makeText(Voca_Note_List_Form.this, e.toString(), Toast.LENGTH_LONG).show();
        }

        SimpleAdapter vocaAdapter = new SimpleAdapter(this, vocaNoteList,
                android.R.layout.simple_list_item_2, new String[] {"word", "meaning"},
                new int[] {android.R.id.text1, android.R.id.text2});
        listView_Word.setAdapter(vocaAdapter);



        button_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                vocaNoteList.clear();

                for (int i = 0; i < vocaWords.size(); i++) {
                    if (vocaWords.get(i).getWord().contains(editText_search.getText())) {
                        String meanings = vocaWords.get(i).getwMean()[0];
                        for (int j = 1; j < vocaWords.get(i).getwMean().length; j++) {
                            meanings += "," + vocaWords.get(i).getwMean()[j];
                        }
                        HashMap<String, String> voca = new HashMap<String, String>();
                        voca.put("word", vocaWords.get(i).getWord());
                        voca.put("meaning", meanings);
                        vocaNoteList.add(voca);
                    }
                }
                SimpleAdapter vocaAdapter = new SimpleAdapter(Voca_Note_List_Form.this, vocaNoteList,
                        android.R.layout.simple_list_item_2, new String[] {"word", "meaning"},
                        new int[] {android.R.id.text1, android.R.id.text2});
                listView_Word.setAdapter(vocaAdapter);

                //editText_search.setText("");
            }
        });
        button_goBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}

