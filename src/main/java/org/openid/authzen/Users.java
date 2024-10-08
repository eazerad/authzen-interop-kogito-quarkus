package org.openid.authzen;

import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;

/*
 * PIP: Get list of test users for interop from JSON file.
 */
public class Users {
    private static Logger logger = LoggerFactory.getLogger(Users.class);

    private static Map<String, Object> usersMap;
    private static final String USERS_FILE = "users.json";

    /**
     * Get a map of the users - key for the map is the id we use for user lookup.
     * @param userId the user id we use for the look ups
     * @return the map.
     */
    @SuppressWarnings("unchecked")
    public static Map<String, Object> getUser(String userId) {
        try {
                // read only once the file and keep the map in memory.
                if(usersMap == null) {
                    InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream(USERS_FILE);
                    ObjectMapper mapper = new ObjectMapper();
            
                    synchronized(Users.class) {
                        usersMap = mapper.readValue(is, Map.class);
                    }
                }
        } catch (StreamReadException e) {
            logger.error("unable to parse "+USERS_FILE, e);
        } catch (DatabindException e) {
            logger.error("unable to bind data coming from "+USERS_FILE, e);
        } catch (IOException e) {
            logger.error("unable to read "+USERS_FILE, e);
        }
        if (usersMap!=null) {
            logger.debug(">> User id: "+userId +" >> " +usersMap.get(userId));
            return (Map<String, Object>) usersMap.get(userId);

        }

        return Collections.emptyMap();

    }

    public static boolean isRole(String id, String role) {
        Map<String, Object> user = getUser(id);
        List<String> roles = (List<String>) user.get("roles");
        if(roles!=null && roles.contains(role)) {
            return true;
        }
        return false;
    }


}
