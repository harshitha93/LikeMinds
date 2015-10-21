package com.ground0.likeminds.Adapters;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.ground0.likeminds.Fragments.ProfileFragment;

import java.util.Objects;

/**
 * Created by Arjun on 04-09-2015.
 */
public class PersonalDirectoryPagerAdapter extends FragmentStatePagerAdapter {

    int numOfTabs;
    Bundle userData;
    String object;

    public PersonalDirectoryPagerAdapter(FragmentManager fm, int numOfTabs, Bundle userData, String object) {
        super(fm);
        this.numOfTabs = numOfTabs;
        this.userData = userData;
        this.object = object;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position)
        {
            case 0:
                Bundle bundle1 = new Bundle();
                bundle1.putString("type","personal");
                bundle1.putString("view_type", "hascoverpic");
                bundle1.putBundle("user_data", userData);
                bundle1.putString("data", object);
                ProfileFragment tab1 = new ProfileFragment();
                tab1.setArguments(bundle1);
                return tab1;
            case 1:
                Bundle bundle5 = new Bundle();
                bundle5.putString("type","orgData");
                bundle5.putBundle("user_data", userData);
                bundle5.putString("view_type", "nocoverpic");
                bundle5.putString("data", object);
                ProfileFragment tab5 = new ProfileFragment();
                tab5.setArguments(bundle5);
                return tab5;
            case 2:
                Bundle bundle2 = new Bundle();
                bundle2.putString("type","emergency");
                bundle2.putBundle("user_data", userData);
                bundle2.putString("view_type", "nocoverpic");
                bundle2.putString("data", object);
                ProfileFragment tab2 = new ProfileFragment();
                tab2.setArguments(bundle2);
                return tab2;
            case 3:
                Bundle bundle3 = new Bundle();
                bundle3.putString("type","family");
                bundle3.putBundle("user_data", userData);
                bundle3.putString("view_type", "nocoverpic");
                bundle3.putString("data", object);
                ProfileFragment tab3 = new ProfileFragment();
                tab3.setArguments(bundle3);
                return tab3;
            case 4:
                Bundle bundle4 = new Bundle();
                bundle4.putString("type","events");
                bundle4.putBundle("user_data", userData);
                bundle4.putString("view_type", "nocoverpic");
                bundle4.putString("data", object);
                ProfileFragment tab4 = new ProfileFragment();
                tab4.setArguments(bundle4);
                return tab4;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return numOfTabs;
    }
}
