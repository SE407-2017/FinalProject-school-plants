package com.example.tony.finalproject_plants;

import android.app.Fragment;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;

import static android.app.Activity.RESULT_OK;

public class addClass extends Fragment {

    public static final int TAKE_PHOTO =1;

    private Button take_photo;
    private Button submit;
    private ImageView new_image;
    private EditText new_name;
    private EditText new_info;
    public Uri imageUri;

    @Override
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup container, Bundle savedInstanceState){
        View view=layoutInflater.inflate(R.layout.add,container,false);

        take_photo= view.findViewById(R.id.take_photo);
        submit=view.findViewById(R.id.submit);
        new_image= view.findViewById(R.id.new_plant_image);
        new_name= view.findViewById(R.id.new_plant_name);
        new_info= view.findViewById(R.id.new_plant_info);

        take_photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name=new_name.getText().toString();
                File outputImage=new File(Environment.getExternalStorageDirectory(),name+".jpg");
                try{
                    if(outputImage.exists()){
                        outputImage.delete();
                    }
                    outputImage.createNewFile();
                }catch (Exception e){
                    e.printStackTrace();
                }
                imageUri=Uri.fromFile(outputImage);
                Intent intent=new Intent("android.media.action.IMAGE_CAPTURE");
                startActivityForResult(intent,TAKE_PHOTO);
            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast toast = Toast.makeText(addClass.this.getActivity(),"添加成功!",Toast.LENGTH_SHORT);
                toast.show();
            }
        });
        return view;
    }

    @Override
    public void onActivityResult(int requestCode,int resultCode,Intent data){
        switch(requestCode){
            case TAKE_PHOTO:
                if(resultCode==RESULT_OK){
                    Bitmap bitmap=(Bitmap)data.getExtras().get("data");
                    new_image.setImageBitmap(bitmap);
                }
                break;
            default:
                break;
        }
    }
}
