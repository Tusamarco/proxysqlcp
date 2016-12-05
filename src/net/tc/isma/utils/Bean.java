package net.tc.isma.utils;

import java.util.*;
import java.lang.reflect.*;
import org.apache.commons.beanutils.PropertyUtils;
import net.tc.isma.lang.LanguageSelector;

/**
 * <p>Title: ISMA</p>
 * <p>Description: Information System Modular Architecture</p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: FAO of the UN</p>
 * @author not attributable
 * @version 1.0
 */

public class Bean
{
    public static String[] extractString(Collection c, String name)
    {
        return (String[]) extractProperty(c, name, new String[0], (LanguageSelector) null);
    }

    public static Object[] extractProperty(Collection c, String name)
    {
        return extractProperty(c, name, (LanguageSelector) null);
    }

    public static List extractPropertyList(Collection c, String name)
    {
        return extractPropertyList(c, name, (LanguageSelector) null);
    }

    public static Object[] extractProperty(Collection c, String name, Object[] dest)
    {
        return extractProperty(c, name, dest, null);
    }

    public static String[] extractString (Collection c, String name, LanguageSelector ls)
    {
        return (String[]) extractProperty(c, name, new String[0], ls);
    }

    public static Object[] extractProperty(Collection c, String name, LanguageSelector ls)
    {
        return extractProperty(c, name, new Object[0], ls);
    }

    public static Object[] extractProperty(Collection c, String name, Object[] dest, LanguageSelector ls)
    {
        return extractPropertyList(c, name, ls).toArray(dest);
    }

    public static List extractPropertyList(Collection c, String name, LanguageSelector ls)
    {
        try
        {
            List values = new Vector();

            if (c != null)
            {
                Iterator iter = c.iterator();
                while (iter.hasNext())
                {
                    Object bean = iter.next();
                    Object val;
                    if ( ls == null )
                    {
                        val = PropertyUtils.getProperty(bean, name);
                    }
                    else
                    {
                        val = ls.getMultilingualProperty(bean, name);
                    }
                    values.add(val);
                }
            }

            return values;
        }
        catch (NoSuchMethodException ex)
        {
            throw new IllegalArgumentException(ex.toString());
        }
        catch (InvocationTargetException ex)
        {
            throw new IllegalArgumentException(ex.toString());
        }
        catch (IllegalAccessException ex)
        {
            throw new IllegalArgumentException(ex.toString());
        }
    }
}
