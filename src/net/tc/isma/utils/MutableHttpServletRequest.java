package net.tc.isma.utils;

import javax.servlet.http.HttpServletRequestWrapper;
import java.util.Enumeration;
import javax.servlet.*;
import java.util.Map;
import java.io.BufferedReader;
import java.util.Locale;
import javax.servlet.http.HttpServletRequest;
import java.util.Hashtable;
import java.util.Iterator;

/**
 * Read-write implementation of {@link javax.servlet.http.HttpServletRequest HttpServletRequest}
 *
 * @todo once complete, will extend MutableServletRequest.  Temporarily extends
 * HttpServletRequestWrapper to provide "get" functionality not yet implemented
 * <p>Title: ISMA</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: FAO of the UN</p>
 * @author
 * @version 1.0
 */
public class MutableHttpServletRequest
        extends HttpServletRequestWrapper
        implements HttpServletRequest
{
    String requestURI;

    public MutableHttpServletRequest(HttpServletRequest request)
    {
        super(request);

        setFromRequest((HttpServletRequest) request);
        /** @todo move up to ServletRequest) */
        setFromRequest((ServletRequest) request);
    }

    public MutableHttpServletRequest(HttpServletRequest request, String[] paramNames)
    {
        super(request);

        setFromRequest((HttpServletRequest) request);
        /** @todo move up to ServletRequest) */
        setFromRequest((ServletRequest) request, paramNames);
    }

    void setFromRequest(HttpServletRequest request)
    {
        this.requestURI = request.getRequestURI();
    }

    public void setRequestURI(String requestURI)
    {
        this.requestURI = requestURI;
    }

    public String getRequestURI()
    {
        return requestURI;
    }

    /**
     * @todo to be moved to MutableServletRequest once complete
     */
    Hashtable params = new Hashtable();

    void setFromRequest(ServletRequest request, String[] paramNames)
    {
        Map originalValues = request.getParameterMap();

        this.copyPartialMap(originalValues, params, paramNames);
    }

    /**
     * @todo Expose in a Utility class?
     * @param source
     * @param dest
     * @param keys
     */
    public  void copyPartialMap(Map source, Map dest, String[] keys)
    {
        if(keys != null && keys.length > 0 && keys[0].equals("*"))
		{
			dest = source;
		}
		else
		{
			for( int i = 0; i < keys.length; i++ )
			{
				String[] vals = ( String[] ) source.get( keys[i] );
				if( vals != null )
				{
					dest.put( keys[i], vals );
				}
			}
		}
		params.putAll(dest);
    }

    void setFromRequest(ServletRequest request)
    {
        params.putAll( request.getParameterMap() );
    }

    public Map getParameterMap()
    {
        return (Map) params.clone();
    }

    public String[] getParameterValues(String param)
    {
        return (String[]) params.get(param);
    }

    public void setParameterValues(String param, String[] values)
    {
        params.put(param, values);
    }

    public String getParameter(String param)
    {
        String[] vals = getParameterValues(param);

        return vals==null ? null : vals[0];
    }

    public void setParameter(String param, String value)
    {
        setParameterValues(param, new String[] {value} );
    }

    public Enumeration getParameterNames()
    {
        return params.keys();
    }

    public void clearParameter(String param)
    {
        params.remove(param);
    }
    /**
     * @todo implement remaining get/set methods
     */
}
