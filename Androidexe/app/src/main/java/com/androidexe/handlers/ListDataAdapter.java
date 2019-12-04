package com.androidexe.handlers;

import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.androidexe.R;
import com.androidexe.databinding.RowListDataBinding;
import com.androidexe.model.ListData;
import com.androidexe.utlis.AppMethods;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

public class ListDataAdapter extends RecyclerView.Adapter<ListDataAdapter.ViewHolder> {
   private ArrayList<ListData> arrayList;
   private Activity activity;
   private LayoutInflater layoutInflater;

   private static final String TAG = "ListDataAdapter";

   public ListDataAdapter(ArrayList<ListData> arrayList, Activity activity) {
      this.arrayList = arrayList;
      this.activity = activity;
   }

   @NonNull
   @Override
   public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
      if (layoutInflater == null) {
         layoutInflater = LayoutInflater.from(parent.getContext());
      }

      RowListDataBinding binding = DataBindingUtil.inflate(layoutInflater, R.layout.row_list_data, parent, false);

      return new ViewHolder(binding);

   }

   @Override
   public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
      ListData model = arrayList.get(position);

      holder.binding.rowListDataTitleTv.setText(model.getTitle());
      holder.binding.rowListDataDesTv.setText(model.getDescription());
      AppMethods.setRoundedCornerImage(activity, model.getImageHref(), holder.binding.rowListDataIv);

   }


   @Override
   public int getItemCount() {
      return arrayList.size();
   }

   class ViewHolder extends RecyclerView.ViewHolder {
      RowListDataBinding binding;

      ViewHolder(@NonNull RowListDataBinding itemView) {
         super(itemView.getRoot());
         binding = itemView;
      }
   }
}
