package net.tc.isma.data.xml.generic;

import net.tc.isma.actions.generic.actionImpl;
import java.sql.*;
import net.tc.isma.persister.IsmaPersister;
import net.tc.isma.data.db.generic.ResultSetWrap;
import java.io.*;
import java.util.List;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import org.apache.commons.beanutils.BeanUtils;
import java.util.Iterator;
import net.tc.isma.data.generic.*;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.Attribute;
import net.tc.isma.data.generic.baseObject;
import net.tc.isma.utils.Text;
import java.io.StringWriter;
import java.util.Hashtable;
import net.tc.isma.utils.SynchronizedMap;

/**
 * Base class used by any database action in FAOSTAT
 *
 */

public abstract class actionLoadReferenceImplXml extends actionImpl {
    private String encoding = "UTF-8";
//	File f = new File(FaostatPersister.getMAINROOT() +"/test/all" + ".xml");
//	FileWriter fw = null;


    public actionLoadReferenceImplXml() {
    }

    /**
     * get result method by default the record set is FORWARD only
     * CONDURRED READ ONLY and no records number fatch
     */
    public ResultSetWrap getResultSet(String sql) {
        return getResultSet(sql, false, ResultSet.TYPE_FORWARD_ONLY,
                            ResultSet.CONCUR_READ_ONLY);
    }

    /**
     * get result method by default the record set is SCOLLABLE only
     * CONDURRED READ_WRITEand no records number fatch
     */

    public ResultSetWrap getResultSet(String sql, boolean fetchRecordsNumber) {
        return getResultSet(sql, fetchRecordsNumber,
                            ResultSet.TYPE_SCROLL_INSENSITIVE,
                            ResultSet.CONCUR_UPDATABLE);
    }

    /**
     * get result method a ResultSetWrap is returned
     */

    public ResultSetWrap getResultSet(String sql, boolean fetchRecordsNumber,
                                      int ResultSetTYPE, int ResultSetUpdatable) {
        try {
            Connection cpds = IsmaPersister.getConnectionlIn();
            Statement stmnt = cpds.createStatement(ResultSetTYPE,
                    ResultSetUpdatable);
            ResultSet rs = stmnt.executeQuery(sql);

            if (rs != null) {
                return new ResultSetWrap(rs, fetchRecordsNumber);
            }
        } catch (Exception ex) {
            IsmaPersister.getLogSystem().error("", ex);
            return null;
        }

        return null;

    }

    /**
     * Retrive Meta information on the Result Set
     */
    public String getMetaInfo(ResultSet rsIn) {
        if (rsIn == null)
            return null;

        StringWriter sw = new StringWriter();
        try {
            ResultSetMetaData rsm = rsIn.getMetaData();
            PrintWriter pw = new PrintWriter(sw);

            pw.println("Schema: " + rsm.getSchemaName(1) + " Table:" +
                       rsm.getTableName(1));
            int columns = rsm.getColumnCount();
            StringBuffer sb = new StringBuffer();
            for (int i = 1; i < columns; i++) {
                sb.append(rsm.getColumnName(i).toUpperCase() + ",");
            }
            pw.println(sb.toString().substring(0, sb.toString().length() - 1));
        } catch (Exception ex) {
            IsmaPersister.getLogSystem().error("", ex);
            return null;
        }

        return sw.toString();
    }

    /**
     * Return the full record set as a string comma separated
     */
    public String getResultSetAsStringValues(ResultSet rsIn, int size) {
        if (rsIn == null)
            return null;

        StringWriter sw = new StringWriter();
        try {
            PrintWriter pw = new PrintWriter(sw);

            int columns = rsIn.getMetaData().getColumnCount();
            rsIn.first();
            do {
                StringBuffer sb = new StringBuffer();
                for (int i = 1; i < columns; i++) {
                    sb.append(rsIn.getString(i) + ",");
                }
                pw.println(sb.toString().substring(0,
                        sb.toString().length() - 1));
                rsIn.next();
            } while (!rsIn.isLast());
        } catch (Exception ex) {
            IsmaPersister.getLogSystem().error("", ex);
            return null;
        }

        return sw.toString();

    }

