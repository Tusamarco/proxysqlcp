package net.tc.isma.utils;

import org.apache.commons.lang.builder.*;
import java.util.*;

/**
 * <p>Title: ISMA</p>
 * <p>Description: Information System Modular Architecture</p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: FAO of the UN</p>
 * @author  Tusa
 * @version 1.0
 */

public class HtmlToStringStyle extends ToStringStyle
{
    long level;
    long maxLevels;
    ToStringStyle subStyle;

    public HtmlToStringStyle()
    {
        this(0, 3);
    }

    public HtmlToStringStyle(int maxLevels)
    {
        this(0, maxLevels);
    }

    protected HtmlToStringStyle(long level, long maxLevels)
    {
        this.level = level;
        this.maxLevels = maxLevels;
        if ( isFullDetail(null) )
            this.subStyle = new HtmlToStringStyle(level + 1, maxLevels);
        setFieldSeparator("");
        setFieldNameValueSeparator("</td><td>");
        setNullText("<code>null</code>");
        setArrayContentDetail(true);
        setSizeStartText("size[");
        setSizeEndText("]");
    }

    public void appendStart(StringBuffer buffer, Object object)
    {
        buffer.append("<table cellspacing=\"0\" cellpadding=\"0\">");
        buffer.append("<tr><td class=\"objectHeading\" colspan=\"2\">");
        appendClassName(buffer, object);
        buffer.append("@");
        appendIdentityHashCode(buffer, object);
        buffer.append("</td></tr>");
    }

    protected void appendClassName(StringBuffer buffer, Object value)
    {
        buffer.append("<b><code>");
        buffer.append(value.getClass().getName());
        buffer.append("</code></b>");
    }

    protected void appendIdentityHashCode(StringBuffer buffer, Object object)
    {
        buffer.append("<code>");
        buffer.append(object.hashCode());
        buffer.append("</code>");
    }

    public void appendEnd(StringBuffer buffer, Object object)
    {
        buffer.append("</table>");
    }

    protected void appendFieldStart(StringBuffer buffer, String val)
    {
        buffer.append("<tr><td>");
        super.appendFieldStart(buffer, val);
    }

    protected void appendFieldEnd(StringBuffer buffer, String val)
    {
        super.appendFieldEnd(buffer, val);
        buffer.append("</td></tr>");
    }

    protected boolean isFullDetail(Boolean fullDetailRequest)
    {
        return (level < maxLevels);
    }

    protected void appendDetail(StringBuffer buffer, String field, Object val)
    {
        if ( isExpandable(val) )
        {
            buffer.append(ToStringBuilder.reflectionToString(val, subStyle));
        }
        else
        {
            buffer.append(HTML.escape(val));
        }
    }

    protected void appendSummary(StringBuffer buffer, String fieldName, Object value)
    {
        if ( isExpandable(value) )
        {
            buffer.append("{");
            buffer.append(value.getClass().getName());
            buffer.append("@");
            buffer.append(value.hashCode());
            buffer.append("}");
        }
        else
        {
            buffer.append(HTML.escape(value));
        }
    }
    protected boolean isExpandable(Object val)
    {
        String pkg = val.getClass().getPackage().getName();

        return pkg==null || (
             !pkg.startsWith("java.lang") &&
             !pkg.startsWith("java.sql") );
    }

    protected void appendInternal(StringBuffer buffer, String fieldName, Object value, boolean detail)
    {
        if (value instanceof Collection)
        {
            if (detail)
            {
                appendDetailInternal(buffer, fieldName, value, ((Collection)value).toArray());
            }
            else
            {
                appendSummarySize(buffer, fieldName, ( (Collection) value).size());
            }
        }
        else if (value.getClass().isArray())
        {
            if (detail)
            {
                appendDetailInternal(buffer, fieldName, value, (Object[]) value);
            }
            else
            {
                super.appendSummary(buffer, fieldName, (Object[]) value);
            }

        }
        else if (value instanceof Map)
        {
            if (detail)
            {
                appendDetailInternal(buffer, fieldName, value, (Map) value);
            }
            else
            {
                appendSummarySize(buffer, fieldName, ( (Map) value).size());
            }

        }
        else
        {
            if (detail)
            {
                appendDetail(buffer, fieldName, (Object) value);
            }
            else
            {
                appendSummary(buffer, fieldName, (Object) value);
            }
        }
    }

    protected void appendDetailInternal(StringBuffer buffer, String fieldName, Object value, Object[] values)
    {
        appendStart(buffer, value);
        for (int i = 0; i < values.length; i++)
        {
            append(buffer, fieldName+"["+i+"]", values[i], null);
        }
        appendEnd(buffer, value);
    }

    protected void appendDetailInternal(StringBuffer buffer, String fieldName, Object value, Map map)
    {
        appendStart(buffer, value);
        for (Iterator i = map.keySet().iterator(); i.hasNext(); )
        {
            Object key = i.next();
            buffer.append("<tr><td>");
            appendDetail(buffer, fieldName, key.toString());
            buffer.append("</td><td>");
            appendDetail(buffer, fieldName, map.get(key));
            buffer.append("</td></tr>");
        }
        appendEnd(buffer, value);
    }
}