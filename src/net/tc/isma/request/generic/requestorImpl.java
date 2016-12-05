package net.tc.isma.request.generic ;

import javax.servlet.ServletRequest ;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import javax.servlet.http.HttpServletRequestWrapper;
import net.tc.isma.utils.Text;

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
public class requestorImpl implements Requestor
{
    private ArrayList requests = null;

    public requestorImpl(HttpServletRequest request)
    {
        if(request instanceof HttpServletRequest)
        {
            if ( ((HttpServletRequest)request).getHeader( "content-type" ) != null &&
                 ((HttpServletRequest)request).getHeader( "content-type" ).substring( 0 , 9 ).equals( "multipart" ) )
            {
                new Exception(" Could Not be instanziated using a MULTIPART request");
                return;
            }

        }

        setRequest( request ) ;
    }

    public void setRequest( HttpServletRequest request )
    {
        if ( ((HttpServletRequest)request).getHeader( "content-type" ) != null && ((HttpServletRequest)request).getHeader( "content-type" ).substring( 0 , 9 ).equals( "multipart" ) )
        {
            new Exception(" Could Not be instanziated using a MULTIPART request");
            return;
        }


        requestImpl req = new requestImpl(request);
        if(req.getQueryString() == null || req.getQueryString().equals(""))
            return;

        if(requests == null)
            requests = new ArrayList(1);

        for(int x = 0 ; x < requests.size() ; x++)
        {
            String elements = Text.defaultTo(((requestImpl)requests.get(x)).getParameter("Elements"),"");
            String years = Text.defaultTo(((requestImpl)requests.get(x)).getParameter("Years"),"");
            String items = Text.defaultTo(((requestImpl)requests.get(x)).getParameter("Items"),"");
            String areas = Text.defaultTo(((requestImpl)requests.get(x)).getParameter("Areas"),"");
            String format = Text.defaultTo(((requestImpl)requests.get(x)).getParameter("Format"),"");
            String xaxis = Text.defaultTo(((requestImpl)requests.get(x)).getParameter("Xaxis"),"");
            String yaxis = Text.defaultTo(((requestImpl)requests.get(x)).getParameter("Yaxis"),"");
            String aggregate = Text.defaultTo(((requestImpl)requests.get(x)).getParameter("Aggregate"),"");
            String calculate = Text.defaultTo(((requestImpl)requests.get(x)).getParameter("Calculate"),"");
            String symbols = Text.defaultTo(((requestImpl)requests.get(x)).getParameter("Symbols"),"");
            String code = Text.defaultTo(((requestImpl)requests.get(x)).getParameter("Code"),"");
            String sort = Text.defaultTo(((requestImpl)requests.get(x)).getParameter("Sort"),"");

            String elements1 = Text.defaultTo(req.getParameter("Elements"),"");
            String years1 = Text.defaultTo(req.getParameter("Years"),"");
            String items1 = Text.defaultTo(req.getParameter("Items"),"");
            String areas1 = Text.defaultTo(req.getParameter("Areas"),"");
            String format1 = Text.defaultTo(req.getParameter("Format"),"");
            String xaxis1 = Text.defaultTo(req.getParameter("Xaxis"),"");
            String yaxis1 = Text.defaultTo(req.getParameter("Yaxis"),"");
            String aggregate1 = Text.defaultTo(req.getParameter("Aggregate"),"");
            String calculate1 = Text.defaultTo(req.getParameter("Calculate"),"");
            String symbols1 = Text.defaultTo(req.getParameter("Symbols"),"");
            String code1 = Text.defaultTo(req.getParameter("Code"),"");
            String sort1 = Text.defaultTo(req.getParameter("Sort"),"");


            if(requests.get(x) != null)
            {
                if (
                    elements1.equals(elements) &&
                    years1.equals(years) &&
                    items1.equals(items) &&
                    areas1.equals(areas) &&
                    format1.equals(format) &&
                    xaxis1.equals(xaxis) &&
                    yaxis1.equals(yaxis) &&
                    aggregate1.equals(aggregate) &&
                    calculate1.equals(calculate) &&
                    symbols1.equals(symbols) &&
                    code1.equals(code) &&
                    sort1.equals(sort)
                    )
                    return ;
            }
        }
        requests.add(req);

    }

    public HttpServletRequest getRequest( int i )
    {
        if(requests != null && i < requests.size())
            return (HttpServletRequest)requests.get(i);

        return null ;
    }
    public void clear()
    {
        if(requests != null)
            requests.clear();
    }
    public void removeRequest( int idx )
    {
        if(requests != null && requests.size() > 0 &&  idx < requests.size())
        {
            requests.remove( idx ) ;
        }
    }

    public int size()
    {
        return requests.size() ;
    }
    public void moveUp(int idx)
    {
        if(idx != 0 )
        {
            int idx2 = idx -1;
            Object r2 = requests.get(idx2);
            Object r1 = requests.get(idx);
            requests.set(idx,r2);
            requests.set(idx2,r1);
            r1 = null;
            r2 = null;
        }
    }

    public void moveDown(int idx)
    {
        if(idx < (requests.size() - 1))
        {
            int idx2 = idx +1;
            Object r2 = requests.get(idx2);
            Object r1 = requests.get(idx);
            requests.set(idx,r2);
            requests.set(idx2,r1);
            r1 = null;
            r2 = null;
        }

    }

}
