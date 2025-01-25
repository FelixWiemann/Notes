package com.nepumuk.notizen.views.fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.nepumuk.notizen.R;
import com.nepumuk.notizen.core.filtersort.FilterShowAll;
import com.nepumuk.notizen.core.filtersort.ShowAllOfType;
import com.nepumuk.notizen.core.filtersort.ShowFavourites;
import com.nepumuk.notizen.core.filtersort.SortProvider;
import com.nepumuk.notizen.core.filtersort.Sorter;
import com.nepumuk.notizen.core.filtersort.ViewFilter;
import com.nepumuk.notizen.core.objects.StorageObject;
import com.nepumuk.notizen.core.utils.ResourceManager;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

/**
 * first quick and dirty implementation -> needs to be done properly
 */
public class FilterFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(com.nepumuk.notizen.core.R.layout.fragment_filter, container, false);
    }

    /**
     * Called immediately after {@link #onCreateView(LayoutInflater, ViewGroup, Bundle)}
     * has returned, but before any saved state has been restored in to the view.
     * This gives subclasses a chance to initialize themselves once
     * they know their view hierarchy has been completely created.  The fragment's
     * view hierarchy is not however attached to its parent at this point.
     *
     * @param view               The View returned by {@link #onCreateView(LayoutInflater, ViewGroup, Bundle)}.
     * @param savedInstanceState If non-null, this fragment is being re-constructed
     */
    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ListView listView = view.findViewById(com.nepumuk.notizen.core.R.id.filterselect);
        listView.setOnItemClickListener((adapterView, view1, i, l) -> {
            NavHostFragment navHostFragment = (NavHostFragment) getParentFragmentManager().findFragmentById(R.id.main_nav_host);
            if (navHostFragment.getChildFragmentManager().getFragments().get(0) instanceof MainFragment){
                ViewFilter<StorageObject> filter  = appliables.get(i).filter;
                Sorter<StorageObject> sorter = appliables.get(i).sorter;
                if (filter != null) ((MainFragment) navHostFragment.getChildFragmentManager().getFragments().get(0)).filter(filter);
                if (sorter != null) ((MainFragment) navHostFragment.getChildFragmentManager().getFragments().get(0)).sort(sorter);
            }
        });
        listAdapter = new MyArrayAdapter(requireContext(), com.nepumuk.notizen.core.R.layout.favlist_rowlayout, appliables);
        listView.setAdapter(listAdapter);
        addFilterSelect(com.nepumuk.notizen.core.R.string.filter_show_all, new FilterShowAll<>());
        for (Integer i:ShowAllOfType.availableFilters.keySet()) {
            addFilterSelect(i,ShowAllOfType.availableFilters.get(i));
        }
        //addFilterSelect(com.nepumuk.notizen.core.R.string.filter_show_text_notes, new ShowAllOfType<>(TextNote.class));
        //addFilterSelect(com.nepumuk.notizen.core.R.string.filter_show_task_notes, new ShowAllOfType<>(TaskNote.class));
        // db-access need time, therefore we add it in background
        addFilterSelect(com.nepumuk.notizen.core.R.string.filter_show_favourites, new ShowFavourites<>());
        addSortSelect(R.string.sort_by_title_ascending, SortProvider.SortByTitleAscending);
        addSortSelect(R.string.sort_by_title_descending, SortProvider.SortByTitleDescending);
        addSortSelect(R.string.sort_by_type, SortProvider.SortByType);
        addSortSelect(R.string.sort_by_lastchangedate_descending, SortProvider.SortByLastChangeDate);
        addSortSelect(R.string.sort_by_creationdate_descending, SortProvider.SortByCreateDate);
    }

    MyArrayAdapter listAdapter;
    public void addFilterSelect(@StringRes int TextRes, ViewFilter<StorageObject> filter){
        Applyable applyable = new Applyable();
        applyable.title = ResourceManager.getString(TextRes);
        applyable.filter = filter;
        this.appliables.add(applyable);
        listAdapter.notifyDataSetChanged();
    }
    public void addSortSelect(@StringRes int TextRes, Sorter<StorageObject> sorter){
        Applyable applyable = new Applyable();
        applyable.title = ResourceManager.getString(TextRes);
        applyable.sorter = sorter;
        this.appliables.add(applyable);
        listAdapter.notifyDataSetChanged();
    }

    ArrayList<Applyable>appliables = new ArrayList<>();

    class MyArrayAdapter extends ArrayAdapter<Applyable> {

        public MyArrayAdapter(@NonNull Context context, int resource, ArrayList<Applyable> list) {
            super(context, resource, list);
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            if (convertView==null){
                convertView =  getLayoutInflater().inflate(com.nepumuk.notizen.core.R.layout.favlist_rowlayout,parent, false);

            }
            ((TextView)convertView.findViewById(com.nepumuk.notizen.core.R.id.filter_line_title)).setText(getItem(position).title);
            return convertView;
        }
    }

    static class Applyable {
        public String title;
        public Sorter<StorageObject> sorter = null;
        public ViewFilter<StorageObject> filter = null;
    }
}