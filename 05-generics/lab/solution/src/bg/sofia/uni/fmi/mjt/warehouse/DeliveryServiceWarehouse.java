package bg.sofia.uni.fmi.mjt.warehouse;

import bg.sofia.uni.fmi.mjt.warehouse.exceptions.CapacityExceededException;
import bg.sofia.uni.fmi.mjt.warehouse.exceptions.ParcelNotFoundException;
import java.time.LocalDateTime;
import java.util.Map;

public interface DeliveryServiceWarehouse<L, P> {

    /**
     * Adds the provided parcels with the given label as a new item in the warehouse.
     *
     * @param label          the unique identifier of the parcel.
     * @param parcel         the parcel that should be stored
     * @param submissionDate the date when the parcel was submitted into the warehouse
     * @throws CapacityExceededException if there is no capacity left in the warehouse
     * @throws IllegalArgumentException  if the provided date is a date in the future, or any of the parameters is null
     */
    void submitParcel(L label, P parcel, LocalDateTime submissionDate) throws CapacityExceededException;

    /**
     * @param label the label of the wanted parcel
     * @return the parcel with label equal to the provided one. Returns null if no parcel is found
     * @throws IllegalArgumentException when the given label is null
     */
    P getParcel(L label);

    /**
     * Removes the parcel with the given label from the warehouse and returns it
     *
     * @param label the label of the parcel for delivery
     * @return the Parcel with label equal to the given one
     * @throws ParcelNotFoundException  when a parcel with the given label does not exist in the warehouse
     * @throws IllegalArgumentException then the given label is null
     */
    P deliverParcel(L label) throws ParcelNotFoundException;

    /**
     * @return the free space in the warehouse as a decimal fraction.
     *         It should be a number between 0 and 1 rounded to two decimal places
     */
    double getWarehouseSpaceLeft();

    /**
     * @return a Map of all items in the warehouse. if there are no items, the returned map is empty
     */
    Map<L, P> getWarehouseItems();

    /**
     * Removes all items submitted before the given date from the warehouse, and returns them
     *
     * @param before the date that is used for filtering the items
     * @return the items that will be delivered. If there are no items submitted before the given date,
     *         the returned Map is empty. If the given date is in the future, all items in the warehouse are returned
     * @throws IllegalArgumentException when the given date is null
     */
    Map<L, P> deliverParcelsSubmittedBefore(LocalDateTime before);

    /**
     * Removes all items submitted after the given date from the warehouse, and returns them
     *
     * @param after the date that is used for filtering the items
     * @return the items that will be delivered. If there are no items submitted after the given date,
     *         the returned Map is empty. An empty Map is returned if the given date is in the future
     * @throws IllegalArgumentException when the given date is null
     */
    Map<L, P> deliverParcelsSubmittedAfter(LocalDateTime after);
}
