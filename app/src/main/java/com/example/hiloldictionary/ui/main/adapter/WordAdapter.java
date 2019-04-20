package com.example.hiloldictionary.ui.main.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hiloldictionary.R;
import com.example.hiloldictionary.repository.storage.db.Definition;

import java.util.ArrayList;
import java.util.List;

public class WordAdapter extends RecyclerView.Adapter<WordAdapter.VH> {
    private LayoutInflater inflater;
    private ArrayList<Definition> list;
    private ItemClickListener listener;

    public WordAdapter(Context ctx,
                       ArrayList<Definition> list,
                       ItemClickListener listener) {
        inflater = LayoutInflater.from(ctx);
        this.list = list;
        this.listener = listener;
    }

    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent,
                                 int viewType) {
        View view = inflater.inflate(R.layout.word_item, parent, false);
        return new VH(view);
    }

    @Override
    public void onBindViewHolder(@NonNull VH holder,
                                 int position) {
        Definition definition = list.get(position);
        holder.onBind(definition);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void updateData(List<Definition> it) {
        int oldSize = list.size();
        list.addAll(it);
        notifyItemRangeInserted(oldSize, it.size());
    }

    public void onSearchUpdate(List<Definition> it) {
        int oldSize = list.size();
        list.clear();
        list.addAll(it);
        notifyItemRangeRemoved(0,oldSize);
        notifyItemRangeInserted(0,list.size());
    }

    class VH extends RecyclerView.ViewHolder {
        private AppCompatTextView tvName;
        private Definition definition;

        VH(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tv_name);
            itemView.setOnClickListener(v -> listener.onItemClick(definition));

        }

        void onBind(Definition definition) {
            tvName.setText(definition.getWord());
            this.definition = definition;
        }
    }
}
