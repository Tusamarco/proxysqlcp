package net.tc.isma.utils;

import java.util.*;
import net.tc.isma.persister.IsmaPersister;
import net.tc.isma.persister.PersistentObject;
import net.tc.isma.resources.ConfigResource;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.stream.StreamSource;
import java.io.ByteArrayOutputStream;
import java.io.BufferedOutputStream;
import javax.xml.transform.stream.StreamResult;
import net.tc.isma.persister.persistentObjectImpl;
import java.io.Serializable;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.nio.charset.CharsetEncoder;
import java.nio.ByteBuffer;

public class faostatOldCompatibilityTool
{
	public faostatOldCompatibilityTool()
	{
	}

	public static String getParameter( String param )
	{
		Map configMap = ( Map ) IsmaPersister.getDefaultConfigParameterMap();
		String param1 = (String)(( ConfigResource ) configMap.get( "isma_configuration." + param )).getValue();

		return param1;
	}

	public static Vector getWhatsNew( String lang, String version, boolean reload ) throws Exception
	{
		try
		{
			Vector whatsNew = new Vector();
			whatsNew  = ( Vector ) IsmaPersister.get( whatsNew.getClass(), ("isma_configuration.XML_WHATS_NEW_" + version +"." + lang).toLowerCase());

			if( reload || whatsNew == null  || whatsNew.size() < 0)
			{
				String xmlFile = IsmaPersister.getMAINROOT() + getParameter( ( "XML_WHATS_NEW_" + version ).toLowerCase() );
				String xslFile = IsmaPersister.getMAINROOT() + getParameter( ( "XSL_WHATS_NEW_" + version ).toLowerCase() );

//		 String xmlFile = Conf.getConfAbsPath("XML_WHATS_NEW_"+version.toUpperCase());
//		 String xslFile = Conf.getConfAbsPath("XSL_WHATS_NEW_"+version.toUpperCase());
				String resTransformation = readXMLWithXSL( xmlFile, xslFile, new String[]{lang} );

				String[] all_periods = resTransformation.split( "__Date__" );
				// Only For Debug. logger.fine("Nr periods: " + all_periods.length);

				whatsNew = new Vector();
				for( int i = 0; i < all_periods.length; i++ )
				{
					String period = all_periods[i];
					// Only For Debug. logger.finer("Current Period: "+period);
					String[] colls = period.split( "__Coll__" );
					// Only For Debug. logger.fine("Nr Collections: " + colls.length);
					Vector collections = new Vector();
					for( int j = 0; j < colls.length; j++ )
					{
						String coll = colls[j];
						// Only For Debug. logger.finer("Current Collection: "+coll);
						collections.addElement( coll );
					}
					whatsNew.addElement( collections );
				}

				PersistentObject poIn = new persistentObjectImpl(70, whatsNew);
				poIn.setKey( ("isma_configuration.XML_WHATS_NEW_" + version + "." + lang).toLowerCase());
				IsmaPersister.set( ( Serializable ) poIn.getKey(), poIn );

			}
			return whatsNew;
		}
		catch( Exception e )
		{
			System.err.println( e.getMessage() );
			return null;
		}
	}
	/**
	*  Read XML file interpreted by XSL file and put the result in a Vector
	*  @PARAM
	*         "xmlPath": it's the absolute path of XML file to read
	*         "xslPath": it's the absolute path of XSL file for interpreting the XML file
	*  @RETURN
	*         A vector containing a String that is the result of transformation
	*/
	public static String readXMLWithXSL(String xmlPath, String xslPath, String[] params) throws Exception
	{
	   try
	   {
		  // Only For Debug. logger.fine("xmlPath: "+xmlPath+" - xslPath: "+xslPath+" - params: "+params);
		  TransformerFactory tFactory = TransformerFactory.newInstance();
		  Transformer transformer = tFactory.newTransformer(new StreamSource(xslPath));

		  ByteArrayOutputStream outw = new ByteArrayOutputStream();
		  BufferedOutputStream bufos = new BufferedOutputStream(outw);

		  for (int i=0; i<params.length; i++)
		  {
			 transformer.setParameter("param_"+i, params[i]);
		  }
		  transformer.transform(new StreamSource(xmlPath), new StreamResult(bufos));

		  String result = outw.toString();
		  // Only For Debug. logger.fine("result: "+result);

		  return result;
	   } catch (Exception e)
	   {
		  System.err.println(e.getMessage());
		  return null;
	   }
	}
	public static String castToUTF(String iso_str)
	{
		try
		{
			String cstr = null;
			if( iso_str != null )
			{
				byte[] b = iso_str.getBytes( "ISO-8859-1" );
				cstr = new String( b, "UTF-8" );
			}
			return cstr;
		}
		catch(Exception ex)
		{}
		return iso_str;
	}

    public static String getLanguage(String lang)
    {
        if(lang.equals("zh"))
            return "CH";
        return lang.toUpperCase();

    }
    public static String getLanguageUri(String lang)
    {
        if(lang == null)
            return null;

        if(lang.equals("es"))
            return "s";
        if(lang.equals("zh"))
            return "c";
        if(lang.equals("ar"))
            return "a";
        if(lang.equals("fr"))
            return "f";
        if(lang.equals("en"))
            return "e";

        return "e";

    }
}
