package com.learn.widgetdemo;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.RemoteViews;
import android.widget.Toast;

import com.learn.widgetdemo.activity.ConfigActivity;
import com.learn.widgetdemo.activity.MainActivity;

public class ExampleAppWidgetProvider extends AppWidgetProvider {

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        super.onUpdate(context, appWidgetManager, appWidgetIds);

        Toast.makeText(context, "onUpdate ", Toast.LENGTH_SHORT).show();

        for (int appWidgetId : appWidgetIds) {
            Intent intent = new Intent(context, MainActivity.class);
            PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);

            SharedPreferences prefs = context.getSharedPreferences(ConfigActivity.SHARED_PREFS, Context.MODE_PRIVATE);
            String buttonText = prefs.getString(ConfigActivity.KEY_BUTTON_TEXT + appWidgetId, "Press me");


            Intent serviceIntent = new Intent(context, WidgetService.class);
            serviceIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
            serviceIntent.setData(Uri.parse(serviceIntent.toUri(Intent.URI_INTENT_SCHEME)));

            RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.example_widget);
            views.setOnClickPendingIntent(R.id.edit_text_button, pendingIntent);
            views.setCharSequence(R.id.edit_text_button, "setText", buttonText);
            views.setRemoteAdapter(R.id.example_widget_stack_view, serviceIntent);
            views.setEmptyView(R.id.example_widget_stack_view, R.id.example_widget_empty_view);


            Bundle appWidgetOptions = appWidgetManager.getAppWidgetOptions(appWidgetId);
            resizeWidget(appWidgetOptions, views);

            appWidgetManager.updateAppWidget(appWidgetId, views);
        }
    }

    @Override
    public void onAppWidgetOptionsChanged(Context context, AppWidgetManager appWidgetManager, int appWidgetId, Bundle newOptions) {
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.example_widget);
//        resizeWidget(newOptions,views);

        int minWidth = newOptions.getInt(AppWidgetManager.OPTION_APPWIDGET_MIN_WIDTH);
        int maxWidth = newOptions.getInt(AppWidgetManager.OPTION_APPWIDGET_MAX_WIDTH);
        int minHeight = newOptions.getInt(AppWidgetManager.OPTION_APPWIDGET_MIN_HEIGHT);
        int maxHeight = newOptions.getInt(AppWidgetManager.OPTION_APPWIDGET_MAX_HEIGHT);

        String dimension = "Min width: " + minWidth + "\n maxWidth: " + maxWidth + "\n minHeight: " + minHeight + "\n maxHeight:" + maxHeight;

        Toast.makeText(context, dimension , Toast.LENGTH_SHORT).show();

        if(maxHeight > 100) {
            views.setViewVisibility(R.id.tvTitle,View.VISIBLE);
            views.setViewVisibility(R.id.btnPress, View.VISIBLE);
        } else {
            views.setViewVisibility(R.id.tvTitle,View.GONE);
            views.setViewVisibility(R.id.btnPress, View.VISIBLE);
        }

        appWidgetManager.updateAppWidget(appWidgetId,views);

    }

    private void resizeWidget(Bundle newOptions, RemoteViews  views) {

//        //if device is in portrait mode then minWidth and maxHeight are used
//        //If device is landscap mode then maxWidth and minHeight are used
//
//
//
//
//        if(maxHeight > 100 ) {
//            views.setViewVisibility(R.id.tvTitle, View.VISIBLE);
//        } else {
//            views.setViewVisibility(R.id.tvTitle, View.GONE);
//        }
    }

    @Override
    public void onDeleted(Context context, int[] appWidgetIds) {
        Toast.makeText(context, "onDeleted ", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onEnabled(Context context) {
        Toast.makeText(context, "onEnabled ", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDisabled(Context context) {
        Toast.makeText(context, "onDisabled ", Toast.LENGTH_SHORT).show();
    }


}
