package net.tc.isma.xml.dom;

import java.io.File;
import java.util.*;
import org.dom4j.Document;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import org.apache.xml.serialize.OutputFormat;
import org.dom4j.io.DOMReader;
import net.tc.isma.persister.IsmaPersister;

public class StaticDocumentFactory
{
	public StaticDocumentFactory()
	{
	}

	public static Map getDocument(File file)
	{
		if(file == null || !file.exists() || !file.canRead())
			return null;
		Map m = null;
		String encoding = null;


		Document document = null;
		try
		{
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = factory.newDocumentBuilder();
			org.w3c.dom.Document dom = db.parse( file );
			OutputFormat of = new OutputFormat( dom );
			encoding = of.getEncoding();
			DOMReader domr = new DOMReader();
			document = domr.read( dom );
			m = new HashMap();
			m.put(Document.class,document);
			m.put("encoding",encoding);
		}
		catch( Exception ioex )
		{
			IsmaPersister.getLogDataAccess().error("",ioex);//ioex.printStackTrace();
			return null;
		}

		return m;
	}
    public static org.w3c.dom.Document newDocument(String root)
    {
        try
        {

            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance() ;
            DocumentBuilder db = factory.newDocumentBuilder() ;
            org.w3c.dom.Document dom = db.newDocument() ;
            dom.createElement(root);
            return dom;
        }
            catch ( Exception ioex )
            {
                IsmaPersister.getLogDataAccess().error( "" , ioex ) ; //ioex.printStackTrace();
                return null ;
            }

    }
}
