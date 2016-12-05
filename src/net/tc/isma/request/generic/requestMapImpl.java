package net.tc.isma.request.generic ;

import java.io.*;
import java.security.*;
import java.util.*;

import javax.servlet.*;
import javax.servlet.http.*;

import net.tc.isma.persister.*;
import net.tc.isma.utils.*;
import net.tc.isma.utils.SynchronizedMap;

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
public class requestMapImpl implements HttpServletRequest
{
    SynchronizedMap attributes = new SynchronizedMap(0);
    SynchronizedMap parameters = new SynchronizedMap(0);
    private String requestURI ;
    private StringBuffer requestURL ;
    private String queryString ;
    private Principal userPrincipal ;
    private HttpSession session ;
    private String servletPath ;
    private int serverPort ;
    private String serverName ;
    private boolean secure ;
    private String scheme ;
    private boolean requestedSessionIdValid ;
    private boolean requestedSessionIdFromUrl ;
    private boolean requestedSessionIdFromURL ;
    private boolean requestedSessionIdFromCookie ;
    private String requestedSessionId ;
    private String remoteUser ;
    private int remotePort ;
    private String remoteHost ;
    private String remoteAddr ;
    private BufferedReader reader ;
    private String protocol ;
    private String pathTranslated ;
    private String pathInfo ;
    private String method ;
    private int localPort ;
    private String localName ;
    private Enumeration locales ;
    private Locale locale ;
    private String localAddr ;
    private ServletInputStream inputStream ;
    private Enumeration headerNames ;
    private Cookie[] cookies ;
    private String contextPath ;
    private String contentType ;
    private int contentLength ;
    private String authType ;
    private Enumeration attributeNames ;
    private Enumeration parameterNames ;
    private Map parameterMap ;
    private String characterEncoding ;
    private HttpServletRequest request = null;
    private boolean multipart;

    public requestMapImpl()
    {
    }
    public requestMapImpl(HttpServletRequest req )
    {
        request = req;
        Enumeration ea = req.getAttributeNames();
        while(ea.hasMoreElements())
        {
            String eaName = (String)ea.nextElement();
            Object eaValue = (Object)req.getAttribute(eaName);
            attributes.put(eaName, eaValue);
        }

        Enumeration ep = req.getParameterNames();
        while(ep.hasMoreElements())
        {
            String epName =  (String) ep.nextElement();
            String[] epValue = req.getParameterValues(epName);
            parameters.put(epName, epValue);
        }
        try{
            this.setRequestURI( ( ( HttpServletRequest ) req ).getRequestURI() ) ;
            this.setRequestURL( ( ( HttpServletRequest ) req ).getRequestURL() ) ;
            this.setQueryString( ( ( HttpServletRequest ) req ).getQueryString() ) ;
            this.setUserPrincipal( req.getUserPrincipal() ) ;
            this.setSession( req.getSession( true ) ) ;
            this.setServletPath( req.getServletPath() ) ;
            this.setServerPort( req.getServerPort() ) ;
            this.setServerName( req.getServerName() ) ;
            this.setSecure( req.isSecure() ) ;
            this.setScheme( req.getScheme() ) ;
            this.setRequestedSessionId( req.getRequestedSessionId() ) ;
            this.setRequestedSessionIdValid( req.isRequestedSessionIdValid() ) ;
            this.setRequestedSessionIdFromUrl( req.isRequestedSessionIdFromURL() ) ;
            this.setRequestedSessionIdFromCookie( req.isRequestedSessionIdFromCookie() ) ;
            this.setCookies( req.getCookies() ) ;
            this.setRemoteUser( req.getRemoteUser() ) ;
            this.setRemoteAddr( req.getRemoteAddr() ) ;
            this.setRemotePort( req.getRemotePort() ) ;
            this.setRemoteHost( req.getRemoteHost() ) ;
//            this.setReader( req.getReader() ) ;
            this.setProtocol( req.getProtocol() ) ;
            this.setPathTranslated( req.getPathTranslated() ) ;
            this.setPathInfo( req.getPathInfo() ) ;
            this.setMethod( req.getMethod() ) ;
            this.setLocalPort( req.getLocalPort() ) ;
            this.setLocalName( req.getLocalName() ) ;
            this.setLocales( req.getLocales() ) ;
            this.setLocale( req.getLocale() ) ;
            this.setLocalAddr( req.getLocalAddr() ) ;
            this.setInputStream( req.getInputStream() ) ;
            this.setHeaderNames( req.getHeaderNames() ) ;
            this.setContextPath( req.getContextPath() ) ;
            this.setContentLength( req.getContentLength() ) ;
            this.setContentType( req.getContentType() ) ;
            this.setAuthType( req.getAuthType() ) ;

           if(this.getQueryString() == null)
            {
                HttpQuery qs = new HttpQuery(req);
                setQueryString(qs.getQueryString());
                if(getQueryString() == null)
                    setQueryString(qs.toString());
            }

        }catch(Exception ex)
        {
            IsmaPersister.getLogSystem().error(ex) ;
        }

    }

