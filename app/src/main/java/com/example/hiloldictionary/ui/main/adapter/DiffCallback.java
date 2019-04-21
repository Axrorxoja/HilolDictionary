package com.example.hiloldictionary.ui.main.adapter;

import androidx.recyclerview.widget.DiffUtil;

import com.example.hiloldictionary.repository.storage.db.Definition;

import java.util.List;

public class DiffCallback extends DiffUtil.Callback {
    private List<Definition> oldList;
    private List<Definition> newList;

    public DiffCallback(List<Definition> oldList,
                        List<Definition> newList) {
        this.oldList = oldList;
        this.newList = newList;
    }

    @Override

    public int getOldListSize() {
        return oldList.size();
    }

    @Override
    public int getNewListSize() {
        return newList.size();
    }

    @Override
    public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
        Definition oldItem = oldList.get(oldItemPosition);
        Definition newItem = newList.get(newItemPosition);
        return oldItem.getId().equals(newItem.getId());
    }

    @Override
    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
        Definition oldItem = oldList.get(oldItemPosition);
        Definition newItem = newList.get(newItemPosition);
        return oldItem.equals(newItem);
    }
}
