package de.lindemann.niklas.erziehungstipps;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.content.ContextCompat;

/**
 * Created by Niklas on 07.10.2015.
 */
public class MyResourceManager {

    public static Bitmap getPictureByPosition(Context context, int ID){

        Bitmap picture = null;
        switch (ID){
            case 1:
                picture = BitmapFactory.decodeResource(context.getResources(), R.drawable.introduction);
                break;
            case 2:
                picture = BitmapFactory.decodeResource(context.getResources(),R.drawable.reason);
                break;
            case 3:
                picture = BitmapFactory.decodeResource(context.getResources(),R.drawable.prevent);
                break;
            case 4:
                picture = BitmapFactory.decodeResource(context.getResources(),R.drawable.act);
                break;
            case 5:
                picture = BitmapFactory.decodeResource(context.getResources(),R.drawable.calm);
                break;
            case 6:
                picture = BitmapFactory.decodeResource(context.getResources(),R.drawable.exercise);
                break;
        }

        return picture;
    }

    public static int getColorByID(Context context, int ID){
        int color = 0;

        switch (ID){
            case 1:
                color = ContextCompat.getColor(context, R.color.puzzle_1);
                break;
            case 2:
                color = ContextCompat.getColor(context, R.color.puzzle_2);
                break;
            case 3:
                color = ContextCompat.getColor(context, R.color.puzzle_3);
                break;
            case 4:
                color = ContextCompat.getColor(context, R.color.puzzle_4);
                break;
            case 5:
                color = ContextCompat.getColor(context, R.color.puzzle_5);
                break;
            case 6:
                color = ContextCompat.getColor(context, R.color.puzzle_6);
                break;
        }

        return color;
    }

}
