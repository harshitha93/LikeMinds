package com.ground0.likeminds.Adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.ground0.likeminds.Data.Post;
import com.ground0.likeminds.R;
import com.ground0.likeminds.UserProfile;


import java.util.Vector;

/**
 * Created by Arjun on 25-08-2015.
 */
public class InboxAdapter extends RecyclerView.Adapter<InboxAdapter.ViewHolder> {

    Context context;
    LayoutInflater layoutInflater;
    Vector<Post> data;


    class CountIncrementer implements View.OnClickListener {

        boolean done = false;

        @Override
        public void onClick(View v) {

            if (!done) {
                int count = Integer.parseInt(((Button) v).getText().toString());
                ((Button) v).setText("" + ++count);

                done = true;

                ///change drawable
                ((Button)v).setCompoundDrawablesWithIntrinsicBounds(R.drawable.like_active, 0, 0, 0);

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
        public TextView content, senderName, sendDate;

        public Button upvote, share, comment;

        public ImageView image;


        public ViewHolder(View itemView) {
            super(itemView);
            content = (TextView)itemView.findViewById(R.id.group_description);
            senderName = (TextView)itemView.findViewById(R.id.group_name);
            sendDate = (TextView)itemView.findViewById(R.id.send_date);

            upvote = (Button)itemView.findViewById(R.id.upvote);
            share = (Button)itemView.findViewById(R.id.share);
            comment = (Button)itemView.findViewById(R.id.comment);

            image = (ImageView)itemView.findViewById(R.id.group_icon);
        }



    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if(layoutInflater == null)
        {
            layoutInflater = LayoutInflater.from(context);
        }
        View view = layoutInflater.inflate(R.layout.wall_card, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        view.findViewById(R.id.group_name)
                .setOnClickListener(
                        new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                context.startActivity(new Intent(context, UserProfile.class));
                            }
                        });



        view.findViewById(R.id.upvote).setOnClickListener(new CountIncrementer());
        view.findViewById(R.id.comment).setOnClickListener(new CountIncrementer());
        view.findViewById(R.id.share).setOnClickListener(new CountIncrementer());


        return viewHolder;
    }


    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        String string = new String();
        string = data.get(position).getTextContent();
        holder.content.setText(string);
        string = data.get(position).getSenderName();
        holder.senderName.setText(string);
        string = data.get(position).getSendDate();
        holder.sendDate.setText(string);



        holder.upvote.setText(""+data.get(position).getNlike());
        holder.share.setText(""+data.get(position).getNshare());
        holder.comment.setText(""+data.get(position).getNcomment());

        if(data.get(position).getImage()!=null)
            holder.image.setImageDrawable(data.get(position).getImage());

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public InboxAdapter(Context context, Vector<Post> data) {
        this.context = context;
        this.data = data;

    }

    public void add(int position, Post item)
    {
        data.add(position,item);
        notifyItemInserted(position);
    }

    public void remove(Post item)
    {
        int position = data.indexOf(item);
        data.remove(position);
        notifyItemRemoved(position);
    }
}

