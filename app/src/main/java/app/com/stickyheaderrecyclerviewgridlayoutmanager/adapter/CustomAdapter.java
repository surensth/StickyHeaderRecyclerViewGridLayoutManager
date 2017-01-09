package app.com.stickyheaderrecyclerviewgridlayoutmanager.adapter;

import java.util.List;

import eu.davidea.flexibleadapter.FlexibleAdapter;
import eu.davidea.flexibleadapter.items.AbstractFlexibleItem;

/**
 * Created by surensth on 12/27/16.
 */

public class CustomAdapter extends FlexibleAdapter<AbstractFlexibleItem> {

    public CustomAdapter(List<AbstractFlexibleItem> items) {
        //true = Items implement hashCode() and have stableIds!
        super(items, true);
    }

}
