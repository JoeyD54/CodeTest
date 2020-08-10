package com.example.integralcodetest;

import androidx.annotation.RequiresPermission;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Xml;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xmlpull.v1.XmlSerializer;

import java.lang.reflect.Array;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;



public class PostActivity extends AppCompatActivity {

    private static final String TAG = "PostActivity";

    Button postButton;
    TextView postText;

    public static String POST_ACTIVITY = "com.example.integralcodetest.POST_ACTIVITY";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);

        postText = findViewById(R.id.searchField);
        postButton = findViewById(R.id.postButton);

        postButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                //On click, if postText is not null, send text back to main and populate list.
                if(postText.getText().toString().matches("")){
                    Toast.makeText(PostActivity.this, "Type out your message before " +
                            "sending!", Toast.LENGTH_SHORT).show();
                } else {
                    //Call xml update here with the contents of postText
                    addToXML("Kate", postText.getText().toString());

                    //Go back to main
                    Intent i = new Intent(getApplicationContext(), MainActivity.class);
                    i.putExtra(POST_ACTIVITY, postText.getText().toString());
                    startActivity(i);
                }
            }
        });
    }

    //TODO: get text from postText and put it into XML
    void addToXML(String userid, String post){
        String appendedPost = "";


        try {
            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
            Document document = documentBuilder.parse("posts.xml");
            Element root = document.getDocumentElement();
            NodeList userNode = root.getElementsByTagName("user");

            ArrayList<User> user = new ArrayList<>();
            user.add(new User());


            Element newUser = document.createElement("user");
            Element newUserID = document.createElement("userid");

        } catch (Exception e){
            Log.i(TAG, "addToXML: Document builder failed");
        }

    }

}