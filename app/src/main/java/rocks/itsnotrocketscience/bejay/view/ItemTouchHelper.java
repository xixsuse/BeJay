package rocks.itsnotrocketscience.bejay.view;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by nemi on 21/02/2016.
 */
public class ItemTouchHelper {

    private final GestureDetector gestureDetector;
    private final ItemTouchGestureListener itemTouchGestureListener;
    private OnItemClickedListener onItemClickedListener;
    private final RecyclerView.OnItemTouchListener itemTouchListener;
    private RecyclerView recyclerView;

    public interface OnItemClickedListener {
        void onItemClicked(RecyclerView recyclerView, int adapterPosition);
    }

    private class ItemTouchGestureListener extends GestureDetector.SimpleOnGestureListener {
        @Override
        public boolean onSingleTapConfirmed(MotionEvent e) {
            if(onItemClickedListener != null) {
                View view = recyclerView.findChildViewUnder(e.getX(), e.getY());
                if(view != null) {
                    RecyclerView.ViewHolder viewHolder = recyclerView.getChildViewHolder(view);
                    if(viewHolder != null) {
                        int adapterPosition = viewHolder.getAdapterPosition();
                        onItemClickedListener.onItemClicked(recyclerView, adapterPosition);
                    }
                }
                return false;
            }
            return true;
        }
    }

    private class ItemTouchListener extends RecyclerView.SimpleOnItemTouchListener {
        @Override
        public void onTouchEvent(RecyclerView rv, MotionEvent e) {
            gestureDetector.onTouchEvent(e);
        }

        @Override
        public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
            return gestureDetector.onTouchEvent(e);
        }
    }

    public ItemTouchHelper(Context context) {
        itemTouchGestureListener = new ItemTouchGestureListener();
        gestureDetector = new GestureDetector(context, itemTouchGestureListener);
        itemTouchListener = new ItemTouchListener();
    }

    public void setup(RecyclerView recyclerView) {
        if(this.recyclerView != null) {
            throw new IllegalStateException("already set up");
        }
        this.recyclerView = recyclerView;
        recyclerView.addOnItemTouchListener(itemTouchListener);
    }


    public OnItemClickedListener getOnItemClickedListener() {
        return onItemClickedListener;
    }

    public void setOnItemClickedListener(OnItemClickedListener onItemClickedListener) {
        this.onItemClickedListener = onItemClickedListener;
    }
}
