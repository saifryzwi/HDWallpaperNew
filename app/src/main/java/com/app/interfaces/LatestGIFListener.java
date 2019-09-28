package com.app.interfaces;

import com.app.items.ItemGIF;

import java.util.ArrayList;

public interface LatestGIFListener {
    void onStart();
    void onEnd(String success, ArrayList<ItemGIF> arrayListCat);
}