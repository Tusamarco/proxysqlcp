package net.tc.isma.utils.file;

import java.io.*;
import java.util.*;
import net.tc.isma.utils.SynchronizedMap;
import java.lang.ref.SoftReference;

public class FileHandler implements FileDataReader
{
    private File in = null;
    private File out = null;
    private byte b[] = new byte[4096];
    String outPath = null;
    boolean filecreate = false;
    long fileSize = 0;
    private InputStream ins = null;
    private BufferedReader dataReader = null;
    private boolean hasHeader = false;
    List headersL = null;

    public FileHandler()
        throws FileNotFoundException
    {}

    public FileHandler(File in, File out)
        throws FileNotFoundException
    {
        this.in = in;
        this.out = out;
        setSize(in.length());
    }
    public FileHandler(String filePath)
    {
        SoftReference sf = new SoftReference(new File(filePath));
         this.in = (File)sf.get();

        if(this.in == null)
        {
            new FileNotFoundException();
            return;
        }
        this.out = null;
        setSize(in.length());
        if(!in.exists())
        {
            try{
                String separator = "/";
                if(in.getAbsoluteFile().pathSeparator.endsWith(";"))
                    separator = "\\";
                String aT = in.getAbsolutePath().substring(in.getAbsolutePath().
                        lastIndexOf(separator), in.getAbsolutePath().length());
                if (aT.indexOf(".") > 0) {
                    in.mkdirs();
                    if(in.isDirectory())
                        in.delete();
                } else
                    in.mkdirs();
            }catch(Exception ex)
            {
                ex.printStackTrace();
            }
        }
    }

    public FileHandler(File in, File out, String outPath, boolean filecreate)
        throws FileNotFoundException
    {
        in = in;
        out = out;
        outPath = outPath;
        filecreate = filecreate;
    }

    public void setOut(File out)
    {
        this.out = out;
    }

    public void setIn(File in)
    {
        this.in = in;
        this.setSize(in.length());
    }

    public void setIns( InputStream ins )
    {
        this.ins = ins ;
    }

    public static ArrayList getAllFiles(String path,ArrayList files)
    {
        return getAllFiles(path,files, true);
    }
    public static ArrayList getAllFiles(String path, ArrayList files, boolean recursive)
    {
        return     getAllFiles(path, files, recursive , "*");
    }

    public static ArrayList getAllFiles(String path, ArrayList files, boolean recursive , String filter)
    {
        return     getAllFiles(path, files, recursive , "*", true);
    }

    public static ArrayList getAllFiles(String path, ArrayList files, boolean recursive , String filter, boolean casesensitive)
    {

        fileNameFilter fnf = new fileNameFilter(filter, casesensitive);
        File ff = new File(path);
        File[] ffList = ff.listFiles(fnf);
        for (int x = 0; x < ffList.length; x++)
        {
            File f = ffList[x];
            if (f.isFile() && f.exists() && f.length() > 0)
                files.add(f.getPath());
            if (f.isDirectory() && recursive)
                files = getAllFiles(f.getPath(), files, recursive, filter);
        }

        return files;
    }

    public static boolean copyFile(String source, String destinationPath, String fileDestName)
        throws FileNotFoundException, IOException
    {
            if (checkFilePath(source) && checkPath(destinationPath, true) != null)
            {
                java.io.File fileDest = new java.io.File(destinationPath + fileDestName);
                java.io.File file = new java.io.File(source);
                FileOutputStream fw = new FileOutputStream(fileDest);
                FileInputStream fr = new FileInputStream(file);
                int size = fr.available();
                int i = 0;
                byte[] b = new byte[size];
                do{
                    i = fr.read(b);
                    if(i > -1)
                        fw.write(b);
                }while(i > -1);
                fw.flush();
                fw.close();
                fr.close();


            }
            return true;

    }

    public boolean copyFile()
    {
        try{
            if ( in != null && in.exists() && out != null )
            {
                if ( out.exists() )
                    out.delete() ;
                FileOutputStream fw = new FileOutputStream( out ) ;
                FileInputStream fr = new FileInputStream( in ) ;
                int size = fr.available() ;
                int i = 0 ;
                byte[] b = new byte[size] ;
                do
                {
                    i = fr.read( b ) ;
                    if ( i > -1 )
                        fw.write( b ) ;
                }
                while ( i > -1 ) ;
                fw.flush() ;
                fw.close() ;
                fr.close() ;

            }
            return true ;
        }
        catch(FileNotFoundException ex){return false;}
        catch(IOException ex){return false;}

    }
    public String readXmlString()
    {
        if(this.in == null || !this.in.exists() || this.in.isDirectory())
            return null;

        StringWriter sw = new StringWriter();

        if(dataReader == null)
            this.readInitialize(false);

        String line = null;
        do {
            try {

                if ((line = dataReader.readLine()) != null)
                    sw.write(new String(line.getBytes(),"UTF-8"));
                else
                    return sw.toString();

            } catch (IOException ex) {
                ex.printStackTrace();
            }

        } while (line != null);


        return sw.toString();
    }

