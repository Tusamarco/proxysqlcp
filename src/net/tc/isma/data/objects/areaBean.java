package net.tc.isma.data.objects;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import net.tc.isma.data.common.MultiLanguage;
import java.util.Map;
import java.util.Iterator;
import net.tc.isma.data.referencepicker.*;
import java.util.HashMap;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public class areaBean extends MultiLanguage
	implements Serializable
{

	private String nameEn;
	private String nameFr;
	private String nameEs;
	private String nameAr;
	private String nameZh;
	private String ISO3;
	private String grpInd;
	private String nameLongEn;
	private String nameLongFr;
	private String nameLongEs;
	private String nameLongAr;
	private String nameLongZh;
	private Object id;
	private String endYear;
	private String startYear;
  private java.util.Map subAreas;

	public String toString()
	{
		return ToStringBuilder.reflectionToString( this );
	}

	public boolean equals( Object other )
	{
		if( ! ( other instanceof areaBean ) )return false;
		areaBean castOther = ( areaBean ) other;
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

	public String getStartYear()
	{
		return startYear;
	}

	public void setStartYear( String startYear )
	{
		this.startYear = startYear;
	}

	public String getEndYear()
	{
		return endYear;
	}

	public void setEndYear( String endYear )
	{
		this.endYear = endYear;
	}

	public String getISO3()
	{
		return ISO3;
	}

	public void setISO3( String ISO3 )
	{
		this.ISO3 = ISO3;
	}

	public java.util.Map getSubAreas()
	{
		return subAreas;
	}

	public void setSubAreas(java.util.Map subAreas)
	{
		this.subAreas = subAreas;
	}

	public String getGrpInd()
	{
		return grpInd;
	}

	public void setGrpInd( String grpInd )
	{
		this.grpInd = grpInd;
	}

	public String getNameLongEn()
	{
		return nameLongEn;
	}
        public String getNamelongEn()
        {
                return nameLongEn;
        }

	public void setNameLongEn( String nameLongEn )
	{
		this.nameLongEn = nameLongEn;
	}

	public String getNameLongFr()
	{
		return nameLongFr;
	}
        public String getNamelongFr()
        {
                return nameLongFr;
        }

	public void setNameLongFr( String nameLongFr )
	{
		this.nameLongFr = nameLongFr;
	}

	public String getNameLongEs()
	{
		return nameLongEs;
	}
        public String getNamelongEs()
        {
                return nameLongEs;
        }

	public void setNameLongEs( String nameLongEs )
	{
		this.nameLongEs = nameLongEs;
	}

	public String getNameLongAr()
	{
		return nameLongAr;
	}
        public String getNamelongAr()
        {
                return nameLongAr;
        }

	public void setNameLongAr( String nameLongAr )
	{
		this.nameLongAr = nameLongAr;
	}

	public String getNameLongZh()
	{
		return nameLongZh;
	}
        public String getNamelongZh()
        {
                return nameLongZh;
        }

	public void setNameLongZh( String nameLongZh )
	{
		this.nameLongZh = nameLongZh;
	}

	public void loadSubAreas( Map object )
	{

		this.subAreas = object;
//		subAreas = new HashMap();
//		Iterator it = object.keySet().iterator();
//		while(it.hasNext())
//		{
//			String key = (String)it.next();
//			if(key != null)
//			{
//				Object id = new Long((String)object.get(key).toString());
//				if(areaPicker.getArea(id) != null)
//					subAreas.put(id,areaPicker.getArea(id));
//			}
//		}
//		if(subAreas != null && subAreas.size() < 0)
//			this.setSubAreas(subAreas);
	}

}
