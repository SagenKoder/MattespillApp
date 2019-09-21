package app.sagen.matteapp.aktiviteter;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;

public class StatistikkActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistikk);
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }

    public void showMainMenu(View view) {
        finish();
    }

}