    /**
     * Retrive the full set of values as a List of object
     * is possible to pass a BEAN or an Object that implements the Map interface
     */
    public Map getXmlValues(Map docMap, Class cls) {
        Map m = new SynchronizedMap();
        Document document = null;

        if (docMap.get(Document.class) != null) {
            document = (Document) docMap.get(Document.class);
            encoding = (String) docMap.get("encoding");
//			System.out.println(document.asXML());
            try {
//				 fw = new FileWriter(f);

//			 		fw.write(document.asXML());
//		 			fw.close();
//					f = null;
            } catch (Exception eeex) {}

            if (document.getRootElement() instanceof Element) {
                Element root = document.getRootElement();
                String toExtract = cls.getName().replaceAll(".class", "").
                                   replaceAll(cls.getPackage().getName(), "").
                                   toLowerCase();
                if (toExtract.startsWith("."))
                    toExtract = toExtract.substring(1);

                List domainsXml = root.elements(toExtract);
                if (domainsXml != null && domainsXml.size() > 0) {
                    for (int ie = 0; ie < domainsXml.size(); ie++) {
                        try {
                            long timestart = System.currentTimeMillis();

                            baseObject bo = (baseObject) cls.newInstance();
                            Element eToWalk = (Element) domainsXml.get(ie);
                            Map attributes = getElementAttribute(eToWalk);
                            Object id = null;

                            /*Retrieve the IDS from the attribute of the element
                             each object could then specify his own
                             */
                            id = getIdFromAttributes(attributes, id);

//						fw.write("<" + eToWalk.getQualifiedName() + ">");

                            String key = eToWalk.getPath().replaceAll("/", ".").
                                         replaceAll("Text()", "");
                            if (key.startsWith("."))
                                key = key.substring(1);

                            key = loadAttributes((Map) bo, (Node) eToWalk, "", true);

                            bo.put("ENCODING", encoding);
                            bo = (baseObject) treeWalk((Element) domainsXml.get(
                                    ie), (Map) bo, "");
                            if (bo != null)
                                m.put(id, bo);

//						fw.write("</" + eToWalk.getQualifiedName() + ">");
                            long timeend = System.currentTimeMillis();
                            String typeBean = "Bean";

                            if (cls.newInstance() instanceof Map)
                                typeBean = "Map";

                            IsmaPersister.getLogPerformance().info(
                                    "Required time to load " + cls.getName() +
                                    " type = " + typeBean + " time = " +
                                    (timeend - timestart) + " millSeconds");

                        } catch (Exception ex) {}

                    }
//			try{fw.close();
//			f = null;
//		}catch(Exception eewe){}

                }
            }

        }

        return m;

    }

    public abstract Object getIdFromAttributes(Map attributes, Object id);

