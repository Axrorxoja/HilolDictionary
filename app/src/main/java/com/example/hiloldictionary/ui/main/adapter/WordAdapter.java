package com.example.hiloldictionary.ui.main.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hiloldictionary.R;
import com.example.hiloldictionary.repository.storage.db.Definition;

import java.util.ArrayList;
import java.util.List;

import timber.log.Timber;

public class WordAdapter extends RecyclerView.Adapter<WordAdapter.VH> {
    private LayoutInflater inflater;
    private ArrayList<Definition> defaultList;
    private ArrayList<Definition> originList;
    private ItemClickListener listener;

    public WordAdapter(Context ctx,
                       ArrayList<Definition> list,
                       ItemClickListener listener) {
        inflater = LayoutInflater.from(ctx);
        this.defaultList = list;
        originList = (ArrayList<Definition>) defaultList.clone();
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
        Definition definition = defaultList.get(position);
        holder.onBind(definition);
    }

    @Override
    public int getItemCount() {
        return defaultList.size();
    }

    public void updateData(List<Definition> it) {
        DiffCallback callback = new DiffCallback(defaultList, it);
        DiffUtil.DiffResult result = DiffUtil.calculateDiff(callback);
        defaultList.addAll(it);
        originList.addAll(it);
        result.dispatchUpdatesTo(this);
    }

    public void onSearch(List<Definition> it) {
        StringBuilder builder = new StringBuilder();
        for (Definition definition : it) {
            builder.append(definition.getWord()).append(" ");
        }
        Timber.d("onSearch:%s", builder.toString());
        DiffCallback callback = new DiffCallback(defaultList, it);
        DiffUtil.DiffResult result = DiffUtil.calculateDiff(callback);
        defaultList.clear();
        defaultList.addAll(it);
        //todo learn notify by payload
        result.dispatchUpdatesTo(this);
    }

    public void searchClosed() {
        onSearch(originList);
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
