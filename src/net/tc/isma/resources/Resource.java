package net.tc.isma.resources;

import org.dom4j.Element;
import net.tc.isma.persister.*;

public interface Resource
{
  boolean isInizialized = false;
  Long resourceDate = null;
  public static int CONFIGURATION = 1;
  public static int DISPLAYDATA = 2;
  public static int RECORDSDATA = 3;

  public static int PERSISTENT = 50;
  public static int TRANSIENT = 60;
  public static int ETERNAL = 70;

  public static int XML = 100;
  public static int INI = 110;
  public static int RESOURCEBUNDLE = 120;
  public static int RESOURCE = 200;

  public void load();
  public void loadXml();
//  public void reFresh();
//  public boolean checkLastModify(Object obj);
//  public boolean isInizialized();
  public int getResourceType();
  public Object getResource();
//  public int objectLifeCycle();
  public void treeWalk( Element element);
}
