package com.example.flickrgallery.util;

import android.support.annotation.NonNull;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class SerializableArrayUtil {
    public static ArrayList<? extends Serializable> asSerializableArrayList(
            @NonNull
                    List<? extends Serializable> list) {
        if (list == null) {
            throw new NullPointerException("list must be not null");
        }

        if (list instanceof ArrayList) {
            return (ArrayList<? extends Serializable>) list;
        } else {
            ArrayList<Serializable> arrayList = new ArrayList<>(list.size());
            arrayList.addAll(list);
            return arrayList;
        }
    }
}
