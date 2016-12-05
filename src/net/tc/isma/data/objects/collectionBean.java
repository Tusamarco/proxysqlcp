package net.tc.isma.data.objects;

import java.io.* ;
import java.util.Iterator ;
import java.util.Map ;

import org.apache.commons.lang.builder.* ;
import net.tc.isma.data.common.MultiLanguage ;
import net.tc.isma.persister.IsmaPersister ;
import net.tc.isma.utils.SynchronizedMap ;
import net.tc.isma.comparators.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public class collectionBean extends MultiLanguage
	implements Serializable
{

	private Object id;
	private String nameEn;
	private String nameFr;
	private String nameEs;
	private String nameAr;
	private String nameZh;
	private Map notes;
	private String thirdkey;
	private String axisElement;
	private String zone;
	private int version;
	private String additionalText;
	private String disseminateind;
	private java.util.Map areas;
	private java.util.Map elements;
	private java.util.Map items;
	private String type;
	private String grpind;
	private String firstkey;
	private String secondkeytex;
	private String selecttext;
	private String copyrightlink;
	private String copyrighttext;
  private String startYear;
  private String endYear;
    private String redirect;
    private String internalname ;
    public String toString()
	{
		return ToStringBuilder.reflectionToString( this );
	}

	public boolean equals( Object other )
	{
		if( ! ( other instanceof collectionBean ) )return false;
		collectionBean castOther = ( collectionBean ) other;
		return new EqualsBuilder()
			.append( this.id, castOther.id )
			.isEquals();
	}

	public int hashCode()
	{
		return new HashCodeBuilder()
			.append( id )
			.toHashCode();
	}

	private void writeObject( ObjectOutputStream oos ) throws IOException
	{
		oos.defaultWriteObject();
	}

	private void readObject( ObjectInputStream ois ) throws ClassNotFoundException, IOException
	{
		ois.defaultReadObject();
	}

	public Object getId()
	{
		return id;
	}

	public void setId( Object id )
	{
		this.id = id;
	}

	public String getNameEn()
	{
		return nameEn;
	}

	public void setNameEn( String nameEn )
	{
		this.nameEn = nameEn;
	}

	public String getNameFr()
	{
		return nameFr;
	}

	public void setNameFr( String nameFr )
	{
		this.nameFr = nameFr;
	}

	public String getNameEs()
	{
		return nameEs;
	}

	public void setNameEs( String nameEs )
	{
		this.nameEs = nameEs;
	}

	public String getNameAr()
	{
		return nameAr;
	}

	public void setNameAr( String nameAr )
	{
		this.nameAr = nameAr;
	}

	public String getNameZh()
	{
		return nameZh;
	}

	public void setNameZh( String nameZh )
	{
		this.nameZh = nameZh;
	}











	public Map getNotes()
	{
		return notes;
	}

	public void setNotes( Map noteIn )
	{
		this.notes = noteIn;
		this.notes = notes;
	}

	public String getThirdkey()
	{
		return thirdkey;
	}

	public void setThirdkey( String thirdkey )
	{
		this.thirdkey = thirdkey;
	}

	public String getAxisElement()
	{
		return axisElement;
	}

	public void setAxisElement( String axisElement )
	{
		this.axisElement = axisElement;
	}

	public String getZone()
	{
		return zone;
	}

	public void setZone( String zone )
	{
		this.zone = zone;
	}

	public int getVersion()
	{
		return version;
	}

	public void setVersion( int version )
	{
		this.version = version;
	}

	public String getAdditionalText()
	{
		return additionalText;
	}

	public void setAdditionalText( String additionalText )
	{
		this.additionalText = additionalText;
	}

	public java.util.Map getItems()
	{
		return items;
	}

	public void setItems( java.util.Map items )
	{
		this.items = items;
	}

	public java.util.Map getElements()
	{
		return elements;
	}

	public void setElements( java.util.Map elements )
	{
		this.elements = elements;
	}

	public java.util.Map getAreas()
	{
		return areas;
	}

	public void setAreas( java.util.Map areas )
	{
		this.areas = areas;
	}

	public String getDisseminateind()
	{
		return disseminateind;
	}

	public void setDisseminateind( String disseminateind )
	{
		this.disseminateind = disseminateind;
	}



	public String getType()
	{
		return type;
	}

	public void setType( String type )
	{
		this.type = type;
	}

	public String getGrpind()
	{
		return grpind;
	}

	public void setGrpind( String grpind )
	{
		this.grpind = grpind;
	}

	public void loadNotes( Map object )
	{
		notes = new SynchronizedMap(0);
		Iterator it = object.keySet().iterator();

		while( it.hasNext() )
		{
			String key = ( String ) it.next();
			Map keys = new SynchronizedMap(0);
			if(key.indexOf(".version") > 0 || key.indexOf(".index") > 0)
			{
				String version = "";
				String index= "";
				String[] kar = key.split("\\.");
				for(int i = 0 ; i < kar.length ; i++)
				{
					if(kar[i].startsWith("version"))
						version = kar[i].replaceAll("version","");
					if(kar[i].startsWith("index"))
						index = kar[i].replaceAll("index","");

				}
				keys.put("version",version);
				keys.put("index",index);
			}
			notes.put(keys, ( String ) object.get( key ) );
		}
	}

	public String getFirstkey()
	{
		return firstkey;
	}

	public void setFirstkey( String firstkey )
	{
		this.firstkey = firstkey;
	}

	public String getSecondkeytex()
	{
		return secondkeytex;
	}

	public void setSecondkeytex( String secondkeytex )
	{
		this.secondkeytex = secondkeytex;
	}

	public String getSelecttext()
	{
		return selecttext;
	}

	public void setSelecttext( String selecttext )
	{
		this.selecttext = selecttext;
	}

	public String getCopyrightlink()
	{
		return copyrightlink;
	}

	public void setCopyrightlink( String copyrightlink )
	{
		this.copyrightlink = copyrightlink;
	}

	public String getCopyrighttext()
	{
		return copyrighttext;
	}

	public void setCopyrighttext( String copyrighttext )
	{
		this.copyrighttext = copyrighttext;
	}

	public void loadItems( Object map )
	{
		try{

			if( map instanceof Map )
			{
				items = new SynchronizedMap( 0 );
				Iterator it = ( ( Map ) map ).keySet().iterator();
				while( it.hasNext() )
				{
					Object key = it.next();
					String value = ( String ) ( ( Map ) map ).get( key );
					items.put( value, value );
				}
			}
			else
				items = null;
		}
		catch(Exception ex){IsmaPersister.getLogDataAccess().error("",ex);}

	}
	public void loadAreas( Object map )
	{
		try{
			if( map instanceof Map )
			{

				areas = new SynchronizedMap( 0 );
				Iterator it = ( ( Map ) map ).keySet().iterator();
				while( it.hasNext() )
				{
					Object key = it.next();
					String value = ( String ) ( ( Map ) map ).get( key );
					areas.put( value, value );
				}
			}
			else
				areas = null;
		}
		catch(Exception ex){IsmaPersister.getLogDataAccess().error("",ex);}



	}

	public void loadElements( Object map )
	{
		try{
			if( map instanceof Map )
			{

				elements = new SynchronizedMap( 0 );
				Iterator it = ( ( Map ) map ).keySet().iterator();
				while( it.hasNext() )
				{
					Object key = it.next();
//                    if(key instanceof String)
//                    {
//                        Object value =  ( ( Map ) map ).get( key ) ;
//                        if ( value != null && !value.equals("") )
//                            elements.put( value ,value) ;
//
//                    }
//                    else
                    {

                        Map value = null;
                        if(( ( Map ) map ).get( key ) instanceof Map)
                        {
                            value = ( Map ) ( ( Map ) map ).get( key ) ;
                            if ( value != null && value.size() > 0 )
                                elements.put( key , value ) ;
                        }
                    }
				}
			}
			else
				elements = null;
		}
		catch(Exception ex){IsmaPersister.getLogDataAccess().error("",ex);}

	}
  public void loadRedirect(Map redirectM)
  {
	  if(redirectM == null)
	  {
		  this.redirect = null;
		  return;
	  }
	  setRedirect((String)redirectM.get("redirect.link"));

  }
  public String getStartYear()
  {
    return startYear;
  }
  public void setStartYear(String startYear)
  {
    this.startYear = startYear;
  }
  public String getEndYear()
  {
    return endYear;
  }
  public void setEndYear(String endYear)
  {
    this.endYear = endYear;
  }
    public String getRedirect()
    {
	return redirect;
    }

    public String getInternalname()
    {
        return internalname ;
    }

    public void setRedirect(String redirect)
    {
	this.redirect = redirect;
    }

    public void setInternalname( String internalname )
    {
        this.internalname = internalname ;
    }


}
