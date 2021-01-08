package com.lzr.lbase;

import androidx.fragment.app.Fragment;

public abstract class BaseFragment extends Fragment {

    private boolean first = true;

    @Override
    public void onResume() {
        super.onResume();
        if (first) {
            loadData();
            first = false;
        }
    }

    public abstract void loadData();
}
