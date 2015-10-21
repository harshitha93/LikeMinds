package com.ground0.likeminds.Adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ground0.likeminds.Data.Group;
import com.ground0.likeminds.ProfileViewActivity;
import com.ground0.likeminds.R;

import java.util.Vector;

/**
 * Created by Arjun on 26-08-2015.
 */
public class GroupAdapter extends RecyclerView.Adapter<GroupAdapter.ViewHolder> {

    LayoutInflater layoutInflater;
    Context context;
    Vector<Group> data;

    public GroupAdapter(Context context,Vector<Group> data)
    {
        this.context = context;
        this.data = data;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if(layoutInflater == null)
        {
            layoutInflater = LayoutInflater.from(context);
        }
        View view = layoutInflater.inflate(R.layout.group_card, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        view.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                //Go to respective group
                Intent intent = new Intent(context, ProfileViewActivity.class);
                context.startActivity(intent);

            }
        });
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        String string = new String();
        string = data.get(position).getGroupName();
        holder.groupName.setText(string);
        string = data.get(position).getGroupDescription();
        holder.groupDescription.setText(string);
        holder.profileMarker.setBackgroundResource(data.get(position).getBackground());
        if(data.get(position).getImage()!=null)
            holder.image.setImageDrawable(data.get(position).getImage());

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public TextView groupName, groupDescription;
        public View profileMarker;
        public ImageView image;

        public ViewHolder(View itemView) {
            super(itemView);

            groupName = (TextView)itemView.findViewById(R.id.group_name);
            groupDescription = (TextView)itemView.findViewById(R.id.group_description);
            profileMarker = itemView.findViewById(R.id.profile);
            image = (ImageView)itemView.findViewById(R.id.group_icon);

        }
    }
}
