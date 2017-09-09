package takeyourseat.fragments;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
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

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import takeyourseat.data.remote.ApiService;
import takeyourseat.data.remote.ApiUtils;
import takeyourseat.model.Comment;
import takeyourseat.model.Rating;

/**
 * A simple {@link Fragment} subclass.
 */
public class RateCommentsFragment extends Fragment  {

    private ListView commentsList;
    private RatingBar ratingBar;
    private Button addComment;
    private AlertDialog dialog;
    private RatingBar rateComments;
    private Button addComDialog;
    private Button cancel;
    private EditText commentText;
    private String commentTextText,rateNum;
    private boolean showAddComment;
    private String comment,rateCom;
    private ApiService apiService;
    private ArrayList<String> comments;
    private ArrayList<Integer> ratings;
    private ArrayAdapter<String> adapter;
    private Integer sum;
    int restaurantId;



    public RateCommentsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (savedInstanceState != null) {
            showAddComment = savedInstanceState.getBoolean("showAddComment");
            comment = savedInstanceState.getString("comment");
            rateCom = savedInstanceState.getString("rateNum");
        } else {
            comment = "";
            rateCom = "";
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_rate_comments, container, false);
        ratingBar = (RatingBar) v.findViewById(R.id.ratingBar);

        commentsList = (ListView) v.findViewById(R.id.commentsList);
        addComment = (Button) v.findViewById(R.id.addComment);
        restaurantId = getArguments().getInt("id");

        comments = new ArrayList<>();
        apiService = ApiUtils.getApiService();
        try {
            apiService.getCommentsForRestaurant(String.valueOf(restaurantId)).enqueue(new Callback<List<Comment>>() {
                @Override
                public void onResponse(Call<List<Comment>> call, Response<List<Comment>> response) {
                    if (response.isSuccessful()) {
                        for (int i = 0; i < response.body().size(); i++) {
                            comments.add(response.body().get(i).getDescription());
                            adapter = new ArrayAdapter<String>(getActivity(), R.layout.fragment_comments_row,R.id.commentsTextView, comments);
                            commentsList.setAdapter(adapter);
                            registerForContextMenu(commentsList);
                        }
                    }
                }

                @Override
                public void onFailure(Call<List<Comment>> call, Throwable t) {
                    Log.e("Comments", "error loading from API");
                }
            });
        }
        catch (Exception ex) {
            Log.e("CommentFragment", ex.getMessage());
        }

        ratings = new ArrayList<>();
        sum = 0;
        try {
            apiService.getRatingForRestaurant(String.valueOf(restaurantId)).enqueue(new Callback<List<Rating>>() {
                @Override
                public void onResponse(Call<List<Rating>> call, Response<List<Rating>> response) {
                    if (response.isSuccessful()) {
                        for (int i = 0; i < response.body().size(); i++) {
                            ratings.add(response.body().get(i).getRank());
                            sum += ratings.get(i);
                        }
                        int size = ratings.size();
                        float rank = sum.floatValue() / size;
                        ratingBar.setStepSize(0.01f);
                        ratingBar.setRating(rank);
                        ratingBar.invalidate();
                    }
                }
                @Override
                public void onFailure(Call<List<Rating>> call, Throwable t) {
                    Log.e("Rating", "error loading from API");
                }
            });
        }
        catch (Exception ex) {
            Log.e("CommentFragment", ex.getMessage());
        }

