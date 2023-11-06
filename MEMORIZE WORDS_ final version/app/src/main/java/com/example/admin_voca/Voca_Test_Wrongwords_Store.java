package com.example.admin_voca;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class Voca_Test_Wrongwords_Store{

    void store(String id, String s, Wrong_Answer_E currentwrongWord, Voca_E currentVocaWord){
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
                fout.write(id+"/"+currentwrongWord.getWord() +"/" + sr + "\n");
                break;
            }

            fout.close();
            br.close();





        }catch(IOException e){
            //Toast.makeText(Voca_Test_Form.this, e.toString(), Toast.LENGTH_LONG).show();
        }
    }
}
