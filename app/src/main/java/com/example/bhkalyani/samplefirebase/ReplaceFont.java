package com.example.bhkalyani.samplefirebase;

import android.content.Context;
import android.graphics.Typeface;

import java.lang.reflect.Field;

/**
 * Created by Bhkalyani on 3/12/2017.
 */

public class ReplaceFont
{
    public static void ReplaceDefaultFont(Context context,String DefaultFontReplaced,String NewFontName)
    {
        Typeface customfont = Typeface.createFromAsset(context.getAssets(),NewFontName);
        replaceFont(DefaultFontReplaced,customfont);
    }

    private static void replaceFont(String defaultFontReplaced, Typeface customfont)
    {
        try {
            Field myField = Typeface.class.getDeclaredField(defaultFontReplaced);
            myField.setAccessible(true);
            myField.set(null,customfont);

        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }
}
