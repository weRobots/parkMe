package com.robots.we.parkme.beans;

import java.util.HashMap;

public class Slot {

    private String id;

    private int level;

    private int columnIndex;

    private int rawIndex;

    private SlotStatus status;

    private SlotType slotType;

    private HashMap<Direction, String> navigationDetail;

    public String getId() {
        return id;
    }

    public void setId(final String id) {
        this.id = id;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(final int level) {
        this.level = level;
    }

    public SlotStatus getStatus() {
        return status;
    }

    public void setStatus(final SlotStatus status) {
        this.status = status;
    }

    public HashMap<Direction, String> getNavigationDetail() {
        return navigationDetail;
    }

    public void setNavigationDetail(final HashMap<Direction, String> navigationDetail) {
        this.navigationDetail = navigationDetail;
    }

    public SlotType getSlotType() {
        return slotType;
    }

    public void setSlotType(SlotType slotType) {
        this.slotType = slotType;
    }

    public int getColumnIndex() {
        return columnIndex;
    }

    public void setColumnIndex(int columnIndex) {
        this.columnIndex = columnIndex;
    }

    public int getRawIndex() {
        return rawIndex;
    }

    public void setRawIndex(int rawIndex) {
        this.rawIndex = rawIndex;
    }
}
