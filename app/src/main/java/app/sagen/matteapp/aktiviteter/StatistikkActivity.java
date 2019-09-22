package app.sagen.matteapp.aktiviteter;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import app.sagen.matteapp.StorageHelper;

public class StatistikkActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistikk);
        updateValues();
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }

    public void updateValues() {
        TextView correctText = findViewById(R.id.correct_answers);
        TextView wrongText = findViewById(R.id.wrong_answers);

        StorageHelper.Storage data = StorageHelper.loadStorage(this);

        correctText.setText(String.format("%s", data.getTotalCorrect()));
        wrongText.setText(String.format("%s", data.getTotalWrong()));
    }

    public void showMainMenu(View view) {
        finish();
    }

    public void resetStats(View view) {

        StorageHelper.Storage data = StorageHelper.loadStorage(this);
        data.setTotalWrong(0);
        data.setTotalCorrect(0);
        StorageHelper.saveStorage(this, data);

        updateValues();
    }
}
