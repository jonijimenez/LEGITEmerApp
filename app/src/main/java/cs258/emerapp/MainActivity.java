package cs258.emerapp;

import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.view.View;
import android.provider.ContactsContract.Contacts;
import android.widget.Toast;


public class MainActivity extends ActionBarActivity {

    static GPSTracker gps;
    private static final int CONTACT_PICKER_RESULT = 1001;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        gps = new GPSTracker(MainActivity.this);
        Button location= (Button)findViewById(R.id.button);
        Button contacts=(Button)findViewById(R.id.button2);


        location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            //    setContentView(R.layout.activity_maps;
                startActivity(new Intent(MainActivity.this, LocationMap.class));
            }
        });

        contacts.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                Intent contactPickerIntent = new Intent(Intent.ACTION_PICK,
                        Contacts.CONTENT_URI);
                startActivityForResult(contactPickerIntent, CONTACT_PICKER_RESULT);
            }
        });

    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        ContentResolver cr = getContentResolver();
        String name = "", phone = "";
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case CONTACT_PICKER_RESULT:
                    // handle contact results
                    Uri contactData = data.getData();
                    Cursor c =  managedQuery(contactData, null, null, null, null);
                    if (c.moveToFirst()) {
                        name = c.getString(c.getColumnIndexOrThrow(ContactsContract.Contacts.DISPLAY_NAME));
                        String id = c.getString(c.getColumnIndex(ContactsContract.Contacts._ID));

                        Cursor pCur = cr.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,
                                ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?",
                                new String[]{id}, null);
                        while (pCur.moveToNext()) {
                            phone = pCur.getString(
                                    pCur.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                            Toast toast = Toast.makeText(this, name + " -> " + phone, Toast.LENGTH_SHORT);
                            toast.show();
                        }
                        pCur.close();
                    }
                    Toast toast = Toast.makeText(this, name + " -> " + phone, Toast.LENGTH_SHORT);
                    toast.show();
                    break;
            }

        } else {
            // gracefully handle failure
            Toast toast = Toast.makeText(this, "Failure", Toast.LENGTH_SHORT);
            toast.show();
        }
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
