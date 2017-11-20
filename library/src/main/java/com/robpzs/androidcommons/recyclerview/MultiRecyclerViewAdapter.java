package com.robpzs.androidcommons.recyclerview;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import java.util.List;

/**
 * RecyclerView.Adapter which holds and manages multiple adapters with their individual ViewHolders.
 *
 * @author robpzs
 */
public class MultiRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<RecyclerView.Adapter> adapters;

    public MultiRecyclerViewAdapter(List<RecyclerView.Adapter> adapters) {
        this.adapters = adapters;

        for (final RecyclerView.Adapter adapter : adapters) {
            adapter.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
                @Override
                public void onChanged() {
                    notifyDataSetChanged();
                }

                @Override
                public void onItemRangeChanged(int positionStart, int itemCount) {
                    notifyItemRangeChanged(positionStart, itemCount);
                }

                @Override
                public void onItemRangeInserted(int positionStart, int itemCount) {
                    notifyItemRangeInserted(positionStart, itemCount);
                }

                @Override
                public void onItemRangeRemoved(int positionStart, int itemCount) {
                    notifyItemRangeRemoved(positionStart, itemCount);
                }
            });
        }
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        if (recyclerView.getLayoutManager() instanceof GridLayoutManager) {
            final GridLayoutManager manager = (GridLayoutManager) recyclerView.getLayoutManager();
            manager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                @Override
                public int getSpanSize(int position) {
                    RecyclerView.Adapter adapter = getChildAdapterAtPosition(position);

                    if (adapter instanceof SpannedRecyclerViewAdapter) {
                        int size = ((SpannedRecyclerViewAdapter) adapter).getSpanSize();
                        return size == SpannedRecyclerViewAdapter.FULL_SPAN
                                ? manager.getSpanCount() : size;
                    } else {
                        return 1;
                    }
                }

            });
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.Adapter adapter = getChildAdapter(viewType);
        return adapter.createViewHolder(parent, 0);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        RecyclerView.Adapter adapter = getChildAdapterAtPosition(position);
        int positionInChild = getPositionInChildAdapter(position);
        adapter.onBindViewHolder(viewHolder, positionInChild);
    }

    @Override
    public int getItemCount() {
        int count = 0;
        for (RecyclerView.Adapter adapter : adapters) {
            count += adapter.getItemCount();
        }
        return count;
    }

    @Override
    public int getItemViewType(int position) {
        int count = 0;
        for (int i = 0; i < adapters.size(); i++) {
            count += adapters.get(i).getItemCount();
            if (position < count) {
                return i;
            }
        }
        throw new IllegalArgumentException("Unknown view type");
    }

    /**
     * Gets child adapter by the global item position.
     * @param position Global item position.
     * @return Adapter.
     */
    private RecyclerView.Adapter getChildAdapterAtPosition(int position) {
        int viewType = getItemViewType(position);
        return getChildAdapter(viewType);
    }

    /**
     * Gets the child adapter for the corresponding view type.
     * @param viewType View type.
     * @return Adapter.
     */
    private RecyclerView.Adapter getChildAdapter(int viewType) {
        return adapters.get(viewType);
    }

    /**
     * Gets actual item position in the inner adapter.
     * @param position The position of the item within the adapter's data set.
     * @return The position of the item within the inner adapter.
     */
    private int getPositionInChildAdapter(int position) {
        int adapterItemCount;
        for (int i = 0; i < adapters.size(); i++) {
            adapterItemCount = adapters.get(i).getItemCount();
            if (position - adapterItemCount < 0) {
                break;
            }
            position -= adapterItemCount;
        }
        return position;
    }

}
