package app.sagen.aktiviteter;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RadioButton;

import androidx.appcompat.app.AppCompatActivity;

public class PreferanserActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preferanser);

        loadValues();
    }

    private void loadValues() {
        Log.d("PreferenceActivity", "Loading values for preferences");

        SharedPreferences sharedPref = getSharedPreferences(
                getString(R.string.preferanser_file), Context.MODE_PRIVATE);

        int numTasks = sharedPref.getInt(
                getString(R.string.preferenser_setting_key_num_tasks),
                getResources().getInteger(R.integer.preferanser_default_num_tasks));

        String lang = sharedPref.getString(
                getString(R.string.preferenser_setting_key_language),
                getResources().getString(R.string.preferanser_default_language));

        Log.d("PreferenceActivity", "num_tasks = " + numTasks + " AND language = " + lang);

        RadioButton radioButtonNorsk = findViewById(R.id.radio_norsk);
        RadioButton radioButtonTysk = findViewById(R.id.radio_tysk);

        switch (lang) {
            case "NO":
                radioButtonNorsk.setChecked(true);
                break;
            case "DE":
                radioButtonTysk.setChecked(true);
                break;
            default:
                Log.e("PreferenceActivity", "Could not find the language '" + lang + "'!");
        }


        RadioButton radioButtonNumTasks1 = findViewById(R.id.radio_num_tasks_1);
        RadioButton radioButtonNumTasks2 = findViewById(R.id.radio_num_tasks_2);
        RadioButton radioButtonNumTasks3 = findViewById(R.id.radio_num_tasks_3);

        int num1 = getResources().getInteger(R.integer.preference_number_of_tasks_1);
        int num2 = getResources().getInteger(R.integer.preference_number_of_tasks_2);
        int num3 = getResources().getInteger(R.integer.preference_number_of_tasks_3);

        if(numTasks == num1) {
            radioButtonNumTasks1.setChecked(true);
        } else if(numTasks == num2) {
            radioButtonNumTasks2.setChecked(true);
        } else if(numTasks == num3) {
            radioButtonNumTasks3.setChecked(true);
        } else {
            Log.e("PreferenceActivity", "Could not find the number '" + numTasks + "'!");
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

        SharedPreferences.Editor editor = getSharedPreferences(
                getString(R.string.preferanser_file), Context.MODE_PRIVATE).edit();

        String numTasksKey = getResources().getString(R.string.preferenser_setting_key_num_tasks);

        switch (view.getId()) {
            case R.id.radio_num_tasks_1:
                editor.putInt(numTasksKey, getResources().getInteger(R.integer.preference_number_of_tasks_1));
                break;
            case R.id.radio_num_tasks_2:
                editor.putInt(numTasksKey, getResources().getInteger(R.integer.preference_number_of_tasks_2));
                break;
            case R.id.radio_num_tasks_3:
                editor.putInt(numTasksKey, getResources().getInteger(R.integer.preference_number_of_tasks_3));
                break;
        }

        editor.apply();
    }

    public void updateLanguage(View view) {
        Log.d("PreferanserActivity", "updateLanguage :: " + view.toString());

        SharedPreferences.Editor editor = getSharedPreferences(
                getString(R.string.preferanser_file), Context.MODE_PRIVATE).edit();

        String langKey= getResources().getString(R.string.preferenser_setting_key_language);

        switch (view.getId()) {
            case R.id.radio_norsk:
                editor.putString(langKey, "NO");
                break;
            case R.id.radio_tysk:
                editor.putString(langKey, "DE");
                break;
        }

        editor.apply();
    }
}
