/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import java.util.List;

/**
 *
 * @author niiku
 */
public class StringUtils {

    public static String join(String r[], String d) {
        StringBuilder sb = new StringBuilder();
        int i;
        for (i = 0; i < r.length - 1; i++) {
            sb.append(r[i] + d);
        }
        return sb.toString() + r[i];
    }

    public static String join(List<String> r, String d) {
        return join(r.toArray(new String[r.size()]), d);
    }
}
