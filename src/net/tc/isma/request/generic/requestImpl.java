package net.tc.isma.request.generic;

import java.io.*;
import java.lang.ref.*;
import java.security.*;
import java.util.*;

import javax.servlet.*;
import javax.servlet.http.*;

import com.jspsmart.upload.*;
import org.apache.commons.lang.builder.*;
import net.tc.isma.data.hibernate.*;
import net.tc.isma.persister.*;
import net.tc.isma.utils.*;
import net.tc.isma.utils.SynchronizedMap;

public class requestImpl implements HttpServletRequest {
    private net.tc.isma.auth.security.UserBase userBean = null;
    private HttpServletRequestWrapper wrapper = null;
    private HttpServletRequest req = null;
    private net.tc.isma.actions.Results result = null;
    private javax.servlet.http.HttpServletResponse response = null;
    private String language = "en";
    private java.util.Locale locale = null;
    private boolean reload = false;
    private String version = "ext";
    private String uri;
    private boolean system = false;
    private boolean multipart = false;

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
    private SmartUpload smartUpload;
    private Request smartUplRequest = null;
    Files uploadedFiles = null;
    private HSessionFactory dataStoreFactory;


    public requestImpl(HttpServletRequest request, ServletConfig config)
    {
        init(request, config);
    }
    public requestImpl(HttpServletRequest request)
    {
        init(request, null);
    }

    public requestImpl(Map paramsIn)
    {
        parameters = new SynchronizedMap(paramsIn);
    }

    private void init(HttpServletRequest request, ServletConfig config)
    {
        if (request.getHeader("content-type") != null &&
            request.getHeader("content-type").substring(0, 9).equals("multipart"))
            setMultipart(true);

        if(this.isMultipart())
        {
            java.lang.ref.SoftReference mS = new SoftReference(new com.jspsmart.upload.SmartUpload());
            smartUpload = (SmartUpload)mS.get();
            try
            {
                smartUpload.initialize(config, request, response);
                smartUpload.upload();
                smartUplRequest = smartUpload.getRequest();
            }
            catch (Exception ex1)
            {
//                ISMAMainServlet.sysLog.fwLog().error("******* error " + this.getClass().getName() + "   " + ex1);
            }


        }
        wrapper = new HttpServletRequestWrapper(request);
        req = request;


        Enumeration ea = req.getAttributeNames();

        while (ea.hasMoreElements()) {
            String eaName = (String) ea.nextElement();
            Object eaValue = (Object) req.getAttribute(eaName);
            attributes.put(eaName, eaValue);
        }

        if(isMultipart() && smartUplRequest != null)
        {
            Enumeration ep = smartUplRequest.getParameterNames();
            while (ep.hasMoreElements()) {
                String epName = (String) ep.nextElement();
                String[] epValue = smartUplRequest.getParameterValues(epName);
                parameters.put(epName, epValue);
            }
        }
        else
        {
            Enumeration ep = req.getParameterNames();
            while (ep.hasMoreElements()) {
                String epName = (String) ep.nextElement();
                String[] epValue = req.getParameterValues(epName);
                parameters.put(epName, epValue);
            }

        }
        try {
            this.setRequestURI(((HttpServletRequest) req).getRequestURI());
            this.setRequestURL(((HttpServletRequest) req).getRequestURL());
            this.setQueryString(((HttpServletRequest) req).getQueryString());
            this.setUserPrincipal(req.getUserPrincipal());
            this.setSession(req.getSession(true));
            this.setServletPath(req.getServletPath());
            this.setServerPort(req.getServerPort());
            this.setServerName(req.getServerName());
            this.setSecure(req.isSecure());
            this.setScheme(req.getScheme());
            this.setRequestedSessionId(req.getRequestedSessionId());
            this.setRequestedSessionIdValid(req.isRequestedSessionIdValid());
            this.setRequestedSessionIdFromUrl(req.isRequestedSessionIdFromURL());
            this.setRequestedSessionIdFromCookie(req.isRequestedSessionIdFromCookie());
            this.setCookies(req.getCookies());
            this.setRemoteUser(req.getRemoteUser());
            this.setRemoteAddr(req.getRemoteAddr());
            this.setRemotePort(req.getRemotePort());
            this.setRemoteHost(req.getRemoteHost());
    //            this.setReader( req.getReader() ) ;
            this.setProtocol(req.getProtocol());
            this.setPathTranslated(req.getPathTranslated());
            this.setPathInfo(req.getPathInfo());
            this.setMethod(req.getMethod());
            this.setLocalPort(req.getLocalPort());
            this.setLocalName(req.getLocalName());
            this.setLocales(req.getLocales());
            this.setLocale(req.getLocale());
            this.setLocalAddr(req.getLocalAddr());
            this.setInputStream(req.getInputStream());
            this.setHeaderNames(req.getHeaderNames());
            this.setContextPath(req.getContextPath());
            this.setContentLength(req.getContentLength());
            this.setContentType(req.getContentType());
            this.setAuthType(req.getAuthType());

            if (this.getQueryString() == null) {
                HttpQuery qs = new HttpQuery(req);
                setQueryString(qs.getQueryString());
                if (getQueryString() == null)
                    setQueryString(qs.toString());
            }


            if (parameters.get("lang") != null)
                setLanguage((String) parameters.get("lang"));
            if (parameters.get("language") != null && parameters.get("lang") == null)
                setLanguage(((String) parameters.get("language")).toLowerCase());

            if (parameters.get("version") != null)
                setVersion((String) parameters.get("version"));

            setLocale(new Locale(getLanguage()));

            if (parameters.get("reload") != null &&
                parameters.get("reload").equals("true"))
                this.reload = true;

            if (isMultipart() && smartUplRequest != null) {
                if (this.smartUpload.getFiles() != null)
                {
                    java.lang.ref.SoftReference mS = new SoftReference(smartUpload.getFiles());
                    uploadedFiles = (Files)mS.get();
                }
            }
        } catch (Exception ex) {
            IsmaPersister.getLogSystem().error(ex);
        }

    }



