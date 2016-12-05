package net.tc.isma.views;

import java.io.InputStream;
import java.io.OutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.tc.isma.actions.Results;
import net.tc.isma.request.generic.requestImpl;

public interface View
{
	public static String JSP = "jsp";
	public static String SERVLET = "servlet";
	public static String DISPATCHER = "dispatcher";
	public static String STREAM = "stream";

	public String getContentType();
	public InputStream getIn();
	public OutputStream getOut();
	public HttpServletRequest getRequest();
	public HttpServletResponse getResponse();
	public Results getResult();
	public String getViewType();

	public void render();
	public void setContentType( String contentType );
	public void setIn( InputStream in );
	public void setOut( OutputStream out );
	public void setRequest( HttpServletRequest request );
	public void setResponse( HttpServletResponse response );
	public void setResult( Results result );
	public void setRequestWrapper( requestImpl requestWrapper );
	public void setViewType( String viewType );

	public String getName();
	public void setName( String name );
	public String getCls();
	public void setCls( String cls );
	public String getPackg();
	public void setPackg( String packg );
	public String resolvePath();
	public void setDefQuery(String query);
	public String getDefQuery();

}
