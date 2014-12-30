package org.alpha;

import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * This class contains helper utility methods.
 */
public class Helper {

    /**
     * The name of the class.
     */
    private static final String CLASS_NAME = Helper.class.getName();

    /**
     * The logger to use for logging.
     */
    private static final Logger LOGGER = LogManager.getLogger(Helper.class.getName());

    /**
     * Generates string representation of objects.
     */
    private static final ObjectMapper DEFAULT_OBJECT_MAPPER = new ObjectMapper();

    /**
     * The error message when an object cannot be converted to JSON string.
     */
    public static final String MESSAGE_JSON_ERROR = "Cannot convert to json string.";

    /**
     * Represents the error message.
     */
    private static final String MESSAGE_ERROR = "[Error in method '%s']";

    /**
     * Hidden constructor for utility class.
     */
    private Helper() {
        //
    }

    /**
     * Converts the object to JSON string.
     * @param obj the object to convert
     * @return the string representation of objects in JSON format.
     */
    public static String toJSONString(Object obj) {
        final String signature = CLASS_NAME + "#toJSONString";
        try {
            return DEFAULT_OBJECT_MAPPER.writeValueAsString(obj);
        } catch (IOException e) {
            logException(LOGGER, signature, e);
        }
        return MESSAGE_JSON_ERROR;
    }

    /**
     * Logs the given exception and message at <code>ERROR</code> level.
     * @param <T> the exception type
     * @param logger the logger object, cannot be null
     * @param signature the signature of the method to log.
     * @param exception the exception to log.
     * @return the passed in exception.
     */
    public static <T extends Throwable> T logException(Logger logger, String signature, T exception) {
        logger.error(String.format(MESSAGE_ERROR, signature), exception);
        return exception;
    }
}
