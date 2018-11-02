package bcit.comp7082assignent1;

import android.media.ExifInterface;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Paul on 2018-09-27.
 */

public class Helper {

    public File save(File dir) throws IOException {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());

        String imageFileName = "JPEG" + "_caption_" + timeStamp;

        return File.createTempFile(imageFileName, ".jpg", dir);
    }

    public void remove(String path) {
        File file = new File(path);
        file.delete();
    }

    public void update(String path, String caption) {
        String[] part = path.split("_");
        File newFile = new File(part[0] + "_" + caption + "_" + part[2] + "_" + part[3]);
        File oldFile = new File(path);
        oldFile.renameTo(newFile);
    }

    public ArrayList<String> find(File[] fList, Date minDate, Date maxDate, String caption, Float latitude, Float longitude) throws ParseException, IOException {
        ArrayList<String> photoGallery = new ArrayList<>();
        if (fList != null) {
            for (File f : fList) {
                if (caption != null && !caption.isEmpty()) {
                    String fileCaption = f.getPath().split("_")[1];
                    if (caption.equals(fileCaption)) {
                        photoGallery.add(f.getPath());
                    }
                } else {
                    if (latitude != null && longitude != null) {
                        ExifInterface exif = new ExifInterface(f.getAbsolutePath());

                        Float fileLatitude = coordinatesToFloat(exif.getAttribute(ExifInterface.TAG_GPS_LATITUDE), exif.getAttribute(ExifInterface.TAG_GPS_LATITUDE_REF));

                        Float fileLongitude = coordinatesToFloat(exif.getAttribute(ExifInterface.TAG_GPS_LONGITUDE), exif.getAttribute(ExifInterface.TAG_GPS_LONGITUDE_REF));

                        if (((fileLatitude >= latitude - 5) && (fileLatitude <= latitude + 5)) &&
                                ((fileLongitude >= longitude - 5) && (fileLongitude <= longitude + 5))) {
                            photoGallery.add(f.getPath());
                        }
                    } else {
                        String dateString = f.getPath().split("_")[2] + "_" + f.getPath().split("_")[3].substring(0, 6);
                        Date date = new SimpleDateFormat("yyyyMMdd_HHmmss").parse(dateString);
                        if (date.after(minDate) && date.before(maxDate)) {
                            photoGallery.add(f.getPath());
                        }
                    }
                }
            }
        }
        return photoGallery;
    }

    // taken from http://android-er.blogspot.com/2010/01/convert-exif-gps-info-to-degree-format.html
    private Float coordinatesToFloat(String coordinate, String direction) {
        Float result;
        String[] DMS = coordinate.split(",", 3);

        String[] stringD = DMS[0].split("/", 2);
        Double D0 = Double.valueOf(stringD[0]);
        Double D1 = Double.valueOf(stringD[1]);
        Double FloatD = D0 / D1;

        String[] stringM = DMS[1].split("/", 2);
        Double M0 = Double.valueOf(stringM[0]);
        Double M1 = Double.valueOf(stringM[1]);
        Double FloatM = M0 / M1;

        String[] stringS = DMS[2].split("/", 2);
        Double S0 = Double.valueOf(stringS[0]);
        Double S1 = Double.valueOf(stringS[1]);
        Double FloatS = S0 / S1;

        result = Float.valueOf(String.valueOf(FloatD + (FloatM / 60) + (FloatS / 3600)));

        if (direction.equals("S") || direction.equals("W")) {
            result = result * -1;
        }

        return result;
    }
}
