package rocks.itsnotrocketscience.bejay.search;

import android.support.v7.widget.RecyclerView;

public abstract class BaseAdapter<VH extends RecyclerView.ViewHolder, T> extends RecyclerView.Adapter<VH> {
    public abstract T getItem(int position);
}
