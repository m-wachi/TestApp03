package com.example.mstkwachi.testapp03;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.AdapterView;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private String[] data = {"HT-03A", "Xperia", "NexusOne", "Droid"};

    private AdapterView.OnItemClickListener mMessageClickHandler = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        //List<String> lstFilePath = null;
        String[] lstFile = null;
        if (isExternalStorageWritable()) {
            lstFile = listAppDir();
        } else {
            lstFile = new String[0];
        }

        //ArrayAdapter<String> arrayAdapter
        //        = new ArrayAdapter<String>( this, R.layout.content_main_rowitem, (String[])lstFilePath.toArray(new String[lstFilePath.size()]) );

        final ArrayAdapter<String> arrayAdapter
                = new ArrayAdapter<String>( this, R.layout.content_main_rowitem, lstFile);


        ListView listVw = (ListView) findViewById(R.id.content_main_listView);
        listVw.setAdapter(arrayAdapter);

        mMessageClickHandler = new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView parent, View view, int position, long id) {
                Log.d("TestApp03", "hello, listview-item is clicked.");
                String fileName =  arrayAdapter.getItem(position);
                Log.d("TestApp03", "item pos=" + String.valueOf(position) + ", value=" + fileName);
                Intent intent = new Intent(MainActivity.this, EditText01Activity.class);
                intent.putExtra(Constants.INTENT_KEY_FILENAME, fileName);

                startActivity(intent);
            }
        };

        listVw.setOnItemClickListener(mMessageClickHandler);


    }

    private String[] listAppDir() {
        String appDirPath = Environment.getExternalStorageDirectory() + "/TestApp03";

        File appDir = new File(appDirPath);
        if (!appDir.exists()) {
            if (appDir.mkdir()) {
                Log.i("TestApp03", "cannot create " + appDirPath );
            }
        }

        //ArrayList<String> lstFilePath = new ArrayList<String>();
        String[] lstFile = appDir.list();
        //lstFilePath.add("Item A01");
        //lstFilePath.add("Item B01");


        return lstFile;

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

    /* Checks if external storage is available for read and write */
    public boolean isExternalStorageWritable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            return true;
        }
        return false;
    }

}
