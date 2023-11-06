package com.example.admin_voca;

public class Voca_E {
    private String word;
    private String[] wMean;
    private int qTimes;
    private int wTimes;
    private String difficulty;

    public String getWord() {
        return word;
    }

    public String[] getwMean() {
        return wMean;
    }

    public int getqTimes() {
        return qTimes;
    }

    public int getwTimes() {
        return wTimes;
    }

    public String getDifficulty() {
        return difficulty;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public void setwMean(String[] wMean) {
        this.wMean = wMean;
    }

    public void setqTimes(int qTimes) {
        this.qTimes = qTimes;
    }

    public void setwTimes(int wTimes) {
        this.wTimes = wTimes;
    }

    public void setDifficulty(String difficulty) {
        this.difficulty = difficulty;
    }
}
