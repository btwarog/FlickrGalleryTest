package com.example.flickrgallery.screen.photosearch.action;

import com.example.flickrgallery.screen.photosearch.model.Photo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class DataInputStreamDecoderImpl implements DataInputStreamDecoder<List<Photo>> {

    @Override
    public List<Photo> decode(InputStream inputStream) throws IOException, JSONException {
        BufferedReader streamReader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
        StringBuilder responseStrBuilder = new StringBuilder();

        String inputStr;
        while ((inputStr = streamReader.readLine()) != null)
            responseStrBuilder.append(inputStr);

        String jsonString = responseStrBuilder.toString();
        int start = "jsonFlickrFeed(".length();
        String fixedJsonString = jsonString.substring(start, jsonString.length() - 1);
        JSONObject jsonObject = new JSONObject(fixedJsonString);

        JSONArray itemsArray = jsonObject.getJSONArray("items");
        List<Photo> result = new ArrayList<>(itemsArray.length());
        for (int i = 0; i < itemsArray.length(); i++) {
            JSONObject arrayElement = itemsArray.getJSONObject(i);
            Photo photo = getPhotoFromJsonObject(arrayElement);
            result.add(photo);
        }

        return result;
    }

    private Photo getPhotoFromJsonObject(JSONObject arrayElement) throws JSONException {
        String title = arrayElement.getString("title");
        String link = arrayElement.getString("link");
        JSONObject mediaObject = arrayElement.getJSONObject("media");
        String photoUrlM = mediaObject.getString("m");
        String dateTaken = arrayElement.getString("date_taken");
        String description = arrayElement.getString("description");
        String published = arrayElement.getString("published");

        Photo photo = new Photo(
                title,
                link,
                photoUrlM,
                dateTaken,
                description,
                published
        );
        return photo;
    }
}
