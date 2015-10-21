package com.ground0.likeminds.Adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import com.amulyakhare.textdrawable.TextDrawable;
import com.amulyakhare.textdrawable.util.ColorGenerator;
import com.ground0.likeminds.Data.Member;
import com.ground0.likeminds.R;
import com.ground0.likeminds.UserProfile;
import com.squareup.picasso.Picasso;
import com.timehop.stickyheadersrecyclerview.StickyRecyclerHeadersAdapter;

import java.util.List;

//import se.emilsjolander.stickylistheaders.StickyListHeadersAdapter;

/**
 * Created by Arjun on 01-09-2015.
 */

/*
//Pinned header listAdapter

public class MemberListAdapter extends BaseAdapter implements StickyListHeadersAdapter
{

    @Override
    public View getHeaderView(int i, View view, ViewGroup viewGroup) {
        return null;
    }

    @Override
    public long getHeaderId(int i) {
        return 0;
    }

    @Override
    public int getCount() {
        return 0;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return null;
    }
}

*/




//RecyclerView Adapter

public class MemberListAdapter  extends RecyclerView.Adapter<MemberListAdapter.ViewHolder>
        implements StickyRecyclerHeadersAdapter<MemberListAdapter.ViewHolder>{

    LayoutInflater layoutInflater;
    Context context;
    List<Member> data; // Change to Data class later

    public class ViewHolder extends RecyclerView.ViewHolder implements RecyclerView.OnClickListener
    {

        //Section header
        public TextView sectionHeader;
        //Items
        public TextView memberName;
        public ImageView memberImage;
        //Add the rest of the views later

        public ViewHolder(View itemView) {
            super(itemView);
            memberName = (TextView) itemView.findViewById(R.id.member_name);
            memberImage = (ImageView) itemView.findViewById(R.id.member_icon);
            sectionHeader = (TextView) itemView.findViewById(R.id.header_id);
            itemView.setOnClickListener(this);
        }


        @Override
        public void onClick(View v) {
            int position = this.getAdapterPosition();

            Intent intent = new Intent(context, UserProfile.class);
            //Add parameters as arguments
            Bundle userData = new Bundle();
            userData.putString("user_name",memberName.getText().toString());
            userData.putString("user_image",data.get(position).getImage());
//            userData.putString("user_image","http://www.cherrywork.net/img/block1.jpg");
            userData.putString("user_id",data.get(position).getId());
            intent.putExtra("user_data",userData);
            intent.putExtra("username",data.get(position).getName());

            context.startActivity(intent);
        }
    }

    public MemberListAdapter(Context context, List<Member> data)
    {
        this.context = context;
        this.data= data;
    }

    @Override
    public MemberListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if(layoutInflater == null)
        {
            layoutInflater = LayoutInflater.from(context);
        }
        View view = layoutInflater.inflate(R.layout.member_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);

        return viewHolder;

    }



    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        String name = data.get(position).getName();
        String image_url = data.get(position).getImage();
        holder.memberName.setText(name);
        Log.i("com.ground0.likeminds","Image URL @onBindView :"+image_url);
        assert image_url!=null;
        if(image_url.equals(""))
        {
            ColorGenerator generator = ColorGenerator.MATERIAL;
            int color1 = generator.getRandomColor();

            TextDrawable drawable = TextDrawable.builder()
                    .beginConfig()
                    .height(60)
                    .width(60)
                    .endConfig()
                    .buildRound(""+name.charAt(0), color1);

            holder.memberImage.setImageDrawable(drawable);
        }
        else
            Picasso.with(context).load(image_url).into(holder.memberImage);
    }


    //The section header show up based on this function
    @Override
    public long getHeaderId(int i) {

        return data.get(i).getName().charAt(0);
//
//        if (i == 0) {
//            return -1;
//        } else {
//            return data.get(i).getName().charAt(0);
//        }
    }

    @Override
    public ViewHolder onCreateHeaderViewHolder(ViewGroup viewGroup) {
        if(layoutInflater == null)
        {
            layoutInflater = LayoutInflater.from(context);
        }
        View view = layoutInflater.inflate(R.layout.member_list_section_header, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(view);


        return viewHolder;
    }

    @Override
    public void onBindHeaderViewHolder(ViewHolder viewHolder, int i) {

        viewHolder.sectionHeader.setText(String.valueOf(data.get(i).getName().charAt(0)));
//        viewHolder.itemView.setBackgroundColor(getRandomColor());
    }

//    private int getRandomColor() {
//        SecureRandom rgen = new SecureRandom();
//        return Color.HSVToColor(150, new float[]{
//                rgen.nextInt(359), 1, 1
//        });
//    }

    @Override
    public int getItemCount() {
        return data.size();
    }
}


/*

    //Deprecated MemberListAdapter


public class MemberListAdapter  extends RecyclerView.Adapter<MemberListAdapter.ViewHolder> {

    LayoutInflater layoutInflater;
    Context context;
    Vector<String> data; // Change to Data class later


    public class ViewHolder extends RecyclerView.ViewHolder
    {
        public TextView memberName;
        //Add the rest of the views later

        public ViewHolder(View itemView) {
            super(itemView);
            memberName = (TextView) itemView.findViewById(R.id.member_name);
        }



    }

    public MemberListAdapter(Context context, Vector<String> data)
    {
        this.context = context;
        this.data= data;
    }

    @Override
    public MemberListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if(layoutInflater == null)
        {
            layoutInflater = LayoutInflater.from(context);
        }
        View view = layoutInflater.inflate(R.layout.member_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, UserProfile.class);
                context.startActivity(intent);
            }
        });

        return viewHolder;

    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        String name = data.get(position);
        holder.memberName.setText(name);

    }



    @Override
    public int getItemCount() {
        return data.size();
    }
}    */
