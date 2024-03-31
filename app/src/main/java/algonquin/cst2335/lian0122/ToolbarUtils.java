package algonquin.cst2335.lian0122;

import android.content.Intent;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.view.MenuItem;

public class ToolbarUtils {
    public static void setupToolbar(AppCompatActivity activity, int toolbarId) {
        Toolbar toolbar = activity.findViewById(toolbarId);
        activity.setSupportActionBar(toolbar);
    }

    public static boolean handleMenuItem(AppCompatActivity activity, MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_favorites) {
            Intent favoritesIntent = new Intent(activity, Sun_FavoritesActivity.class);
            activity.startActivity(favoritesIntent);
            return true;
        } else if (id == R.id.action_about) {
            Toast.makeText(activity, "Created By Oliver Kadvany - 041096826", Toast.LENGTH_LONG).show();
            return true;
        } else if (id == R.id.action_help) {
            showHelpDialog(activity);
            return true;
        } else if (id == R.id.action_sunSeeker) {
            Intent intent = new Intent(activity, Sun_MainActivity.class);
            activity.startActivity(intent);
            return true;
        } else if (id == R.id.action_dictionary) {
            Intent intent = new Intent(activity, Sun_MainActivity.class);
            activity.startActivity(intent);
            return true;
        } else if (id == R.id.action_recepie) {
            Intent intent = new Intent(activity, Sun_MainActivity.class);
            activity.startActivity(intent);
            return true;
        }else if (id == R.id.action_song) {
            Intent intent = new Intent(activity, Sun_MainActivity.class);
            activity.startActivity(intent);
            return true;
        }



        return false;
    }

    private static void showHelpDialog(AppCompatActivity activity) {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setTitle(R.string.help_dialog_title);
        builder.setMessage(R.string.help_dialog_message);

        builder.setPositiveButton(android.R.string.ok, (dialog, which) -> dialog.dismiss());

        AlertDialog dialog = builder.create();
        dialog.show();
    }
}
