package rocks.itsnotrocketscience.bejay.search;

import android.support.annotation.LayoutRes;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public abstract class ViewHolderFactory<VH extends RecyclerView.ViewHolder> {
    private final int layoutResource;
    private final LayoutInflater layoutInflater;

    public ViewHolderFactory(@LayoutRes int layoutResource, LayoutInflater layoutInflater) {
        this.layoutResource = layoutResource;
        this.layoutInflater = layoutInflater;
    }

    public VH create(ViewGroup parent) {
        return create(layoutInflater.inflate(layoutResource, parent, false));
    }

    protected abstract VH create(View view);
}
