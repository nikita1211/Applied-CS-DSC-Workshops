package com.example.rucha.uiinputcontrol;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.Toast;
import android.widget.ToggleButton;

public class MainActivity extends AppCompatActivity {

    ToggleButton tb;
    CheckBox cb,cb2;
    RadioButton rb1,rb2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tb=(ToggleButton) findViewById(R.id.toggleButton);
         cb=(CheckBox)findViewById(R.id.eng);
        cb2=(CheckBox)findViewById(R.id.hin);
        rb1=(RadioButton)findViewById(R.id.male);
        rb2=(RadioButton)findViewById(R.id.female);
       // LinearLayout layout=(LinearLayout) findViewById(R.id.display);


        rb1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this,"You selected"+rb1.getText(),Toast.LENGTH_SHORT).show();

            }
        });

        rb2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this,"You selected"+rb2.getText(),Toast.LENGTH_SHORT).show();

            }
        });
    }

    public void showAlert(View view) {
        AlertDialog.Builder alert=new AlertDialog.Builder(this);
        alert.setTitle("Exit");
        alert.setMessage("Are you sure?");
        alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });

        alert.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        alert.setCancelable(false);

        alert.show();
    }

    public void showProgress(View view) {
           ProgressDialog pd=new ProgressDialog(this);
        pd.setTitle("Download");
        pd.setMessage("Please Wait...");
        pd.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        pd.setButton(ProgressDialog.BUTTON_NEGATIVE, "Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        pd.show();


    }
}
