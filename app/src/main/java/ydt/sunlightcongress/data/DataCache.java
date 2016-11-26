package ydt.sunlightcongress.data;

import android.content.Context;

import com.alibaba.fastjson.JSON;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.PrintWriter;

import ydt.sunlightcongress.data.cache.Cache;

/**
 * Created by Caodongyao on 2016/11/26.
 */

// cache data to file system
public class DataCache {
    private final static String FILE_NAME = "hadasewdsd";

    private Context mContext;

    public DataCache(Context context){
        this.mContext = context;
    }

    public void put(Cache cache){
        if(cache == null)
            return;
        try {
            File file = new File(mContext.getFilesDir(), FILE_NAME);
            FileOutputStream fos = new FileOutputStream(file, false);
            PrintWriter printWriter = new PrintWriter(fos);
            String output = JSON.toJSONString(cache);
            printWriter.print(output);
            printWriter.flush();
            printWriter.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public Cache get(){
        File file = new File(mContext.getFilesDir(), FILE_NAME);
        try {
            if(!file.exists() || !file.isFile())
                return new Cache();
            FileInputStream fis = new FileInputStream(file);
            String input = convertInputStreamToString(fis);
            return JSON.parseObject(input, Cache.class);
        }catch (Exception e){
            if(file.exists())
                file.delete();
            return new Cache();
        }
    }

    private String convertInputStreamToString(InputStream is) {
        try {
            StringBuffer out = new StringBuffer();
            byte[] b = new byte[4096];
            for (int n; (n = is.read(b)) != -1; ) {
                out.append(new String(b, 0, n));
            }
            return out.toString();
        }catch (Exception e){
            return "";
        }
    }

}
