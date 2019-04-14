package com.example.hiloldictionary.ui.main.diff_util;

import com.example.hiloldictionary.repository.storage.db.Definition;

import java.util.List;

import androidx.recyclerview.widget.DiffUtil;

public class DiffUtillCallBack extends DiffUtil.Callback {
    private List<Definition> oldList;
    private List<Definition> newList;

    public DiffUtillCallBack(List<Definition> oldList, List<Definition> newList) {
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
        return oldList.get(oldItemPosition).getId().equals(newList.get(newItemPosition).getId());

    }

    @Override
    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
        Definition oldDefinition = oldList.get(oldItemPosition);
        Definition newDefinition = newList.get(newItemPosition);

        return oldDefinition.getWord().equals(newDefinition.getWord()) &&
                oldDefinition.getDefinition().equals(newDefinition.getDefinition());
    }
}
