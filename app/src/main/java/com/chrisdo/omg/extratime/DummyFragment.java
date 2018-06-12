package com.chrisdo.omg.extratime;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.TextView;

import com.chrisdo.omg.extratime.data.StatsData;
import com.chrisdo.omg.extratime.data.User;

import org.w3c.dom.Text;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

public class DummyFragment extends Fragment {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
            }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        TextView tv = view.findViewById(R.id.dummy_text_view);
        tv.setText(getArguments().getString("dummyText"));

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {



        return inflater.inflate(R.layout.dummy_layout, container, false);

    }

    @Override
    public void onPause() {
        super.onPause();
    }
}
