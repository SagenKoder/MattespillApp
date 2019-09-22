package app.sagen.matteapp.aktiviteter;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import app.sagen.matteapp.StorageHelper;

public class HovedActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onResume() {
        super.onResume();
        StorageHelper.setSystemLangauge(this);
        setContentView(R.layout.activity_main);
    }

    public void showGame(View view) {
        startActivity(new Intent(this, SpillActivity.class));
        overridePendingTransition(R.anim.push_down_in, R.anim.push_down_out);
    }

    public void showStatistikk(View view) {
        startActivity(new Intent(this, StatistikkActivity.class));
        overridePendingTransition(R.anim.push_down_in, R.anim.push_down_out);
    }

    public void showPreferences(View view) {
        startActivity(new Intent(this, PreferanserActivity.class));
        overridePendingTransition(R.anim.push_down_in, R.anim.push_down_out);
    }
}
