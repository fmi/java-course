package collections.products;

import java.util.LinkedHashSet;
import java.util.Iterator;

public class RecentlyViewedProducts {
    private final int maxSize;
    private final LinkedHashSet<String> productIds;

    public RecentlyViewedProducts(int maxSize) {
        this.maxSize = maxSize;
        this.productIds = new LinkedHashSet<>();
    }

    public void viewProduct(String productId) {
        // Remove if already present to update position
        productIds.remove(productId);
        productIds.add(productId);

        // If size exceeds max, remove oldest
        if (productIds.size() > maxSize) {
            Iterator<String> it = productIds.iterator();
            it.next();
            it.remove();
        }
    }

    public Iterable<String> getRecentlyViewed() {
        // Returns products from oldest to newest
        return productIds;
    }

    public static void main(String... args) {
        RecentlyViewedProducts recent = new RecentlyViewedProducts(3);

        recent.viewProduct("P1"); // User views product P1
        recent.viewProduct("P2"); // User views product P2
        recent.viewProduct("P3"); // User views product P3
        System.out.println("After viewing P1, P2, P3: " + recent.getRecentlyViewed());

        recent.viewProduct("P2"); // User views P2 again (should move to most recent)
        System.out.println("After viewing P2 again: " + recent.getRecentlyViewed());

        recent.viewProduct("P4"); // User views P4 (oldest, P1, should be dropped)
        System.out.println("After viewing P4: " + recent.getRecentlyViewed());

        recent.viewProduct("P3"); // User views P3 again (should move to most recent)
        System.out.println("After viewing P3 again: " + recent.getRecentlyViewed());
    }

}