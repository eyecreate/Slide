package me.ccrama.redditslide.Fragments;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import net.dean.jraw.models.Submission;

import me.ccrama.redditslide.Activities.CommentsScreen;
import me.ccrama.redditslide.Activities.CommentsScreenPopup;
import me.ccrama.redditslide.OfflineSubreddit;
import me.ccrama.redditslide.R;
import me.ccrama.redditslide.SettingValues;
import me.ccrama.redditslide.TimeUtils;
import me.ccrama.redditslide.Views.PopulateSubmissionViewHolder;


/**
 * Created by ccrama on 6/2/2015.
 */
public class TitleFull extends Fragment {

    private int i = 0;
    private Submission s;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(
                R.layout.submission_titlecard, container, false);

        TextView title = (TextView) rootView.findViewById(R.id.title);
        TextView desc = (TextView) rootView.findViewById(R.id.desc);

        title.setText(s.getTitle());
        desc.setText(s.getSubredditName() + getString(R.string.submission_properties_seperator) + s.getAuthor() + " " + TimeUtils.getTimeAgo(s.getCreated().getTime(), getContext()) +
                getString(R.string.submission_properties_seperator) +
                PopulateSubmissionViewHolder.getSubmissionScoreString(s.getScore(), getActivity().getResources(), s)
                + getString(R.string.submission_properties_seperator)
                + getActivity().getResources().getQuantityString(R.plurals.submission_comment_count, s.getCommentCount(), s.getCommentCount()));



        rootView.findViewById(R.id.desc).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (SettingValues.tabletUI && getActivity().getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
                    Intent i2 = new Intent(getActivity(), CommentsScreenPopup.class);
                    i2.putExtra(CommentsScreenPopup.EXTRA_PAGE, i);
                    i2.putExtra(CommentsScreen.EXTRA_SUBREDDIT, sub);
                    (getActivity()).startActivity(i2);

                } else {
                    Intent i2 = new Intent(getActivity(), CommentsScreen.class);
                    i2.putExtra(CommentsScreen.EXTRA_PAGE, i);
                    i2.putExtra(CommentsScreen.EXTRA_SUBREDDIT, sub);
                    (getActivity()).startActivity(i2);
                }
            }
        });
        return rootView;
    }

    public String sub;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = this.getArguments();
        i = bundle.getInt("page", 0);
        sub = bundle.getString("sub");
        s = OfflineSubreddit.getSubreddit(sub).submissions.get(bundle.getInt("page", 0));

    }

}
