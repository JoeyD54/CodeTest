package com.example.integralcodetest;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.integralcodetest.ui.home.HomeFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

public class MainActivity extends AppCompatActivity{

    private static final String TAG = "MainActivity";

    Button post_Button;
    String activity;
    TextView test;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        post_Button = findViewById(R.id.post_Button);
        test = findViewById(R.id.text_test);

        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_search)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navView, navController);

        post_Button.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                //On click, go to new activity.
                Intent i = new Intent(getApplicationContext(), PostActivity.class);
                //startActivity(i);
                if(i != null)
                    startActivity(i);
                else {
                    Toast.makeText(MainActivity.this, "Something went wrong." +
                            "Please refresh and try again", Toast.LENGTH_SHORT).show();
                    Log.i(TAG, "onClick: failed to get intent info.");
                }
            }
        });
        
        Intent i = getIntent();
        //activity = i.getStringExtra("POST_ACTIVITY");

        if(i != null) {
            activity = i.getStringExtra("POST_ACTIVITY");
            //Toast.makeText(MainActivity.this, "activity is: " + activity.toString(),
             //       Toast.LENGTH_SHORT).show();
            //if(activity == "com.example.integralcodetest.POST_ACTIVITY")
        } else {
            Log.i(TAG, "onCreate: failed to retrieve post info");
        }

        parseXML();


        if(findViewById(R.id.homeScreen) != null){
            if(savedInstanceState != null){
                return;
            }

            parseXML();
            //FragmentTransaction fragmentTransaction = getSupportFragmentManager().
             //       beginTransaction().add(R.id.homeScreen, builder, null);
            //fragmentTransaction.commit();
        }
    }

    private void parseXML(){
        XmlPullParserFactory parserFactory;
        test.setText("XML CALLED");
        try{
            parserFactory = XmlPullParserFactory.newInstance();
            XmlPullParser parser = parserFactory.newPullParser();
            InputStream is = getAssets().open("posts.xml");
            parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
            parser.setInput(is, null);

            parsePosts(parser);

            is.close();
        } catch (XmlPullParserException e){
            Log.i(TAG, "parseXML: failed initial parse");
        } catch (IOException e){
            Log.i(TAG, "parse Post failed");
        }
    }

    private void parsePosts(XmlPullParser parser) throws IOException, XmlPullParserException{
        ArrayList<User> users = new ArrayList<>();
        Bundle bundle = new Bundle();
        int eventType = parser.getEventType();
        User currentUser = null;
        test.setText("XML CALLED 2");

        while(eventType != XmlPullParser.END_DOCUMENT){
            String eltName = null;
            switch(eventType){
                case XmlPullParser.START_TAG:
                    eltName = parser.getName();
                    if("user".equals(eltName)) {
                        currentUser = new User();
                        users.add(currentUser);
                    } else if(currentUser != null){
                        if("userid".equals(eltName)){
                            currentUser.userid = parser.nextText();
                            Log.i(TAG, "parsePosts: userid = " + currentUser.userid);
                        } else if ("post".equals(eltName)){
                            currentUser.post = parser.nextText();
                            Log.i(TAG, "parsePosts: post = " + currentUser.post);
                        }
                    }
                    break;
            }
            eventType = parser.next();

        }
        //PASS PARSED DATA TO HOME FRAGMENT.
        String myMessage = "main activity to fragment";
        
        //bundle.putString("message", myMessage);

        assert users == null;

        if(users != null){
            HomeFragment homeFragment = new HomeFragment();
            homeFragment.setArguments(bundle);
        }

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.commit();
        //Log.i(TAG, "parsePosts: bundle = " + );



        //bundle.putSerializable("key", users);
        printPosts(users);
    }

    private void printPosts(ArrayList<User> users){

        StringBuilder builder = new StringBuilder();

        for(User user : users){
            test.setText(user.post);
            builder.append(user.userid).append("\n").
                    append(user.post).append("\n\n");
            //test.setText(user.post);
        }
        test.setText(builder.toString());
        Log.i(TAG, "\nprintPosts: builder = " + builder.toString());
    }
}