    public HttpServletRequest getRequest() {
        return req;
    }

//    public javax.servlet.http.HttpSession getSession() {
//        return wrapper.getSession(true);
//    }

    public javax.servlet.ServletContext getContext() {
        if (wrapper == null)
            return null;
        return wrapper.getSession(true).getServletContext();
    }

    public net.tc.isma.auth.security.UserBase getUserBean() {
        return userBean;
    }

    public void setUserBean(net.tc.isma.auth.security.UserBase userBean) {
        this.userBean = userBean;
    }

    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    public HttpQuery getHttpQuery() {
        if (wrapper == null)
            return null;

        return new HttpQuery((HttpServletRequest) wrapper.getRequest());

    }

    public HttpQuery getHttpQuery(String[] params) {
        if (wrapper == null && params != null)
            return null;

        return new HttpQuery((HttpServletRequest) wrapper.getRequest(), params);

    }

    public boolean equals(Object other) {
        if (!(other instanceof requestImpl))return false;
        requestImpl castOther = (requestImpl) other;
        return new EqualsBuilder()
                .append(this.wrapper, castOther.wrapper)
                .isEquals();
    }

    public int hashCode() {
        return new HashCodeBuilder()
                .append(wrapper)
                .toHashCode();
    }

    public net.tc.isma.actions.Results getResult() {
        return result;
    }

    public void setResult(net.tc.isma.actions.Results resultIn) {
        this.result = resultIn;
    }

    public javax.servlet.http.HttpServletResponse getResponse() {
        return response;
    }

