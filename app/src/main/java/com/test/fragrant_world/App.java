package com.test.fragrant_world;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkInfo;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;
import com.nostra13.universalimageloader.core.display.SimpleBitmapDisplayer;

public class App extends Application {

    private static Context context;
    /** Application cash size 50 mb. */
    private static final int CASH_HEAP = 52428800;
    /** Display album images options. */
    private static DisplayImageOptions options;

    private static DisplayImageOptions roundedOptions;

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
        initImageLoader();
        initDisplayImagePhotoOptions();
        initCardImagePhotoOptions();
    }

    /** Init cashing image loader. */
    private void initImageLoader() {
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(context)
                .threadPriority(Thread.NORM_PRIORITY - 2)
                .denyCacheImageMultipleSizesInMemory()
                .diskCacheFileNameGenerator(new Md5FileNameGenerator())
                .diskCacheSize(CASH_HEAP)
                .tasksProcessingOrder(QueueProcessingType.LIFO)
                .build();
        ImageLoader.getInstance().init(config);
    }

    /** Init display album photo options. */
    private void initDisplayImagePhotoOptions() {
        options = new DisplayImageOptions.Builder()
                .displayer(new SimpleBitmapDisplayer())
                .cacheOnDisk(true)
                .bitmapConfig(Bitmap.Config.ARGB_8888)
                .build();
    }

    /** Init display album photo options. */
    private void initCardImagePhotoOptions() {
        roundedOptions = new DisplayImageOptions.Builder()
                .displayer(new RoundedBitmapDisplayer(8))
                .cacheOnDisk(true)
                .bitmapConfig(Bitmap.Config.ARGB_8888)
                .build();
    }

    /**
     * Getter for photo display options.
     * @return photo options
     */
    public static DisplayImageOptions getOptions() {
        return options;
    }

    public static DisplayImageOptions getRoundedOptions() {
        return roundedOptions;
    }

    /**
     * Get application context.
     * @return Application context.
     */
    public static Context getAppContext() {
        return context;
    }


    /**
     * Get color resource by id.
     * @param colorId color id
     * @return color
     */
    public static Integer getClr(int colorId) {
        return ContextCompat.getColor(context, colorId);
    }

    /**
     * Get drawable by resource id.
     * @param drawID resource id
     * @return drawable
     */
    public static Drawable getImage(int drawID) {
        return ContextCompat.getDrawable(context, drawID);
    }
    /**
     * Get dimen resource.
     * @param dimenID dimen resource id
     * @return resource value
     */
    public static float getDimen(int dimenID) {
        return context.getResources().getDimension(dimenID);
    }


    /**
     * Hide keyboard.
     * @param activity context
     */
    public static void hideKeyboard(Activity activity) {
        View view1 = activity.getCurrentFocus();
        if (view1 != null) {
            ((InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE))
                    .hideSoftInputFromWindow(view1.getWindowToken(), 0);
        }
    }

    /**
     * Get string by id.
     * @param strId string id
     * @return string
     */
    public static String getStr(int strId) {
        return context.getString(strId);
    }

    /**
     * Show toast message.
     * @param message message
     */
    public static void showToast(String message) {
        Toast.makeText(getAppContext(), message, Toast.LENGTH_SHORT).show();
    }

    /**
     * Show toast message.
     * @param stringID string id
     */
    public static void showToast(int stringID) {
        Toast.makeText(getAppContext(), getStr(stringID), Toast.LENGTH_SHORT).show();
    }

    /**
     * Checking for connection availability.
     * @return true if available
     */
    public static boolean isConnectionAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            if (connectivityManager != null) {
                NetworkInfo[] info = connectivityManager.getAllNetworkInfo();
                if (info != null) {
                    for (NetworkInfo anInfo : info) {
                        if (anInfo.getState() == NetworkInfo.State.CONNECTED) {
                            return true;
                        }
                    }
                }
            }
        } else {
            Network[] networks = connectivityManager.getAllNetworks();
            NetworkInfo networkInfo;
            for (Network mNetwork : networks) {
                networkInfo = connectivityManager.getNetworkInfo(mNetwork);
                if (networkInfo.getState().equals(NetworkInfo.State.CONNECTED)) {
                    return true;
                }
            }
        }
        return false;
    }
}
