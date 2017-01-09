package app.com.stickyheaderrecyclerviewgridlayoutmanager;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import app.com.stickyheaderrecyclerviewgridlayoutmanager.adapter.CustomAdapter;
import app.com.stickyheaderrecyclerviewgridlayoutmanager.model.HeaderItem;
import app.com.stickyheaderrecyclerviewgridlayoutmanager.model.SimpleItem;
import butterknife.ButterKnife;
import eu.davidea.flexibleadapter.items.AbstractFlexibleItem;
import eu.davidea.flexibleadapter.items.IHeader;

/**
 * Created by surensth on 12/27/16.
 */

public class SaleFragment extends Fragment {
    RecyclerView mRecyclerView;
    CustomAdapter mAdapter;
    private List<AbstractFlexibleItem> mList;

    public static SaleFragment newInstance() {
        SaleFragment fragment = new SaleFragment();
        return fragment;
    }

    public SaleFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sale, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        createAbstrastFlexibleItemList();
        mRecyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);

        setUpAdapter();
//        setUpRecyclerView();

    }

    private void createAbstrastFlexibleItemList() {
        HeaderItem header = null;
        mList = new ArrayList<>();
        int lastHeaderId = 0;
        for (int i = 0; i < 400; i++) {
            header = i % Math.round(400 /70) == 0 ? newHeader(++lastHeaderId) : header;
            mList.add(newSimpleItem(i + 1, header));
        }
    }

    public static HeaderItem newHeader(int i) {
        HeaderItem header = new HeaderItem("H" + i);
        header.setTitle("Header " + i);
        //header is hidden and un-selectable by default!
        return header;
    }

    public static SimpleItem newSimpleItem(int i, IHeader header) {
        SimpleItem item = new SimpleItem("I" + i, (HeaderItem) header);
        item.setTitle("Simple Item " + i);
        return item;
    }

    private void setUpRecyclerView() {


    }

    public GridLayoutManager makingGridLayoutManager() {
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 2);
        gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                Log.v("testo","data position view type "+mAdapter.getItemViewType(position));

                //NOTE: If you use simple integer to identify the ViewType,
                //here, you should use them and not Layout integers
                switch (mAdapter.getItemViewType(position)) {

                    case R.layout.recycler_header_item:
                        return 2;
                    default:
                        return 1;
                }
            }
        });
        return gridLayoutManager;
    }

    private void setUpAdapter() {
        mAdapter = new CustomAdapter(mList);
        mAdapter.expandItemsAtStartUp()
                .setAutoCollapseOnExpand(false)
                .setMinCollapsibleLevel(1)//Auto-collapse only items with level >= 1 (avoid to collapse also sections!)
                .setAutoScrollOnExpand(true)
                .setRemoveOrphanHeaders(false);

        mRecyclerView.setLayoutManager(makingGridLayoutManager());
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setDisplayHeadersAtStartUp(true);
        mAdapter.enableStickyHeaders();
    }
}
