package net.tc.isma.data.objects;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Map;
import java.util.HashMap;
import java.util.Iterator;
import net.tc.isma.data.referencepicker.*;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import net.tc.isma.utils.SynchronizedMap;
import net.tc.isma.data.common.MultiLanguage;
import java.io.Serializable;
import net.tc.isma.comparators.*;

public class subsetBean  extends MultiLanguage
	implements Serializable
{
	private Object id;
	private String startyear;
	private String endyear;
	private String grpind;
	private String nameEn;
	private String nameFr;
	private String nameEs;
	private String nameAr;
	private String nameZh;
	private String[] banner = null;
	private java.util.Map domains;
    private String divisionlink;
    private String divisionlinkText;
	public subsetBean()
	{
	}

	public String toString()
	{
		return ToStringBuilder.reflectionToString( this );
	}

	public boolean equals( Object other )
	{
		if( ! ( other instanceof subsetBean ) )return false;
		subsetBean castOther = ( subsetBean ) other;
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

	public String getStartyear()
	{
		return startyear;
	}

	public void setStartyear( String startyear )
	{
		this.startyear = startyear;
	}

	public String getEndyear()
	{
		return endyear;
	}

	public void setEndyear( String endyear )
	{
		this.endyear = endyear;
	}

	public String getGrpind()
	{
		return grpind;
	}

	public void setGrpind( String grpind )
	{
		this.grpind = grpind;
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

	public String[] getBanner()
	{
		return banner;
	}

	public void setBanner( String[] banner )
	{
		this.banner = banner;
	}

	public String getDivisionlink()
	{
		return divisionlink;
	}

	public void setDivisionlink(String divisionlink)
	{
		this.divisionlink = divisionlink;
	}

	public java.util.Map getDomains()
	{
		return domains;
	}

	public void setDomains( java.util.Map domains )
	{
		this.domains = domains;
	}

	public void loadBanner( Map map )
	{
		if(map == null || map.size() <= 0)
			return ;
		String text = (String)map.get("banner.img");
		String type = (String)map.get("img.type");
		this.banner = new String[]{text, type};
	}

    public String getDivisionlinkText()
    {
		return divisionlinkText;
    }
    public void setDivisionlinkText(String divisionlinkText)
    {
		this.divisionlinkText = divisionlinkText;
    }
	public void loadDomains( Map object )
	{
		if(object == null || object.size() <= 0)
			return;
		if(getDomains() == null)
			domains = new SynchronizedMap();

		Iterator it = object.keySet().iterator();
		while(it.hasNext())
		{
			String key = (String)it.next();
			Map keys = new SynchronizedMap(0);
			if(key.indexOf(".version") > 0 || key.indexOf(".id.") > 0)
			{
				String version = "";
				String id= "";
				String order = "0";

				String[] kar = key.replaceAll("\\.id\\.","\\.id@").split("\\.");
				for(int i = 0 ; i < kar.length ; i++)
				{
					if(kar[i].startsWith("version"))
						version = kar[i].replaceAll("version","");
					if(kar[i].startsWith("order"))
						order = kar[i].replaceAll("order","");
					if(kar[i].startsWith("id@"))
						id = kar[i].replaceAll("id@","");

				}
				keys.put("id",id);
				keys.put("version",version);
				keys.put("order",order);
			}
			domains.put(keys, ( String ) object.get( key ) );
		}
		domains = domain.sort(getDomains());
		setDomains(domains);
	}

}
