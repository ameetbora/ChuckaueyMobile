package com.example.mlubli.chuckauey2;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.io.File;
import java.io.FileOutputStream;
import java.util.NoSuchElementException;

public class SignActivity extends AppCompatActivity {

    private TextView tvName,tvDescription;
    private ImageView img;
    File root;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        tvName = (TextView) findViewById(R.id.signName);
        tvDescription = (TextView) findViewById(R.id.txtDesc);
        img = (ImageView) findViewById(R.id.signImage);

        // Recieve data
        Intent intent = getIntent();
        String Name = intent.getExtras().getString("Name");
        String Description = intent.getExtras().getString("Description");
        int image = intent.getExtras().getInt("Image") ;

        // Setting values
        tvName.setText(Name);
        tvDescription.setText(Description);
        img.setImageResource(image);

     // Sharing the Sign

        /*
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                View content = findViewById(R.id.signImage);
                content.setDrawingCacheEnabled(true);
                Bitmap bitmap = content.getDrawingCache();
                root = Environment.getExternalStorageDirectory();
                File cachePath = new File(root.getAbsolutePath() + "/DCIM/Camera/image.jpg");
                try
                {
                    root.createNewFile();
                    FileOutputStream ostream = new FileOutputStream(root);
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, ostream);
                    ostream.close();
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }

                Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
                sharingIntent.setType("image/*");
                String shareBody = "Here is the share content body";
           //     sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Subject Here");
            //    sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
                sharingIntent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(new File(root.getAbsolutePath() + "/DCIM/Camera/image.jpg")));
                startActivity(Intent.createChooser(sharingIntent, "Share via"));
            }
        });
*/
    }

}
