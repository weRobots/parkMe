package com.robots.we.parkme.convert;


import android.util.Xml;

import com.robots.we.parkme.beans.CarPark;
import com.robots.we.parkme.beans.Direction;
import com.robots.we.parkme.beans.Slot;
import com.robots.we.parkme.beans.SlotStatus;
import com.robots.we.parkme.beans.SlotType;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by supun.hettigoda on 11/28/2015.
 */
public class CarParkXMLParser {
    // We don't use namespaces
    private static final String ns = null;

    public static CarPark parse(InputStream in) {
        try {
            XmlPullParser parser = Xml.newPullParser();
            parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
            parser.setInput(in, null);
            parser.nextTag();
            return readCarPark(parser);

            // possible exceptions will return a null
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    private static CarPark readCarPark(XmlPullParser parser) throws XmlPullParserException, IOException {

        // map xml data to the CarPark bean
        CarPark carPark = new CarPark();

        parser.require(XmlPullParser.START_TAG, ns, Tags.CAR_PARK);
        while (parser.next() != XmlPullParser.END_TAG) {
            if (parser.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }

            String name = parser.getName();

            // id
            if (name.equals(Tags.ID)) {
                carPark.setId(readValue(parser, Tags.ID));
            }
            // center
            else if (name.equals(Tags.CENTER)) {
                carPark.setCenterLocation(readValue(parser, Tags.CENTER));
            }
            // slots
            else if (name.equals(Tags.SLOTS)) {
                carPark.setSlots(readSlots(parser));
            }
            // skip tags that are not concerned
            else {
                skip(parser);
            }
        }

        return carPark;
    }

    private static Set<Slot> readSlots(XmlPullParser parser) throws IOException, XmlPullParserException {
        Set<Slot> slots = new HashSet<Slot>();

        parser.require(XmlPullParser.START_TAG, ns, Tags.SLOTS);
        while (parser.next() != XmlPullParser.END_TAG) {
            if (parser.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }

            String name = parser.getName();

            // read all slot values
            if (name.equals(Tags.SLOT)) {
                slots.add(readSlot(parser));
            } else {
                skip(parser);
            }
        }
        return slots;
    }

    private static Slot readSlot(XmlPullParser parser) throws IOException, XmlPullParserException {

        Slot slot = new Slot();
        parser.require(XmlPullParser.START_TAG, ns, Tags.SLOT);

        while (parser.next() != XmlPullParser.END_TAG) {
            if (parser.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }

            String name = parser.getName();

            // id
            if (name.equals(Tags.ID)) {
                slot.setId(readValue(parser, Tags.ID));
            }
            // level
            else if (name.equals(Tags.SLOT_LEVEL)) {
                slot.setLevel(Integer.parseInt(readValue(parser, Tags.SLOT_LEVEL)));
            }

            // column index
            else if (name.equals(Tags.COLUMN_INDEX)) {
                slot.setColumnIndex(Integer.parseInt(readValue(parser, Tags.COLUMN_INDEX)));
            }

            // row index
            else if (name.equals(Tags.ROW_INDEX)) {
                slot.setRawIndex(Integer.parseInt(readValue(parser, Tags.ROW_INDEX)));
            }


            // type
            else if (name.equals(Tags.TYPE)) {
                slot.setSlotType(readSlotType(parser));
            }
            // status
            else if (name.equals(Tags.STATUS)) {
                slot.setStatus(readSlotStatus(parser));
            }
            // navigation
            else if (name.equals(Tags.NAVIGATION)) {
                slot.setNavigationDetail(readNavigation(parser));
            }
            // other tags not concerned
            else {
                skip(parser);
            }
        }
        return slot;
    }

    private static HashMap<Direction, String> readNavigation(XmlPullParser parser) throws IOException, XmlPullParserException {
        HashMap<Direction, String> navigation = new HashMap<Direction, String>();

        parser.require(XmlPullParser.START_TAG, ns, Tags.NAVIGATION);
        while (parser.next() != XmlPullParser.END_TAG) {
            if (parser.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }

            String name = parser.getName();
            Direction direction = null;
            String slotId = null;

            // read map entry
            if (name.equals(Tags.MAP_ENTRY)) {
                parser.require(XmlPullParser.START_TAG, ns, Tags.MAP_ENTRY);
                while (parser.next() != XmlPullParser.END_TAG) {
                    if (parser.getEventType() != XmlPullParser.START_TAG) {
                        continue;
                    }

                    String nameInside = parser.getName();


                    // key = direction
                    if (nameInside.equals(Tags.DIRECTION)) {
                        direction = readDirection(parser);
                    }

                    // value = slotID(string)
                    if (nameInside.equals(Tags.STRING)) {
                        slotId = readValue(parser, Tags.STRING);
                    }
                }

                navigation.put(direction, slotId);
            }
            // others not concerned
            else {
                skip(parser);
            }
        }
        return navigation;
    }

    private static Direction readDirection(XmlPullParser parser) throws IOException, XmlPullParserException {
        String direction = readValue(parser, Tags.DIRECTION);
        return Tags.getDirection(direction);
    }

    private static SlotType readSlotType(XmlPullParser parser) throws IOException, XmlPullParserException {
        String type = readValue(parser, Tags.TYPE);
        return Tags.getType(type);
    }

    private static SlotStatus readSlotStatus(XmlPullParser parser) throws IOException, XmlPullParserException {
        String status = readValue(parser, Tags.STATUS);
        return Tags.getStatus(status);
    }

    // read string values.
    private static String readValue(XmlPullParser parser, String tag) throws IOException, XmlPullParserException {
        parser.require(XmlPullParser.START_TAG, ns, tag);
        String value = readText(parser);
        parser.require(XmlPullParser.END_TAG, ns, tag);
        return value;
    }

    // For the tags title and summary, extracts their text values.
    private static String readText(XmlPullParser parser) throws IOException, XmlPullParserException {
        String result = "";
        if (parser.next() == XmlPullParser.TEXT) {
            result = parser.getText();
            parser.nextTag();
        }
        return result;
    }

    private static void skip(XmlPullParser parser) throws XmlPullParserException, IOException {
        if (parser.getEventType() != XmlPullParser.START_TAG) {
            throw new IllegalStateException();
        }
        int depth = 1;
        while (depth != 0) {
            switch (parser.next()) {
                case XmlPullParser.END_TAG:
                    depth--;
                    break;
                case XmlPullParser.START_TAG:
                    depth++;
                    break;
            }
        }
    }
}
