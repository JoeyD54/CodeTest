package com.example.integralcodetest.ui.home;


import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.integralcodetest.R;

import static android.content.ContentValues.TAG;


public class HomeFragment extends Fragment{

    private HomeViewModel homeViewModel;
    private TextView textView, name, postText;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);

        textView = root.findViewById(R.id.text_home);
        name = root.findViewById(R.id.profileName);
        postText = root.findViewById(R.id.postText);

        homeViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });

        //String message = (String) getArguments().getString("message");
        //Log.i(TAG, "onCreateView: Passed messsage = " + message);

        return root;
    }
}