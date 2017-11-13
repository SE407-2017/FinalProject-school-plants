package com.example.tony.finalproject_plants;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

/**
 * Created by user on 2017/10/23.
 */

public class findclass extends Fragment {
    private Button finbtn;
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.find, container, false);
        finbtn = view.findViewById(R.id.button);
        finbtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(findclass.this.getActivity(),results.class);
                startActivity(intent);
            }
        });
        return view;
    }
}