        addComment.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                showAddCommentDialog();
            }
        });
        return v;
    }

    private void addComment(String commentTextText, String rateNum) {
        initialize();
        if(!validate()) {
            Toast.makeText(getActivity(),"Failed leaving comment",Toast.LENGTH_SHORT).show();
        } else {
            final Comment comment = new Comment();
            comment.setDescription(commentTextText);
            comment.setRestaurant(restaurantId);
            SharedPreferences sharedPreferences = getActivity().getSharedPreferences("userDetails", Context.MODE_PRIVATE);
            int id = sharedPreferences.getInt("id",0);
            comment.setUser(id);

            final Rating rate = new Rating();
            StringTokenizer tokens = new StringTokenizer(rateNum, ".");
            String rankString = tokens.nextToken();
            int rank = Integer.parseInt(rankString);
            rate.setRestaurant(restaurantId);
            rate.setUser(id);
            rate.setRank(rank);

        apiService.insertComment(comment).enqueue(new Callback<String>() {
                @Override
                public void onResponse(Call<String> call, Response<String> response) {
                    if(response.isSuccessful()) {
                        if(response.body() != null) {
                            Toast.makeText(getActivity(), "Created new comment with ID: " + response.body(), Toast.LENGTH_LONG);
                            comments.add(comment.getDescription());
                            adapter.notifyDataSetChanged();
                            dialog.dismiss();
                        }
                        else {
                            Toast.makeText(getActivity(), "Failed to create new comment", Toast.LENGTH_LONG);
                        }
                    }
                    else {
                        Log.e("Comment", "Error in response: " + response.code());
                    }
                }

                @Override
                public void onFailure(Call<String> call, Throwable t) {
                    Log.e("Comment", "Error loading from API");
                }
            });

            apiService.insertRating(rate).enqueue(new Callback<String>() {
                @Override
                public void onResponse(Call<String> call, Response<String> response) {
                    if(response.isSuccessful()) {
                        if(response.body() != null) {
                            ratings.add(rate.getRank());
                            sum = 0;
                            for(int rating : ratings) {
                                sum += rating;
                            }
                            int size = ratings.size();
                            float rank = sum.floatValue() / size;
                            ratingBar.setRating(rank);
                            ratingBar.invalidate();
                            dialog.dismiss();
                        }
                        else {
                            Toast.makeText(getActivity(), "Failed to create new rate", Toast.LENGTH_LONG);
                        }
                    }
                    else {
                        Log.e("Rate", "Error in response: " + response.code());
                    }
                }

                @Override
                public void onFailure(Call<String> call, Throwable t) {
                    Log.e("Rate", "Error loading from API");
                }
            });

        }
    }

    private boolean validate() {
        boolean valid = true;
        if(commentTextText.isEmpty()) {
            commentText.setError("Please enter comment");
            valid = false;
        }
        if(commentTextText.length() >= 50) {
            commentText.setError("Comment is to long");
            valid = false;
        }

        return valid;

    }

    private void initialize() {
        commentTextText = commentText.getText().toString();
        rateNum = String.valueOf(rateComments.getRating());
    }

    private void showAddCommentDialog() {
        showAddComment = true;
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        View mView = LayoutInflater.from(getActivity()).inflate(R.layout.activity_add_comment,null);
        rateComments = (RatingBar) mView.findViewById(R.id.ratingBar2);
        commentText = (EditText) mView.findViewById(R.id.commentText);
        commentText.setText(comment);
        addComDialog = (Button) mView.findViewById(R.id.addComDialog);
        cancel = (Button) mView.findViewById(R.id.cancelCom);
        // ne sacuva rate kada se okrene ekran
        // rateComments.setRating(Float.parseFloat(rateCom));
        rateComments.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                rateNum = String.valueOf(rateComments.getRating());
                Toast.makeText(getActivity(),rateNum,Toast.LENGTH_SHORT).show();
            }}
        );

        commentText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.length() >= 100) {
                    commentText.setError("Comment is too long");
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        addComDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addComment(commentText.getText().toString(),rateNum);
            }
        });

        builder.setView(mView);
        dialog = builder.create();
        dialog.show();
    }


    @Override
    public void onResume() {
        super.onResume();
        if (showAddComment) {
            showAddCommentDialog();
        }
    }


    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if(dialog != null && dialog.isShowing()) {
            outState.putString("rateNum",String.valueOf(rateComments.getRating()));
            outState.putString("comment",commentText.getText().toString());
            outState.putBoolean("showAddComment",showAddComment);

        }
    }

}
