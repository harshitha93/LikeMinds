package com.ground0.likeminds;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.PopupMenu;
import android.widget.PopupWindow;
import android.widget.Toast;

import com.ground0.likeminds.Background.MemberListAsyncTask;
import com.ground0.likeminds.Background.MemberViewAsyncTask;
import com.ground0.likeminds.Fragments.LandingFragment;
import com.ground0.likeminds.Fragments.MembersFragment;
import com.ground0.likeminds.ServerAddress.ServerDetails;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.MalformedURLException;
import java.net.URL;

public class LandingActivity extends AppCompatActivity {

    ActionBarDrawerToggle actionBarDrawerToggle;
    Context context;
    static PopupWindow popupWindow;
    //,popupDim;

    static int toolbarWidth;
//   static Point screenSize;
//
//    static int[] popupMeasuredSize = new int[2];

    static final String TAG = "com.ground0.likeminds";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landing);

        Toolbar toolbar = (Toolbar) findViewById(R.id.burger_toolbar);
        setSupportActionBar(toolbar);

        context = this;
        Log.i(TAG, "Toolbar width :" + toolbarWidth);


        toolbar.setTitle(R.string.abc_action_bar_home_description);

        final DrawerLayout drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        actionBarDrawerToggle = new ActionBarDrawerToggle(
                this,
                drawerLayout,
                R.string.drawer_open,
                R.string.abc_action_bar_home_description
        );

        drawerLayout.setDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.setDrawerIndicatorEnabled(true);


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);


        NavigationView navigationView = (NavigationView)findViewById(R.id.navigation_view);
        navigationView.findViewById(R.id.drawer_header).setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(getApplicationContext(), UserProfile.class);
                        Bundle userData = new Bundle();
                        userData.putString("user_name","Name here");
                        userData.putString("user_image","");
//            userData.putString("user_image","http://www.cherrywork.net/img/block1.jpg");
                        userData.putString("user_id", ServerDetails.USER_ID);
                        intent.putExtra("user_data", userData);
                        intent.putExtra("username", "Name here");
                        startActivity(intent);
                    }
                }
        );
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                Log.i("com.ground0.likeminds", "Navigation Drawer Id : " + menuItem.getItemId());

                if (menuItem.isChecked()) menuItem.setChecked(false);
                else menuItem.setChecked(true);

                drawerLayout.closeDrawers();

                switch (menuItem.getItemId()) {
                    case R.id.navigation_item_social:

                        //remove elevation of toolbar
                        Resources r = getResources();
                        float px = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 4, r.getDisplayMetrics());
                        ((AppCompatActivity)context).getSupportActionBar().setElevation(px);

                        FragmentManager fragmentManager = getSupportFragmentManager();
                        fragmentManager.beginTransaction()
                                .replace(R.id.fragment_placeholder, new LandingFragment())
                                .addToBackStack(null)
                                .commit();
                        return true;

                    case R.id.navigation_item_people:

                        //add elevation to toolbar
                        Resources r1 = getResources();
                        float px1 = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 4, r1.getDisplayMetrics());
                        ((AppCompatActivity)context).getSupportActionBar().setElevation(px1);


                        fragmentManager = getSupportFragmentManager();
                        fragmentManager.beginTransaction()
                                .replace(R.id.fragment_placeholder, new MembersFragment())
                                .addToBackStack(null)
                                .commit();
                        return  true;

                    case R.id.share_whatsapp:
                        shareWhatsapp();
                        return true;
                    case R.id.share_all:
                        shareWithAll();
                        return true;

                }
                Toast.makeText(getApplicationContext(), "Feature not available in this release", Toast.LENGTH_SHORT).show();
                return false;
            }
        });

        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .add(R.id.fragment_placeholder,new LandingFragment())
                .commit();


