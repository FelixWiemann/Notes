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
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.nepumuk.notizen.R;
import com.nepumuk.notizen.core.filtersort.FilterShowAll;
import com.nepumuk.notizen.core.filtersort.ShowAllOfType;
import com.nepumuk.notizen.core.filtersort.ShowFavourites;
import com.nepumuk.notizen.core.filtersort.ViewFilter;
import com.nepumuk.notizen.core.objects.StorageObject;
import com.nepumuk.notizen.core.utils.db_access.AppDataBaseHelper;
import com.nepumuk.notizen.tasks.objects.TaskNote;
import com.nepumuk.notizen.textnotes.objects.TextNote;

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
            new Thread(()-> {
                if (navHostFragment.getChildFragmentManager().getFragments().get(0) instanceof MainFragment){
                    ViewFilter<StorageObject> filter;
                    switch (i){
                        case 1:
                            filter = new ShowFavourites<>(AppDataBaseHelper.getInstance().appDataBase.favouriteDAO().getAll());
                            break;
                        case 2:
                            filter = new ShowAllOfType<>(TaskNote.class);
                            break;
                        case 3:
                            filter = new ShowAllOfType<>(TextNote.class);
                            break;
                        default:
                            filter = new FilterShowAll<>();

                    }
                    requireActivity().runOnUiThread(()-> ((MainFragment) navHostFragment.getChildFragmentManager().getFragments().get(0)).filter(filter));
                }
            }).start();
        });
        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add("show all");
        arrayList.add("Show Favourites");
        arrayList.add("Only show task notes");
        arrayList.add("Only show text notes");
        MyArrayAdapter listAdapter = new MyArrayAdapter(requireContext(), com.nepumuk.notizen.core.R.layout.favlist_rowlayout,arrayList);
        listView.setAdapter(listAdapter);
        listAdapter.notifyDataSetChanged();

    }

    class MyArrayAdapter extends ArrayAdapter<String> {

        public MyArrayAdapter(@NonNull Context context, int resource, ArrayList<String> list) {
            super(context, resource, list);
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            if (convertView==null){
                convertView =  getLayoutInflater().inflate(com.nepumuk.notizen.core.R.layout.favlist_rowlayout,parent, false);

            }
            ((TextView)convertView.findViewById(com.nepumuk.notizen.core.R.id.filter_line_title)).setText(getItem(position));
            return convertView;
        }
    }
}