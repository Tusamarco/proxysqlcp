package net.tc.isma.data.objects;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import net.tc.isma.data.generic.dataObject;
import net.tc.isma.data.referencepicker.*;
import java.util.Iterator;
import net.tc.isma.utils.SynchronizedMap;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public class itemBean extends dataObject
	implements Serializable
{

	private String nameEn;
	private String nameFr;
	private String nameEs;
	private String nameAr;
	private String nameZh;
	private String type;
	private java.util.Map groups;
	private String[] ExcludedProperty = new String[]{""};
	private static Map RetrivableFields = null;
	private boolean isGroup;
	private String disseminateind;
	private String grpind;
	private String nameLongEn;
	private String nameLongFr;
	private String nameLongEs;
	private String nameLongAr;
	private String nameLongZh;
    private Object id;
    private String startYear;
    private String endYear;

	public String toString()
	{
		return ToStringBuilder.reflectionToString( this );
	}

	public boolean equals( Object other )
	{
		if( ! ( other instanceof itemBean ) )return false;
		itemBean castOther = ( itemBean ) other;
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

	public void setId(Object id)
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

	public String getType()
	{
		return type;
	}

	public void setType( String type )
	{
		this.type = type;
	}

	public java.util.Map getGroups()
	{
		return groups;
	}

	public void setGroups( java.util.Map groups )
	{
		this.groups = groups;
	}

	public boolean isIsGroup()
	{
		return isGroup;
	}

	public void setIsGroup( boolean isGroup )
	{
		this.isGroup = isGroup;
	}

	public String[] getExcludedProperty()
	{
		return this.ExcludedProperty;
	}

	public List getRetrivableFields()
	{
		if( RetrivableFields == null )
			RetrivableFields = initRetrivableFields();

		return new ArrayList( RetrivableFields.keySet() );
	}
	public Map getRetrivableFieldsMap()
	{
		if( RetrivableFields == null )
			RetrivableFields = initRetrivableFields();

		return RetrivableFields;
	}

	public void init( Map rowValues )
	{

	}

	public Class itemClass()
	{
		return this.getClass();
	}

	private Map initRetrivableFields()
	{
		RetrivableFields = new HashMap();

		RetrivableFields.put( "itemBean", "id" );
		RetrivableFields.put( "DISSEMINATE_IND", "disseminateind" );
		RetrivableFields.put( "ITEM_TYP", "type" );
		RetrivableFields.put( "GRP_IND", "grpind" );
		RetrivableFields.put( "NAME_E", "nameEn" );
		RetrivableFields.put( "NAME_F", "nameFr" );
		RetrivableFields.put( "NAME_S", "nameEs" );
		RetrivableFields.put( "NAME_A", "nameAr" );
		RetrivableFields.put( "NAME_C", "nameZh" );
		RetrivableFields.put( "LONG_NAME_E", "nameLongEn" );
		RetrivableFields.put( "LONG_NAME_F", "nameLongFr" );
		RetrivableFields.put( "LONG_NAME_S", "nameLongEs" );
		RetrivableFields.put( "LONG_NAME_A", "nameLongAr" );
		RetrivableFields.put( "LONG_NAME_C", "nameLongZh" );

		return RetrivableFields;
	}

	public String getDisseminateind()
	{
		return disseminateind;
	}

	public void setDisseminateind( String disseminateind )
	{
		this.disseminateind = disseminateind;
	}

	public String getGrpind()
	{
		return grpind;
	}

	public void setGrpind( String grpind )
	{
		this.grpind = grpind;
		if( grpind != null && grpind.equals( "G" ) )
			this.isGroup = true;
		else
			this.isGroup = false;
	}

	public String getNameLongEn()
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

	public void setNameLongFr( String nameLongFr )
	{
		this.nameLongFr = nameLongFr;
	}

	public String getNameLongEs()
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

	public void setNameLongAr( String nameLongAr )
	{
		this.nameLongAr = nameLongAr;
	}

	public String getNameLongZh()
	{
		return nameLongZh;
	}

	public void setNameLongZh( String nameLongZh )
	{
		this.nameLongZh = nameLongZh;
	}

	public void loadGroups( Object object )
	{
        if(object instanceof Map)
            this.groups = (Map)object;
        else
        {
            Map m = new SynchronizedMap(1);
            m.put(object,object);

        }

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

}
