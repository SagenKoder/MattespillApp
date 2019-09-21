package app.sagen.matteapp.aktiviteter;

import android.content.DialogInterface;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.text.Editable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Arrays;

import app.sagen.matteapp.ProgressManager;
import app.sagen.matteapp.Question;
import app.sagen.matteapp.StorageHelper;

public class SpillActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spill);
        updateQuestion();
    }

    @Override
    protected void onResume() {
        Log.d("SpillActivity::RESUME", Arrays.toString(ProgressManager.get(this).getQuestions()));
        super.onResume();
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }

    private void updateQuestion() {
        ProgressManager progressManager = ProgressManager.get(this);

        StorageHelper.Storage data = StorageHelper.loadStorage(this);
        if(!progressManager.hasMoreQuestions()) {
            showNoMoreQuestionsDialog();

            progressManager.resetStats();

            data.setTotalCorrect(data.getCurrentCorrect() + progressManager.getCorrectAnswers());
            data.setTotalWrong(data.getCurrentWrong() + progressManager.getWrongAnswers());

            StorageHelper.saveStorage(this, data);
            return;
        }

        int answeredQuestions = progressManager.getCorrectAnswers() + progressManager.getWrongAnswers();
        if(answeredQuestions >= data.getNumTasks()) {
            showFinishedDialog(progressManager.getCorrectAnswers(), answeredQuestions);

            progressManager.resetStats();

            data.setTotalCorrect(data.getCurrentCorrect() + progressManager.getCorrectAnswers());
            data.setTotalWrong(data.getCurrentWrong() + progressManager.getWrongAnswers());

            StorageHelper.saveStorage(this, data);
            return;
        }

        Question question = progressManager.getCurrentQuestion();

        TextView textView = findViewById(R.id.questionBox);
        textView.setText(String.format("%s = ?", question.getQuestion()));
    }

    private void showFinishedDialog(int correctAnswers, int numTasks) {

        new AlertDialog.Builder(this)
                .setTitle(getResources().getString(R.string.dialog_finished_title))
                .setMessage(String.format(getResources().getString(R.string.dialog_finished_message), correctAnswers, numTasks))
                .setCancelable(true)
                .setNeutralButton(getResources().getString(R.string.dialog_finished_new), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        updateQuestion();
                        dialog.cancel();
                    }
                })
                .setNegativeButton(getResources().getString(R.string.dialog_finished_quit), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        SpillActivity.this.finish();
                        dialog.cancel();
                    }
                })
                .setOnCancelListener(new DialogInterface.OnCancelListener() {
                    @Override
                    public void onCancel(DialogInterface dialog) {
                        updateQuestion();
                    }
                })
                .create()
                .show();

    }

    private void showNoMoreQuestionsDialog() {

        new AlertDialog.Builder(this)
                .setTitle(getResources().getString(R.string.dialog_no_more_title))
                .setMessage(getResources().getString(R.string.dialog_no_more_message))
                .setCancelable(true)
                .setNeutralButton(getResources().getString(R.string.dialog_no_more_btn_avslutt), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        SpillActivity.this.finish();
                        dialog.cancel();
                    }
                })
                .setOnCancelListener(new DialogInterface.OnCancelListener() {
                    @Override
                    public void onCancel(DialogInterface dialog) {
                        SpillActivity.this.finish();
                    }
                })
                .create()
                .show();

    }

    public void addNumber(View view) {

        EditText input = findViewById(R.id.answer_field);

        if(input.getText().length() < 4) {
            input.getText().append(((Button) view).getText());
        }
    }

    public void angreClick(View view) {
        EditText input = findViewById(R.id.answer_field);
        Editable text = input.getText();
        if(text.length() > 0) {
            text.delete(text.length() - 1, text.length());
        }
    }

    public void checkAnswerButton(View view) {
        EditText input = findViewById(R.id.answer_field);
        int inputNumber;
        try {
            inputNumber = Integer.parseInt(input.getText().toString());
        } catch (NumberFormatException e) {
            input.getText().clear();
            return;
        }

        boolean correctAnswer = ProgressManager.get(this).checkAnswerAndProgressIfCorrect(inputNumber);

        // todo: Fix the fix, clear the answer_field without breaking it....
        setContentView(R.layout.activity_spill);
        updateQuestion();

        TextView feedbackText = findViewById(R.id.answer_feedback_text);
        if(correctAnswer) {
            // todo: show correct answer message
            feedbackText.setText(R.string.spill_riktig_svar);
            feedbackText.setTextColor(ContextCompat.getColor(this, R.color.primary));
        } else {
            // todo: show wrong answer message
            feedbackText.setText(R.string.spill_feil_svar);
            feedbackText.setTextColor(ContextCompat.getColor(this, R.color.error_text));
        }
    }

    public void avsluttButton(View view) {
        finish();
    }
}