    public boolean writeToFile(String fr)
    {


        try{
            if ( in != null)// && in.exists())
            {

                FileWriter fw = new FileWriter( in ) ;
                fw.write(fr);

//                BufferedReader inb = new BufferedReader(fr);
//
//                FileWriter fw = new FileWriter( in ) ;
//
//                int size = fr..available() ;
//                int i = 0 ;
//                char[] b = new char[size] ;
//                do
//                {
//                    i = inb.read( b ) ;
//                    if ( i > -1 )
//                        fw.write( b ) ;
//                }
//                while ( i > -1 ) ;
                fw.flush() ;
                fw.close() ;
//                fr.close() ;

            }
            return true ;
        }
        catch(FileNotFoundException ex){return false;}
        catch(IOException ex){return false;}

    }

    public static File checkPath(String objectPath)
    {
        return checkPath(objectPath, false);
    }
    public static boolean checkFilePath(String path)
    {
        if (path == null || path.length() < 1)
            return false;
        java.io.File file = new java.io.File(path);
        return file.exists();
    }

    public static File checkPath(String objectPath, boolean create)
    {
       try{
           java.io.File ptPathTocheck = new java.io.File(objectPath);
           if(ptPathTocheck.exists())
               return ptPathTocheck;

           if (! (ptPathTocheck.exists()) && create)
           {
               ptPathTocheck.mkdirs();
               return ptPathTocheck;
           }
           return null;
       }catch(Exception ex)
       {
           ex.printStackTrace();
           return null;}
    }
    public File getIn()
    {
        return in;
    }
    public File getOut()
    {
        return out;
    }
    public long getSize()
    {
        return fileSize;
    }
    public void setSize(long fileSize)
    {
        this.fileSize = fileSize;
    }

    public boolean deleteFile()
    {
        if(in != null && in.exists())
        {
            try{
                if(!in.delete())
                 {   in.deleteOnExit();
                     return false;}
            }catch(Exception ex)
            {
                try{
                    in.deleteOnExit();
                    return true;
                }
                catch(Exception eex){ return false;}
            }

        }
        return false;
    }
    public boolean copyFileStream()
    {
        try{
            if ( ins != null && out != null )
            {
                if ( out.exists() )
                    out.delete() ;
                FileOutputStream fw = new FileOutputStream( out ) ;

                int size = ins.available() ;
                if(size ==0 )
                    size = 4;
                int i = 0 ;
                byte[] b = new byte[size] ;
                do
                {
                    i = ins.read( b ) ;
                    if ( i > -1 )
                        fw.write( b ) ;
                }
                while ( i > -1 ) ;
                fw.flush() ;
                fw.close() ;
                ins.close() ;

            }
            return true ;
        }
        catch(FileNotFoundException ex){return false;}
        catch(IOException ex){return false;}

    }
    public void close()
    {
        in = null;
        out = null;
        try
        {
            ins.close() ;
        }
        catch ( IOException ex )
        {
        }
        ins = null;
    }
    public void readInitialize(boolean hasHeader)
    {
        try {
            // create the object File
//            dataReader = new RandomAccessFile(this.getIn(),"r");
            FileInputStream fs = new FileInputStream(getIn());
            InputStreamReader ir = new InputStreamReader(fs,"UTF-8");
            dataReader = new BufferedReader(ir);

            if(hasHeader)
            {
                String line = null;
                if((line = dataReader.readLine()) != null)
                {
                    if(line != null)
                    {
                        String[] headers =  new String(line.getBytes(), "UTF-8").split(",");
                        if(headers != null)
                            headersL = Arrays.asList(headers);
                    }

                }


            }

        }catch(Exception ex)
        {
            ex.printStackTrace();
            try {
                dataReader.close();
            } catch (IOException ex1) {
            }

        }


    }
    public Map getRowAsMap()
    {
        String line = null;
        try {

            if((line = dataReader.readLine()) != null)
                return getLineasMap(new String(line.getBytes(), "UTF-8"));
        } catch (IOException ex)
        {
            ex.printStackTrace();
        }


        return null;
    }

    /**
     * getLineasMap
     *
     * @param line String
     * @return Map
     */
    private Map getLineasMap(String line)
    {
        if(line != null)
        {
            Map record = new SynchronizedMap(0);
            String[] values = line.replaceAll(",,",", ,").split(",");

            for(int i = 0 ; i < values.length ; i++)
            {
                if(headersL != null && headersL.size() == values.length )
                    record.put(headersL.get(i),values[i]);
                else
                {
                    if(headersL.get(i) != null)
                        record.put(headersL.get(i), values[i]);
                    else
                        record.put(new Long(i), values[i]);
                }
            }
            return record;

        }

        return null;
    }

    public String getRowAsString()
    {
        String line = null;
        try {

            if((line = dataReader.readLine()) != null)
                return new String(line.getBytes(), "UTF-8");
        } catch (IOException ex)
        {
            ex.printStackTrace();
        }


        return null;
    }
    public Map getAllRowsAsMap()
    {

        Map recordSet = new SynchronizedMap(0);
        Map singleRecord = null;
        int ir = 1;
        do{
            String line = null;
            try {

                if ((line = dataReader.readLine()) != null)
                    singleRecord = getLineasMap(new String(line.getBytes(), "UTF-8"));
                else
                    return recordSet;
            }
            catch (IOException ex)
            {
                ex.printStackTrace();
                singleRecord = null;
            }
            recordSet.put(new Long(ir++),singleRecord);

        }while(singleRecord != null);

        return recordSet;
    }
    public List getHeaders()
    {

        return headersL;
    }
}
