package com.amuyu.melitos.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.amuyu.melitos.R;
import com.amuyu.melitos.ui.vender.VentasItem;

import java.util.List;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import static com.facebook.FacebookSdk.getApplicationContext;

public class VentasAdapter extends RecyclerView.Adapter<VentasAdapter.ItemViewHolder>{
    public static final int SPAN_COUNT_ONE = 1;
    public static final int SPAN_COUNT_THREE = 3;

    private static final int VIEW_TYPE_SMALL = 1;
    private static final int VIEW_TYPE_BIG = 2;

    private List<VentasItem> mItems;
    private GridLayoutManager mLayoutManager;

    public VentasAdapter(List<VentasItem> items, GridLayoutManager layoutManager) {
        mItems = items;
        mLayoutManager = layoutManager;
    }

    @Override
    public int getItemViewType(int position) {
        int spanCount = mLayoutManager.getSpanCount();
        if (spanCount == SPAN_COUNT_ONE) {
            return VIEW_TYPE_BIG;
        } else {
            return VIEW_TYPE_SMALL;
        }
    }

    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        if (viewType == VIEW_TYPE_BIG) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.vender_item, parent, false);
        } else {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.vender_item2, parent, false);
        }
        return new ItemViewHolder(view, viewType);
    }

    @Override
    public void onBindViewHolder(ItemViewHolder holder, int position) {
        //VentasItem item = mItems.get(position);
//        String a = item.getTitle();
//        holder.title.setText("" + a);
//        holder.iv.setImageResource(item.getImgResId());
//        holder.info.setText(item.getPrice());
//        if (getItemViewType(position) == VIEW_TYPE_BIG) {
//            holder.info.setText(item.getPrice());
//        }

        //CODIGO PARA VERIFICAR LOS CHECKBOX
//        holder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
//                Toast toast = Toast.makeText(compoundButton.getContext(), position, Toast.LENGTH_SHORT);
//                toast.setMargin(50,50);
//                toast.show();
//            }
//        });

    }

    @Override
    public int getItemCount() {
        return 30;
    }

    class ItemViewHolder extends RecyclerView.ViewHolder{
        ImageView iv;
        TextView title;
        TextView info;
        //CheckBox checkBox;

        ItemViewHolder(View itemView, int viewType) {
            super(itemView);
            if (viewType == VIEW_TYPE_BIG) {
                iv = (ImageView) itemView.findViewById(R.id.rowImageView);
                title = (TextView) itemView.findViewById(R.id.rowTextView);
                info = (TextView) itemView.findViewById(R.id.rowTextView2);
                //checkBox = (CheckBox) itemView.findViewById(R.id.rowCheckBox);
            } else {
                iv = (ImageView) itemView.findViewById(R.id.SrowImageView);
                title = (TextView) itemView.findViewById(R.id.SrowTextView);
                info = (TextView) itemView.findViewById(R.id.SrowTextView2);
            }
        }
    }



}
