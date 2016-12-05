package net.tc.mysql.cluster.cp.actions;

import net.tc.isma.actions.generic.actionImpl;
import net.tc.isma.persister.IsmaPersister;
import java.io.*;
import java.util.List;
import java.util.HashMap;
import java.util.Map;
import java.util.Iterator;
import net.tc.isma.utils.Text;
import net.tc.isma.utils.SynchronizedMap;


public abstract class NdbAction extends actionImpl {
    private static String encoding = "UTF-8";


    public NdbAction() {
    }






    public Map getReferenceFromPersister(String name) {
        if (name == null || name.equals(""))
            return null;

        return (Map) IsmaPersister.get(SynchronizedMap.class, name);

    }


}
