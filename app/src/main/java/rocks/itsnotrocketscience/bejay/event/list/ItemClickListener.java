package rocks.itsnotrocketscience.bejay.event.list;

/**
 * Created by sirfunkenstine on 23/02/16.
 */
public interface ItemClickListener<T> {
    void onClick(T item, int position);
}
