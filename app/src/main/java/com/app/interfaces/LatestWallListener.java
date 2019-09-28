package com.app.interfaces;

import com.app.items.ItemWallpaper;

import java.util.ArrayList;

public interface LatestWallListener {
    void onStart();
    void onEnd(String success, ArrayList<ItemWallpaper> arrayListCat);
}
