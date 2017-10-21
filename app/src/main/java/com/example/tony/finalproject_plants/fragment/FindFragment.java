package com.example.tony.finalproject_plants.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.tony.finalproject_plants.R;

/**
 * Created by SHIYONG on 2017/10/15.
 */

public class FindFragment extends Fragment {
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup container, Bundle savedInstanceState){
        View findView=layoutInflater.inflate(R.layout.fragment_find,null);
        return findView;
    }
}
