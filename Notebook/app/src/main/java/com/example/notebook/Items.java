package com.example.notebook;

public class Items {
    private String word;
    private String sentence;

    public Items()
    {
        word="";
        sentence="";
    }
    public Items(String w,String s)
    {
        this.word=w;
        this.sentence=s;
    }

    public void setSentence(String sentence) {
        this.sentence = sentence;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public String getSentence() {
        return sentence;
    }

    public String getWord() {
        return word;
    }
}
