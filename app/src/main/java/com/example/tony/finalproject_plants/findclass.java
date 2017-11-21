package com.example.tony.finalproject_plants;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class findclass extends Fragment {
    private Button finbtn;
    private EditText inp;
    private Plant respla;
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.find, container, false);
        finbtn = view.findViewById(R.id.button);
        inp = view.findViewById(R.id.searchtext);
        finbtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                String inf = inp.getText().toString();
                Intent intent = new Intent(findclass.this.getActivity(),results.class);
                intent.putExtra("input",inf);
                respla = Plant.getPlantByName(inf);
                if (respla!=null) {
                    startActivity(intent);
                }
                else{
                    Toast toa = Toast.makeText(findclass.this.getActivity(),"No Result!",Toast.LENGTH_SHORT);
                    toa.setGravity(Gravity.CENTER,10,200);
                    toa.show();
                }
            }
        });
        return view;
    }
}
