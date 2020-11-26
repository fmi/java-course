package bg.sofia.uni.fmi.mjt.warehouse;

import bg.sofia.uni.fmi.mjt.warehouse.exceptions.CapacityExceededException;
import bg.sofia.uni.fmi.mjt.warehouse.exceptions.ParcelNotFoundException;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

public class MJTExpressWarehouse<L, P> implements DeliveryServiceWarehouse<L, P> {

    private final int capacity;
    private final int retentionPeriod;

    private SortedMap<MJTExpressLabel<L>, P> items;

    /**
     * Creates a new instance of MJTExpressWarehouse with the given characteristics
     *
     * @param capacity        the total number of items that the warehouse can store
     * @param retentionPeriod the maximum number of days for which a parcel can stay in the warehouse,
     *                        counted from the day the parcel was submitted.
     *                        After that time passes, the parcel is removed from the storage
     */
    public MJTExpressWarehouse(int capacity, int retentionPeriod) {
        this.capacity = capacity;
        this.retentionPeriod = retentionPeriod;
        this.items = new TreeMap<>();
    }

    @Override
    public void submitParcel(L label, P parcel, LocalDateTime submissionDate) throws CapacityExceededException {
        if (label == null || parcel == null || submissionDate == null) {
            throw new IllegalArgumentException("Provided arguments cannot be null");
        }

        if (submissionDate.isAfter(LocalDateTime.now())) {
            throw new IllegalArgumentException("Provided submission date cannot be in the future");
        }

        if (this.items.size() >= this.capacity) {
            if (items.firstKey().getCreationDate().plusDays(retentionPeriod).isAfter(LocalDateTime.now())) {
                throw new CapacityExceededException();
            }
            items.remove(items.firstKey());
        }

        this.items.put(new MJTExpressLabel<>(label, submissionDate), parcel);
    }

    @Override
    public P getParcel(L label) {
        if (label == null) {
            throw new IllegalArgumentException("Provided label cannot be null");
        }

        return this.items.get(wrapLabel(label));
    }

    @Override
    public P deliverParcel(L label) throws ParcelNotFoundException {
        if (label == null) {
            throw new IllegalArgumentException("Provided label cannot be null");
        }

        MJTExpressLabel<L> key = wrapLabel(label);
        P parcel = this.items.get(key);
        if (parcel == null) {
            throw new ParcelNotFoundException();
        }

        this.items.remove(key);
        return parcel;
    }

    @Override
    public double getWarehouseSpaceLeft() {
        return (double) 1 - this.items.size() / (double) this.capacity;
    }

    @Override
    public Map<L, P> getWarehouseItems() {
        Map<L, P> newMap = new HashMap<>();
        for (Map.Entry<MJTExpressLabel<L>, P> entry : items.entrySet()) {
            newMap.put(entry.getKey().getLabel(), entry.getValue());
        }

        return newMap;
    }

    @Override
    public Map<L, P> deliverParcelsSubmittedBefore(LocalDateTime before) {
        if (before == null) {
            throw new IllegalArgumentException("Provided before date cannot be null");
        }

        if (before.isAfter(LocalDateTime.now())) {
            return getWarehouseItems();
        }

        SortedMap<MJTExpressLabel<L>, P> head = items.headMap(new MJTExpressLabel<>(null, before));
        return deliverParcels(head);
    }

    @Override
    public Map<L, P> deliverParcelsSubmittedAfter(LocalDateTime after) {
        if (after == null) {
            throw new IllegalArgumentException("Provided after date cannot be null");
        }

        if (after.isAfter(LocalDateTime.now())) {
            return Collections.emptyMap();
        }

        SortedMap<MJTExpressLabel<L>, P> tail = items.tailMap(new MJTExpressLabel<>(null, after));
        return deliverParcels(tail);
    }

    private MJTExpressLabel<L> wrapLabel(L label) {
        return new MJTExpressLabel<>(label, null);
    }

    private Map<L, P> deliverParcels(SortedMap<MJTExpressLabel<L>, P> map) {
        Map<L, P> newMap = new HashMap<>();
        for (Map.Entry<MJTExpressLabel<L>, P> entry : map.entrySet()) {
            newMap.put(entry.getKey().getLabel(), entry.getValue());
            items.remove(entry.getKey());
        }

        return newMap;
    }
}
