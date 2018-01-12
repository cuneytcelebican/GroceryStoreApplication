/**
 * Created by cuneytcelebican on 2017-02-23.
 */
public class RefrigeratorItem extends PerishableItem {
    public RefrigeratorItem(String n, float p, float w) {
        super(n, p, w);
    }

    public String toString () {
        return super.toString() + "[keep refrigerated]";
    }
}
