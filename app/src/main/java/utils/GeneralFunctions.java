package utils;

import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.IOException;

public class GeneralFunctions {
    public static File setUpImageFile(String directory) throws IOException {
        File imageFile = null;
        if (Environment.MEDIA_MOUNTED.equals(Environment
                .getExternalStorageState())) {
            File storageDir = new File(directory);
            if (storageDir != null) {
                if (!storageDir.mkdirs()) {
                    if (!storageDir.exists()) {
                        Log.d("CameraSample", "failed to create directory");
                        return null;
                    }
                }
            }
            imageFile = File.createTempFile(AppConstants.JPEG_FILE_PREFIX
                            + System.currentTimeMillis() + "_",
                    AppConstants.JPEG_FILE_SUFFIX, storageDir);
        }
        return imageFile;
    }


}