package bg.sofia.uni.fmi.mjt.warehouse;

import java.time.LocalDateTime;
import java.util.Objects;

public class MJTExpressLabel<L> implements Comparable<MJTExpressLabel<L>> {
    private final LocalDateTime creationDate;
    private final L label;

    MJTExpressLabel(L label, LocalDateTime creationDate) {
        this.label = label;
        this.creationDate = creationDate;
    }

    @Override
    public int compareTo(MJTExpressLabel<L> o) {
        if (this.creationDate != null && o.creationDate != null) {
            int datesCompare = this.creationDate.compareTo(o.creationDate);
            if (datesCompare != 0) {
                return datesCompare;
            }
        }
        if (this.label != null && o.label != null && this.label.equals(o.label)) {
            // the labels are identical
            return 0;
        }

        // the labels are not the same, but the dates are, hence the ordering does not matter
        return 1;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof MJTExpressLabel)) return false;
        MJTExpressLabel<?> that = (MJTExpressLabel<?>) o;
        return Objects.equals(label, that.label);
    }

    @Override
    public int hashCode() {
        return Objects.hash(label);
    }

    LocalDateTime getCreationDate() {
        return creationDate;
    }

    L getLabel() {
        return label;
    }
}