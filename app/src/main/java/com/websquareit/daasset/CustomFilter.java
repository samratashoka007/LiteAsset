package com.websquareit.daasset;

import android.widget.Filter;

import com.websquareit.daasset.adapter.ListViewAdapter;

import java.util.ArrayList;
import java.util.List;

public class CustomFilter extends Filter {
    ListViewAdapter adapter;
    List<DataModel> filterList;

    public CustomFilter(List<DataModel> filterList2, ListViewAdapter adapter2) {
        this.filterList = filterList2;
        this.adapter = adapter2;
    }

    /* access modifiers changed from: protected */
    public FilterResults performFiltering(CharSequence constraint) {
        FilterResults results = new FilterResults();
        if (constraint == null || constraint.length() <= 0) {
            results.count = this.filterList.size();
            results.values = this.filterList;
        } else {
            CharSequence constraint2 = constraint.toString().toUpperCase();
            ArrayList<DataModel> filteredMovies = new ArrayList<>();
            for (int i = 0; i < this.filterList.size(); i++) {
                if (((DataModel) this.filterList.get(i)).getSiteName().toUpperCase().contains(constraint2)) {
                    filteredMovies.add(this.filterList.get(i));
                }
            }
            results.count = filteredMovies.size();
            results.values = filteredMovies;
        }
        return results;
    }

    /* access modifiers changed from: protected */
    public void publishResults(CharSequence constraint, FilterResults results) {
    }
}
