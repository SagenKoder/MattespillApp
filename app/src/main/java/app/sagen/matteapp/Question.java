package app.sagen.matteapp;

import androidx.annotation.NonNull;

public class Question {

    private String question;
    private int answer;

    void setQuestion(String question) {
        this.question = question;
    }

    void setAnswer(int answer) {
        this.answer = answer;
    }

    public String getQuestion() {
        return question;
    }

    public int getAnswer() {
        return answer;
    }

    @NonNull
    @Override
    public String toString() {
        return "[Question q='" + question + "', a='" + answer + "']";
    }
}
