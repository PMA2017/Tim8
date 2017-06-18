package takeyourseat.dialogs;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;

import com.example.anica.takeyourseat.R;

/**
 * Created by anica on 20.5.2017.
 */

public class RemoveFriendsDialog extends AlertDialog.Builder{

    private boolean a = false;
    public RemoveFriendsDialog(Context context) {

        super(context);

        setUpDialog();
    }

    private boolean setUpDialog(){
        setTitle(R.string.delete);
        setMessage(R.string.delOk);
        setCancelable(false);


        setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
               // delete from friends list
                dialog.dismiss();
                a = true;
            }
        });

        setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();
                a = false;
            }
        });
        return a;
    }

    public AlertDialog prepareDialog(){
        AlertDialog dialog = create();
        dialog.setCanceledOnTouchOutside(false);

        return dialog;
    }
}

