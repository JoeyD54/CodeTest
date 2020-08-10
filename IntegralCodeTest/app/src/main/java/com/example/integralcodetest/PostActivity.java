package com.example.integralcodetest;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import java.io.File;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import androidx.appcompat.app.AppCompatActivity;


public class PostActivity extends AppCompatActivity {

    private static final String TAG = "PostActivity";

    Button postButton;
    TextView postText;
    String userid;

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
                    Log.i(TAG, "onClick: CALLING TO UPDATE XML");
                    userid = "Kate";
                    addToXML(userid, postText.getText().toString());
                    Toast.makeText(PostActivity.this, "You typed: " +
                            postText.getText().toString(), Toast.LENGTH_SHORT).show();

                    //Go back to main
                    Log.i(TAG, "onClick: Going back to MAIN ACTIVITY");
                    Log.i(TAG, "onClick: text field contents = " + postText.getText().toString());
                    //Intent i = new Intent(getApplicationContext(), MainActivity.class);
                    //i.putExtra(POST_ACTIVITY, postText.getText().toString());
                    //startActivity(i);
                }
            }
        });
    }

    void addToXML(String userid, String post){
        //ArrayList<User> user = new ArrayList<>();
        //user.add(new User());
        Log.i(TAG, "addToXML: I AM IN ADD TO XML");
        try{

            Log.i(TAG, "addToXML: I AM AT THE START OF THE TRY XML STEP");
            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
            Document document = documentBuilder.parse("posts.xml");

            //Get USERS node
            Node users = document.getFirstChild();

            //Create new XML node
            Element user = document.createElement("user");
            user.appendChild(document.createTextNode(userid));
            user.appendChild(document.createTextNode(post));

            //Add it to end of user node list within parent USERS node.
            users.appendChild(user);


            //Write modified XML to file
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(document);
            StreamResult result = new StreamResult(new File("posts.xml"));
            transformer.transform(source, result);

            Log.i(TAG, "addToXML: XML has finished processing");


            /*
            Node parentNode = document.getParentNode();
            Node node = document.createElement("user");

            Node userNode = document.createTextNode(userid);
            Node postNode = document.createTextNode(post);
            node.appendChild(userNode);
            node.appendChild(postNode);

            parentNode.appendChild(node);



            for(int i = 0; i < usersNode.getLength(); i++){
                Message message = new Message();
                Node userNode = usersNode.item(i);
                NodeList properties = userNode.getChildNodes();
                for(int j = 0; j < properties.getLength(); j++){
                    Node property = properties.item(j);
                    String name = property.getNodeName();
                    if(name.equalsIgnoreCase(""))
                }
            }

            //Element newUser = document.createElement("user");
           // Element newUserID = document.createElement("userid");
*/
        } catch (Exception e){
            Log.i(TAG, "addToXML: Document builder failed");
        }
    }

}