    public Object getAttribute( String name )
    {
        return attributes.get(name) ;
    }

    public Enumeration getAttributeNames()
    {
        return (Enumeration) attributes.keySet() ;
    }

    public void setCharacterEncoding( String env )
        throws UnsupportedEncodingException
    {
    }

    public String getParameter( String name )
    {
        if(parameters.get(name) != null && ((String[])parameters.get(name)).length > 0)
            return ((String[])parameters.get(name))[0] ;

        return null;
    }

    public Enumeration getParameterNames()
    {
        return (Enumeration)parameters.keySet() ;
    }

    public String[] getParameterValues( String name )
    {

        if(parameters.get(name) != null )
            return (String[]) parameters.get(name);

        return null ;
    }

    public Map getParameterMap()
    {
        return parameters ;
    }

    public void setAttribute( String name , Object o )
    {
    }

    public void removeAttribute( String name )
    {
    }

    public RequestDispatcher getRequestDispatcher( String path )
    {
        return null ;
    }

    public String getRealPathContext( String path )
    {
        if(request.getSession().getServletContext() != null)
            request.getSession().getServletContext().getRealPath(path);
        return null;
    }

    public long getDateHeader( String name )
    {
        if(request != null)
            request.getDateHeader(name);
        return 0;    }

    public String getHeader( String name )
    {
        if(request != null)
            request.getHeader(name);
        return null;
    }

    public Enumeration getHeaders( String name )
    {
        if(request != null)
            request.getHeaders(name);
        return null;
    }

    public int getIntHeader( String name )
    {
        if(request != null)
            request.getIntHeader(name);
        return 0;
    }

    public boolean isUserInRole( String role )
    {
        if(request != null)
            request.isUserInRole(role);
        return false;
    }

    public HttpSession getSession( boolean create )
    {
        return this.session ;
    }

    public Principal getUserPrincipal()
    {
        return userPrincipal ;
    }

    public String getAuthType()
    {
        return authType ;
    }

    public String getCharacterEncoding()
    {
        return characterEncoding ;
    }

    public int getContentLength()
    {
        return contentLength ;
    }

    public String getContentType()
    {
        return contentType ;
    }

    public String getContextPath()
    {
        return contextPath ;
    }

    public Cookie[] getCookies()
    {
        return cookies ;
    }

    public Enumeration getHeaderNames()
    {
        return headerNames ;
    }

    public ServletInputStream getInputStream()
    {
        return inputStream ;
    }

    public String getLocalAddr()
    {
        return localAddr ;
    }

    public Locale getLocale()
    {
        return locale ;
    }

    public Enumeration getLocales()
    {
        return locales ;
    }

    public String getLocalName()
    {
        return localName ;
    }

    public int getLocalPort()
    {
        return localPort ;
    }

    public String getMethod()
    {
        return method ;
    }

    public String getPathInfo()
    {
        return pathInfo ;
    }

    public String getPathTranslated()
    {
        return pathTranslated ;
    }

    public String getProtocol()
    {
        return protocol ;
    }

    public String getQueryString()
    {
        return queryString ;
    }

    public BufferedReader getReader()
    {
        return reader ;
    }

    public String getRemoteAddr()
    {
        return remoteAddr ;
    }

    public String getRemoteHost()
    {
        return remoteHost ;
    }

    public int getRemotePort()
    {
        return remotePort ;
    }

    public String getRemoteUser()
    {
        return remoteUser ;
    }

    public String getRequestedSessionId()
    {
        return requestedSessionId ;
    }

    public boolean isRequestedSessionIdFromCookie()
    {
        return requestedSessionIdFromCookie ;
    }

    public boolean isRequestedSessionIdFromURL()
    {
        return requestedSessionIdFromURL ;
    }

    public boolean isRequestedSessionIdFromUrl()
    {
        return requestedSessionIdFromUrl ;
    }

    public boolean isRequestedSessionIdValid()
    {
        return requestedSessionIdValid ;
    }

    public String getRequestURI()
    {
        return requestURI ;
    }

    public StringBuffer getRequestURL()
    {
        return requestURL ;
    }

    public String getScheme()
    {
        return scheme ;
    }

    public boolean isSecure()
    {
        return secure ;
    }

    public String getServerName()
    {
        return serverName ;
    }

    public int getServerPort()
    {
        return serverPort ;
    }

    public String getServletPath()
    {
        return servletPath ;
    }

    public HttpSession getSession()
    {
        return session ;
    }

    public boolean isMultipart() {
        return multipart;
    }

    public void setRequestURI( String requestURI )
    {
        this.requestURI = requestURI ;
    }

