/**
 * Copyright 2012 Neil Borle, Mitchell Home, Bronte Lee, Aaron
 * Padlesky, Eddie Santos
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * 
 */
package ca.ualberta.cs.c301f12t01.util;

import java.util.*;
import java.lang.StringBuilder;

/**
 * StringUtils -- utilities for strings, yo!
 *
 * 
 * @author Eddie Antonio Santos <easantos@ualberta.ca>
 */
public class StringUtils {

    /**
     * Returns a String built up of calling toString() all of the items in the
     * Iterable, separated by the separator.
     * @param <E>
     */
    @SuppressWarnings("rawtypes")
    public static String joinToString(Iterable items, CharSequence seperator) {
        StringBuilder builder = new StringBuilder();

        /* Done in the old-style because I need to explicitly call
         * .hasNext() */
        Iterator item_iterator = items.iterator();
        while (item_iterator.hasNext()) {

            builder.append(item_iterator.next().toString());

            if (item_iterator.hasNext())
                builder.append(seperator);
        }

        return builder.toString();

    }

}
