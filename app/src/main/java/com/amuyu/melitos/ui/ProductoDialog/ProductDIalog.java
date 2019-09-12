package com.amuyu.melitos.ui.ProductoDialog;

import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.amuyu.melitos.R;

public class ProductDIalog extends DialogFragment {

    private ProductDialogViewModel mViewModel;

    public static ProductDIalog newInstance() {
        return new ProductDIalog();
    }

    public ProductDIalog getInstance(){
        ProductDIalog a = new ProductDIalog();
        return a;
    }
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.product_dialog_fragment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(ProductDialogViewModel.class);
        // TODO: Use the ViewModel
    }

}
