package com.example.admin_voca;

import static android.text.TextUtils.split;

import android.widget.Toast;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;

public class voca_add_c {
private String word;
private  String meaning;

public void setWord(String word){this.word=word;}
public void setMeaning(String meaning){this.meaning=meaning;}
    public void Add_Voca(String English, String Meaning, String difficultdata){
    Voca_E voca = new Voca_E();
    voca.setWord(English);
    voca.setDifficulty(difficultdata);
        try {
            FileWriter fw = new FileWriter("/data/data/com.example.admin_voca/words.txt",true);
            BufferedWriter bufwr = new BufferedWriter(fw) ;
            bufwr.write("\n"+voca.getWord() + "/" + Meaning + "/0/0/" + voca.getDifficulty());
            bufwr.close();
            fw.close();
        }catch(Exception e){
            File file = new File("/data/data/com.example.admin_voca/words.txt");
            e.printStackTrace();
        }
    }
}
