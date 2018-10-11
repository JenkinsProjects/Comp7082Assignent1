package bcit.comp7082assignent1;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.ExifInterface;
import android.media.Image;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;


import java.io.File;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    public static final int SEARCH_ACTIVITY_REQUEST_CODE = 0;
    static final int CAMERA_REQUEST_CODE = 1;
    private String currentPhotoPath = null;
    private int currentPhotoIndex = 0;
    private ArrayList<String> photoGallery;
    private Date filterStartDate = new Date(Long.MIN_VALUE);
    private Date filterEndDate = new Date(Long.MAX_VALUE);
    private String filterCaption = "";
    private Float filterLatitude = null;
    private Float filterLongitude = null;
    private Helper helper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button btnLeft = (Button) findViewById(R.id.btnLeft);
        Button btnRight = (Button) findViewById(R.id.btnRight);
        Button btnFilter = (Button) findViewById(R.id.btnFilter);
        Button btnDelete = (Button) findViewById(R.id.btnDelete);
        btnLeft.setOnClickListener(this);
        btnRight.setOnClickListener(this);
        btnFilter.setOnClickListener(filterListener);
        btnDelete.setOnClickListener(deleteListener);
        helper = new Helper();
        Date minDate = new Date(Long.MIN_VALUE);
        Date maxDate = new Date(Long.MAX_VALUE);
        try {
            photoGallery = populateGallery(minDate, maxDate, null, null, null);
        } catch (ParseException | IOException e) {
            e.printStackTrace();
        }
        Log.d("onCreate, size", Integer.toString(photoGallery.size()));
        if (photoGallery.size() > 0) {
            currentPhotoPath = photoGallery.get(currentPhotoIndex);
        }
        displayPhoto(currentPhotoPath);
    }

    private View.OnClickListener filterListener = new View.OnClickListener() {
        public void onClick(View v) {
            Intent i = new Intent(MainActivity.this, SearchActivity.class);
            startActivityForResult(i, SEARCH_ACTIVITY_REQUEST_CODE);
        }
    };

    private View.OnClickListener deleteListener = new View.OnClickListener() {
        public void onClick(View v) {
            helper.remove(currentPhotoPath);
        }
    };

    private ArrayList<String> populateGallery(Date minDate, Date maxDate, String caption,
                                              Float latitude, Float longitude) throws
            ParseException, IOException {
        File file = new File(Environment.getExternalStorageDirectory()
                .getAbsolutePath(), "/Android/data/bcit.comp7082assignent1/files/Pictures");

        File[] fList = file.listFiles();
        photoGallery = helper.find(fList, minDate, maxDate, caption, latitude, longitude);
        return photoGallery;
    }

    private void displayPhoto(String path) {
        ImageView iv = (ImageView) findViewById(R.id.ivMain);
        iv.setImageBitmap(BitmapFactory.decodeFile(path));
        displayTimestamp(path);
        displayCaption(path);
    }

    private void displayTimestamp(String path) {
        TextView ts = (TextView) findViewById(R.id.timestamp);
        if (path != null && !path.isEmpty()) {
            String dateString = path.split("_")[2] + "_" + path.split("_")[3].substring(0, 6);
            ts.setText(dateString);
        } else {
            ts.setText("Timestamp");
        }
    }

    private void displayCaption(String path) {
        EditText et = (EditText) findViewById(R.id.caption);
        if (path != null && !path.isEmpty()) {
            String[] part = path.split("_");
            et.setText(part[1]);
        } else {
            et.setText(null);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    private void updatePhoto(String path, String caption) throws ParseException, IOException {
        helper.update(path, caption);
        populateGallery(filterStartDate, filterEndDate, filterCaption, null, null);
    }

    public void onClick(View v) {
        try {
            if (photoGallery.size() > 0) {
                updatePhoto(photoGallery.get(currentPhotoIndex), ((EditText) findViewById(R.id
                        .caption)).getText().toString());
            }
        } catch (ParseException | IOException e) {
            e.printStackTrace();
        }
        switch (v.getId()) {
            case R.id.btnLeft:
                --currentPhotoIndex;
                break;
            case R.id.btnRight:
                ++currentPhotoIndex;
                break;
            default:
                break;
        }

        if (currentPhotoIndex >= photoGallery.size()) {
            currentPhotoIndex = photoGallery.size() - 1;
        }
        if (currentPhotoIndex < 0) {
            currentPhotoIndex = 0;
        }

        if (photoGallery.size() > 0) {
            currentPhotoPath = photoGallery.get(currentPhotoIndex);
        }
        Log.d("phpotoleft, size", Integer.toString(photoGallery.size()));
        Log.d("photoleft, index", Integer.toString(currentPhotoIndex));
        displayPhoto(currentPhotoPath);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == SEARCH_ACTIVITY_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                Log.d("createImageFile", data.getStringExtra("STARTDATE"));
                Log.d("createImageFile", data.getStringExtra("ENDDATE"));
                try {
                    if (!data.getStringExtra("STARTDATE").isEmpty() && !data.getStringExtra("ENDDATE").isEmpty()) {
                        filterStartDate = new SimpleDateFormat("yyyyMMddHHmmss").parse(data.getStringExtra("STARTDATE"));
                        filterEndDate = new SimpleDateFormat("yyyyMMddHHmmss").parse(data.getStringExtra("ENDDATE"));
                    } else {
                        filterStartDate = new Date(Long.MIN_VALUE);
                        filterEndDate = new Date(Long.MAX_VALUE);
                    }
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                filterCaption = data.getStringExtra("CAPTION");

                if (!data.getStringExtra("LATITUDE").isEmpty() && !data.getStringExtra("LONGITUDE").isEmpty()) {
                    filterLatitude = Float.parseFloat(data.getStringExtra("LATITUDE"));
                    filterLongitude = Float.parseFloat(data.getStringExtra("LONGITUDE"));
                } else{
                    filterLatitude = null;
                    filterLongitude = null;
                }

                try {
                    photoGallery = populateGallery(filterStartDate, filterEndDate, filterCaption,
                            filterLatitude, filterLongitude);
                } catch (ParseException | IOException e) {
                    e.printStackTrace();
                }
                Log.d("onCreate, size", Integer.toString(photoGallery.size()));
                currentPhotoIndex = 0;
                if (photoGallery.size() > 0) {
                    currentPhotoPath = photoGallery.get(currentPhotoIndex);
                } else {
                    currentPhotoPath = null;
                }
                displayPhoto(currentPhotoPath);
            }
        }
        if (requestCode == CAMERA_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                Log.d("createImageFile", "Picture Taken");
                try {
                    photoGallery = populateGallery(new Date(Long.MIN_VALUE), new Date(Long.MAX_VALUE), null, null, null);
                } catch (ParseException | IOException e) {
                    e.printStackTrace();
                }
                currentPhotoIndex = 0;
                currentPhotoPath = photoGallery.get(currentPhotoIndex);
                displayPhoto(currentPhotoPath);
            }
        }
    }

    public void takePicture(View v) {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                Log.d("FileCreation", "Failed");
            }
            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(this,
                        "bcit.comp7082assignent1.pictures.fileprovider", photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, CAMERA_REQUEST_CODE);
            }
        }
    }

    private File createImageFile() throws IOException {
        File dir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = helper.save(dir);
        currentPhotoPath = image.getAbsolutePath();
        Log.d("createImageFile", currentPhotoPath);
        return image;
    }
}
