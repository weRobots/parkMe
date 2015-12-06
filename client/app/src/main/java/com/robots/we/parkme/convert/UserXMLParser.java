package com.robots.we.parkme.convert;

import android.util.Xml;

import com.robots.we.parkme.beans.Direction;
import com.robots.we.parkme.beans.User;
import com.robots.we.parkme.beans.UserRole;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by supun.hettigoda on 12/6/2015.
 */
public class UserXMLParser {

    // We don't use namespaces
    private static final String ns = null;

    public static User parse(InputStream in) throws IOException, XmlPullParserException {

        XmlPullParser parser = Xml.newPullParser();
        parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
        parser.setInput(in, null);
        parser.nextTag();
        return readUser(parser);

    }

    private static User readUser(XmlPullParser parser) throws XmlPullParserException, IOException {

        // map xml data to the CarPark bean
        User user = new User();

        parser.require(XmlPullParser.START_TAG, ns, Tags.USER);
        while (parser.next() != XmlPullParser.END_TAG) {
            if (parser.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }

            String name = parser.getName();

            // id
            if (name.equals(Tags.USER_ID)) {
                user.setUserId(readValue(parser, Tags.USER_ID));
            }
            // center lat
            else if (name.equals(Tags.NAME)) {
                user.setName(readValue(parser, Tags.NAME));
            }
            // center log
            else if (name.equals(Tags.MOBILE)) {
                user.setMobileNumber(readValue(parser, Tags.MOBILE));
            }
            // slots
            else if (name.equals(Tags.VEHICLE)) {
                user.setVehichleNumber(readValue(parser, Tags.VEHICLE));
            }
            // role
            else if (name.equals(Tags.ROLE)) {
                user.setRole(readRole(parser));
            }

            // skip tags that are not concerned
            else {
                skip(parser);
            }
        }

        return user;
    }

    private static UserRole readRole(XmlPullParser parser) throws IOException, XmlPullParserException {
        String role = readValue(parser, Tags.ROLE);
        return Tags.getRole(role);
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
