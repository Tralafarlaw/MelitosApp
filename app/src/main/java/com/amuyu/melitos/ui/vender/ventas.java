package com.amuyu.melitos.ui.vender;

import androidx.appcompat.app.AppCompatDelegate;
import androidx.lifecycle.ViewModelProviders;

import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.amuyu.melitos.Adapters.VentasAdapter;
import com.amuyu.melitos.R;

import java.util.ArrayList;
import java.util.List;

import static com.amuyu.melitos.Adapters.VentasAdapter.SPAN_COUNT_ONE;
import static com.amuyu.melitos.Adapters.VentasAdapter.SPAN_COUNT_THREE;
import static com.facebook.FacebookSdk.getApplicationContext;

public class ventas extends Fragment {

    private VentasViewModel mViewModel;
    private RecyclerView recyclerView;
    private VentasAdapter itemAdapter;
    private GridLayoutManager gridLayoutManager;
    private List<VentasItem> items;

    public static ventas newInstance() {
        return new ventas();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        setHasOptionsMenu(true);

        return inflater.inflate(R.layout.ventas_fragment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(VentasViewModel.class);
        // TODO: Use the ViewModel
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);

        initItemsData();

        gridLayoutManager = new GridLayoutManager(getActivity(), SPAN_COUNT_ONE);
        itemAdapter = new VentasAdapter(items, gridLayoutManager);
        recyclerView = (RecyclerView) getActivity().findViewById(R.id.rv);
        recyclerView.setAdapter(itemAdapter);
        recyclerView.setLayoutManager(gridLayoutManager);


    }
    private void initItemsData() {
        items = new ArrayList<>(4);
        items.add(new VentasItem(R.drawable.ic_account_balance_black_24dp, "Image 1", 20));
        items.add(new VentasItem(R.drawable.ic_add_black_24dp, "Image 2", 10));
        items.add(new VentasItem(R.drawable.com_facebook_auth_dialog_background, "Image 3", 27));
        items.add(new VentasItem(R.drawable.com_facebook_auth_dialog_header_background, "Image 4", 45));
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_switch_layout, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }




    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.menu_switch_layout) {
            switchLayout();
            switchIcon(item);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void switchLayout() {
        if (gridLayoutManager.getSpanCount() == SPAN_COUNT_ONE) {
            gridLayoutManager.setSpanCount(SPAN_COUNT_THREE);
        } else {
            gridLayoutManager.setSpanCount(SPAN_COUNT_ONE);
        }
        itemAdapter.notifyItemRangeChanged(0, itemAdapter.getItemCount());
    }

    private void switchIcon(MenuItem item) {
        if (gridLayoutManager.getSpanCount() == SPAN_COUNT_THREE) {
            item.setIcon(getResources().getDrawable(R.drawable.ic_span_3));
        } else {
            item.setIcon(getResources().getDrawable(R.drawable.ic_span_1));
        }
    }



}
