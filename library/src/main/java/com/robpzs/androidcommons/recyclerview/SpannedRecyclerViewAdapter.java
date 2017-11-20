package com.robpzs.androidcommons.recyclerview;

import android.support.v7.widget.RecyclerView;

/**
 * RecyclerViewAdapter to be used with MultiRecyclerViewAdapter and spanned LayoutManager
 * (ie. GridLayoutManager).
 * This adapter allows to set span size for each adapter.
 *
 * @author robpzs
 */
public abstract class SpannedRecyclerViewAdapter<VH extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<VH> {

    protected static final int FULL_SPAN = -1;

    public abstract int getSpanSize();

}
