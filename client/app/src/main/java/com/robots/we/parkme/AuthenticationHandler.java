package com.robots.we.parkme;

import android.os.AsyncTask;
import android.util.Log;

import com.robots.we.parkme.beans.User;
import com.robots.we.parkme.beans.UserRole;
import com.robots.we.parkme.convert.UserXMLParser;
import com.robots.we.parkme.network.HttpRequestHandler;

import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by supun.hettigoda on 12/6/2015.
 */
public final class AuthenticationHandler {

    public static User USER;
    private static final String TAG = "AuthenticationHandler";


    /**
     * user profile builder
     */
    private static UserProfileBuilder USER_PROFILE_BUILDER;

    public static boolean isAdmin() {
        return (AuthenticationHandler.USER.getRole() == UserRole.ADMIN);
    }

    public static void load(String userId) {
        // execute user loading  background task
        new userLoadTask().execute(userId);
    }

    public static void save() {
        // execute user loading  background task
        new userSaveTask().execute(USER);
    }

    // task to load user
    private static class userLoadTask extends AsyncTask<String, Void, User> {

        @Override
        protected User doInBackground(String... id) {
            try {
                // create car park view again for the latest server information
                InputStream result = HttpRequestHandler.loadUser(id[0]);
                return UserXMLParser.parse(result);
            } catch (IOException e) {
                Log.i(TAG, "user authentication error...");
                return new User();
            } catch (XmlPullParserException e) {
                Log.i(TAG, "user authentication error, XML parsing fail...");
                return new User();
            }
        }

        @Override
        protected void onPostExecute(User user) {
            Log.i(TAG, "user:" + user.getName() + " , role:" + user.getRole() + " authenticated");
            AuthenticationHandler.USER = user;
            USER_PROFILE_BUILDER.buildUserProfile();
        }
    }

    // task to load user
    private static class userSaveTask extends AsyncTask<User, Void, User> {

        @Override
        protected User doInBackground(User... user) {
            try {
                // create car park view again for the latest server information
                InputStream result = HttpRequestHandler.save(user[0]);
                return UserXMLParser.parse(result);
            } catch (IOException e) {
                Log.i(TAG, "user authentication error...");
                return new User();
            } catch (XmlPullParserException e) {
                Log.i(TAG, "user authentication error, XML parsing fail...");
                e.printStackTrace();
                return new User();
            }
        }

        @Override
        protected void onPostExecute(User user) {
            Log.i(TAG, "user:" + user.getName() + " , role:" + user.getRole() + " authenticated");
            AuthenticationHandler.USER = user;
            USER_PROFILE_BUILDER.updatePreferenceData();
            USER_PROFILE_BUILDER.buildUserProfile();
        }
    }

    /**
     * to register the  user profile builder
     *
     * @param userProfileBuilder
     */
    public static void registerUserProfileBuilder(UserProfileBuilder userProfileBuilder) {
        USER_PROFILE_BUILDER = userProfileBuilder;
    }

    /**
     * user profile builder
     */
    public interface UserProfileBuilder {
        void buildUserProfile();

        void updatePreferenceData();
    }
    
}