    public void setRequestURL( StringBuffer requestURL )
    {
        this.requestURL = requestURL ;
    }

    public void setQueryString( String queryString )
    {
        this.queryString = queryString ;
    }

    public void setUserPrincipal( Principal userPrincipal )
    {
        this.userPrincipal = userPrincipal ;
    }

    public void setSession( HttpSession session )
    {
        this.session = session ;
    }

    public void setServletPath( String servletPath )
    {
        this.servletPath = servletPath ;
    }

    public void setServerPort( int serverPort )
    {
        this.serverPort = serverPort ;
    }

    public void setServerName( String serverName )
    {
        this.serverName = serverName ;
    }

    public void setSecure( boolean secure )
    {
        this.secure = secure ;
    }

    public void setScheme( String scheme )
    {
        this.scheme = scheme ;
    }

    public void setRequestedSessionIdValid( boolean requestedSessionIdValid )
    {
        this.requestedSessionIdValid = requestedSessionIdValid ;
    }

    public void setRequestedSessionIdFromUrl( boolean requestedSessionIdFromUrl )
    {
        this.requestedSessionIdFromUrl = requestedSessionIdFromUrl ;
    }

    public void setRequestedSessionIdFromURL( boolean requestedSessionIdFromURL )
    {
        this.requestedSessionIdFromURL = requestedSessionIdFromURL ;
    }

    public void setRequestedSessionIdFromCookie( boolean
                                                 requestedSessionIdFromCookie )
    {
        this.requestedSessionIdFromCookie = requestedSessionIdFromCookie ;
    }

    public void setRequestedSessionId( String requestedSessionId )
    {
        this.requestedSessionId = requestedSessionId ;
    }

    public void setRemoteUser( String remoteUser )
    {
        this.remoteUser = remoteUser ;
    }

    public void setRemotePort( int remotePort )
    {
        this.remotePort = remotePort ;
    }

    public void setRemoteHost( String remoteHost )
    {
        this.remoteHost = remoteHost ;
    }

    public void setRemoteAddr( String remoteAddr )
    {
        this.remoteAddr = remoteAddr ;
    }

    public void setReader( BufferedReader reader )
    {
        this.reader = reader ;
    }

    public void setProtocol( String protocol )
    {
        this.protocol = protocol ;
    }

    public void setPathTranslated( String pathTranslated )
    {
        this.pathTranslated = pathTranslated ;
    }

    public void setPathInfo( String pathInfo )
    {
        this.pathInfo = pathInfo ;
    }

    public void setMethod( String method )
    {
        this.method = method ;
    }

    public void setLocalPort( int localPort )
    {
        this.localPort = localPort ;
    }

    public void setLocalName( String localName )
    {
        this.localName = localName ;
    }

    public void setLocales( Enumeration locales )
    {
        this.locales = locales ;
    }

    public void setLocale( Locale locale )
    {
        this.locale = locale ;
    }

    public void setLocalAddr( String localAddr )
    {
        this.localAddr = localAddr ;
    }

    public void setInputStream( ServletInputStream inputStream )
    {
        this.inputStream = inputStream ;
    }

    public void setHeaderNames( Enumeration headerNames )
    {
        this.headerNames = headerNames ;
    }

    public void setCookies( Cookie[] cookies )
    {
        this.cookies = cookies ;
    }

    public void setContextPath( String contextPath )
    {
        this.contextPath = contextPath ;
    }

    public void setContentType( String contentType )
    {
        this.contentType = contentType ;
    }

    public void setContentLength( int contentLength )
    {
        this.contentLength = contentLength ;
    }

    public void setAuthType( String authType )
    {
        this.authType = authType ;
    }

    private void setMultipart(boolean multipart) {
        this.multipart = multipart;
    }

    public String getRealPath(String path) {
        return "";
    }
	@Override
	public AsyncContext getAsyncContext() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public long getContentLengthLong() {
		// TODO Auto-generated method stub
		return 0;
	}
	@Override
	public DispatcherType getDispatcherType() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public ServletContext getServletContext() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public boolean isAsyncStarted() {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public boolean isAsyncSupported() {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public AsyncContext startAsync() throws IllegalStateException {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public AsyncContext startAsync(ServletRequest arg0, ServletResponse arg1) throws IllegalStateException {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public boolean authenticate(HttpServletResponse arg0) throws IOException, ServletException {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public String changeSessionId() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public Part getPart(String arg0) throws IOException, ServletException {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public Collection<Part> getParts() throws IOException, ServletException {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public void login(String arg0, String arg1) throws ServletException {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void logout() throws ServletException {
		// TODO Auto-generated method stub
		
	}
	@Override
	public <T extends HttpUpgradeHandler> T upgrade(Class<T> arg0) throws IOException, ServletException {
		// TODO Auto-generated method stub
		return null;
	}
}
