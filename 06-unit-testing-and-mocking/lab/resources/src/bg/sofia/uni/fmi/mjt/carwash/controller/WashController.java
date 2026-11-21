package bg.sofia.uni.fmi.mjt.carwash.controller;

import bg.sofia.uni.fmi.mjt.carwash.WashRequest;
import bg.sofia.uni.fmi.mjt.carwash.exception.WashUnavailableException;

public interface WashController {

    /**
     * Submits one wash request to the machine.
     * A refused or failed submission leaves the controller's business state unchanged.
     *
     * @param request the request to submit
     * @return true if the request was accepted, false if it was refused
     * @throws IllegalArgumentException if request is null
     * @throws WashUnavailableException if the controller could not accept the request
     */
    boolean submit(WashRequest request);

}
