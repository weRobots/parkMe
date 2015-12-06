package com.robots.we.parkme.convert;

import com.robots.we.parkme.beans.Direction;
import com.robots.we.parkme.beans.SlotStatus;
import com.robots.we.parkme.beans.SlotType;
import com.robots.we.parkme.beans.UserRole;

/**
 * define XML data tag names.
 * <p/>
 * Created by suppa on 30/11/2015.
 */
public class Tags {

    // car park data
    public static final String CAR_PARK = "carpark";
    public static final String ID = "id";
    public static final String CENTER_LAT = "center_lat";
    public static final String CENTER_LOG = "center_log";
    public static final String SLOTS = "slots";

    // slot data
    public static final String SLOT = "slot";
    public static final String SLOT_LEVEL = "level";
    public static final String COLUMN_INDEX = "columnIndex";
    public static final String ROW_INDEX = "rawIndex";
    public static final String STATUS = "status";
    public static final String TYPE = "type";
    public static final String NAVIGATION = "navigation";

    // user
    public static final String USER = "user";
    public static final String USER_ID = "userId";
    public static final String NAME = "name";
    public static final String ROLE = "role";
    public static final String VEHICLE = "vehicleNumber";
    public static final String MOBILE = "mobileNumber";

    // other
    public static final String DIRECTION = "direction";
    public static final String MAP_ENTRY = "entry";
    public static final String STRING = "string";

    public static SlotType getType(String value) {
        // H
        if (SlotType.HORIZONTAL.toString().equals(value)) {
            return SlotType.HORIZONTAL;
        }
        // V
        else if (SlotType.VERTICAL.toString().equals(value)) {
            return SlotType.VERTICAL;
        }
        return null;
    }

    public static SlotStatus getStatus(String value) {
        // allocated
        if (SlotStatus.ALLOCATED.toString().equals(value)) {
            return SlotStatus.ALLOCATED;
        }
        // available
        else if (SlotStatus.AVAILABLE.toString().equals(value)) {
            return SlotStatus.AVAILABLE;
        }
        // available
        else if (SlotStatus.BLOCKED.toString().equals(value)) {
            return SlotStatus.BLOCKED;
        }
        return null;
    }

    public static Direction getDirection(String value) {

        if (Direction.BACK_LEFT.toString().equals(value)) {
            return Direction.BACK_LEFT;
        } else if (Direction.BACK_RIGHT.toString().equals(value)) {
            return Direction.BACK_RIGHT;
        } else if (Direction.FRONT_LEFT.toString().equals(value)) {
            return Direction.FRONT_LEFT;
        } else if (Direction.FRONT_RIGHT.toString().equals(value)) {
            return Direction.FRONT_RIGHT;
        } else if (Direction.LEFT_BACK.toString().equals(value)) {
            return Direction.LEFT_BACK;
        } else if (Direction.LEFT_FRONT.toString().equals(value)) {
            return Direction.LEFT_FRONT;
        } else if (Direction.RIGHT_BACK.toString().equals(value)) {
            return Direction.RIGHT_BACK;
        } else if (Direction.RIGHT_FRONT.toString().equals(value)) {
            return Direction.RIGHT_FRONT;
        }

        return null;
    }

    public static UserRole getRole(String value) {
        if (UserRole.DEFAULT.toString().equals(value)) {
            return UserRole.DEFAULT;
        } else if (UserRole.ADMIN.toString().equals(value)) {
            return UserRole.ADMIN;
        }

        return null;
    }
}
