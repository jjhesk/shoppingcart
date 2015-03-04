package com.hb.hkm.hypebeaststore;

import android.content.res.Configuration;
import android.graphics.drawable.Drawable;
import android.opengl.Matrix;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import it.sephiroth.android.library.imagezoom.ImageViewTouch;
import it.sephiroth.android.library.imagezoom.ImageViewTouch.OnImageViewTouchDoubleTapListener;
import it.sephiroth.android.library.imagezoom.ImageViewTouch.OnImageViewTouchSingleTapListener;
import it.sephiroth.android.library.imagezoom.ImageViewTouchBase.DisplayType;
import it.sephiroth.android.library.imagezoom.ImageViewTouchBase.OnDrawableChangeListener;


/**
 * Created by hesk on 3/3/15.
 */
public class zoomimage extends ActionBarActivity {
    public static final String URLKEY = "image_url_full";
    private static final String LOG_TAG = "image-test";

    private ImageViewTouch mImage;
    private Button mButton1;
    private Button mButton2;
    private CheckBox mCheckBox;
    static int displayTypeCount = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
     //   requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.act_zoom_image);
        Toast.makeText(this, "ImageViewTouch.VERSION: " + ImageViewTouch.VERSION, Toast.LENGTH_SHORT).show();
        onSetLayout();

    }

    private void onSetLayout() {
        mImage = (ImageViewTouch) findViewById(R.id.image);

        // set the default image display type
        mImage.setDisplayType(DisplayType.FIT_IF_BIGGER);

        mButton1 = (Button) findViewById(R.id.button);
        mButton2 = (Button) findViewById(R.id.button2);
        mCheckBox = (CheckBox) findViewById(R.id.checkbox1);

        mButton1.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //selectRandomImage(mCheckBox.isChecked());
                    }
                }
        );

        mButton2.setOnClickListener(
                new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {

                        int current = mImage.getDisplayType().ordinal() + 1;
                        if (current >= DisplayType.values().length) {
                            current = 0;
                        }

                        mImage.setDisplayType(DisplayType.values()[current]);
                    }
                }
        );

        mImage.setSingleTapListener(
                new OnImageViewTouchSingleTapListener() {

                    @Override
                    public void onSingleTapConfirmed() {
                        Log.d(LOG_TAG, "onSingleTapConfirmed");
                    }
                }
        );

        mImage.setDoubleTapListener(
                new OnImageViewTouchDoubleTapListener() {

                    @Override
                    public void onDoubleTap() {
                        Log.d(LOG_TAG, "onDoubleTap");
                    }
                }
        );

        mImage.setOnDrawableChangedListener(
                new OnDrawableChangeListener() {

                    @Override
                    public void onDrawableChanged(Drawable drawable) {
                        Log.i(LOG_TAG, "onBitmapChanged: " + drawable);
                    }
                }
        );

        final Picasso pica = Picasso.with(this);
        if (getIntent().getExtras() != null) {
            String url = getIntent().getExtras().getString(URLKEY, "");
            pica.load(url).into(mImage);
        }
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    Matrix imageMatrix;
/*
    public void selectRandomImage(boolean small) {
        Cursor c = getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, null, null, null, null);
        if (c != null) {
            int count = c.getCount();
            //int position = (int) (Math.random() * count);
            int position = 0;
            if (c.moveToPosition(position)) {
                long id = c.getLong(c.getColumnIndex(MediaStore.Images.Media._ID));
                Uri imageUri = Uri.parse(MediaStore.Images.Media.EXTERNAL_CONTENT_URI + "/" + id);

                Log.d("image", imageUri.toString());

                final DisplayMetrics metrics = getResources().getDisplayMetrics();
                int size = (int) (Math.min(metrics.widthPixels, metrics.heightPixels) / 0.75);

                if (small) {
                    size /= 3;
                }

                Bitmap bitmap = DecodeUtils.decode(this, imageUri, size, size);

                if (null != bitmap) {
                    Log.d(LOG_TAG, "screen size: " + metrics.widthPixels + "x" + metrics.heightPixels);
                    Log.d(LOG_TAG, "bitmap size: " + bitmap.getWidth() + "x" + bitmap.getHeight());
                    mImage.setImageBitmap(bitmap, null, -1, 8f);

                    mImage.setOnDrawableChangedListener(
                            new OnDrawableChangeListener() {
                                @Override
                                public void onDrawableChanged(final Drawable drawable) {
                                    Log.v(LOG_TAG, "image scale: " + mImage.getScale() + "/" + mImage.getMinScale());
                                    Log.v(LOG_TAG, "scale type: " + mImage.getDisplayType() + "/" + mImage.getScaleType());

                                }
                            }
                    );

                } else {
                    Toast.makeText(this, "Failed to load the image", Toast.LENGTH_LONG).show();
                }
            }
            c.close();
            return;
        }
    }*/
}
