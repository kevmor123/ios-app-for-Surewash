package com.example.kevin.surewash;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.preference.PreferenceFragment;
//Settings class works perfectly just i dont have the name, email, and password able to change as it depends on how
//you are accessing the database
//SharedPreferences SP = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
//String strUserName = SP.getString("username", "NA");
//This code here is an example on how you would be able to access what is typed and what the user wants to change the email/password to
public class Settings extends PreferenceActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getFragmentManager().beginTransaction().replace(android.R.id.content, new MyPreferenceFragment()).commit();
    }

    //Creating preference screen using the preferences.xml file
    public static class MyPreferenceFragment extends PreferenceFragment
    {
        @TargetApi(Build.VERSION_CODES.M)
        @Override
        public void onCreate(final Bundle savedInstanceState)
        {
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.preferences);

        }
    }
}
