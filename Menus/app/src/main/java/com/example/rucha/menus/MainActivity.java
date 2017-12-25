package com.example.rucha.menus;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.view.ActionMode;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import static com.example.rucha.menus.R.menu.menu_contextbar;

public class MainActivity extends AppCompatActivity {

    EditText e1, e2;
    View view;
  //  private ActionMode mActionMode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        e1 = (EditText) findViewById(R.id.t1);
        e2 = (EditText) findViewById(R.id.t2);

        view = findViewById(R.id.t3);

        registerForContextMenu(e1);
        registerForContextMenu(e2);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_output, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.settings:
                Toast.makeText(MainActivity.this, "You clicked Settings", Toast.LENGTH_SHORT).show();
                break;

            case R.id.status:
                Toast.makeText(MainActivity.this, "You clicked Status", Toast.LENGTH_SHORT).show();
                break;

            case R.id.mic:
                Toast.makeText(MainActivity.this, "You clicked Mic", Toast.LENGTH_SHORT).show();
                break;
        }
        return true;
    }

    //floating context menu

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);

        switch (v.getId()) {
            case R.id.t1:
                getMenuInflater().inflate(R.menu.menu_floating, menu);
                break;
            case R.id.t2:
                getMenuInflater().inflate(R.menu.menu_floating2, menu);
                break;
        }
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.i1:
                // e1.setText(R.id.i1.getText().toString());
                Toast.makeText(MainActivity.this, "Item1", Toast.LENGTH_SHORT).show();
                break;

            case R.id.i2:
                Toast.makeText(MainActivity.this, "Item2", Toast.LENGTH_SHORT).show();
                break;

            case R.id.s1:
                Toast.makeText(MainActivity.this, "One", Toast.LENGTH_SHORT).show();
                break;

            case R.id.s2:
                Toast.makeText(MainActivity.this, "Two", Toast.LENGTH_SHORT).show();
                break;
        }
        return true;
    }

}