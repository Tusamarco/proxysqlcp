package net.tc.isma.views.stream;

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

import net.tc.isma.request.generic.requestImpl;
//import org.apache.fop.apps.*;
//import org.apache.avalon.framework.logger.*;

import java.io.FileInputStream;
import java.io.File;

import org.xml.sax.InputSource;
//import org.fao.ncp.objects.Reports;
//import org.fao.ncp.common.reporter.xmlObjectGenerator;
import java.io.ByteArrayOutputStream;
import java.io.*;
import net.tc.isma.actions.Results;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.stream.StreamSource;
import javax.xml.transform.Source;
import javax.xml.transform.sax.SAXResult;
import javax.xml.transform.Result;
import net.tc.isma.lang.LanguageSelector;
//import org.apache.fop.apps.Options;


public class viewFop
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

    public viewFop( )
    {}

	public viewFop( String nameIn )
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


//            Reports r = null;
//            r = (Reports)IsmaPersister.get(Reports.class,"CurrentReport");
//
//            if(r == null)
//            {
//                IsmaPersister.getLogXmlXslTransformation().error("Error in transformation Report does not exist! ");
//                return;
//            }
//            String XSLfile = this.getDefQuery();
//            xmlObjectGenerator xo = new xmlObjectGenerator();
//
//            InputSource xslIs = xo.getXslInputSource(XSLfile);
//            InputSource xmlIs = xo.getXmlReportAsInputSource(r);
//            try{
//                //                XSLTInputHandler input = new XSLTInputHandler(xmlIs, xslIs);
//                //                renderXML(input, response);
//                String reportXHtml = xo.getReportXmlTransformedAsFoString(r,new LanguageSelector(r.getLanguage()));
//                renderXMLPDF(reportXHtml, response);
//                //                renderXML2(xo.getXmlReportAsInputStream(r),xo.getXslInputStream(XSLfile) , response);
//            }
//            catch(Exception ex)
//            {
//                ex.printStackTrace();
//            }

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

        private void renderXML2(InputStream xmlIs, InputStream xslIs, HttpServletResponse response)
        {
//            Driver driver = new Driver();
//
//            //Setup logger
//            //            Logger logger = new ConsoleLogger(ConsoleLogger.LEVEL_INFO);
//            //            driver.setLogger(logger);
//
//            //Setup Renderer (output format)
//            driver.setRenderer(Driver.RENDER_PDF);
//
//            //Setup output
//            OutputStream out = null;
//            try {
//                out = response.getOutputStream();
//                driver.setOutputStream(out);
//
//                //Setup XSLT
//                TransformerFactory factory = TransformerFactory.newInstance();
//                Transformer transformer = factory.newTransformer(new StreamSource(xslIs));
//
//                //Setup input for XSLT transformation
//                Source src = new StreamSource(xmlIs);
//
//                //Resulting SAX events (the generated FO) must be piped through to FOP
//                Result res = new SAXResult(driver.getContentHandler());
//
//                //Start XSLT transformation and FOP processing
//                transformer.transform(src, res);
//            }
//            catch(Exception ex)
//            {
//                ex.printStackTrace();
//            } finally {
//                try{
//                    out.flush();
//                    out.close();
//                }catch(Exception ex1){ex1.printStackTrace();}
//            }
        }



        public void renderXMLPDF(String  input,
                              HttpServletResponse response) throws ServletException {

//         try
//         {
//             String userConfig= IsmaPersister.getMAINROOT() + "/WEB-INF/userconfig.xml";
//             System.out.println(userConfig);
//             java.io.File userConfigFile = new File(userConfig);
//             org.apache.fop.apps.Options options = new Options(userConfigFile);
//             String fontBaseDir = IsmaPersister.getMAINROOT() + "/WEB-INF/xmlFonts";
//             System.out.println("fontBaseDir " +fontBaseDir);
//             org.apache.fop.configuration.Configuration.put("fontBaseDir", fontBaseDir);
//
//
//           //  String baseDir = FaostatPersister.getMAINROOT() + "/WEB-INF/xmlFonts";
//           //  System.out.println("baseDir " +baseDir);
//           //  org.apache.fop.configuration.Configuration.put("baseDir", baseDir);
//           //  org.apache.fop.configuration.Configuration.put("fontBaseDir","C:/WINNT/Fonts");
//
//           //  String fontPath = "C:/WINNT/Fonts";
//           //  System.out.println("fontPath " +fontPath);
//           //  org.apache.fop.configuration.Configuration.put("fontPath",fontPath);
//             // org.apache.fop.fonts.apps.TTFReader.
//
//
//             byte[] br = input.getBytes("UTF-8");
//             ByteArrayInputStream bai = new ByteArrayInputStream(br);
//             Driver driver = new Driver(new InputSource(bai),response.getOutputStream());
//
//             //Setup logger
//             //             Logger logger = new ConsoleLogger(ConsoleLogger.LEVEL_DEBUG);
//             //             driver.setLogger(logger);
//
//             //Setup Renderer (output format)
//             driver.setRenderer(Driver.RENDER_PDF);
//             driver.run();
//         }
//         catch(Exception exx)
//         {
//             exx.printStackTrace();
//         }

       }

}
