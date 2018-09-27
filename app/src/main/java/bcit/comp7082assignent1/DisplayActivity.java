package bcit.comp7082assignent1;

import android.graphics.BitmapFactory;
import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;

public class DisplayActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display);
        displayPhoto(getIntent().getStringExtra("path"));
    }

    private void displayPhoto(String path) {
        ImageView iv = (ImageView) findViewById(R.id.ivMain);
        iv.setImageBitmap(BitmapFactory.decodeFile(path));
        editView();
    }

    private void editView(){
        /*TextView ts = (TextView) findViewById(R.id.timestamp);
        File image = new File(path);
        Image image;
        Long lastModified = image.lastModified();
        ts.setText(lastModified.toString());*/
    }
}
