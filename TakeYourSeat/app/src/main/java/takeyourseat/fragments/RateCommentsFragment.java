package takeyourseat.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.Toast;

import com.example.anica.takeyourseat.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class RateCommentsFragment extends Fragment {

    ListView commentsList;
    RatingBar rate;
    Button addComment;
    AlertDialog dialog;
    RatingBar rateComments;
    Button addComDialog;
    Button cancel;
    EditText commentText;
    String commentTextText,rateNum;



    public RateCommentsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_rate_comments, container, false);
        rate = (RatingBar) v.findViewById(R.id.ratingBar);

        commentsList = (ListView) v.findViewById(R.id.commentsList);
        addComment = (Button) v.findViewById(R.id.addComment);
        String[] items = {"Comment 1","Comment 2","Comment 3","Comment 4","Comment 5","Comment 6","Comment 7","Comment 8"};
        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), R.layout.fragment_comments_row,R.id.commentsTextView, items);
        commentsList.setAdapter(adapter);
        registerForContextMenu(commentsList);
        rate.setRating(4);

        addComment.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                View mView = LayoutInflater.from(getActivity()).inflate(R.layout.activity_add_comment,null);
                rateComments = (RatingBar) mView.findViewById(R.id.ratingBar2);
                commentText = (EditText) mView.findViewById(R.id.commentText);
                addComDialog = (Button) mView.findViewById(R.id.addComDialog);
                cancel = (Button) mView.findViewById(R.id.cancelCom);
                rateComments.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
                    @Override
                    public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                        Toast.makeText(getActivity(),String.valueOf(rating),Toast.LENGTH_SHORT).show();
                    }
                }

                );

                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });

                addComDialog.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        addComment();
                    }
                });
                builder.setView(mView);
                dialog = builder.create();
                dialog.show();

            }
        });
        return v;
    }

    private void addComment() {
        initialize();
        if(!validate()) {
            Toast.makeText(getActivity(),"Failed leaving comment",Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getActivity(),"Successful leaving comment",Toast.LENGTH_SHORT).show();
            dialog.dismiss();
        }
    }

    private boolean validate() {
        boolean valid = true;
        if(commentTextText.isEmpty()) {
            commentText.setError("Please enter comment");
            valid = false;
        }
        return valid;

    }

    private void initialize() {
        commentTextText = commentText.getText().toString().trim();
        rateNum = String.valueOf(rateComments.getRating());


    }

}
