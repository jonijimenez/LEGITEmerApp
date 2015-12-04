package cs258.emerapp;

import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract.Contacts;
import android.provider.ContactsContract;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;


public class MainActivity extends ActionBarActivity {

    static GPSTracker gps;
    private static final int CONTACT_PICKER_RESULT = 1001;

    ImageButton rob, fire, emer, call;
    EditText msgTxt;

    //String for the message to be sent
    String message = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        gps = new GPSTracker(MainActivity.this);
        Button location= (Button)findViewById(R.id.button);
        Button contacts=(Button)findViewById(R.id.button2);

        //Buttons for emergencies
        rob = (ImageButton)findViewById(R.id.imageButton);
        fire = (ImageButton)findViewById(R.id.imageButton2);
        emer = (ImageButton)findViewById(R.id.imageButton3);
        call = (ImageButton)findViewById(R.id.imageButton4);
        msgTxt = (EditText)findViewById(R.id.editText);

        location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            //    setContentView(R.layout.activity_maps;
                startActivity(new Intent(MainActivity.this, LocationMap.class));
            }
        });

        //Button for the general contacts
        contacts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, GenContacts.class);
                i.putExtra("Header","General Contacts");
                startActivity(i);

            }
        });



        //Listener for emergency buttons
        rob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                message = "Emergency: Robbery\n";
                sendMessage(message);
            }
        });
        fire.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                message = "Emergency: Fire\n";
                sendMessage(message);
            }
        });
        emer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                message = "Emergency: Hijacking\n";
                sendMessage(message);
            }
        });
        call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                message = "Emergency: Rape\n";
                sendMessage(message);
            }
        });
    }

    public void sendMessage(String msg){
        msg = msg + "Location: \n" +
                    "Sent to: \n" +
                    "Sender: ";

        msgTxt.setText(msg);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
