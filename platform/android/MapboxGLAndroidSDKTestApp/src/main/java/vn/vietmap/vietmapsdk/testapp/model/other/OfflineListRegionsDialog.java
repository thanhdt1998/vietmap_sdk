package vn.vietmap.vietmapsdk.testapp.model.other;

import android.app.Dialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import vn.vietmap.vietmapsdk.testapp.R;

import java.util.ArrayList;

import timber.log.Timber;

public class OfflineListRegionsDialog extends DialogFragment {

  public static final String ITEMS = "ITEMS";

  @NonNull
  @Override
  public Dialog onCreateDialog(Bundle savedInstanceState) {
    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

    // Read args
    Bundle args = getArguments();
    ArrayList<String> offlineRegionsNames = (args == null ? null : args.getStringArrayList(ITEMS));
    CharSequence[] items = offlineRegionsNames.toArray(new CharSequence[offlineRegionsNames.size()]);

    builder.setTitle("List of offline regions")
      .setIcon(R.drawable.ic_airplanemode_active_black)
      .setItems(items, (dialog, which) -> Timber.d("Selected item: %s", which))
      .setPositiveButton("Accept", (dialog, which) -> Timber.d("Dialog dismissed"));

    return builder.create();
  }
}
