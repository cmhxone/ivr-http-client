package com.ivr.util;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class JsonUtil {

    /**
     * Convert object as JSON String
     * 
     * @param object
     * @return
     */
    public static String makeJsonString(Map<String, Object> object) {
        StringBuilder sb = new StringBuilder();

        int idx = 0;

        sb.append("{");
        for (Entry<String, Object> entry : object.entrySet()) {
            idx++;

            sb.append("\"");
            sb.append(entry.getKey());
            sb.append("\":\"");
            sb.append(entry.getValue().toString());
            sb.append("\"");

            if (idx != object.size()) {
                sb.append(",");
            }
        }
        sb.append("}");

        return sb.toString();
    }

    /**
     * Convert object list as JSON String
     * 
     * @param objects
     * @return
     */
    public static String makeJsonString(List<Map<String, Object>> objects) {
        StringBuilder sb = new StringBuilder();

        int idx = 0;

        sb.append("[");
        for (Map<String, Object> object : objects) {
            idx++;
            sb.append(makeJsonString(object));

            if (idx != objects.size()) {
                sb.append(",");
            }
        }
        sb.append("]");

        return sb.toString();
    }
}
