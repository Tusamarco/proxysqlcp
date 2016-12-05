package net.tc.isma.utils;

import net.tc.isma.*;
import net.tc.isma.data.*;
import net.tc.isma.data.hibernate.*;
import java.util.*;
import org.hibernate.*;
import javax.naming.*;
import net.tc.isma.persister.IsmaPersister;
import java.sql.*;

public abstract class DbCounter {
    public DbCounter() {
    }

//    public static Long getCount(HibernateDatastoreSessionFactory dsf)
//    {
//        long idc = 0;
//        try
//        {
//            Session ds = dsf.openSession();
//            try
//            {
//                String toLoad = "COUNTER";
//                Autocount counter = (Autocount) ds.load(net.tc.isma.utils.Autocount.class, toLoad);
//
//                if (counter != null)
//                {
//                    idc = counter.getValLong();
//                    idc++;
//                    counter.setValLong(idc);
//                    ds.saveOrUpdate(counter);
//                    ds.flush();
//                    ds.connection().commit();
//                }
//                dsf.closeSession(ds);
//
//            }
//            catch (Exception ex)
//            {
//                FaostatPersister.getLogDataAccess().error(ex) ;
//            }
//            finally
//            {
//                try
//                {
//                    if (ds != null)
//                    {
//                        ds.close();
//                    }
//                }
//                catch (Exception ex1)
//                {
//                    FaostatPersister.getLogDataAccess().error(ex1) ;
//                }
//            }
//
//            return new Long(idc);
//        }
//        catch (Exception ex)
//        {
//            FaostatPersister.getLogDataAccess().error(ex) ;
//        }
//        return new Long(0);
//
//    }

    /*    public static Long getCount(String application, String module)
        {
            if(application == null || application.equals("") || module == null || module.equals(""))
                FaostatPersister.getLogDataAccess().error("INVALID COUNTER Parameter in DbCounter.class" ) ;


            long idc = 0;
            try
            {
                HibernateUtil.beginTransaction();
                Session ds = HibernateUtil.getSession();
                try
                {
                    String toLoad = module;
                    Autocountx coTemp = new Autocountx();
                    coTemp.setApplication(application);
//                coTemp.setModule(module);
                    coTemp.setId("COUNTER");

//                List l = ds.find(new QueryByExample(coTemp, new String[] {"application","id"},  null));

     Autocountx counter =(Autocountx) ds.load(Autocountx.class,toLoad);

//                if(l == null || l.size() ==0)
//                    throw new ISMAException("INVALID COUNTER in DbCounter.class" );
                    if(counter == null)
                        FaostatPersister.getLogDataAccess().error("INVALID COUNTER in DbCounter.class" ) ;

//                counter = (Autocountx)l.get(0);

                    if (counter != null)
                    {
//                    counter.setId(toLoad);
                        idc = counter.getValLong();
                        idc++;
                        counter.setValLong(idc);
                        ds.saveOrUpdate(counter);
                        ds.flush();
                        ds.connection().commit();
                    }
                    HibernateUtil.closeSession();
                }
                catch (Exception ex)
                {
                        FaostatPersister.getLogDataAccess().error(ex);
                }
                finally
                {
                    try
                    {
                        if (ds != null)
                        {
                            ds.close();
                        }
                    }
                    catch (Exception ex1)
                    {
                        FaostatPersister.getLogDataAccess().error(ex1);
                    }
                }

                return new Long(idc);
            }
            catch (Exception ex)
            {
                FaostatPersister.getLogDataAccess().error(ex);
            }
            return new Long(0);
        }
     */
    public static Long getCount(String application, String module) {
        Long l = null;
        HSession s = IsmaPersister.getSessionFactory().openSession();
        try {
            s.beginTransaction();
            l = getCount(application, module, s);
        } catch (Exception ex) {
            try {
                s.connection().rollback();
            } catch (Exception ex1) {
                ex1.printStackTrace();
            }
            ex.printStackTrace();
        } finally {
            try {
                s.connection().commit();
                IsmaPersister.getSessionFactory().closeSession(s);

            } catch (Exception ex1) {
                ex1.printStackTrace();
            }

        }
        return l;
    }


    public static Long getCount(String application, String module, Session ds) {
        if (application == null || application.equals("") || module == null ||
            module.equals(""))
            IsmaPersister.getLogDataAccess().error(
                    "INVALID COUNTER Parameter in DbCounter.class");

        long idc = 0;
        try {
//            Session ds = dsf.openSession();
            try {
                String toLoad = module;
                Autocountx coTemp = new Autocountx();
                coTemp.setApplication(application);
//                coTemp.setModule(module);
                coTemp.setId("COUNTER");

//                List l = ds.find(new QueryByExample(coTemp, new String[] {"application","id"},  null));

                Autocountx counter = (Autocountx) ds.load(Autocountx.class,
                        toLoad);

//                if(l == null || l.size() ==0)
//                    throw new ISMAException("INVALID COUNTER in DbCounter.class" );
                if (counter == null)
                    IsmaPersister.getLogDataAccess().error(
                            "INVALID COUNTER in DbCounter.class");

//                counter = (Autocountx)l.get(0);

                if (counter != null) {
//                    counter.setId(toLoad);
                    idc = counter.getValLong();
                    idc++;
                    counter.setValLong(idc);
                    ds.saveOrUpdate(counter);
//                    ds.flush();
//                    ds.connection().commit();
                }

            } catch (Exception ex) {
                IsmaPersister.getLogDataAccess().error(ex);
            }

            return new Long(idc);
        } catch (Exception ex) {
            IsmaPersister.getLogDataAccess().error(ex);
        }
        return new Long(0);
    }

//    public abstract String getApplication();
//    public abstract String getModule();
    public static Long getCount(String application, String module,
                                HSessionFactory dsf) {
        if (application == null || application.equals("") || module == null ||
            module.equals(""))
            IsmaPersister.getLogDataAccess().error(
                    "INVALID COUNTER Parameter in DbCounter.class");

        long idc = 0;
        try {
            HSession dsh = dsf.openSession();
            try {

                dsh.beginTransaction();
                String toLoad = module;
                Autocountx coTemp = new Autocountx();
                coTemp.setApplication(application);
//                coTemp.setModule(module);
                coTemp.setId("COUNTER");

                Autocountx counter = (Autocountx) dsh.load(Autocountx.class,
                        toLoad);

//                if(l == null || l.size() ==0)
//                    throw new ISMAException("INVALID COUNTER in DbCounter.class" );
                if (counter == null)
                    IsmaPersister.getLogDataAccess().error(
                            "INVALID COUNTER in DbCounter.class");

//                counter = (Autocountx)l.get(0);

                if (counter != null) {
//                    counter.setId(toLoad);
                    idc = counter.getValLong();
                    idc++;
                    counter.setValLong(idc);
                    dsh.save(counter);
                }
            } catch (Exception ex) {
                IsmaPersister.getLogDataAccess().error(ex);
            } finally {
                dsh.flush();
                dsh.connection().commit();
                dsf.closeSession(dsh);
            }

            return new Long(idc);
        } catch (Exception ex) {
            IsmaPersister.getLogDataAccess().error(ex);
        }
        return new Long(0);
    }

}
