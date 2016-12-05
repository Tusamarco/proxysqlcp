package net.tc.isma.data.hibernate;

import net.tc.isma.data.common.*;
import java.util.Map;
import java.util.Iterator;
import net.tc.isma.utils.SynchronizedMap;
import net.tc.isma.utils.Text;
import org.hibernate.Session;

/**
 * <p>Title: </p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2005</p>
 *
 * <p>Company: </p>
 *
 * @author not attributable
 * @version 1.0
 */
public abstract class HybernateObjectIdx extends MultiLanguage {
    public HybernateObjectIdx() {
    }

    public abstract Object getId();
    public abstract void setId(Object id);
    /**
     * getMapAsStringCSV
     *
     * @param metaHeaderMap Map
     * @return String[]
     */

}
