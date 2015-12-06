package com.robots.we.parkme.beans;

import java.util.Set;

/**
 * Defines the Car park.
 *
 * @author suppa
 */
public class CarPark {

    String id;
    String centerLocationLat;
    String centerLocationLog;
    Set<Slot> slots;
    User admin;

    public String getId() {
        return id;
    }

    public void setId(final String id) {
        this.id = id;
    }

    public Set<Slot> getSlots() {
        return slots;
    }

    public void setSlots(final Set<Slot> slots) {
        this.slots = slots;
    }

    public int getColumns() {
        return 6;
    }

    public int getRaws() {
        return 10;
    }

    public String getCenterLocationLat() {
        return centerLocationLat;
    }

    public void setCenterLocationLat(String centerLocationLat) {
        this.centerLocationLat = centerLocationLat;
    }

    public String getCenterLocationLog() {
        return centerLocationLog;
    }

    public void setCenterLocationLog(String centerLocationLog) {
        this.centerLocationLog = centerLocationLog;
    }

    public User getAdmin() {
        return admin;
    }

    public void setAdmin(User admin) {
        this.admin = admin;
    }
}
