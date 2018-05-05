package com.example.jarvis.geoquiz;

public class Question {
    private int id;
    private boolean answear;

    public Question(int id, boolean answear) {
        this.id = id;
        this.answear = answear;
    }

    public int getId() {
        return id;
    }

    public boolean isAnswear() {
        return answear;
    }

    public void setAnswear(boolean answear) {
        this.answear = answear;
    }
}
