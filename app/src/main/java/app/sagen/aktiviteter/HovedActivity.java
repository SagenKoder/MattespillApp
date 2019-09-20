package app.sagen.aktiviteter;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class HovedActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
