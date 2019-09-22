package app.sagen.matteapp.aktiviteter;

import android.content.DialogInterface;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.os.Handler;
import android.os.Message;
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onBackPressed() {
        showExitDialog();
    }

    public void avsluttButton(View view) {
        showExitDialog();
    }

    private void clearFeedback() {
        TextView feedbackText = findViewById(R.id.answer_feedback_text);
        feedbackText.setText("");
    }

    private void updateQuestion() {
        ProgressManager progressManager = ProgressManager.get(this);

        boolean finished = false;
        StorageHelper.Storage data = StorageHelper.loadStorage(this);
        if(!progressManager.hasMoreQuestions()) {
            showNoMoreQuestionsDialog();
            finished = true;
        }

        int answeredQuestions = progressManager.getCorrectAnswers() + progressManager.getWrongAnswers();
        if(answeredQuestions >= data.getNumTasks()) {
            showFinishedDialog(progressManager.getCorrectAnswers(), answeredQuestions);
            finished = true;
        }

        if(finished) {
            data.setTotalCorrect(data.getTotalCorrect() + progressManager.getCorrectAnswers());
            data.setTotalWrong(data.getTotalWrong() + progressManager.getWrongAnswers());
            StorageHelper.saveStorage(this, data);
            progressManager.resetStats();
            return;
        }

        Question question = progressManager.getCurrentQuestion();

        TextView textView = findViewById(R.id.questionBox);
        textView.setText(String.format("%s = ?", question.getQuestion()));
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

    public int parseAnswer() {
        EditText input = findViewById(R.id.answer_field);
        try {
            return Integer.parseInt(input.getText().toString());
        } catch (NumberFormatException e) {
            input.getText().clear();
            Log.i("SpillActivity", "Could not parse number " + input.getText());
            return -1;
        }
    }

    public void checkAnswerButton(View view) {

        ProgressManager progressManager = ProgressManager.get(this);
        boolean correctAnswer = progressManager.checkAnswerAndProgressIfCorrect(parseAnswer());

        // todo: Clear the answer_field without breaking it....
        setContentView(R.layout.activity_spill);

        // Update text fields and check for finish states
        updateQuestion();

        TextView feedbackText = findViewById(R.id.answer_feedback_text);
        if(correctAnswer) {
            feedbackText.setText(R.string.spill_riktig_svar);
            feedbackText.setTextColor(ContextCompat.getColor(this, R.color.primary_dark));
        } else {
            feedbackText.setText(R.string.spill_feil_svar);
            feedbackText.setTextColor(ContextCompat.getColor(this, R.color.error_text));
        }
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
                        clearFeedback();
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

    private void showExitDialog() {

        new AlertDialog.Builder(this)
                .setTitle(getString(R.string.exit_dialog_title))
                .setMessage(getString(R.string.exit_dialog_message))
                .setCancelable(true)
                .setNegativeButton(getString(R.string.exit_dialog_negative), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                })
                .setPositiveButton(getString(R.string.exit_dialog_possitive), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        finish();
                        ProgressManager.get(SpillActivity.this).resetStats();
                        dialog.cancel();
                    }
                })
                .create()
                .show();

    }
}
