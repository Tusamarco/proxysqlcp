package net.tc.isma.utils.file;

import java.util.Map;
import java.util.List;

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
public interface FileDataReader {

    public void readInitialize(boolean hasHeader);
    public Map getRowAsMap();
    public String getRowAsString();
    public Map getAllRowsAsMap();
    public List getHeaders();
}
