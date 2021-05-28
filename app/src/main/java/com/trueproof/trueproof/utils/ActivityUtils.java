package com.trueproof.trueproof.utils;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.amplifyframework.core.Amplify;
import com.trueproof.trueproof.R;
import com.trueproof.trueproof.activities.BatchListActivity;
import com.trueproof.trueproof.activities.MainActivity;
import com.trueproof.trueproof.activities.SettingsActivity;
import com.trueproof.trueproof.activities.TakeMeasurementActivity;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class ActivityUtils {

    private final UserSettings userSettings;

    @Inject
    ActivityUtils(UserSettings userSettings) {
        this.userSettings = userSettings;
    }

    public boolean onOptionsItemSelected(AppCompatActivity activity, MenuItem menuItem){
        if (menuItem.getItemId() == R.id.nav_settings)
            activity.startActivity(new Intent(activity, SettingsActivity.class));
        if (menuItem.getItemId() == R.id.nav_batch_list)
            activity.startActivity(new Intent(activity, BatchListActivity.class));
        if (menuItem.getItemId() == R.id.nav_quick_calculator)
            activity.startActivity(new Intent(activity, MainActivity.class));
        if (menuItem.getItemId() == R.id.nav_log_out) {
            Amplify.Auth.signOut(
                    () -> {
                        Toast.makeText(activity, "Successfully logged out!", Toast.LENGTH_LONG).show();
                        userSettings.invalidateCache();
                    },
                        r->{}
                    );

            // Clears the back stack while starting the main activity
            Intent intent = new Intent(activity, MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            activity.startActivity(intent);
            activity.finish();
        }
        return true;
    }
}
