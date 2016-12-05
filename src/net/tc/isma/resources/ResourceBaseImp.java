package net.tc.isma.resources;

import java.util.Map;
import java.io.File;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.io.SAXReader;
import org.dom4j.Element;
import org.dom4j.Node;
import java.util.List;
import org.dom4j.Attribute;
import java.util.HashMap;
import java.io.IOException;
import net.tc.isma.persister.*;
import net.tc.isma.*;
import net.tc.isma.utils.*;
import net.tc.isma.xml.dom.StaticDocumentFactory;
import java.io.UnsupportedEncodingException;

public class ResourceBaseImp extends HashMap implements Resource
{
	Long resourceDate = null;
	private int resType = 0;
	private int type = Resource.INI;
	private int physicalType = Resource.INI;
	public File internalPhysicalReference = null;
	private static String encoding = "UTF-8";

	public ResourceBaseImp()
	{
	}


	public int getResourceType()
	{
		return 0;
	}
	public ResourceBaseImp( File f, int resource) throws IOException
	{

		if(f == null || resource == 0 )
			throw new IOException();
		internalPhysicalReference = f;
		if(!internalPhysicalReference.exists())
			throw new IOException();

		resType = resource;
		if(internalPhysicalReference.getPath().toLowerCase().indexOf(".xml") > 0)
			physicalType = Resource.XML;

		if(physicalType == Resource.XML)
			loadXml();
		else
			load();
	}

	public ResourceBaseImp( File f ) throws IOException
	{
		internalPhysicalReference = f;
		if(!internalPhysicalReference.exists())
			throw new IOException();

		resType = Resource.CONFIGURATION;
		if(internalPhysicalReference.getPath().toLowerCase().indexOf(".xml") > 0)
			physicalType = Resource.XML;

		if(physicalType == Resource.XML)
			loadXml();
		else
			load();
	}

	public ResourceBaseImp( String file ) throws IOException
	{
		if(file == null || file.equals(""))
			return;
		File internalPhysicalReference = new File(file);
		if(!internalPhysicalReference.exists())
			throw new IOException();

		resType = Resource.CONFIGURATION;
		if(internalPhysicalReference.getPath().toLowerCase().indexOf(".xml") > 0)
			physicalType = Resource.XML;

		if(physicalType == Resource.XML)
			loadXml();
		else
			load();

	}

	public void load()
	{
	}


	private static Document getRootDocument(File f)
	{
		if(!f.exists())
			return null;

		SAXReader reader = new SAXReader();
		Document document = null;
		try
		{
			document = reader.read( f );
			return document;

		}
		catch( DocumentException ex )
		{
			ex.printStackTrace();
		}

		return null;

	}
	public static Map getElementAttribute(Element el)
	{
		Map am = null;
		if( el.attributes() != null )
		{
			am = new HashMap();
			List attributes = el.attributes();
			for( int ia = 0; ia < attributes.size(); ia++ )
			{
				Attribute a = (Attribute)attributes.get(ia);
				am.put(a.getQName().getName(),Text.getEncodedString(a.getValue(),encoding));
			}
		}

		return am;
	}

	public static  Map getInizializationParameter(File f)
	{
		Document doc = getRootDocument(f);
		if(doc == null)
			return null;
		Map mp = getElementAttribute(doc.getRootElement());

		return mp;
	}
	public void loadXml()
	{
		if( internalPhysicalReference == null )
			return;
		try
		{
			SAXReader reader = new SAXReader();
			Document document = null;// reader.read( internalPhysicalReference );
			clear();

			Map docMap = StaticDocumentFactory.getDocument( internalPhysicalReference );
			if( docMap != null && docMap.size() < 0 )
			{
				if( docMap.get( Document.class ) != null )
				{
					document = ( Document ) docMap.get( Document.class );
					encoding = ( String ) docMap.get( "encoding" );
					treeWalk( document.getRootElement() );
				}
			}

//			treeWalk( document.getRootElement() );
			return;

		}
		catch( Exception ex )
		{
			ex.printStackTrace();
		}


	}

	public void treeWalk( Element element )
	{
		for( int i = 0; i < element.nodeCount(); i++ )
		{
			Node node = element.node( i );
			Map attributes = null;

			if(node instanceof Element)
			{
				String key = node.getPath().replaceAll("/",".").replaceAll("Text()","");
				if(key.startsWith("."))
					key = key.substring(1);

				Object type = null;
				Object value = null;

				if( ( ( Element ) node ).attributes() != null )
				{
					attributes = getElementAttribute( ( Element ) node );
					if(attributes.containsKey("type"))
					{
						try
						{
							type = Class.forName( ( String ) attributes.get( "type" ) ).newInstance();
						}
						catch( Exception ex )
						{
							ex.printStackTrace();
						}
					}
				}
				if(type == null)
				{
					if( encoding != null )
					{
						try
						{
							value = Text.getEncodedString(node.getText(), encoding ); //node.getText();
							IsmaPersister.getLogSystem().info( "Encoded("+encoding+")parameter " + key + " = " + value );

						}
						catch( Exception uex )
						{
							IsmaPersister.getLogSystem().error( "Unsupported Encoding = " + uex );
							value = node.getText();
						}
					}
					else
					{
						value = node.getText();
					}
				}
				else
				{
					if(type instanceof java.util.Map)
					{
						String[] nodeValues = node.getText().split(",");
						if(nodeValues.length > 0)
						{
							Map valueMap = new HashMap();
							for(int ia = 0 ; ia < nodeValues.length ; ia++)
								valueMap.put(nodeValues[ia], nodeValues[ia]);

							value = valueMap;
						}

					}
				}
				if(value instanceof String)
					value = cleanValue((String)value);

				if(!containsKey(key))
				{
					if( value != null )
					{
						try{
							ConfigResource cr = new ConfigResourceImpl((Map)attributes,key,value);
							put( key, cr );
						}
						catch(Exception exx){exx.printStackTrace();}

					}
				}
				treeWalk( ( Element ) node );
			}
			else
			{
				continue;
//				System.out.println("********* " +node.getName());
//				System.out.println("********* " +node.getNodeType());
//				System.out.println("********* " +node.getNodeTypeName());
//				System.out.println("********* " +node.getPath());
//				System.out.println("********* " +node.getText());
			}
		}
		return;
	}
	public String cleanValue( String value )
	{
		if(value == null || value.equals(""))
			return null;

		String valueOut = value.replaceAll("\\t","").replaceAll("\\r","").replaceAll("\\n","");
		return valueOut;
	}

	public static int getResourceType(File f)
	{
		if(f != null && f.exists())
		{
			Map conf = getInizializationParameter( f );
			if( f.getPath().indexOf( ".xml" ) > 0 )
			{
				if(conf.containsKey("tohandleas"))
				{
					if(Integer.parseInt((String)conf.get("tohandleas")) == Resource.INI)
						return Resource.INI;
					return Resource.XML;
				}
				else
					return Resource.XML;
			}
			else
				return Resource.INI;
		}
		return 0;
	}

	public Object getResource()
	{
		if(isInizialized && internalPhysicalReference != null)
			return internalPhysicalReference;

		return null;
	}
    public int getPhysicalType()
    {
		return physicalType;
    }
	public void setPhysicalType()
	{
		if(internalPhysicalReference.getPath().toLowerCase().indexOf(".xml") > 0)
			physicalType = Resource.XML;
	}

}
