package com.robpzs.androidcommons.recyclerview;

import android.content.Context;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.StringRes;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * ViewAdapter to be used as a Header.
 * Layout resource must have a TextView with ID "text".
 *
 * @author robpzs
 */
public class HeaderViewAdapter extends SpannedRecyclerViewAdapter<HeaderViewAdapter.ViewHolder> {

    @LayoutRes
    private int layoutRes;

    private String text;
    @StringRes
    private int textRes;

    /**
     *
     * @param layoutRes Header layout resource.
     * @param text Text.
     */
    public HeaderViewAdapter(@LayoutRes int layoutRes, String text) {
        this.layoutRes = layoutRes;
        this.text = text;
    }

    /**
     *
     * @param layoutRes Header layout resource.
     * @param textRes Text resource.
     */
    public HeaderViewAdapter(@LayoutRes int layoutRes, @StringRes int textRes) {
        this.layoutRes = layoutRes;
        this.textRes = textRes;
    }

    @Override
    public int getSpanSize() {
        return FULL_SPAN;
    }

    @Override
    public HeaderViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        return new ViewHolder(inflater.inflate(layoutRes, parent, false));
    }

    @Override
    public void onBindViewHolder(HeaderViewAdapter.ViewHolder holder, int position) {
        if (text != null) {
            holder.text.setText(this.text);
        } else {
            holder.text.setText(this.textRes);
        }
    }

    @Override
    public int getItemCount() {
        return 1;
    }

    /**
     *
     */
    static class ViewHolder extends RecyclerView.ViewHolder {

        TextView text;

        ViewHolder(View view) {
            super(view);
            Context context = view.getContext();
            @IdRes int resId = context.getResources().getIdentifier("text", "id", context.getPackageName());
            this.text = view.findViewById(resId);
        }

    }
}
