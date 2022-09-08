package com.lechi.yxx.util;

public class MimeTypeUtils {


    public String getExtension(String extension) throws Exception {
        try {
            extension = extension.substring(extension.lastIndexOf("."));
            return extension;
        }catch (Exception e){
            return "jpg";
        }
    }

}