//        //TAB code
//
//        TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_layout);
//
//        //Set the title of the tabs in the tab layout
//        tabLayout.addTab(tabLayout.newTab().setText("Feeds"));
//        tabLayout.addTab(tabLayout.newTab().setText("Wall"));
//        tabLayout.addTab(tabLayout.newTab().setText("Groups"));
//        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
//
//        //set up the view pager for the swiping between the tabs
//        final ViewPager viewPager = (ViewPager) findViewById(R.id.pager);
//
//        //get the adapter for switching between the fragments
//        final LandingPagerAdapter adapter = new LandingPagerAdapter
//                (getSupportFragmentManager(), tabLayout.getTabCount());
//        viewPager.setAdapter(adapter);
//        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
//        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
//            @Override
//            public void onTabSelected(TabLayout.Tab tab) {
//                viewPager.setCurrentItem(tab.getPosition());
//            }
//
//            @Override
//            public void onTabUnselected(TabLayout.Tab tab) {
//
//            }
//
//            @Override
//            public void onTabReselected(TabLayout.Tab tab) {
//
//            }
//        });



    }

    public void shareWithAll()
    {
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, "www.cherrywork.net");
        sendIntent.setType("text/plain");
        startActivity(Intent.createChooser(sendIntent, "Share to..."));
    }

    public void shareWhatsapp()
    {
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, "www.cherrywork.net");
        sendIntent.setType("text/plain");
        sendIntent.setPackage("com.whatsapp");
        startActivity(sendIntent);

    }


    public static boolean openApp(Context context, String packageName) {
        PackageManager manager = context.getPackageManager();
        try {
            Intent i = manager.getLaunchIntentForPackage(packageName);
            if (i == null) {
                throw new NameNotFoundException();
            }
            i.addCategory(Intent.CATEGORY_LAUNCHER);
            context.startActivity(i);
            return true;
        } catch (NameNotFoundException e) {
            return false;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_landing, menu);

//        popupWindow = new PopupWindow(this);
//        popupDim = new PopupWindow(this);

//        LayoutInflater layoutInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//        ViewGroup viewGroup = (ViewGroup)findViewById(R.id.popup_app_drawer);
//        View view = layoutInflater.inflate(R.layout.popup_app_drawer, viewGroup, false);
//        View dim = layoutInflater.inflate(R.layout.content_dim, viewGroup, false);


//        popupDim.setContentView(dim);start
//        popupDim.setFocusable(false);
//        popupDim.setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.dim)));
//        popupDim.setAnimationStyle(android.R.anim.fade_in);

//        getWindowManager().getDefaultDisplay().getSize(screenSize);

//        popupDim.setWidth(screenSize.x);
//        popupDim.setHeight(screenSize.y);



//        popupWindow.setContentView(view);
//        popupWindow.setFocusable(true);
//        popupWindow.setBackgroundDrawable(new BitmapDrawable());
//        popupWindow.setHeight(WindowManager.LayoutParams.MATCH_PARENT);
//        popupWindow.setWidth(WindowManager.LayoutParams.MATCH_PARENT);

//        View apps = view.findViewById(R.id.popup_app_drawer);
//
//        RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams) apps.getLayoutParams();
//        lp.setMargins(0, getSupportActionBar().getHeight() + findViewById(R.id.tab_layout).getHeight(), 0, 0);
//        apps.setLayoutParams(lp);
//
//        apps.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Log.i(TAG,"Touch registered on the drawer.");
//            }
//        });
//
//        View container = view.findViewById(R.id.popup_app_drawer_container);
//        container.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                popupWindow.dismiss();
//            }
//        });


//        view.measure(WindowManager.LayoutParams.WRAP_CONTENT,WindowManager.LayoutParams.WRAP_CONTENT);
//        popupMeasuredSize[0] = view.getMeasuredWidth();
//        popupMeasuredSize[1] = view.getMeasuredHeight();

//        Log.i(TAG,"Pop up measured Size : "+popupMeasuredSize[0]+" | "+popupMeasuredSize[1]);

//
//        view.findViewById(R.id.action_leave_app).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//
//                openApp(getApplicationContext(), "com.example.jananin.leave_time");
//            }
//        });
//        view.findViewById(R.id.action_talent_app).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                openApp(getApplicationContext(), "com.example.harshitha.talentacquisition");
//            }
//        });


        return true;
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        actionBarDrawerToggle.onConfigurationChanged(newConfig);

    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        actionBarDrawerToggle.syncState();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if(actionBarDrawerToggle.onOptionsItemSelected(item)){
            return true;
        }
        //noinspection SimplifiableIfStatement

        switch (id)
        {
            case R.id.action_bar_apps :


                PopupMenu popupMenu = new PopupMenu(context, findViewById(id));
                popupMenu.getMenuInflater().inflate(R.menu.menu_apps,popupMenu.getMenu());
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.leave:
                                openApp(getApplicationContext(), "com.example.jananin.leave_time");
                                return true;

                            case R.id.timesheet:
                                openApp(getApplicationContext(), "com.example.arun.timesheet" );
                                return true;

                            case R.id.talent_acq:
                                openApp(getApplicationContext(), "com.example.harshitha.talentacquisition");
                                return true;


                        }
                        return false;
                    }
                });
                popupMenu.show();

//                View actionMenu = findViewById(R.id.action_bar_apps);
//                int[] location = new int[2];
//                actionMenu.getLocationOnScreen(location);



//                popupDim.showAtLocation(actionMenu.getRootView(), Gravity.NO_GRAVITY, 0, 0);
//                popupWindow.showAtLocation(actionMenu.getRootView(), Gravity.NO_GRAVITY, location[0]-popupMeasuredSize[0], location[1]);

//                getSupportActionBar().getHeight();
//                popupWindow.showAtLocation(actionMenu.getRootView(), Gravity.NO_GRAVITY, 0,0);


//                popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
//                    @Override
//                    public void onDismiss() {
//                        popupDim.dismiss();
//                    }
//                });
                break;
        }


        return super.onOptionsItemSelected(item);
    }

}
