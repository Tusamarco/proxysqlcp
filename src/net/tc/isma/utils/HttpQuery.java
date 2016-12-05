package net.tc.isma.utils;

import javax.servlet.http.*;
import javax.servlet.*;
import java.net.URLEncoder;
import java.util.*;
import java.io.*;

/**
 * Builder for HTML links and forms.  Embellishes the generic read/write
 * implementation of HttpServletRequest (MutableHttpServletRequest) with
 * methods for setting and getting various data-types, default values, and
 * link/form creation.
 *
 * @todo refactor out MutableHttpRequest
 * @author
 * @version 1.0
 */
public class HttpQuery
        extends MutableHttpServletRequest
{
    private boolean forConcat;

    public HttpQuery(HttpServletRequest request)
    {
        super(request);
    }

    public HttpQuery(HttpServletRequest request, String[] paramNames)
    {
        super(request, paramNames);
    }

    // Shorthand/convenience methods.
    public String get(String param)
    {
        return getParameter(param);
    }

    public String get(String param, String defaultValue)
    {
        String val = getParameter(param);

        if ( Text.isEmpty(val) )
        {
            return defaultValue;
        }
        else
        {
            return val;
        }
    }

    public void set(String param, String value)
    {
        setParameter(param, value);
    }

    public void clear(String param)
    {
        clearParameter(param);
    }

    public void clear(String[] params)
    {
        for (int i = 0; i < params.length; i++)
        {
            clearParameter(params[i]);
        }
    }

    public String[] getValues(String param)
    {
        return getParameterValues(param);
    }

    public Iterator getNames()
    {
        return params.keySet().iterator();
    }

   public int getInt(String param, int deflt)
   {
       String val = get(param);

       if ( val == null )
       {
           return deflt;
       }

       try
       {
           return Integer.parseInt(val);
       }
       catch ( NumberFormatException e )
       {
           return deflt;
       }
   }

    public void setInt(String param, int value)
    {
        set( param, Integer.toString(value) );
    }

    public boolean defaultTo(String param, String value)
    {
        if ( isEmpty(param) )
        {
            set(param, value);
            return true;
        }
        else
        {
            return false;
        }
    }

    public boolean defaultTo(String param, int value)
    {
        if ( isEmpty(param) )
        {
            set(param, Integer.toString(value));
            return true;
        }
        else
        {
            try
            {
                int i = Integer.parseInt(get(param));
                return false;
            }
            catch ( NumberFormatException e )
            {
                set(param, Integer.toString(value));
                return true;
            }
        }
    }

    public String getURLEncoded(String param)
    {
        String s = get(param);

        if ( s == null ) return "";

//        return URLEncoder.encode(s);
        try
        {
            return URLEncoder.encode(s, "UTF-8");
        }
        catch ( UnsupportedEncodingException ex )
        {
            return "%3F%3F%3F";  // ???
        }
    }

    public boolean isNull(String param)
    {
        return get(param) == null;
    }

    public boolean isEmpty(String param)
    {
        return Text.isEmpty( get(param) );
    }

    /**
     * qs1 gets the contents of q2 appended to it.  '&' is added if necessary
     * @param qs1
     * @param qs2
     */
    public static void concatQueryStrings(StringBuffer qs1, StringBuffer qs2)
    {
        if ( qs1.length() > 0 && qs2.length() > 0 )
        {
            qs1.append("&");
        }

        qs1.append(qs2);
    }

    public StringBuffer toQueryString()
    {
        StringBuffer sb = new StringBuffer();

        Iterator iter = getNames();
        while ( iter.hasNext() )
        {
            String param = (String) iter.next();

            concatQueryStrings(sb, toQueryString(param, true));
        }

        if ( isForConcat() && sb.length() > 0 )
            sb.append("&");

        return sb;
    }

    /**
     * Return an HTTP QueryString with all values of a particular parameter
     * @param param
     * @param includeEmpty
     * @return
     */
    public StringBuffer toQueryString(String param, boolean includeEmpty)
    {
        StringBuffer sb = new StringBuffer();

        String[] vals = getValues(param);

        if ( Text.isEmpty(vals) )
        {
            if ( includeEmpty )
            {
                sb.append(param);
                sb.append("=");
            }

            return sb;
        }

        for (int j = 0; j < vals.length; j++)
        {
            StringBuffer sb2 = new StringBuffer();
            String val;
//            val = URLEncoder.encode(vals[j]);
            try
            {
                val = URLEncoder.encode(vals[j], "UTF-8");
            }
            catch (UnsupportedEncodingException ex)
            {
                val = "%3F%3F%3F";  // ???
            }

            sb2.append(param);
            sb2.append("=");
            sb2.append(val);

            concatQueryStrings(sb, sb2);
        }

        return sb;
    }

    public StringBuffer toQueryString(String[] params, boolean includeEmpty)
    {
        StringBuffer sb = new StringBuffer();

        for (int i = 0; i < params.length; i++)
        {
            concatQueryStrings(sb, toQueryString(params[i], includeEmpty));
        }

        return sb;
    }

    public String toString()
    {
        String s = toQueryString().toString();
        StringBuffer sb = new StringBuffer(getUri());

        if ( s.length() == 0 )
        {
            if ( isForConcat() )
                sb.append("?");
        }
        else
        {
            sb.append("?");
            sb.append(s);
        }

        return sb.toString();
    }

    public void setUri(String uri)
    {
        setRequestURI(uri);
    }

    public String getUri()
    {
        return getRequestURI();
    }

    public Map getParameterMap(String paramNames[])
    {
        Map res = new HashMap();

        super.copyPartialMap(params, res, paramNames);

        return res;
    }

    /**
     * Creates attributes for use in an HTML &lt;option&gt; tag in the format:
     * <code>name="&lt;<i>selectName</i>&gt;" [selected="true"]</code>
     * @param selectName current name of param containing value of &lt;select&gt; box, usually from HTTP request.
     * @param optionValue value attribute of &lt;option&gt;
     * @return value and selected attribute declarations, where appropriate
     */
    public String toHtmlOptionAttribs(String selectName, String optionValue)
    {
        return HTML.optionAttribs(optionValue, get(selectName));
    }

    public String toHtmlCheckboxAttribs(String selectName, String optionValue)
    {
        return HTML.checkboxAttribs(optionValue, getValues(selectName));
    }

    public String toHtmlHiddenFormFields()
    {
        return HTML.hiddenFormFields( params );
    }

    public String toHtmlHiddenFormField(String paramName)
    {
        return toHtmlHiddenFormFields(new String[] {paramName});
    }

    public String toHtmlHiddenFormFields(String[] paramNames)
    {
        return HTML.hiddenFormFields( getParameterMap(paramNames) );
    }

    public String toHtmlNameValueAttribs(String paramName)
    {
        return HTML.nameValueAttribs(paramName, get(paramName, ""));
    }

    public static HttpQuery getInstance(HttpServletRequest request)
    {
        if ( request instanceof HttpQuery )
        {
            return (HttpQuery) request;
        }
        else
        {
            Object o = request.getAttribute(HttpQuery.class.getName());
            if ( o != null && o instanceof HttpQuery )
            {
                return (HttpQuery) o;
            }
            else
            {
                return new HttpQuery(request);
            }
        }
    }

    public boolean isForConcat()
    {
        return forConcat;
    }

    public void setForConcat(boolean forConcat)
    {
        this.forConcat = forConcat;
    }

    public Object clone()
    {
        return new HttpQuery(this);
    }
}
