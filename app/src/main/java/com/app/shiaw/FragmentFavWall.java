package com.app.shiaw;

import android.content.Intent;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.OvershootInterpolator;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.app.adapter.AdapterWallpaperFav;
import com.app.interfaces.InterAdListener;
import com.app.interfaces.RecyclerViewClickListener;
import com.app.items.ItemWallpaper;
import com.app.utils.Constant;
import com.app.utils.DBHelper;
import com.app.utils.Methods;

import java.util.ArrayList;

import jp.wasabeef.recyclerview.adapters.AnimationAdapter;
import jp.wasabeef.recyclerview.adapters.ScaleInAnimationAdapter;

public class FragmentFavWall extends Fragment {

    DBHelper dbHelper;
    Methods methods;
    RecyclerView recyclerView;
    AdapterWallpaperFav adapter;
    ArrayList<ItemWallpaper> arrayList;
    ProgressBar progressBar;
    TextView textView_empty;
    int pos;
    GridLayoutManager grid;
    InterAdListener interAdListener;

    public static FragmentFavWall newInstance(int position) {
        FragmentFavWall fragment = new FragmentFavWall();
        Bundle args = new Bundle();
        args.putInt("pos", position);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_wallpaper, container, false);

        pos = getArguments().getInt("pos");

        interAdListener = new InterAdListener() {
            @Override
            public void onClick(int position, String type) {
                Intent intent = new Intent(getActivity(), WallPaperDetailsActivity.class);
                intent.putExtra("pos", position);
                Constant.arrayList.clear();
                Constant.arrayList.addAll(arrayList);
                startActivity(intent);
            }
        };

        dbHelper = new DBHelper(getActivity());
        methods = new Methods(getActivity(), interAdListener);
        arrayList = new ArrayList<>();
        arrayList.addAll(dbHelper.getWallpapers("fav",""));

        progressBar = rootView.findViewById(R.id.pb_wall);
        progressBar.setVisibility(View.GONE);
        textView_empty = rootView.findViewById(R.id.tv_empty_wall);

        recyclerView = rootView.findViewById(R.id.rv_wall);
        recyclerView.setHasFixedSize(true);
        grid = new GridLayoutManager(getActivity(), 2);

        recyclerView.setLayoutManager(grid);
        adapter = new AdapterWallpaperFav(getActivity(), arrayList, new RecyclerViewClickListener() {
            @Override
            public void onClick(int position) {
                methods.showInter(position,"");
            }
        });
        AnimationAdapter adapterAnim = new ScaleInAnimationAdapter(adapter);
        adapterAnim.setFirstOnly(true);
        adapterAnim.setDuration(500);
        adapterAnim.setInterpolator(new OvershootInterpolator(.9f));
        recyclerView.setAdapter(adapterAnim);
        setExmptTextView();

        return rootView;
    }

    private void setExmptTextView() {
        if (arrayList.size() == 0) {
            textView_empty.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.GONE);
        } else {
            recyclerView.setVisibility(View.VISIBLE);
            textView_empty.setVisibility(View.GONE);
        }
    }
}
