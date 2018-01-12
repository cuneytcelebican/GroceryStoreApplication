/**
 * Created by cuneytcelebican on 2017-02-23.
 */
public abstract class PerishableItem extends GroceryItem {
    public PerishableItem(String n, float p, float w) {
        super(n, p, w);
    }

    public String toString () {
        return super.toString() + " (perishable)";
    }
}
