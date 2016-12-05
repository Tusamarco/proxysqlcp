package net.tc.isma.views.generic;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;

import org.apache.log4j.Logger;
import net.tc.isma.views.View;
import net.tc.isma.actions.Results;
import net.tc.isma.request.generic.requestImpl;
import net.tc.isma.actions.generic.results;
import net.tc.isma.persister.IsmaPersister;
import net.tc.isma.utils.HttpQuery;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public class genericRedirect  extends HttpServlet implements View{
    //Initialize global variables
    private String contentType;
    private String viewType;
    private javax.servlet.http.HttpServletRequest request;
    private javax.servlet.http.HttpServletResponse response;
    private ServletContext context = null;
    private java.io.OutputStream out;
    private java.io.InputStream in;
    private net.tc.isma.actions.Results result;
    private String name = null;
    private String cls = null;
    private String packg = null;
    private String query = null;
    private net.tc.isma.request.generic.requestImpl requestWrapper;

    public genericRedirect( )
    {
    }

    public genericRedirect( String nameIn )
    {
            if( name != null )
                    this.name = nameIn;
    }


    protected void service(HttpServletRequest req, HttpServletResponse resp) throws
            ServletException, IOException {
        HttpQuery qs = null;
        String additionalParameters = null;

        if(requestWrapper != null && requestWrapper.getQueryString() != null && !requestWrapper.getQueryString().equals(""))
            additionalParameters = requestWrapper.getQueryString();

        String  uri = null;
        if(req != null && req.getParameter("URI") != null)
        {
            uri = req.getParameter("URI");

        }
        if(uri == null && req != null && req.getAttribute("URI") != null)
            uri = (String) req.getAttribute("URI");

        if(additionalParameters != null)
        {
            if(uri.indexOf("?") > 0 && uri.indexOf("&") > 0)
                uri = uri + "&" + additionalParameters;
            else if(uri.indexOf("?") > 0 && uri.indexOf("&") <= 0)
                uri = uri + additionalParameters;
            else if(uri.indexOf("?") <= 0 )
                uri = uri + "?" + additionalParameters;

        }
        resp.sendRedirect(uri);
        return;
    }


    public String getContentType()
    {
            return contentType;
    }

    public void setContentType( String contentType )
    {
            this.contentType = contentType;
    }

    public String getViewType()
    {
            return viewType;
    }

    public void setViewType( String viewType )
    {
            this.viewType = viewType;
    }

    public javax.servlet.http.HttpServletRequest getRequest()
    {
            return request;
    }

    public void setRequest( javax.servlet.http.HttpServletRequest request )
    {
            this.request = request;
    }

    public javax.servlet.http.HttpServletResponse getResponse()
    {
            return response;
    }

    public void setResponse( javax.servlet.http.HttpServletResponse response )
    {
            this.response = response;
    }

    public java.io.OutputStream getOut()
    {
            return out;
    }

    public void setOut( java.io.OutputStream out )
    {
            this.out = out;
    }

    public java.io.InputStream getIn()
    {
            return in;
    }

    public void setIn( java.io.InputStream in )
    {
            this.in = in;
    }

    public net.tc.isma.actions.Results getResult()
    {
            return result;
    }

    public void setResult( net.tc.isma.actions.Results resultIn )
    {
            this.result = resultIn;
            if( result != null )
            {
                    requestImpl reqImpl = ( requestImpl ) ( ( results ) result ).get( requestImpl.class );

                    this.request = reqImpl.getRequest();
                    this.response = ( ( ( results ) result ).get( HttpServletResponse.class ) != null ) ?
                            ( HttpServletResponse ) ( ( results ) result ).get( HttpServletResponse.class ) : null;
                    this.context = reqImpl.getContext();
                    request.setAttribute( "requestImplementation", reqImpl );

            }
    }
    public void render()
    {
        try
        {

            if(this.getDefQuery() != null)
                this.request.setAttribute("URI", this.getDefQuery());

            this.service(this.request, this.response);
        } catch (IOException ex) {
        } catch (ServletException ex) {
            ex.printStackTrace();
        }

    }

    public String getName()
    {
            return name;
    }

    public void setName( String name )
    {
            this.name = name;
    }

    public String getCls()
    {
            return cls;
    }

    public void setCls( String cls )
    {
            this.cls = cls;
    }

    public String getPackg()
    {
            return packg;
    }

    public void setPackg( String packg )
    {
            this.packg = packg;
    }

    public String resolvePath()
    {
            String path = "";
            if( packg == null || packg.equals( "" ) || cls == null || cls.equals( "" ) )
                    return null;

            if( !packg.equals( "." ) )
                    path = packg + ".";

            path = path + cls;

            return path;
    }

    public void setDefQuery( String queryIn )
    {
            query = queryIn;
    }

    public String getDefQuery()
    {
            return query;
    }

    public void setRequestWrapper( net.tc.isma.request.generic.requestImpl requestWrapperIn )
    {
            this.requestWrapper = requestWrapperIn;
            if( requestWrapper != null )
            {
                    this.request = requestWrapper.getRequest();
                    this.response = requestWrapper.getResponse();
                    this.context = requestWrapper.getContext();
                    //this.setResult(requestWrapper.getResult());
                    request.setAttribute( "requestImplementation", requestWrapper );
            }

    }

}
