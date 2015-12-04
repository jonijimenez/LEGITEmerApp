package cs258.emerapp;

import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;
import android.support.v4.app.FragmentActivity;
import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract.Contacts;
import android.view.View;
import android.widget.Button;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by JoniMarie on 12/3/15.
 */
public class GenContacts extends FragmentActivity {

    Button addContacts;
    ListView contactsList;

    //Elements to make a listView
    ArrayList<HashMap<String,String>> contactsArray;
    String[] from;
    int[] to;

    //Header
    TextView header;

    SimpleAdapter adapter;
    private static final int CONTACT_PICKER_RESULT = 1001;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts);

        Intent i = getIntent();

        //Add Functionality
        addContacts = (Button)findViewById(R.id.button4);
        contactsList = (ListView)findViewById(R.id.listView);
        header = (TextView)findViewById(R.id.textView);

        //setting header
        header.setText(i.getExtras().getString("Header"));


        //Setting listView Adapter
        contactsArray = new ArrayList<HashMap<String,String>>();

        from = new String[]{"name","phone"};
        to = new int[]{android.R.id.text1, android.R.id.text2};

        adapter = new SimpleAdapter(this,contactsArray,android.R.layout.simple_list_item_2,from,to);
        //adapter.notifyDataSetChanged();

        contactsList.setAdapter(adapter);


        //Adding contacts
        addContacts.setOnClickListener(new View.OnClickListener(){
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
                           // Toast toast = Toast.makeText(this, name + " -> " + phone, Toast.LENGTH_SHORT);
                           // toast.show();

                            HashMap<String, String> newContact = new HashMap<String,String>();
                            newContact.put("name",name);
                            newContact.put("phone",phone);

                            contactsArray.add(newContact);
                            adapter.notifyDataSetChanged();
                        }
                        pCur.close();
                    }
                    // Toast toast = Toast.makeText(this, name + " -> " + phone, Toast.LENGTH_SHORT);
                    // toast.show();
                    break;
            }

        } else {
            // gracefully handle failure
            // Toast toast = Toast.makeText(this, "Failure", Toast.LENGTH_SHORT);
            // toast.show();
        }
    }
}
