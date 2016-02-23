package rocks.itsnotrocketscience.bejay.search;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import rocks.itsnotrocketscience.bejay.R;

public class SectionHeaderViewHolder extends RecyclerView.ViewHolder {
    private TextView title;

    public SectionHeaderViewHolder(View itemView) {
        super(itemView);
        title = (TextView) itemView.findViewById(R.id.title);
    }

    public void setTitle(CharSequence title) {
        this.title.setText(title);
    }
}
