package com.example.cmp309;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.ArrayList;

public class MyAdapter extends PagerAdapter {

    //Set an array list for the resource ids of images for the adapter
    private ArrayList<Integer> images;
    //Set a layout inflater for this fragment
    private LayoutInflater inflater;
    private Context context;

    //Setting the main adapter function
    public MyAdapter(Context context, ArrayList<Integer> images) {
        this.context = context;
        this.images = images;
        inflater = LayoutInflater.from(context);
    }

    //Function to remove the image from view once either the user swipes or the time is up
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    //Function to get the number of images in the array, for use in the main function
    public int getCount() {
        return images.size();
    }

    //Create the object for the Image Slider
    public Object instantiateItem(ViewGroup view, int position) {
        View myImageLayout = inflater.inflate(R.layout.slide, view, false);
        ImageView myImage = (ImageView) myImageLayout.findViewById(R.id.image);
        myImage.setImageResource(images.get(position));
        view.addView(myImageLayout, 0);
        return myImageLayout;
    }

    //Implemented abstract method
    public boolean isViewFromObject(View view, Object object) {
        return view.equals(object);
    }
}