    /**
     * Use reflection to populate the object if it's a BEAN or just populate it if is an instance of MAP
     */
    private Object setPropertyValue(Object bean, Map rowValues) {
        if (bean == null || rowValues == null)
            return null;

        boolean isMap = false;

        try {
            isMap = ((baseObject) bean).hasMap(bean.getClass());

            Map RetrivableFields = ((baseObject) bean).getRetrivableFieldsMap();
            Iterator it = rowValues.keySet().iterator();
            while (it.hasNext()) {
                Object oKey = it.next();
                if (RetrivableFields.containsKey(oKey)) {
                    Object value = (Object) rowValues.get(oKey);
                    String proname = (String) RetrivableFields.get(oKey);

                    if (value != null && !value.equals("null")) {
                        if (value instanceof String)
                            ((String) value).trim();

                        if (isMap) {
                            ((Map) bean).put(proname, value);
                        } else {
                            BeanUtils.setProperty(bean, proname, value);

                        }
                    } else {
                        if (isMap) {
                            ((Map) bean).put(proname, null);
                        } else {
                            BeanUtils.setProperty(bean, proname, null);
                        }
                    }

                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return bean;
    }

    private Object treeWalk(Element e, Map bo, String rootid) {
        try {
            for (int i = 0; i < e.nodeCount(); i++) {
                Node node = e.node(i);
                if (node instanceof Element) {
                    String key = rootid;
                    key = key + "." + ((Element) node).getQualifiedName();
                    if (key.startsWith("."))
                        key = key.substring(1);

                    Object value = null;

                    if (((Element) node).attributes() != null) {
                        key = loadAttributes(bo, node, key);
                    }

                    if (((Element) node).elements() != null &&
                        ((Element) node).elements().size() > 0) {
                        value = getMultipleValue(((Element) node), key);
                    } else {
                        value = node.getText();

                    }
                    if (!bo.containsKey(key)) {
                        bo.put(key, value);
                    }
                    IsmaPersister.getLogXmlXslTransformation().info(
                            "Loading value: " + key + " = " +
                            Text.getEncodedString(value, encoding) + " XML = " +
                            Text.getEncodedString(node.asXML(), encoding));
                } else {
                    continue;
                }
            }

        } catch (Exception ex) {
            IsmaPersister.getLogDataAccess().error("", ex);
        }
//System.out.println("------ " + bo.toString());
        return bo;
    }

    private Map treeWalkM(Element e, Map boM, String rootid) {
        try {
            for (int i = 0; i < e.nodeCount(); i++) {
                Node node = e.node(i);
                if (node instanceof Element) {
                    String key = rootid;
                    key = key + "." + ((Element) node).getQualifiedName();
                    if (key.startsWith("."))
                        key = key.substring(1);

                    Object value = null;

                    if (((Element) node).attributes() != null) {
                        key = loadAttributes(boM, node, key);
                    }

                    if (((Element) node).elements() != null &&
                        ((Element) node).elements().size() > 0) {
                        value = getMultipleValue(((Element) node), key);
                    } else {
                        value = node.getText();

                    }
                    if (!boM.containsKey(key)) {
                        if (value != null && !value.equals(""))
                            boM.put(key, value);
                    }
                    IsmaPersister.getLogXmlXslTransformation().info(
                            "Loading value: " + key + " = " +
                            Text.getEncodedString(value, encoding) + " XML = " +
                            Text.getEncodedString(node.asXML(), encoding));
                } else {
                    continue;
                }
            }

        } catch (Exception ex) {
            IsmaPersister.getLogDataAccess().error("", ex);
        }
//System.out.println("------ " + bo.toString());
        return boM;
    }


    private String loadAttributes(Map bo, Node node, String keyIn) {
        return loadAttributes(bo, node, keyIn, false);
    }

    private String loadAttributes(Map bo, Node node, String keyIn, boolean root) {

        Map attributes = null;
        String langPostfix = "";
        String IdPostfix = "";
        attributes = getElementAttribute((Element) node);
        String elementName = ((Element) node).getQualifiedName();
        Iterator ita = attributes.keySet().iterator();
        String key = "";

        try {
            if (attributes.containsKey("lang")) {
                langPostfix = (String) attributes.get("lang");
                langPostfix = langPostfix.substring(0, 1).toUpperCase() +
                              langPostfix.substring(1);
            }
            if (attributes.containsKey("id") && !root) {
                IdPostfix = "." + attributes.get("id");
            }
            if (attributes.containsKey("version") && !root) {
                IdPostfix = IdPostfix + ".version" + attributes.get("version");
            }
            if (attributes.containsKey("index") && !root) {
                IdPostfix = IdPostfix + ".index" + attributes.get("index");
            }
            if (attributes.containsKey("order") && !root) {
                IdPostfix = IdPostfix + ".order" + attributes.get("order");
            }
            /** @todo
             *I know is not clean and should not be done in this way but I need a quick fix I will think on how do it better later..
             *  */
            if (attributes.containsKey("ele") &&
                attributes.containsKey("displayele") && !root) {
                IdPostfix = IdPostfix + "ele" + attributes.get("ele") +
                            "_displayele" + attributes.get("displayele");
                String tempKey = key + "." + elementName + "." + IdPostfix;
                SynchronizedMap snM = new SynchronizedMap(2);
                snM.put("ele", new Integer((String) attributes.get("ele")));
                snM.put("displayele",
                        new Integer((String) attributes.get("displayele")));
                if (tempKey.startsWith("."))
                    tempKey = tempKey.substring(1);
                bo.put(tempKey, snM);
            }
            if (attributes.containsKey("ele") &&
                attributes.containsKey("displayele") && root) {
                bo.put("element.ele", (String) attributes.get("ele"));
                bo.put("element.displayele",
                       (String) attributes.get("displayele"));
            }

            while (ita.hasNext()) {
                String keyA = (String) ita.next();
//		String tempKey = key + "." + elementName + "." + keyA;
                String tempKey = key + "." + elementName + "." + keyA +
                                 langPostfix + IdPostfix;
                if (tempKey.startsWith("."))
                    tempKey = tempKey.substring(1);

                if (!keyA.equals("lang")
                    && !keyA.equals("version")
                    && !keyA.equals("index")
                    && !keyA.equals("order")
                    && !keyA.equals("ele")
                    && !keyA.equals("displayele")
                        ) {
                    bo.put(tempKey, (String) attributes.get(keyA));
                    IsmaPersister.getLogXmlXslTransformation().info(
                            "Loading attribute: " + tempKey + " = " +
                            attributes.get(keyA));
                }
            }
        } catch (Exception ex) {
            IsmaPersister.getLogXmlXslTransformation().error("", ex);
        }

        return keyIn + langPostfix + IdPostfix;
    }

    private Map getElementAttribute(Element el) {
        Map am = null;
        try {
            if (el.attributes() != null) {
                am = new HashMap();
                List attributes = el.attributes();
                for (int ia = 0; ia < attributes.size(); ia++) {
                    Attribute a = (Attribute) attributes.get(ia);
                    am.put(a.getQName().getName(),
                           Text.getEncodedString(a.getValue(), encoding));
                }
            }
        } catch (Exception eex) {}
        return am;
    }

    private Map getMultipleValue(Element e, String key) {
        Map multipleValues = new SynchronizedMap(0);
        multipleValues = treeWalkM(e, multipleValues, key);

        return multipleValues;
    }

    public Map getReferenceFromPersister(String name) {
        if (name == null || name.equals(""))
            return null;

        return (Map) IsmaPersister.get(SynchronizedMap.class, name);

    }

}
