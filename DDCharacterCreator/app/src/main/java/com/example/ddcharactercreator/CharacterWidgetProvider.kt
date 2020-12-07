package com.example.ddcharactercreator

import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.content.Intent
import android.widget.RemoteViews

class CharacterWidgetProvider : AppWidgetProvider() {
    override fun onUpdate(context: Context, appWidgetManager: AppWidgetManager, appWidgetIds: IntArray) {
        // There may be multiple widgets active, so update all of them
        for (appWidgetId in appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId)
        }
    }

    override fun onEnabled(context: Context) {
        // Enter relevant functionality for when the first widget is created
    }

    override fun onDisabled(context: Context) {
        // Enter relevant functionality for when the last widget is disabled
    }

    companion object {
        private fun updateAppWidget(context: Context, appWidgetManager: AppWidgetManager, appWidgetId: Int) {
            val views = getCharacterListView(context, (this as DetailActivity?)!!.character)

            // Instruct the widget manager to update the widget
            appWidgetManager.updateAppWidget(appWidgetId, views)
        }

        private fun getCharacterListView(context: Context, character: Character?): RemoteViews {
            val views = RemoteViews(context.packageName, R.layout.character_widget_provider)
            val widgetText: CharSequence = context.getString(R.string.select_a_character)
            if (character == null) {
                views.setTextViewText(R.id.appwidget_text, widgetText)
                val intent = Intent(context, MainActivity::class.java)
                intent.action = "MainActivity"
                intent.flags = Intent.FLAG_ACTIVITY_SINGLE_TOP
                val pendingIntent = PendingIntent.getActivity(context, 0, intent, 0)
                views.setOnClickPendingIntent(R.id.appwidget_text, pendingIntent)
            } else {
                views.setTextViewText(R.id.appwidget_text, character.getName())
                val intent = Intent(context, DetailActivity::class.java)
                intent.action = character.getName()
                intent.flags = Intent.FLAG_ACTIVITY_SINGLE_TOP
                intent.putExtra((this as DetailActivity?)!!.CHARACTER, character)
                val pendingIntent = PendingIntent.getActivity(context, 0, intent, 0)
                views.setOnClickPendingIntent(R.id.appwidget_text, pendingIntent)
            }
            return views
        }

        fun updateCharacterWidget(context: Context, appWidgetManager: AppWidgetManager, appWidgetIds: IntArray) {
            for (appWidgetId in appWidgetIds) {
                updateAppWidget(context, appWidgetManager, appWidgetId)
            }
        }
    }
}