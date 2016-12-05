package net.tc.isma.comparators;

import java.util.Comparator;
import java.io.Serializable;
import org.apache.commons.collections.comparators.ComparableComparator;
import net.tc.isma.actions.*;
import net.tc.isma.actions.generic.actionImpl;
import net.tc.isma.data.hibernate.HybernateObject;

/**
 * Compares two Strings or String arrays
 *
 * <p>Title: FAOSTAT</p>
 * <p>Description: Information System Modular Architecture</p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: FAO of the UN</p>
 * @author  Tusa
 * @version 1.0
 */

public class HibernateOrderComparator
    implements Comparator, Serializable
{
    private Comparator comparator;
    private boolean ignoreCase;
    private boolean ignoreAccents;

    public HibernateOrderComparator()
    {

    }


    public int compare(Object o1, Object o2)
    {
        if(o1 instanceof HybernateObject && o2 instanceof HybernateObject)
        {

            if (o1 != null && o2 != null) {
                Long a = (Long)((HybernateObject) o1).getOrder();
                Long b = (Long)((HybernateObject) o2).getOrder();
                if(a != null && b !=null)
                    return a.intValue() - b.intValue();
                else
                    return 0;
            } else
                return 0;
        }
        else
            return 0;
    }


}
