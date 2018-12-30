/*
 * www.javagl.de - AutoGUI
 *
 * Copyright (c) 2014-2018 Marco Hutter - http://www.javagl.de
 * 
 * Permission is hereby granted, free of charge, to any person
 * obtaining a copy of this software and associated documentation
 * files (the "Software"), to deal in the Software without
 * restriction, including without limitation the rights to use,
 * copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the
 * Software is furnished to do so, subject to the following
 * conditions:
 * 
 * The above copyright notice and this permission notice shall be
 * included in all copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
 * EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES
 * OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 * NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT
 * HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY,
 * WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING
 * FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR
 * OTHER DEALINGS IN THE SOFTWARE.
 */
package de.javagl.autogui.view;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

/**
 * Utility methods to create comparator instances
 */
class Comparators
{
    /**
     * Creates a comparator that compares strings according to the given order.
     * The strings of the given order and the strings to be compared will be
     * converted to lower case before they are compared. Strings that are
     * not contained in the given order will be considered as "greater" than
     * any string that is contained in the given order (and thus, be ordered
     * <i>after</i> all the strings that appear in this order). Two strings 
     * that are <i>both</i> not contained in the given order will be considered
     * as being equal (and thus, their sort order will not be changed for
     * stable sorts)
     *  
     * @param order The order of the strings
     * @return The comparator that compares according to the given order
     */
    static Comparator<String> createOrderIgnoreCase(String ... order)
    {
        final Map<String, Integer> map = new HashMap<String, Integer>();
        for (int i=0; i<order.length; i++)
        {
            String s = order[i];
            map.put(s.toLowerCase(), i);
        }
        return (s0, s1) -> 
        {
            String sLower0 = s0.toLowerCase();
            String sLower1 = s1.toLowerCase();
            Integer i0 = map.get(sLower0);
            Integer i1 = map.get(sLower1);
            if (i0 == null)
            {
                i0 = map.size();
            }
            if (i1 == null)
            {
                i1 = map.size();
            }
            return Integer.compare(i0, i1);
        };
    }

    /**
     * Private constructor to prevent instantiation
     */
    private Comparators()
    {
        // Private constructor to prevent instantiation
    }
}
