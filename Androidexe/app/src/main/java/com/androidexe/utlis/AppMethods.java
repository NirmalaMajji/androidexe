package com.androidexe.utlis;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.view.View;
import android.widget.ImageView;

import com.androidexe.R;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

import androidx.annotation.Nullable;

public class AppMethods {

   public static void setRoundedCornerImage(Activity activity, String image, ImageView iv) {

      Glide.with(activity).load(image).placeholder(R.drawable.ic_dummy).addListener(new RequestListener<Drawable>() {
         @Override
         public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
            iv.setVisibility(View.GONE);

            return false;
         }

         @Override
         public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
            iv.setVisibility(View.VISIBLE);
            return false;
         }
      }).into(iv);
   }

   public static boolean isConnectingToInternet(Context context) {
      try {
         ConnectivityManager connectivity = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
         if (connectivity != null) {
            NetworkInfo[] info = connectivity.getAllNetworkInfo();
            if (info != null)
               for (int i = 0; i < info.length; i++)
                  if (info[i].getState() == NetworkInfo.State.CONNECTED) {

                     return true;
                  }
         }
      } catch (Exception e) {
         e.printStackTrace();
      }
      return false;
   }

   public static void alertForNoInternet(final Context context) {

      AlertDialog.Builder alertDialogBuilder;
      if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
         alertDialogBuilder = new AlertDialog.Builder(context, android.R.style.Theme_Material_Light_Dialog_Alert);
      } else {
         alertDialogBuilder = new AlertDialog.Builder(context);
      }
      alertDialogBuilder.setMessage(context.getResources().getString(R.string.no_internet_connection)).setCancelable(false).setPositiveButton(context.getResources().getString(R.string.ok),
              (dialog, id) -> dialog.cancel());

      AlertDialog alertDialog = alertDialogBuilder.create();
      alertDialog.show();

   }

}
