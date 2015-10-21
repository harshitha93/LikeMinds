package com.ground0.likeminds.Adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.ground0.likeminds.Data.Feed;
import com.ground0.likeminds.ProfileViewActivity;
import com.ground0.likeminds.R;
import com.ground0.likeminds.UserProfile;

import org.eazegraph.lib.charts.BarChart;
import org.eazegraph.lib.models.BarModel;

import java.util.Vector;

/**
 * Created by Arjun on 25-08-2015.
 */
public class FeedAdapter extends RecyclerView.Adapter<FeedAdapter.ViewHolder> {

    Context context;
    LayoutInflater layoutInflater;
    Vector<Feed> data;
    View rootView;

    int[] colors = {Color.parseColor("#FE6DA8"),Color.parseColor("#56B7F1"),Color.parseColor("#CDA67F"),Color.parseColor("#FED70E")};

    class CountIncrementer implements View.OnClickListener {

        boolean done = false;

        @Override
        public void onClick(View v) {

            if (!done) {
                int count = Integer.parseInt(((Button) v).getText().toString());
                ((Button) v).setText("" + ++count);


                done = true;
//                v.getBackground()
//                        .setColorFilter(
//                                new PorterDuffColorFilter(
//                                        context.getResources().getColor(R.color.primary), PorterDuff.Mode.SRC_ATOP
//                                ));


                //change drawable
                ((Button)v).setCompoundDrawablesWithIntrinsicBounds( R.drawable.like_active, 0, 0, 0);
            }else {
                int count = Integer.parseInt(((Button) v).getText().toString());
                ((Button) v).setText("" + --count);
                done = false;

                ///change drawable
                ((Button)v).setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_thumb_up_black_18dp, 0, 0, 0);
            }
        }
    }


    public class ViewHolder extends RecyclerView.ViewHolder
    {
        public  TextView content, groupName, postDate, author;
        public ImageView feedImage;

        //Poll content
        public FrameLayout poll_layout;
        public RadioGroup radioGroup;
        public BarChart barChart;

        //Button Group
        public Button upvote, share, comment;


        //Image
        public ImageView profileImage;

        public ViewHolder(View itemView) {
            super(itemView);
            content = (TextView)itemView.findViewById(R.id.group_description);
            groupName = (TextView)itemView.findViewById(R.id.group_name);
            postDate = (TextView)itemView.findViewById(R.id.post_date);
            author = (TextView)itemView.findViewById(R.id.author);
            feedImage = (ImageView)itemView.findViewById(R.id.feed_image);
            upvote = (Button)itemView.findViewById(R.id.upvote);
            share = (Button)itemView.findViewById(R.id.share);
            comment = (Button)itemView.findViewById(R.id.comment);
            poll_layout = (FrameLayout)itemView.findViewById(R.id.answer_container);
            radioGroup = (RadioGroup)itemView.findViewById(R.id.answer_group);
            barChart = (BarChart)itemView.findViewById(R.id.piechart);
            profileImage = (ImageView)itemView.findViewById(R.id.group_icon);


        }



    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if(layoutInflater == null)
        {
            layoutInflater = LayoutInflater.from(context);
        }
        View view = layoutInflater.inflate(R.layout.feed_card, parent, false);
        rootView = view;
        ViewHolder viewHolder = new ViewHolder(view);

        view.findViewById(R.id.group_name)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        context.startActivity(new Intent(context, ProfileViewActivity.class));
                    }
                });

        view.findViewById(R.id.author)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        context.startActivity(new Intent(context, UserProfile.class));
                    }
                });




        view.findViewById(R.id.upvote).setOnClickListener(new CountIncrementer());
        view.findViewById(R.id.share).setOnClickListener(new CountIncrementer());






        return viewHolder;
    }


    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {

        String string = new String();
        string = data.get(position).getContent();
        holder.content.setText(string);
        string = data.get(position).getGroupName();
        holder.groupName.setText(string);
        string = data.get(position).getDate();
        holder.postDate.setText(string);
        string = data.get(position).getAuthor();
        holder.author.setText(string);
        Drawable image = data.get(position).getImage();
        if(image!=null)
        {
            holder.feedImage
                    .setImageDrawable(image);
            holder.feedImage.setVisibility(View.VISIBLE);


        }else
        {
            holder.feedImage.setVisibility(View.GONE);
        }
        image = data.get(position).getProfileImage();
        if(image!=null)
        {
            holder.profileImage
                    .setImageDrawable(image);
        }





        //If polls layout
        holder.barChart.setVisibility(View.GONE);
        holder.radioGroup.setVisibility(View.VISIBLE);

        if(data.get(position).getPoll()!=null){

            holder.barChart.clearChart();
            holder.radioGroup.removeAllViews();
            int count =0 ;
            for(String stringAnswer:data.get(position).getPoll().getAnswerList())
            {
                RadioButton radioButton = new RadioButton(context);
                radioButton.setText(stringAnswer);
                holder.radioGroup.addView(radioButton);
                holder.barChart.addBar(new BarModel(stringAnswer, data.get(position).getPoll().getAnswers().get(stringAnswer), colors[count]));
                count++;
            }

//            holder.barChart.addBar(new BarModel("Nein", 2.3f, Color.parseColor("#FE6DA8")));
//            holder.barChart.addBar(new BarModel("Obviously", 2.f, Color.parseColor("#56B7F1")));
//            holder.barChart.addBar(new BarModel("You are over used", 3.3f, Color.parseColor("#CDA67F")));
//            holder.barChart.addBar(new BarModel("Its subtle", 1.1f, Color.parseColor("#FED70E")));
        }


        if(data.get(position).getType()!=null && (data.get(position).getType()).equals("poll") && !data.get(position).getPoll().isAnswered())
        {



            holder.poll_layout.setVisibility(View.VISIBLE);
            holder.radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup group, int checkedId) {
                    holder.radioGroup.setVisibility(View.GONE);
                    holder.barChart.setVisibility(View.VISIBLE);

                    holder.barChart.startAnimation();

                    data.get(position).getPoll().setAnswered(true);
                }
            });

        }
        else if(data.get(position).getType()!=null && (data.get(position).getType()).equals("poll") && data.get(position).getPoll().isAnswered())
        {
            holder.poll_layout.setVisibility(View.VISIBLE);
            holder.radioGroup.setVisibility(View.GONE);
            holder.barChart.setVisibility(View.VISIBLE);

        }
        else
        {
            holder.poll_layout.setVisibility(View.GONE);
        }


        holder.upvote.setText(""+data.get(position).getNlike());
        holder.share.setText(""+data.get(position).getNshare());
        holder.comment.setText(""+data.get(position).getNcomment());



    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public FeedAdapter(Context context, Vector<Feed> data) {
        this.context = context;
        this.data = data;

    }

    public void add(int position, Feed item)
    {
        data.add(position,item);
        notifyItemInserted(position);
    }

    public void remove(Feed item)
    {
        int position = data.indexOf(item);
        data.remove(position);
        notifyItemRemoved(position);
    }
}
