package com.hack2017.shay_z.printerinfo.models;


import android.content.Context;
import android.graphics.Bitmap;

import java.io.Serializable;

/**
 * Created by shay_z on 27-Apr-17.
 */

public class University implements Serializable {
//    private final Context mainActivity;
    private String url;
    private String name;
    private String logoUrl;
    private String imagePath;
    public Table table;

    public String getLogoUrl() {
        return logoUrl;
    }

    public University() {
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public String getImagePath() {
        return imagePath;
    }



    public Bitmap getBitmap() {
        return UrlUtils.loadImage(imagePath, name);
    }

//    public Drawable getLogo() {
//        return logo;
//    }


    public University(String url, String name, String logoUrl) {
//        this.mainActivity = context;
        this.url = url;
        this.name = name;
//        imagePath = UrlUtils.saveImage(mainActivity, name, logoUrl);
        this.logoUrl = logoUrl;
//        setDrawbleLogo(logoUrl);
//        pathandimage = setBitMapLogo(logoUrl);
    }


//    private File setBitMapLogo(String logoUrl) {
//        Bitmap bitmap = null;
//        InputStream inputStream = null;
//        try {
//            inputStream = new URL(logoUrl).openStream();
//            bitmap = BitmapFactory.decodeStream(inputStream);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        ContextWrapper cw = new ContextWrapper(mainActivity);
//        File directory = cw.getDir("images", Context.MODE_PRIVATE);
//        File myPathandimage = new File(directory,name+".jpg");
//        FileOutputStream fos = null;
//        try {
//            fos = new FileOutputStream(myPathandimage);
//            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        } finally {
//            try {
//                fos.close();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//
//        }
//        Log.d("123", "sssssssssssssssssssssssssssss"+directory.getAbsolutePath());
//        return myPathandimage;
//    }


//    private void setDrawbleLogo(String logoUrl) {
//        InputStream is = null;
//        try {
//            is = (InputStream) new URL(logoUrl).getContent();
//        } catch (IOException e) {
//            Log.d("123", "Ioeceptionnnnn UniversityClass inputstream");
//            e.printStackTrace();
//        }
//        this.logo = Drawable.createFromStream(is, "src name");
////        FileOutputStream fileOutputStream ;
////        try {
////            fileOutputStream = mainActivity.openFileOutput(name, Context.MODE_PRIVATE);
////            logo.
////        } catch (FileNotFoundException e) {
////            e.printStackTrace();
////        }
//
//    }


    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
