package rocks.itsnotrocketscience.bejay.models;

/**
 * Created by sirfunkenstine on 04/05/16.
 */
public interface Visitable{
    void accept(Visitor visitor);
}