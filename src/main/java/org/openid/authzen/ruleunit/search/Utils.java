package org.openid.authzen.ruleunit.search;

import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.openid.authzen.Users;
import org.openid.authzen.model.Record;
import org.openid.authzen.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;

/*
 * PIP: Get list of test users for interop from JSON file.
 */
public class Utils {
    private static Logger logger = LoggerFactory.getLogger(Users.class);

    private static List<User> usersList;
    private static List<Record> recordsList;
    private static final String USERS_FILE = "users2.json";
    private static final String RESOURCES_FILE = "records.json";


    /**
     * Get a the list of users.
     * @return the list.
     */
    //@SuppressWarnings("unchecked")
    public static List<User> getUsers() {
        try {
                // read only once the file and keep the map in memory.
                if(usersList == null) {
                    InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream(USERS_FILE);
                    ObjectMapper mapper = new ObjectMapper();
            
                    synchronized(Utils.class) {
                        usersList = mapper.readValue(is, new TypeReference<List<User>>() {});
                        //usersList = mapper.readValue(is,  List.class);

                        logger.info("Loaded users from file: "+USERS_FILE);
                    }
                }
        } catch (StreamReadException e) {
            logger.error("unable to parse "+USERS_FILE, e);
        } catch (DatabindException e) {
            logger.error("unable to bind data coming from "+USERS_FILE, e);
        } catch (IOException e) {
            logger.error("unable to read "+USERS_FILE, e);
        }
        if (usersList!=null) {
            return (List<User>) usersList;

        }

        return Collections.emptyList();

    }


    //@SuppressWarnings("unchecked")
    public static List<Record> getRecords() {
        try {
                // read only once the file and keep the map in memory.
                if(recordsList == null) {
                    InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream(RESOURCES_FILE);
                    ObjectMapper mapper = new ObjectMapper();
            
                    synchronized(Utils.class) {
                        recordsList = mapper.readValue(is, new TypeReference<List<Record>>() {});
                        //recordsList = mapper.readValue(is,  List.class);

                    }
                }
        } catch (StreamReadException e) {
            logger.error("unable to parse "+RESOURCES_FILE, e);
        } catch (DatabindException e) {
            logger.error("unable to bind data coming from "+RESOURCES_FILE, e);
        } catch (IOException e) {
            logger.error("unable to read "+RESOURCES_FILE, e);
        }
        if (recordsList!=null) {
            return (List<Record>) recordsList;

        }

        return Collections.emptyList();

    }

}
