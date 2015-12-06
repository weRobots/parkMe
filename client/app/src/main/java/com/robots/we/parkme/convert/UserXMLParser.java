package com.robots.we.parkme.convert;

import android.util.Xml;

import com.robots.we.parkme.beans.User;

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

    public static User parse(InputStream in) {
        try {
            XmlPullParser parser = Xml.newPullParser();
            parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
            parser.setInput(in, null);
            parser.nextTag();
            return readUser(parser);

            // possible exceptions will return a null
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    private static User readUser(XmlPullParser parser) throws XmlPullParserException, IOException {

        // map xml data to the CarPark bean
        User user = new User();

        parser.require(XmlPullParser.START_TAG, ns, Tags.CAR_PARK);
        while (parser.next() != XmlPullParser.END_TAG) {
            if (parser.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }

            String name = parser.getName();


        }

        return user;
    }
}
