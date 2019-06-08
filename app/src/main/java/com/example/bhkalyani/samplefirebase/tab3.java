package com.example.bhkalyani.samplefirebase;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Bhkalyani on 4/3/2017.
 */

public class tab3 extends Fragment
{
    private View view;
    private static RecyclerView recyclerView;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.tab3, container, false);

        return view;
    }
    private void setRecyclerView()
    {

    }
}
