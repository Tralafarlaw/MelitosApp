package com.amuyu.melitos.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.amuyu.melitos.C;
import com.amuyu.melitos.R;
import com.amuyu.melitos.ui.ProductoDialog.ProductDIalog;

public class ProductsAdapter extends RecyclerView.Adapter<ProductsAdapter.MyVH> implements View.OnClickListener {
    FragmentManager manager;
    public ProductsAdapter(FragmentManager a){
        manager = a;
    }
    @NonNull
    @Override
    public MyVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View viewv = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_item, parent, false);
        return new MyVH(viewv);
    }

    @Override
    public void onBindViewHolder(@NonNull MyVH h, int position) {

        h.ImageText.setText(C.TEST_INV[position]);
        h.Nombre.setText(C.TEST_INV[position]);
        h.Stock.setText(C.TEST_INV[position]);
        h.Precio.setText(C.TEST_INV[position]);
        h.mRoot.setOnClickListener(this);
    }

    @Override
    public int getItemCount() {
        return C.TEST_INV.length;
    }

    @Override
    public void onClick(View view) {
        DialogFragment dialogFragment = ProductDIalog.Companion.newInstance();
        dialogFragment.show(manager, "TAG_DIALOG");
    }

    class MyVH extends RecyclerView.ViewHolder {
        TextView Nombre, Precio, Stock, ImageText;
        View mRoot;
        public MyVH(@NonNull View itemView) {
            super(itemView);
            ImageText = itemView.findViewById(R.id.item_image_text);
            Nombre = itemView.findViewById(R.id.item_product_title);
            Precio = itemView.findViewById(R.id.item_product_price);
            Stock = itemView.findViewById(R.id.item_product_stock);
            mRoot = itemView;
        }
    }
}
