package com.ground0.likeminds.Fragments;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.Fragment;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v8.renderscript.Allocation;
import android.support.v8.renderscript.Element;
import android.support.v8.renderscript.RenderScript;
import android.support.v8.renderscript.ScriptIntrinsicBlur;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.TextView;

import com.amulyakhare.textdrawable.TextDrawable;
import com.amulyakhare.textdrawable.util.ColorGenerator;
import com.google.gson.Gson;
import com.ground0.likeminds.Adapters.PersonalDirectoryDetailsAdapter;
import com.ground0.likeminds.Background.MemberViewAsyncTask;
import com.ground0.likeminds.Data.Profile;
import com.ground0.likeminds.R;
import com.ground0.likeminds.ServerAddress.ServerDetails;
import com.ground0.likeminds.UserProfile;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by Arjun on 07-09-2015.
 */
public class ProfileFragment extends Fragment {

    View rootView;
    static Context context;


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        context = (AppCompatActivity)activity;
    }


    public ProfileFragment()
    {

    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


        String viewType = getArguments().getString("view_type");
        final Bundle userData = getArguments().getBundle("user_data");
        String json = getArguments().getString("data");
        Log.i("com.ground0.likeminds", "Data recieved @ Frag :" + json);
        Gson gson = new Gson();
        Profile data = gson.fromJson(json, Profile.class);

        if (viewType.equals("hascoverpic")) {

            rootView = inflater.inflate(R.layout.fragment_contact_details, container, false);

            ImageView header = (ImageView) rootView.findViewById(R.id.header);
            ImageView profilePic = (ImageView) rootView.findViewById(R.id.contact_details_profile_pic);
            TextView userName = (TextView) rootView.findViewById(R.id.contact_details_user_name);
            final BitmapFactory.Options options = new BitmapFactory.Options();
            options.inSampleSize = 8;


            userName.setText(userData.getString("user_name"));
            Log.i("com.ground0.likeminds", "Image URL :" + userData.getString("user_image"));


            if (!(userData.getString("user_image")).isEmpty()) {
                Picasso.with(context).load(userData.getString("user_image")).into(profilePic);

//            ImageView bitmapImageView = new ImageView(context);
                Picasso.with(context).load(userData.getString("user_image")).into(new Target() {
                    @Override
                    public void onBitmapLoaded(Bitmap blurTemplate, Picasso.LoadedFrom from) {
//                    bitmap = ((BitmapDrawable)bitmapImageView.getDrawable()).getBitmap();
//                    Bitmap blurTemplate = Bitmap.createBitmap(bitmap);

//            ImageView tempImageView = new ImageView(context);
//            Picasso.with(context).load(userData.getString("user_image")).into(tempImageView);
//            Bitmap blurTemplate = BitmapFactory.decodeResource(getResources(), R.drawable.profile_picture, options);
                        //define this only once if blurring multiple times
                        RenderScript rs = RenderScript.create(context);


                        //this will blur the bitmapOriginal with a radius of 8 and save it in bitmapOriginal
                        final Allocation input = Allocation.createFromBitmap(rs, blurTemplate); //use this constructor for best performance, because it uses USAGE_SHARED mode which reuses memory
                        final Allocation output = Allocation.createTyped(rs, input.getType());
                        final ScriptIntrinsicBlur script = ScriptIntrinsicBlur.create(rs, Element.U8_4(rs));
                        script.setRadius(8f);
                        script.setInput(input);
                        script.forEach(output);
                        output.copyTo(blurTemplate);
                    }

                    @Override
                    public void onBitmapFailed(Drawable errorDrawable) {

                    }

                    @Override
                    public void onPrepareLoad(Drawable placeHolderDrawable) {

                    }
                });
            } else {
                ColorGenerator generator = ColorGenerator.MATERIAL;
                int color1 = generator.getRandomColor();

                TextDrawable drawable = TextDrawable.builder()
                        .beginConfig()
                        .height(96)
                        .width(96)
                        .endConfig()
                        .buildRound("" + (userData.getString("user_name")).charAt(0), color1);

                ((ImageView) rootView.findViewById(R.id.contact_details_profile_pic)).setImageDrawable(drawable);
                header.setImageDrawable(new ColorDrawable(color1));
            }


//            header.setImageBitmap(blurTemplate);
        } else if (viewType.equals("nocoverpic"))
            rootView = inflater.inflate(R.layout.fragment_contact_details_no_coverpic, container, false);


//        collapsingToolbar.setTitle("Mr Arjun");
//        collapsingToolbar.setCollapsedTitleTextColor(getResources().getColor(R.color.primary_text_default_material_dark));
//        collapsingToolbar.setExpandedTitleColor(getResources().getColor(R.color.primary_text_default_material_dark));

//        collapsingToolbar.setCollapsedTitleTextColor(Color.TRANSPARENT);


//        ((CoordinatorLayout)findViewById(R.id.coordinator_layout)).setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//                return true;
//            }
//        });


//        ProfileTableDatabaseOpenHelper profileTableDatabaseOpenHelper = new ProfileTableDatabaseOpenHelper(context);
//        Profile profile = profileTableDatabaseOpenHelper.getMember(userData.getString("user_id"));

//        ProfileListItem profileListItem = new ProfileListItem("Nothing to display","");
//        List<ProfileListItem> listItems = new ArrayList<>();
//        listItems.add(profileListItem);
//        Profile data = new Profile(listItems,listItems,listItems,listItems,listItems);
//
        RecyclerView recyclerView = (RecyclerView) rootView.findViewById(R.id.contact_details_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));

        //recycler View add the adapter
        Bundle arguments = getArguments();
        if (data != null)
            recyclerView.setAdapter(new PersonalDirectoryDetailsAdapter(context, (Profile) data, arguments.getString("type"))); //get type from the argument


        View viewParent = rootView.getRootView();
        final SwipeRefreshLayout swipeViewParent =
                (SwipeRefreshLayout) ((AppCompatActivity)context).findViewById(R.id.frag_contact_details_swipe);

        final AppBarLayout appBarLayout =
                (AppBarLayout)rootView.findViewById(R.id.appbar);

        recyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {

                int topRowVerticalPosition =
                        (recyclerView == null || recyclerView.getChildCount() == 0) ? 0 : recyclerView.getChildAt(0).getTop();


                swipeViewParent.setEnabled(topRowVerticalPosition >= 0);

                if(appBarLayout!=null && topRowVerticalPosition >=0 )
                {
                    appBarLayout.setExpanded(true, true);
                }



            }
        });

        return rootView;

    }



}
