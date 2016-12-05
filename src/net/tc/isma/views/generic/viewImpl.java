package net.tc.isma.views.generic;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.tc.isma.actions.generic.results;
import net.tc.isma.views.View;
import net.tc.isma.persister.IsmaPersister;
import java.util.Enumeration;
import net.tc.isma.request.generic.requestImpl;

public class viewImpl
	implements View
{
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

    public viewImpl( )
    {}

	public viewImpl( String nameIn )
	{
		if( name != null )
			this.name = nameIn;
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

//		Enumeration e = request.getHeaderNames();
//		while( e.hasMoreElements() )
//		{
//			String eS = ( String ) e.nextElement();
//			FaostatPersister.getLogVisualizer().debug( eS + " = " + request.getHeader( eS ) );
//		}

		if( getViewType().equals( View.JSP ) )
		{

			String contentType = "text/html; charset=UTF-8";
			if( getContentType() != null )
				contentType = getContentType();

			try
			{
				response.setContentType( contentType );

				String jspPath = null;
				if( resolvePath() != null && !resolvePath().equals( "" ) )
				{
					jspPath = resolvePath();
				}
				else
				{
					return;
				}

				RequestDispatcher disp = null;

				disp = context.getRequestDispatcher( jspPath );
				if( !response.isCommitted() )
                                    disp.include(request, response);

                                response.flushBuffer();
//					disp.include( request, response );

                return;
//                    disp.forward(request,response);

			}
			catch( Exception ex )
			{
				IsmaPersister.getLogVisualizer().error( "", ex );
			}
		}
		else
		{
			String servletClassName = resolvePath();

			try
			{
				HttpServlet view = ( HttpServlet ) Class.forName( servletClassName ).newInstance();
                                if(getDefQuery() != null && !getDefQuery().equals(""))
                                    request.setAttribute("URI", getDefQuery());
				view.service( request, response );
			}
			catch( Exception ex1 )
			{
				IsmaPersister.getLogVisualizer().error( "", ex1 );
			}
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
