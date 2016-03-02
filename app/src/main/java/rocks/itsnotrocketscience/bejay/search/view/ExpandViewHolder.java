package rocks.itsnotrocketscience.bejay.search.view;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import rocks.itsnotrocketscience.bejay.R;

public class ExpandViewHolder extends RecyclerView.ViewHolder {
    private final TextView text;
    public ExpandViewHolder(View itemView) {
        super(itemView);
        text = (TextView) itemView.findViewById(R.id.text);
    }

    public void setText(String text) {
        this.text.setText(text);
    }
}
