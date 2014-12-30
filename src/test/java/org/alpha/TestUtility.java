package org.alpha;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Field;

import javax.persistence.EntityManager;

/**
 * Utility class containing helper methods for unit tests.
 */
public class TestUtility {
    /**
     * Represents the empty string.
     */
    public static final String EMPTY_STRING = " \t ";

    /**
     * Represents the path of test files.
     */
    public static final String TEST_FILES = "test_files" + java.io.File.separator;

    /**
     * Private constructor to prevent this class being instantiated.
     */
    private TestUtility() {
        // empty
    }

    /**
     * Gets value for field of given object by using reflection.
     * @param obj the given object.
     * @param field the field name.
     * @return the field value.
     */
    public static Object getField(Object obj, String field) {
        Object value = null;
        try {
            // Use reflection to get the object represented by String field.
            Field declaredField = obj.getClass().getDeclaredField(field);
            // Suppress Java language access checking when this object is used.
            declaredField.setAccessible(true);

            try {
                value = declaredField.get(obj);
            } finally {
                // Enable Java language access checking when this object is used.
                declaredField.setAccessible(false);
            }
        } catch (IllegalAccessException e) {
            // Ignore. Proper error should happen in unit test.
        } catch (NoSuchFieldException e) {
            // Ignore. Proper error should happen in unit test.
        }

        return value;
    }

    /**
     * Sets value for field of given object by using reflection.
     * @param obj the given object.
     * @param field the field name.
     * @param value the field value.
     */
    public static void setField(Object obj, String field, Object value) {
        try {
            Field declaredField = obj.getClass().getDeclaredField(field);
            declaredField.setAccessible(true);

            try {
                declaredField.set(obj, value);
            } finally {
                // Enable Java language access checking when this object is used again.
                declaredField.setAccessible(false);
            }
        } catch (IllegalAccessException e) {
            // Ignore. Proper error should happen in unit test.
        } catch (IllegalArgumentException e) {
            // Ignore. Proper error should happen in unit test.
        } catch (NoSuchFieldException e) {
            // Ignore. Proper error should happen in unit test.
        }

    }

    /**
     * Executes the SQL statements in the file. Lines that are empty or starts with '#' will be ignore.
     * @param entityManager the entity manager to use
     * @param file the file
     * @throws Exception if an error occurs
     */
    public static void executeSQL(EntityManager entityManager, String file) throws Exception {
        entityManager.clear();
        entityManager.getTransaction().begin();

        String[] values = TestUtility.readFile(file).split(";");

        for (int i = 0; i < values.length; i++) {
            String sql = values[i].trim();
            if ((sql.length() != 0) && (!sql.startsWith("#"))) {
                entityManager.createNativeQuery(sql).executeUpdate();
            }
        }

        entityManager.getTransaction().commit();
    }

    /**
     * This method reads the content of file specified by file path into string.
     * <p>
     * This method is not thread safe.
     * @param path the path to file.
     * @return the content of the file as String.
     * @throws IOException if some error occurs while reading the file like file was not found.
     */
    public static String readFile(String path) throws IOException {
        // Use BufferedReader for faster buffered file I/O performance.
        BufferedReader reader = new BufferedReader(new FileReader(path));

        try {
            // Use StringBuilder is faster than StringBuffer but not thread safe.
            StringBuilder fileData = new StringBuilder();
            // Buffer for reading.
            char[] buf = new char[1024];
            // Number of read chars.
            int numRead = 0;
            // Read characters and append to string buffer.
            while ((numRead = reader.read(buf)) != -1) {
                fileData.append(buf, 0, numRead);
            }
            // Return read content and replace any "\r\n" with "\n".
            return fileData.toString().replace("\r\n", "\n");
        } finally {
            reader.close();
        }
    }

}
