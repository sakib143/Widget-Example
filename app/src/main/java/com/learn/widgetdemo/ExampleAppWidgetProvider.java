package com.learn.widgetdemo;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.widget.RemoteViews;

import com.learn.widgetdemo.activity.ConfigActivity;
import com.learn.widgetdemo.activity.MainActivity;

public class ExampleAppWidgetProvider extends AppWidgetProvider {

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        super.onUpdate(context, appWidgetManager, appWidgetIds);

        Intent intent = new Intent(context, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context,0,intent,0);


        SharedPreferences prefs = context.getSharedPreferences(ConfigActivity.SHARED_PREFS, Context.MODE_PRIVATE);
        String buttonText = prefs.getString(ConfigActivity.KEY_BUTTON_TEXT + appWidgetIds, "Press me");

        RemoteViews remoteViews = new RemoteViews(context.getPackageName(),R.layout.example_widget);
        remoteViews.setOnClickPendingIntent(R.id.btnPress,pendingIntent);
        remoteViews.setCharSequence(R.id.btnPress, "setText", buttonText);

        appWidgetManager.updateAppWidget(appWidgetIds,remoteViews);

    }
}
