package net.tc.isma.actions.generic;

import org.dom4j.Document;
import net.tc.isma.persister.IsmaPersister;
import net.tc.isma.persister.PersistentObject;
import net.tc.isma.resources.Resource;
import net.tc.isma.actions.ActionFactory;
import java.io.File;
import net.tc.isma.actions.ActionChain;
import net.tc.isma.actions.Action;
import org.dom4j.DocumentFactory;
import org.dom4j.Node;
import org.dom4j.DocumentException;
import org.dom4j.io.SAXReader;
import org.dom4j.Element;
import java.util.Map;
import java.util.HashMap;
import java.util.List;
import org.dom4j.Attribute;
import net.tc.isma.views.View;
import net.tc.isma.views.generic.viewImpl;
import java.util.TreeMap;
import net.tc.isma.comparators.ActionOrderComparator;
import java.util.Comparator;
import java.util.Collections;
import java.util.ArrayList;
import net.tc.isma.utils.SynchronizedMap;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;
import net.tc.isma.utils.IniFile;
import java.io.FileReader;

public class actionFactoryImpl
	implements ActionFactory, PersistentObject
{
	private long retrieved = 0;

	private java.util.Map actionChains;
	private PersistentObject po = null;
	private String actionsPath = null;
	private File directory = null;
	private long lastmodify = 0;

	/**
  * This Class is an Implementation of the ActionFactory Interface.
  * It should be instanziate once then stored in a container the persistent object is the wrapped representation of a directory
  * in witch are stored xml file with the Action(s) descriptions
  */
	protected  actionFactoryImpl()
	{
	}

	public static actionFactoryImpl getInstance(PersistentObject poIn)
	{
		return new actionFactoryImpl(poIn);
	}
	public actionFactoryImpl( PersistentObject poIn)
	{
		actionChains = new HashMap();
		this.po = poIn ;

		Object res =  ((Resource)po).getResource();
		if(res instanceof String)
		{	actionsPath = (String)((Resource)po).getResource();
			if( actionsPath != null )
				directory = new File( actionsPath );
		}
		else if(res instanceof File && res != null)
			directory = new File(((File)res).getPath());

		if(directory != null && directory.exists() && directory.isDirectory())
			Load();
	}

/**
 * getActionChains retrieve from the factory the chains with the specific name IF one of the files
 * present in the directory is changed then all the chains are reloded.
 */
	public java.util.Map getActionChains(String[] chainsNames)
	{
		/** FIRST do a check on the FaostatPersister IF they do not exist or Modify THEN create */
		if(this.checkLastModify()|| actionChains.isEmpty())
			Load();

		Map returnMap = new SynchronizedMap();
		for(int i = 0 ; i < chainsNames.length ; i++)
		{
			if(actionChains.containsKey(chainsNames[i]))
				returnMap.put(chainsNames[i], actionChains.get(chainsNames[i]));
		}
		 return returnMap;
	}
	/**
  * the load method well self explanatory...
  */
	private String Load()
	{
		if(directory != null)
		{
			lastmodify = directory.lastModified();
			String[] fileNames = directory.list();
			SAXReader reader = new SAXReader();
			for(int i = 0 ; i < fileNames.length ; i++)
			{
				try
				{
					File nF = new File( directory.getAbsolutePath() +"/"+fileNames[i] ) ;
					if(!nF.exists() || nF.getName().indexOf("CVS") > 0)
						continue;

                                        if (nF.getName().indexOf("CVS") > 0)
                                            continue;

					if(lastmodify < nF.lastModified())
						lastmodify = nF.lastModified();

                                        if(!nF.exists() || nF.getName().indexOf("ini") > 0)
                                        {

                                            IniFile ini = new IniFile();
                                            ini.load(new FileReader(nF), true, "chains");

                                            Integer chainsn = Integer.parseInt(ini.getProperty("main.chains.chains_number"));

                                            for(int il = 1 ; il <= chainsn.intValue() ; il++)
                                            {
                                                ActionChain chain = createChain(ini, il + ".chains.");
                                                if (chain != null && chain.getName() != null && !chain.getName().equals(""))
                                                    actionChains.put(chain.getName(), chain);
                                            }
                                            continue;

                                        }

                                        Document document = reader.read(nF);
//
//                                        OutputFormat format = OutputFormat.createPrettyPrint();
//                                        XMLWriter writer = new XMLWriter(System.out,format);
//                                        writer.write(document);
//                                        writer.close();
                                        try{
                                            List nodes = document.selectNodes("//chains/actionchain");
                                            for (int il = 0; il < nodes.size(); il++) {
                                                ActionChain chain = createChain((Element) nodes.get(il));
                                                if (chain != null && chain.getName() != null &&
                                                    !chain.getName().equals(""))
                                                    actionChains.put(chain.getName(), chain);
                                            }
                                        }
                                        catch(Throwable th)
                                        {
                                            th.printStackTrace();
                                            continue;
                                        }
				}
				catch( Exception ex )
				{
					IsmaPersister.getLogSystem().error( "", ex);
				}
			}
		}
		return null;
	}

        private ActionChain createChain(IniFile ini, String parent)
        {
                ActionChain chain = null;
                if( ini.elements().hasMoreElements())
                {
                        chain = new actionChainImpl();

                        if (ini.getProperty(parent + "name") != null )
                            chain.setName((String) ini.getProperty(parent + "name"));

                        if (ini.getProperty(parent + "security") != null )
                            chain.setSecurity((String) ini.getProperty(parent + "security"));

                        if (ini.getProperty(parent + "execorder") != null)
                            chain.setExecOrder(Integer.parseInt((String) ini.getProperty(parent + "execorder")));

                        if (ini.getProperty(parent + "active") != null )
                            chain.setActive(Boolean.getBoolean((String) ini.getProperty(parent + "security")));

                        if(ini.getProperty(parent + "view") != null )
                        {
                                chain.setView((View) getView(ini, parent));
                        }

                        chain.setActions(createActions(ini, parent));
                        if(chain != null)
                                IsmaPersister.getLogSystem().info("Created " + chain.toString());

                }

                return chain;
        }

        private Object[][] createActions(IniFile ini, String parent)
        {
            TreeMap actionsMap = new TreeMap();
            Object[][] actionsMapA = null;
            try
            {
                if(ini.getProperty(parent +"actionsnumber") == null || ini.getProperty(parent +"actionsnumber").equals(""))
                    return null;

                int ai = Integer.parseInt(ini.getProperty(parent +"actionsnumber"));

                List l = new ArrayList(0);
                actionsMapA = new Object[ai][2];

                for (int i = 1; i <= ai; i++) {
                    Action action = createAction(ini, parent, i);
                    if (!action.isBounded()) {
                        l.add(action);
                    }
                }
                Collections.sort(l, new ActionOrderComparator());
                for (int il = 0; il < l.size(); il++) {
                    Action action = (Action) l.get(il);
                    actionsMapA[il][0] = action.getName();
                    actionsMapA[il][1] = action;
                }

                if (IsmaPersister.getLogSystem().getLevel().equals(IsmaPersister.getLogSystem().getLevel().INFO))
                {
                    if (actionsMapA == null)
                        return actionsMapA;
                    for (int aa = 0; aa < actionsMapA.length; aa++) {
                        IsmaPersister.getLogSystem().info(actionsMapA[aa][0] +
                                "   values=" + (actionsMapA[aa][1]).toString());
                    }
                }
            }catch (Exception ex)
            {
                ex.printStackTrace();
            }

                return actionsMapA;
        }


        private Action createAction( IniFile ini, String parent, int currentaction) throws NumberFormatException
        {
                Action action = new actionImpl();
//                ini.getProperty(parent + "" + currentaction);

                if( ini.getProperty(parent + "name" + currentaction) != null || !ini.getProperty(parent + "name" + currentaction).endsWith(""))
                        action.setName( ( String ) ini.getProperty(parent + "name" + currentaction) );

                if( ini.getProperty(parent + "class" + currentaction) != null || !ini.getProperty(parent + "class" + currentaction).endsWith(""))
                        action.setClassname( ( String ) ini.getProperty(parent + "class" + currentaction) );

                if( ini.getProperty(parent + "pkg" + currentaction) != null || !ini.getProperty(parent + "pkg" + currentaction).endsWith(""))
                        action.setClasspkg( ( String ) ini.getProperty(parent + "pkg" + currentaction) );

                if( ini.getProperty(parent + "execorder" + currentaction) != null || !ini.getProperty(parent + "execorder" + currentaction).endsWith(""))
                        action.setExecorder( Integer.parseInt( ( String ) ini.getProperty(parent + "execorder" + currentaction) ) );

//                if( ini.getProperty(parent + "bounded" + currentaction) != null || !ini.getProperty(parent + "bounded" + currentaction).endsWith(""))
//                        action.setBounded( Boolean.getBoolean( ( String ) ini.getProperty(parent + "bounded" + currentaction)) );
//
//                if(ini.getProperty(parent + "boundedAction" + currentaction) != null && !ini.getProperty(parent + "boundedAction" + currentaction).endsWith(""))
//                {/** @todo Remove completly the bound action is useless */
////                                action.setBoundedAction( createAction( ini.getProperty(parent + "boundedAction" + currentaction) ) );
//                }
                return action;
        }

	private ActionChain createChain(Element root)
	{
		ActionChain chain = null;
		if( root.attributes() != null )
		{
			chain = new actionChainImpl();

			Map attributes = getElementAttribute(root);
			if( attributes.containsKey( "name" ))
				chain.setName((String)attributes.get("name"));

                        if( attributes.containsKey( "security" ))
                            chain.setSecurity((String)attributes.get("security"));

			if( attributes.containsKey( "execorder" ))
				chain.setExecOrder(Integer.parseInt((String)attributes.get("execorder")));

			if( attributes.containsKey( "active" ))
				chain.setActive(Boolean.getBoolean((String)attributes.get("active")));

			if( root.element( "view" ) != null )
			{
				chain.setView((View) getView(root));
			}
			chain.setActions(createActions(root));
			if(chain != null)
				IsmaPersister.getLogSystem().info("Created " + chain.toString());

		}

		return chain;
	}
	private Object[][] createActions(Element chainXml)
	{
		TreeMap actionsMap = new TreeMap();
		Object[][] actionsMapA = null;
		List l = new ArrayList(0);
		if(
			chainXml != null
			&& chainXml.elements("action") != null
			&& chainXml.elements("action").size() > 0
		  )
		  {
			  List actionsList = chainXml.elements("action");
			  if(actionsList != null && actionsList.size() > 0)
				  actionsMapA = new Object[actionsList.size()][2];
//				  actionsMap = new TreeMap();
			  DocumentFactory df = new DocumentFactory();
//			  System.out.println(chainXml.getQualifiedName());
			  Document relativeDoc = df.createDocument((Element)chainXml.detach());

			  for(int il = 0 ; il < actionsList.size() ; il++)
			  {
				  Element actionXml = (Element)actionsList.get(il);
				  Action action = createAction(actionXml, relativeDoc);
//				  System.out.println("aa " + action.toString());
				  if(!action.isBounded())
				  {
					  l.add(action);
//These rows are commented out for sorting purpose
//					  actionsMapA[il][0] = action.getName();
//					  actionsMapA[il][1] = action;
				  }
					  //actionsMap.put(new Integer(action.getExecorder()),action);
			  }
			  Collections.sort(l, new ActionOrderComparator());
			  for(int il = 0 ; il < l.size() ; il++)
			  {
				  Action action = (Action)l.get(il);
				  actionsMapA[il][0] = action.getName();
				  actionsMapA[il][1] = action;
			  }
		  }

		  if(IsmaPersister.getLogSystem().getLevel().equals(IsmaPersister.getLogSystem().getLevel().INFO))
		  {
			  if(actionsMapA == null)
				  return actionsMapA;
			  for( int aa = 0; aa < actionsMapA.length; aa++ )
			  {
				  IsmaPersister.getLogSystem().info( actionsMapA[aa][0] + "   values=" + ( actionsMapA[aa][1] ).toString() );
			  }
		  }
		return actionsMapA;
	}

	private Action createAction( Element actionXmlin, Document relativeDoc) throws NumberFormatException
	{
		Action action = new actionImpl();
		Map actionAtribXml = getElementAttribute( actionXmlin );
		if( actionAtribXml.containsKey( "name" ) )
			action.setName( ( String ) actionAtribXml.get( "name" ) );

		if( actionAtribXml.containsKey( "class" ) )
			action.setClassname( ( String ) actionAtribXml.get( "class" ) );

		if( actionAtribXml.containsKey( "pkg" ) )
			action.setClasspkg( ( String ) actionAtribXml.get( "pkg" ) );

		if( actionAtribXml.containsKey( "execorder" ) )
			action.setExecorder( Integer.parseInt( ( String ) actionAtribXml.get( "execorder" ) ) );

		if( actionAtribXml.containsKey( "bounded" ) )
			action.setBounded( Boolean.getBoolean( ( String ) actionAtribXml.get( "bounded" ) ) );

		if(
			actionAtribXml.containsKey( "boundedAction" )
			&& actionAtribXml.get( "boundedAction" ) != null
			&& !actionAtribXml.get( "boundedAction" ).equals( "" ) )
		{
			Node boundedAXml = null;
			try{boundedAXml = relativeDoc.selectSingleNode( "//action[@name='" + actionAtribXml.get( "boundedAction" ) + "']" );}
			catch(Throwable th){IsmaPersister.getLogSystem().error("",th);}
			if( boundedAXml instanceof Element )
			{
				action.setBoundedAction( createAction( ( Element ) boundedAXml, relativeDoc ) );
			}
		}
		return action;
	}

	private  Map getElementAttribute(Element el)
	{
		Map am = null;
		if( el.attributes() != null )
		{
			am = new HashMap();
			List attributes = el.attributes();
			for( int ia = 0; ia < attributes.size(); ia++ )
			{
				Attribute a = (Attribute)attributes.get(ia);
				am.put(a.getQName().getName(),a.getValue());
			}
		}

		return am;
	}
	private View getView(Element element)
	{
            Element viewXml = element.element("view");
            String type = View.JSP;
            if (viewXml == null)
                return null;

            Map viewAttr = getElementAttribute(viewXml);

            if (viewAttr == null && viewAttr.size() < 1)
                return null;

            View view = null;
            if (viewAttr.containsKey("viewType") &&
                ((String) viewAttr.get("viewType")).equals(View.JSP))
                view = new viewImpl();
            else {
                if (viewAttr.containsKey("class") &&
                    viewAttr.containsKey("packg"))
                {
                    type = (String)viewAttr.get("viewType");

                    String className = viewAttr.get("packg") + "." +
                                       viewAttr.get("class");
                    try {
                        view = (View) Class.forName(className).newInstance();
                    } catch (Exception ex) {
                        IsmaPersister.getLogSystem().error(className, ex);
                    }
                }
            }
            if (view == null)
                return null;

            if (viewAttr.containsKey("name"))
                view.setName((String) viewAttr.get("name"));
            if (viewAttr.containsKey("class"))
                view.setCls((String) viewAttr.get("class"));
            if (viewAttr.containsKey("contentType"))
                view.setContentType((String) viewAttr.get("contentType"));
            if (viewAttr.containsKey("packg"))
                view.setPackg((String) viewAttr.get("packg"));
            if (viewAttr.containsKey("viewType"))
                view.setViewType((String) viewAttr.get("viewType"));

            if (viewAttr.containsKey("query"))
                view.setDefQuery((String) viewAttr.get("query"));

            return view;
	}

        private View getView(IniFile ini, String parent)
        {


            String type = View.JSP;
            View view = null;

            if (ini.getProperty(parent + "view") == null || ini.getProperty(parent + "view").equals(""))
                return view;

            if (ini.getProperty(parent + "viewtype") !=null &&
                ini.getProperty(parent + "viewtype").equals(View.JSP))
                view = new viewImpl();
            else {
                if (ini.getProperty(parent + "viewclass") != null && ini.getProperty(parent + "viewpackg") != null)
                {
                    type = (String)ini.getProperty(parent + "viewtype");

                    String className = ini.getProperty(parent + "viewpackg") + "." +
                                       ini.getProperty(parent + "viewclass");
                    try {
                        view = (View) Class.forName(className).newInstance();
                    } catch (Exception ex) {
                        IsmaPersister.getLogSystem().error(className, ex);
                    }
                }
            }
            if (view == null)
                return null;

            if (ini.getProperty(parent + "viewname") != null)
                view.setName((String) ini.getProperty(parent + "viewname"));
            if (ini.getProperty(parent + "viewclass") != null)
                view.setCls((String) ini.getProperty(parent + "viewclass"));
            if (ini.getProperty(parent + "viewcontenttype") != null)
                view.setContentType((String) ini.getProperty(parent + "viewcontenttype"));
            if (ini.getProperty(parent + "viewpackg") != null)
                view.setPackg((String) ini.getProperty(parent + "viewpackg"));
            if (ini.getProperty(parent + "viewtype") !=null)
                view.setViewType((String) ini.getProperty(parent + "viewtype"));

            if (ini.getProperty(parent + "viewquery") != null)
                view.setDefQuery((String) ini.getProperty(parent + "viewquery"));

            return view;
        }

	public boolean checkLastModify()
	{
		File f = null;
		if( directory instanceof File )
		{
			f = new File( ( ( File ) directory ).getAbsolutePath() );
			if(f.isFile())
			{
				if( f.lastModified() > lastmodify )
				{
					return true;
				}
			}
			else if(f.isDirectory())
			{
				File[] fl = f.listFiles();
				for(int xf = 0 ; xf < fl.length ; xf++)
				{
					if( fl[xf].lastModified() > lastmodify )
					{
						return true;
					}
				}
				return false;
			}
		}
		return false;
	}

	public Object getKey()
	{
		return "";
	}

	public Object getResource()
	{
		return "";
	}

	public boolean isInizialized()
	{
		return false;
	}

	public int objectLifeCycle()
	{
		return 0;
	}

	public void reFresh()
	{
		Load();
	}

	public void setKey( Object key )
	{
	}

	/* (non-Javadoc)
	 * @see net.tc.ismapersister.PersistentObject#getLastModify()
	 */
	public long getLastModify() {
		// TODO Auto-generated method stub
		return this.lastmodify;
	}
	public void increaseRetrieved()
	{
		retrieved++;
	}

	public long getRetrieved()
	{
		return retrieved;
	}

	public void resetRetrieved()
	{
		retrieved = 0;
	}


}
