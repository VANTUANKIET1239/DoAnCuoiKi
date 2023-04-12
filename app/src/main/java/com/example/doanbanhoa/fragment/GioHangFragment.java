package com.example.doanbanhoa.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.os.ConditionVariable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.doanbanhoa.R;



public class GioHangFragment extends Fragment {

    public GioHangFragment() {
        // Required empty public constructor
    }


    public static GioHangFragment newInstance() {
        GioHangFragment fragment = new GioHangFragment();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_gio_hang, container, false);
    }
}