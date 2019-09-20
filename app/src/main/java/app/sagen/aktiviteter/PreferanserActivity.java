package app.sagen.aktiviteter;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RadioButton;

import androidx.appcompat.app.AppCompatActivity;

import app.sagen.StorageHelper;

public class PreferanserActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preferanser);

        loadValues();
    }

    private void loadValues() {
        Log.d("PreferenceActivity", "Loading values for preferences");

        StorageHelper.Storage data = StorageHelper.loadStorage(this);

        Log.d("PreferenceActivity", "num_tasks = " + data.getNumTasks() + " AND language = " + data.getLanguage());

        RadioButton radioButtonNorsk = findViewById(R.id.radio_norsk);
        RadioButton radioButtonTysk = findViewById(R.id.radio_tysk);

        switch (data.getLanguage()) {
            case "NO":
                radioButtonNorsk.setChecked(true);
                break;
            case "DE":
                radioButtonTysk.setChecked(true);
                break;
            default:
                Log.e("PreferenceActivity", "Could not find the language '" + data.getLanguage() + "'!");
        }


        RadioButton radioButtonNumTasks1 = findViewById(R.id.radio_num_tasks_1);
        RadioButton radioButtonNumTasks2 = findViewById(R.id.radio_num_tasks_2);
        RadioButton radioButtonNumTasks3 = findViewById(R.id.radio_num_tasks_3);

        int num1 = getResources().getInteger(R.integer.preference_number_of_tasks_1);
        int num2 = getResources().getInteger(R.integer.preference_number_of_tasks_2);
        int num3 = getResources().getInteger(R.integer.preference_number_of_tasks_3);

        if(data.getNumTasks() == num1) {
            radioButtonNumTasks1.setChecked(true);
        } else if(data.getNumTasks() == num2) {
            radioButtonNumTasks2.setChecked(true);
        } else if(data.getNumTasks() == num3) {
            radioButtonNumTasks3.setChecked(true);
        } else {
            Log.e("PreferenceActivity", "Could not find the number '" + data.getNumTasks() + "'!");
        }
    }

    public void showMainMenu(View view) {
        finish();
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }

    public void updateNumTasks(View view) {
        Log.d("PreferanserActivity", "updateNumTasks :: " + view.toString());

        StorageHelper.Storage data = StorageHelper.loadStorage(this);

        switch (view.getId()) {
            case R.id.radio_num_tasks_1:
                data.setNumTasks(getResources().getInteger(R.integer.preference_number_of_tasks_1));
                break;
            case R.id.radio_num_tasks_2:
                data.setNumTasks(getResources().getInteger(R.integer.preference_number_of_tasks_2));
                break;
            case R.id.radio_num_tasks_3:
                data.setNumTasks(getResources().getInteger(R.integer.preference_number_of_tasks_3));
                break;
        }

        StorageHelper.saveStorage(this, data);
    }

    public void updateLanguage(View view) {
        Log.d("PreferanserActivity", "updateLanguage :: " + view.toString());

        StorageHelper.Storage data = StorageHelper.loadStorage(this);

        switch (view.getId()) {
            case R.id.radio_norsk:
                data.setLanguage("NO");
                break;
            case R.id.radio_tysk:
                data.setLanguage("DE");
                break;
        }

        StorageHelper.saveStorage(this, data);
    }
}
