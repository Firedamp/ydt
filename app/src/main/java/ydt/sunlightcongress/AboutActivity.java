package ydt.sunlightcongress;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;

import java.io.InputStream;

/**
 * Created by Caodongyao on 2016/11/26.
 */

public class AboutActivity extends AppCompatActivity {
    ImageView imageView;
    Bitmap bitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        imageView = new ImageView(this);
        imageView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        setContentView(imageView);

        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... params) {
                try {
                    InputStream ins = getAssets().open("qwert");
                    bitmap = BitmapFactory.decodeStream(ins);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                imageView.setImageBitmap(bitmap);
            }
        }.execute();
    }
}