    public void setResponse(javax.servlet.http.HttpServletResponse response) {
        this.response = response;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

//    public java.util.Locale getLocale() {
//        return locale;
//    }
//
//    public void setLocale(java.util.Locale localeIn) {
//        this.locale = localeIn;
//    }

    public boolean getReload() {
        return this.reload;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getUri() {
        return uri;
    }

    public boolean isSystem() {
        return system;
    }

    public boolean isMiltipart() {
        return multipart;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public void setSystem(boolean system) {
        this.system = system;
    }

    private void setMultipart(boolean multipartIn) {
        this.multipart = multipartIn;
    }



    public void setCharacterEncoding(String string) throws
            UnsupportedEncodingException {
        wrapper.setCharacterEncoding(string);
    }

//    public int getContentLength() {
//        return wrapper.getContentLength();
//    }
//
//    public String getContentType() {
//        return wrapper.getContentType();
//    }
//
//    public ServletInputStream getInputStream() throws IOException {
//        return wrapper.getInputStream();
//    }
/*
    public Enumeration getParameterNames() {
        return wrapper.getParameterNames();
    }

    public String[] getParameterValues(String string) {
        return wrapper.getParameterValues(string);
    }

    public Map getParameterMap() {
        return wrapper.getParameterMap();
    }

    public String getProtocol() {
        return wrapper.getProtocol();
    }

    public String getScheme() {
        return wrapper.getScheme();
    }

    public String getServerName() {
        return wrapper.getServerName();
    }

    public int getServerPort() {
        return wrapper.getServerPort();
    }
*/
    public BufferedReader getReader() throws IOException {
        return wrapper.getReader();
    }

//    public String getRemoteAddr() {
//        return wrapper.getRemoteAddr();
//    }

//    public String getRemoteHost() {
//        return wrapper.getRemoteHost();
//    }

//    public void setAttribute(String string, Object object) {
//        wrapper.setAttribute(string, object);
//    }

    public void removeAttribute(String string) {
        wrapper.removeAttribute(string);
    }

//    public Enumeration getLocales() {
//        return wrapper.getLocales();
//    }

//    public boolean isSecure() {
//        return wrapper.isSecure();
//    }

    public RequestDispatcher getRequestDispatcher(String string) {
        return wrapper.getRequestDispatcher(string);
    }

//    public String getRealPath(String string) {
//        return wrapper.getRealPath(string);
//    }

//    public int getRemotePort() {
//        return wrapper.getRemotePort();
//    }

//    public String getLocalName() {
//        return wrapper.getLocalName();
//    }
//
//    public String getLocalAddr() {
//        return wrapper.getLocalAddr();
//    }
//
//    public int getLocalPort() {
//        return wrapper.getLocalPort();
//    }
//
//    public String getAuthType() {
//        return wrapper.getAuthType();
//    }
//
//    public Cookie[] getCookies() {
//        return wrapper.getCookies();
//    }
//
//    public long getDateHeader(String string) {
//        return wrapper.getDateHeader(string);
//    }
//
//    public String getHeader(String string) {
//        return wrapper.getHeader(string);
//    }
//
//    public Enumeration getHeaders(String string) {
//        return wrapper.getHeaders(string);
//    }
//
//    public Enumeration getHeaderNames() {
//        return wrapper.getHeaderNames();
//    }
//
//    public int getIntHeader(String string) {
//        return wrapper.getIntHeader(string);
//    }
//
//    public String getMethod() {
//        return wrapper.getMethod();
//    }
//
//    public String getPathInfo() {
//        return wrapper.getPathInfo();
//    }
//
//    public String getPathTranslated() {
//        return wrapper.getPathTranslated();
//    }
//
//    public String getContextPath() {
//        return wrapper.getContextPath();
//    }
//
//    public String getQueryString() {
//        return wrapper.getQueryString();
//    }
//
//    public String getRemoteUser() {
//        return wrapper.getRemoteUser();
//    }
//
//    public boolean isUserInRole(String string) {
//        return wrapper.isUserInRole(string);
//    }
//
//    public Principal getUserPrincipal() {
//        return wrapper.getUserPrincipal();
//    }
//
//    public String getRequestedSessionId() {
//        return wrapper.getRequestedSessionId();
//    }
//
//    public String getRequestURI() {
//        return wrapper.getRequestURI();
//    }
//
//    public StringBuffer getRequestURL() {
//        return wrapper.getRequestURL();
//    }
//
//    public String getServletPath() {
//        return wrapper.getServletPath();
//    }
//
//    public HttpSession getSession(boolean _boolean) {
//        return wrapper.getSession(true);
//    }
//
//    public boolean isRequestedSessionIdValid() {
//        return wrapper.isRequestedSessionIdValid();
//    }
//
//    public boolean isRequestedSessionIdFromCookie() {
//        return wrapper.isRequestedSessionIdFromCookie();
//    }
//
//    public boolean isRequestedSessionIdFromURL() {
//        return wrapper.isRequestedSessionIdFromURL();
//    }
//
//    public boolean isRequestedSessionIdFromUrl() {
//        return wrapper.isRequestedSessionIdFromUrl();
//    }

    public Object getAttribute( String name )
    {
        return attributes.get(name) ;
    }

    public Enumeration getAttributeNames()
    {
        return (Enumeration) attributes.keySet() ;
    }


    public String getParameter( String name )
    {
        if(parameters instanceof SynchronizedMap
           && parameters.get(name)!= null
           && parameters.get(name) instanceof String
        )
        {
            if(parameters.get(name) != null )
                return (String) parameters.get(name) ;
            else
                return null;
        }

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

//    public void removeAttribute( String name )
//    {
//    }

//    public RequestDispatcher getRequestDispatcher( String path )
//    {
//        return null ;
//    }

    public String getRealPathContext( String path )
    {
        if(getContext() != null)
            getContext().getRealPath(path);
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

//    public BufferedReader getReader()
//    {
//        return reader ;
//    }

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

    public HSessionFactory getDataStoreFactory() {
        return dataStoreFactory;
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

//    public void setReader( BufferedReader reader )
//    {
//        this.reader = reader ;
//    }

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

    public void setDataStoreFactory(HSessionFactory
                                    dataStoreFactory) {
        this.dataStoreFactory = dataStoreFactory;
    }

    public Files getFiles()
    {
        return uploadedFiles;
    }
    public Map getParametersByNames(String[] names)
    {
        if(parameters != null && (names == null || names.length < 1))
            return null;

        Map outM = new SynchronizedMap(0);

        for (int i = 0; i < names.length; i++)
        {
            if(parameters.get(names[i]) != null && !parameters.get(names[i]).equals(""))
            {
                outM.put(names[i], parameters.get(names[i]));
            }
            else
                outM.put(names[i], null );

        }
        return outM;

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
