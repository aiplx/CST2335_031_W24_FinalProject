/**
 * Utility class for handling toolbar-related actions.
 * Includes methods for setting up and managing toolbar items.
 * Author: Oliver Kadvany - 041096826
 * Lab Section: 031
 * Creation Date: 2024-03-31
 */

package algonquin.cst2335.lian0122;

import android.content.Intent;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.view.MenuItem;

/**
 * Utility class for handling toolbar-related actions.
 * Provides methods for setting up toolbars and handling menu item selections.
 */
public class ToolbarUtils {

    /**
     * Sets up a toolbar for an AppCompatActivity.
     * @param activity The activity where the toolbar is used.
     * @param toolbarId The resource ID of the toolbar.
     */
    public static void setupToolbar(AppCompatActivity activity, int toolbarId) {
        Toolbar toolbar = activity.findViewById(toolbarId);
        activity.setSupportActionBar(toolbar);
    }

    /**
     * Handles toolbar menu item selections.
     * @param activity The activity where the menu item is selected.
     * @param item The selected menu item.
     * @return true if the menu item action is handled, false otherwise.
     */
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
        } else if (id == R.id.action_song) {
            Intent intent = new Intent(activity, Sun_MainActivity.class);
            activity.startActivity(intent);
            return true;
        }

        return false;
    }

    /**
     * Displays a help dialog.
     * @param activity The activity in which the dialog should be shown.
     */
    private static void showHelpDialog(AppCompatActivity activity) {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setTitle(R.string.help_dialog_title);
        builder.setMessage(R.string.help_dialog_message);

        builder.setPositiveButton(android.R.string.ok, (dialog, which) -> dialog.dismiss());

        AlertDialog dialog = builder.create();
        dialog.show();
    }
}
