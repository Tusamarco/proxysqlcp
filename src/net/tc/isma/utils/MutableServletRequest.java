package net.tc.isma.utils;

import java.util.Enumeration;
import java.io.UnsupportedEncodingException;
import javax.servlet.ServletInputStream;
import java.io.IOException;
import java.util.Map;
import java.io.BufferedReader;
import java.util.Locale;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletRequest;
import javax.servlet.ServletRequestWrapper;

/**
 * Read-write implementation of {@link javax.servlet.ServletRequest ServletRequest}
 *
 * @todo to be implemented as needed.  When complete, will no longer need to
 * extend ServletRequestWrapper and can then have a default constructor
 *
 * <p>Title: ISMA</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: FAO of the UN</p>
 * @author 
 * @version 1.0
 */

public class MutableServletRequest
        extends ServletRequestWrapper
        implements ServletRequest
{
//    public MutableServletRequest()
//    {
//    }

    public MutableServletRequest(ServletRequest request)
    {
        super(request);
    }

    /**
     * @todo implement set and get methods
     */
}