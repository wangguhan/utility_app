package gridwatch.kplc.activities.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.EditText;

import gridwatch.kplc.R;


public class ReportDialog extends DialogFragment {
    boolean result;


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        final String tag = "all:ReportDialog:onCreateDialog";
        setStyle(DialogFragment.STYLE_NO_TITLE, R.style.dialog);

        // UUID.randomUUID().toString().substring(0,4);
        int randomPIN = (int)(Math.random()*9000)+1000;
        final String random = String.valueOf(randomPIN);

        final EditText edittext= new EditText(this.getActivity().getApplicationContext());
        edittext.offsetLeftAndRight(10);
        edittext.setTextColor(Color.RED);
        builder.setView(edittext);

        edittext.setInputType(InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS);

        edittext.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {

            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                Log.w(tag, "HIT");
                Log.w(tag, edittext.getText().toString());
                if (edittext.getText().toString().equals(random)) {
                    edittext.setTextColor(Color.GREEN);
                    result = true;
                } else {
                    edittext.setTextColor(Color.RED);
                    result = false;
                }
            }
        });

        final ReportDialogListener activity = (ReportDialogListener) getActivity();
        String message = getText(R.string.report_dialog).toString() + "\n\n" + random;
        builder.setTitle("Confirm Report");

        builder.setMessage(message)
                .setPositiveButton(R.string.report, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        if (!result || edittext.getCurrentTextColor() == Color.RED) {
                            FailureDialog fd = new FailureDialog();
                            fd.show(getFragmentManager(), "FailureDialog");
                        }
                        activity.onDialogReturnValue(result);
                    }
                })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        activity.onDialogReturnValue(result);
                    }
                });

        return builder.create();
    }

    public interface ReportDialogListener {
        public void onDialogReturnValue(Boolean result);
    }
}