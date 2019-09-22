package app.sagen.matteapp;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import app.sagen.matteapp.aktiviteter.R;

public class StorageHelper {

    public static Storage loadStorage(Context context) {
        Storage storage = new Storage();

        SharedPreferences sharedPref = context.getSharedPreferences(
                context.getString(R.string.preferanser_file), Context.MODE_PRIVATE);

        storage.numTasks = sharedPref.getInt(
                context.getResources().getString(R.string.preferenser_setting_key_num_tasks),
                context.getResources().getInteger(R.integer.preferanser_default_num_tasks));

        storage.language = sharedPref.getString(
                context.getResources().getString(R.string.preferenser_setting_key_language),
                context.getResources().getString(R.string.preferanser_default_language));

        storage.totalCorrect = sharedPref.getInt(
                context.getResources().getString(R.string.preferenser_setting_total_correct),
                0);

        storage.totalWrong = sharedPref.getInt(
                context.getResources().getString(R.string.preferenser_setting_total_wrong),
                0);

        storage.currentCorrect = sharedPref.getInt(
                context.getResources().getString(R.string.preferenser_setting_current_correct),
                0);

        storage.currentWrong = sharedPref.getInt(
                context.getResources().getString(R.string.preferenser_setting_current_wrong),
                0);

        return storage;
    }

    public static void saveStorage(Context context, Storage storage) {
        SharedPreferences.Editor sharedPrefEdit = context.getSharedPreferences(
                context.getString(R.string.preferanser_file), Context.MODE_PRIVATE)
                .edit();

        sharedPrefEdit.putString(
                context.getString(R.string.preferenser_setting_key_language),
                storage.language);

        sharedPrefEdit.putInt(
                context.getString(R.string.preferenser_setting_key_num_tasks),
                storage.numTasks);

        sharedPrefEdit.putInt(
                context.getString(R.string.preferenser_setting_current_correct),
                storage.currentCorrect);

        sharedPrefEdit.putInt(
                context.getString(R.string.preferenser_setting_current_wrong),
                storage.currentWrong);

        sharedPrefEdit.putInt(
                context.getString(R.string.preferenser_setting_total_correct),
                storage.totalCorrect);

        sharedPrefEdit.putInt(
                context.getString(R.string.preferenser_setting_total_wrong),
                storage.totalWrong);

        sharedPrefEdit.apply();
    }

    public static void setSystemLangauge(Context context) {
        SharedPreferences sharedPref = context.getSharedPreferences(
                context.getString(R.string.preferanser_file), Context.MODE_PRIVATE);

        String language = sharedPref.getString(
                context.getResources().getString(R.string.preferenser_setting_key_language),
                context.getResources().getString(R.string.preferanser_default_language));

        LanguageHelper.setLocale(context, language);

        Log.d("StorageHelper", "Changed app language to " + language + " v2");
    }

    public static class Storage {
        private int numTasks;
        private String language;

        private int totalCorrect;
        private int totalWrong;

        private int currentCorrect;
        private int currentWrong;

        public int getNumTasks() {
            return numTasks;
        }

        public void setNumTasks(int numTasks) {
            this.numTasks = numTasks;
        }

        public String getLanguage() {
            return language;
        }

        public void setLanguage(String language) {
            this.language = language;
        }

        public int getTotalCorrect() {
            return totalCorrect;
        }

        public void setTotalCorrect(int totalCorrect) {
            this.totalCorrect = totalCorrect;
        }

        public int getTotalWrong() {
            return totalWrong;
        }

        public void setTotalWrong(int totalWrong) {
            this.totalWrong = totalWrong;
        }

        public int getCurrentCorrect() {
            return currentCorrect;
        }

        public void setCurrentCorrect(int currentCorrect) {
            this.currentCorrect = currentCorrect;
        }

        public int getCurrentWrong() {
            return currentWrong;
        }

        public void setCurrentWrong(int currentWrong) {
            this.currentWrong = currentWrong;
        }
    }

}
