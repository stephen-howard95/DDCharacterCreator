package com.example.ddcharactercreator;

import android.text.TextUtils;
import android.util.Log;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class JsonUtils {
    private static final String LOG_TAG = SpellChooserActivity.class.getName();

    private static String extractNextUrlFromJson(String spellJson){
        if(TextUtils.isEmpty(spellJson)){
            return null;
        }
        String nextUrl = new String();

        try{
            JSONObject baseJsonResponse = new JSONObject(spellJson);

            String next = baseJsonResponse.getString("next");

            nextUrl = next;
        }catch(JSONException e){
            e.printStackTrace();
        }
        return nextUrl;
    }

    private static List<Spell> extractListFromJson(String spellJson){
        if(TextUtils.isEmpty(spellJson)){
            return null;
        }
        List<Spell> spells = new ArrayList<>();

        try{

            JSONObject baseJsonResponse = new JSONObject(spellJson);
            JSONArray resultsArray = baseJsonResponse.getJSONArray("results");

            for(int i=0; i<resultsArray.length(); i++){
                JSONObject currentSpell = resultsArray.getJSONObject(i);

                String spellName = currentSpell.getString("name");

                int spellLevel = currentSpell.getInt("level_int");

                String spellRange = currentSpell.getString("range");

                String spellDuration = currentSpell.getString("duration");

                String spellConcentration = currentSpell.getString("concentration");

                String spellCastingTime = currentSpell.getString("casting_time");

                StringBuilder descriptionStringBuilder = new StringBuilder();
                String description = currentSpell.getString("desc");
                String higherLevel = currentSpell.getString("higher_level");

                descriptionStringBuilder.append(description);
                descriptionStringBuilder.append("\n");
                descriptionStringBuilder.append(higherLevel);
                
                String spellClassList = currentSpell.getString("dnd_class");

                Spell spell = new Spell(spellName, descriptionStringBuilder.toString(), spellRange, spellDuration, spellConcentration, spellCastingTime, spellLevel, spellClassList);

                spells.add(spell);
            }

        } catch (JSONException e){
            e.printStackTrace();
        }
        return spells;
    }

    public static String fetchNextUrl(String requestUrl){

        URL url = createUrl(requestUrl);

        String jsonResponse = null;
        try{
            jsonResponse = makeHttpRequest(url);
        }catch(IOException e){
            Log.e(LOG_TAG, "Problem making the HTTP request.", e);
        }

        String nextUrl = extractNextUrlFromJson(jsonResponse);

        return nextUrl;
    }

    public static List<Spell> fetchSpellData(String requestUrl) {

        URL url = createUrl(requestUrl);

        String jsonResponse = null;
        try {
            jsonResponse = makeHttpRequest(url);
        } catch (IOException e) {
            Log.e(LOG_TAG, "Problem making the HTTP request.", e);
        }

        List<Spell> spellsJson = extractListFromJson(jsonResponse);

        return spellsJson;
    }

    private static URL createUrl(String stringUrl) {
        URL url = null;
        try {
            url = new URL(stringUrl);
        } catch (MalformedURLException e) {
            Log.e(LOG_TAG, "Problem building the URL ", e);
        }
        return url;
    }
    private static String makeHttpRequest(URL url) throws IOException {
        String jsonResponse = "";

        // If the URL is null, then return early.
        if (url == null) {
            return jsonResponse;
        }

        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setReadTimeout(10000 /* milliseconds */);
            urlConnection.setConnectTimeout(15000 /* milliseconds */);
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            if (urlConnection.getResponseCode() == 200) {
                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
            } else {
                Log.e(LOG_TAG, "Error response code: " + urlConnection.getResponseCode());
            }
        } catch (IOException e) {
            Log.e(LOG_TAG, "Problem retrieving the JSON results.", e);
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (inputStream != null) {
                inputStream.close();
            }
        }
        return jsonResponse;
    }
    private static String readFromStream(InputStream inputStream) throws IOException {
        StringBuilder output = new StringBuilder();
        if (inputStream != null) {
            InputStreamReader inputStreamReader = null;
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT) {
                inputStreamReader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
            }
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();
            while (line != null) {
                output.append(line);
                line = reader.readLine();
            }
        }
        return output.toString();
    }
}
