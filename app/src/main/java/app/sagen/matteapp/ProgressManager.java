package app.sagen.matteapp;

import android.content.Context;
import android.util.Log;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import app.sagen.matteapp.aktiviteter.R;

public class ProgressManager {

    private static ProgressManager instance;

    public static ProgressManager get(Context context) {
        if(instance == null) instance = new ProgressManager(context);
        return instance;
    }

    private Question[] questions;
    private int progress = 0;
    private int correctAnswers = 0;
    private int wrongAnswers = 0;

    private ProgressManager(Context context) {
        String[] rawQuestions = context.getResources().getStringArray(R.array.questions);
        List<Question> questionsList = new ArrayList<>();

        for(String rawQuestion : rawQuestions) {
            String[] split = rawQuestion.split("=");
            if(split.length != 2) {
                Log.e("ProgressManager", "The question '" + rawQuestion + "' could not be parsed!");
                continue;
            }

            Question q = new Question();
            q.setQuestion(split[0].trim());
            try {
                q.setAnswer(Integer.parseInt(split[1].trim()));
            } catch (NumberFormatException e) {
                Log.e("ProgressManager", "The question '" + rawQuestion + "' could not be parsed!");
                e.printStackTrace();
                continue;
            }

            questionsList.add(q);
        }

        Collections.shuffle(questionsList);
        questions = questionsList.toArray(new Question[0]);
    }

    public Question getCurrentQuestion() {
        if(progress >= questions.length) {
            return null;
        }
        return questions[progress];
    }

    public boolean checkAnswerAndProgressIfCorrect(int input) {
        if(input == getCurrentQuestion().getAnswer()) {
            correctAnswers++;
            progress++;
            return true;
        }
        wrongAnswers++;
        progress++;
        return false;
    }

    public boolean hasMoreQuestions() {
        return progress < questions.length;
    }

    public Question[] getQuestions() {
        return questions;
    }

    public int getProgress() {
        return progress;
    }

    public int getCorrectAnswers() {
        return correctAnswers;
    }

    public int getWrongAnswers() {
        return wrongAnswers;
    }

    public void resetStats() {
        this.correctAnswers = 0;
        this.wrongAnswers = 0;
    }

    public void resetProgress() {
        this.progress = 0;
        List<Question> questionList = Arrays.asList(questions);
        Collections.shuffle(questionList);
        this.questions = questionList.toArray(new Question[0]);
    }
}
