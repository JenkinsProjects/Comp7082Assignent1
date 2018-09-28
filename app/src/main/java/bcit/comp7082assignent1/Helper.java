package bcit.comp7082assignent1;

import android.os.Environment;
import android.util.Log;
import android.widget.EditText;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Paul on 2018-09-27.
 */

public class Helper {

    public File save (File dir) throws IOException {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        // System.out.println("Look at this! " + timeStamp);
        String imageFileName = "JPEG" + "_caption_" + timeStamp;
        File image = File.createTempFile(imageFileName, ".jpg", dir);
        return image;
    }

    public void remove (String path) {
        File file = new File(path);
        file.delete();
    }

    public void update(String path, String caption){
        String[] part = path.split("_");
        File newFile = new File(part[0] + "_" + caption + "_" + part[2] + "_" + part[3]);
        File oldFile = new File(path);
        oldFile.renameTo(newFile);
    }

    public ArrayList<String> find(File[] fList, Date minDate, Date maxDate, String caption) throws ParseException {
        ArrayList<String> photoGallery = new ArrayList<>();
        System.out.println("Dates " + minDate + " " + maxDate);
        if (fList != null) {
            for (File f : fList) {
                System.out.println("Caption thing: " + caption);
                if (caption != null && !caption.isEmpty()) {
                    String fileCaption = f.getPath().split("_")[1];
                    if (caption.equals(fileCaption)) {
                        photoGallery.add(f.getPath());
                    }
                } else {
                    String dateString = f.getPath().split("_")[2] + "_" + f.getPath().split("_")[3].substring(0,6);
                    //System.out.println("how this " + f.getPath() + " " + dateString);
                    Date date = new SimpleDateFormat("yyyyMMdd_HHmmss").parse(dateString);
                    //System.out.println("Date : " + date);
                    System.out.println(minDate + " " + maxDate + " " + date + " ");
                    if (date.after(minDate) && date.before(maxDate)) {
                        photoGallery.add(f.getPath());
                    }
                }
            }
        }
        return photoGallery;
    }
}
