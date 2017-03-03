package com.challenge.json;

import java.io.StringReader;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonNumber;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.json.JsonString;
import javax.json.JsonValue;

/**
 * The JsonParser handles the depth-first parsing of the JSON
 * object to find the proper element.
 */
public class JsonParser {

    /**
     * Return the full path of a specified element in the JSON object.
     * 
     * @param jsonString The full JSON object notation string.
     * @param elementToFind The element name to search for.
     * 
     * @return The full path of the element if it was found, an error message otherwise.
     */
    public static String getFullPath(String jsonString, String elementToFind) {
        // Create a JsonObject from the full notation.
        JsonReader reader = Json.createReader(new StringReader(jsonString));
        JsonObject obj = reader.readObject();
        reader.close();

        // Recursively search/build the path.
        String path = findElement(obj, null, elementToFind);
        if (path == null)
            return "Element was not found.";
        else
            return "\\" + path;
    }

    /**
     * Recursively search the JSON object's values.
     * 
     * @param jsonValue The current JsonValue being examined.
     * @param key The key for the current JSON element.
     * @param elementToFind The element name being searched for.
     * 
     * @return The result of checking the current node.
     */
    private static String findElement(JsonValue jsonValue, String key, String elementToFind) {
        if (jsonValue == null || elementToFind == null) {
            return null;
        }

        // Handle the cases for each JsonValue type.
        switch(jsonValue.getValueType()) {
        case OBJECT:
            // Loop through all the elements in the Object recursively.
            JsonObject object = (JsonObject) jsonValue;
            for (String name : object.keySet()) {
                String path = findElement(object.get(name), name, elementToFind);

                // If 'path' isn't null, it's because the element was found, so prepend and return.
                if (path != null) {
                    if (key != null)
                        return key + "\\" + path;
                    else
                        return path;
                }
            }
            break;
        case ARRAY:
            // Loop through all the elements in the Array recursively.
            JsonArray array = (JsonArray) jsonValue;
            int idx = 0;
            for (JsonValue value : array) {
                String path = findElement(value, null, elementToFind);

                // If 'path' isn't null, it's because the element was found, so prepend and return.
                if (path != null)
                    return String.format("%s[%d]\\%s", key, idx, path);
                idx++;
            }
            break;
        case STRING:
            // We have reached an end node, so compare the value and return.
            JsonString str = (JsonString) jsonValue;
            if (str.getString().equals(elementToFind)) {
                return key;
            }
            break;
        case NUMBER:
            // We have reached an end node, so compare the value and return.
            JsonNumber number = (JsonNumber) jsonValue;
            if (number.toString().equals(elementToFind)) {
                return key;
            }
            break;
        case TRUE:
        case FALSE:
        case NULL:
        default:
            break;
        }

        return null;
    }
}
