package com.ground0.likeminds.Adapters;

import android.content.Context;
import android.media.Image;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ground0.likeminds.Data.Profile;
import com.ground0.likeminds.Data.ProfileListItem;
import com.ground0.likeminds.R;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.util.List;

/**
 * Created by Arjun on 05-09-2015.
 */
public class PersonalDirectoryDetailsAdapter extends RecyclerView.Adapter<PersonalDirectoryDetailsAdapter.ViewHolder>  {

    Context context;
    Profile data;
    String type;
    LayoutInflater layoutInflater;
    List<ProfileListItem> dataList;

    public static final int VIEW_TYPE_FIELD = 0;
    public static final int VIEW_TYPE_DIVIDER = 1;

    static int viewType;

    public PersonalDirectoryDetailsAdapter(Context context, Profile data, String type)
    {
        this.context = context;
        this.data = data;
        this.type = type;
        if(type.equals("personal"))
        {
            dataList = data.getPersonalDetails();
        }
        else if(type.equals("emergency"))
        {
            dataList = data.getEmergencyDetails();
        }
        else if(type.equals("family"))
        {
            dataList = data.getFamilyDetails();
        }
        else if(type.equals("events"))
        {
            dataList = data.getEventDetails();
        }
        else if(type.equals("orgData"))
        {
            dataList = data.getOrgData();
        }


        if(dataList == null)
        {

            dataList = (new Profile()).getPersonalDetails();
        }
    }

    @Override
    public int getItemViewType(int position) {
        switch (position%4)
        {
            case 5: return VIEW_TYPE_DIVIDER;
            default: return VIEW_TYPE_FIELD;
        }
    }

    @Override
    public PersonalDirectoryDetailsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if(layoutInflater == null)
        {
            layoutInflater = LayoutInflater.from(context);
        }
        this.viewType = viewType;


        View view = new View(context);
        switch (this.viewType)
        {
            case VIEW_TYPE_DIVIDER :
                view = layoutInflater.inflate(R.layout.profile_detail_divider_item,parent,false);
                break;

            case VIEW_TYPE_FIELD :
                view = layoutInflater.inflate(R.layout.profile_detail_item, parent, false);
        }

        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;

    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        switch (getItemViewType(position))
        {
            case VIEW_TYPE_DIVIDER :
                holder.dividerTitle.setText(dataList.get(position).getData());
                //holder.dividerImage
                holder.dividerImage.setVisibility(View.VISIBLE);
//                Picasso.with(context).load("https://raw.githubusercontent.com/google/material-design-icons/master/action/drawable-hdpi/ic_face_black_24dp.png")
//                        .into(holder.dividerImage);
                break;
            case VIEW_TYPE_FIELD :
                holder.fieldData.setText(dataList.get(position).getData());
                holder.fieldTitle.setText(dataList.get(position).getTitle());
                //holder.fieldImage;
//                holder.fieldImage.setVisibility(View.VISIBLE);
//                Picasso.with(context).load("https://raw.githubusercontent.com/google/material-design-icons/master/communication/drawable-hdpi/ic_call_black_24dp.png")
//                        .into(holder.fieldImage);
                break;
        }
    }


    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView fieldTitle, fieldData;
        public ImageView fieldImage;

        //divider fields
        public TextView dividerTitle;
        public ImageView dividerImage;



        public ViewHolder(View itemView) {
            super(itemView);


            dividerImage = (ImageView)itemView.findViewById(R.id.profile_detail_divider_imageview);
            dividerTitle = (TextView)itemView.findViewById(R.id.detail_divider_textView);

            fieldTitle = (TextView)itemView.findViewById(R.id.profile_detail_title);
            fieldData = (TextView)itemView.findViewById(R.id.profile_detail_data);
            fieldImage = (ImageView)itemView.findViewById(R.id.profile_detail_item_imageview);



        }
    }

}
