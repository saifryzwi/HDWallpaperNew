package com.app.interfaces;

import com.app.items.ItemCat;

import java.util.ArrayList;

public interface CategoryListener {
    void onStart();
    void onEnd(String success, ArrayList<ItemCat> arrayListCat);
}
