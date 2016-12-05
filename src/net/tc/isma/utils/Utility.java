package net.tc.isma.utils;

import javax.servlet.http.*;
import java.util.*;
import java.beans.*;

import java.lang.reflect.*;
import org.apache.commons.beanutils.BeanUtils;
import java.sql.*;
import java.io.*;
import java.awt.image.renderable.ParameterBlock;
import java.awt.image.RenderedImage;
import java.awt.RenderingHints;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import java.awt.Frame;
import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.FilterOutputStream;
import java.io.ObjectOutputStream;
//import com.sun.image.codec.jpeg.JPEGImageEncoder;

 public  class  Utility
{
    public Utility()
    {
    }

    public ArrayList getListAllFiles(String path)
    {
        File ff = new File(path);
        File[] ffList = ff.listFiles();
//        FaostatPersister.getLogSystem().info("--------------------------");
        ArrayList filesPath = new ArrayList();
        for (int x = 0; x < ffList.length; x++)
        {
            File f = ffList[x];
            filesPath.add(f.getPath());
            if(f.isDirectory())
            {
                ArrayList arF = getListAllFiles(f.getPath());
                filesPath.addAll(arF);
            }
        }

//        FaostatPersister.getLogSystem().info("--------------------------");

        return filesPath;
    }
    public ArrayList getAllFiles(String path,ArrayList files)
        {
            File ff = new File(path);
            File[] ffList = ff.listFiles();
            for (int x = 0; x < ffList.length; x++)
            {
                File f = ffList[x];
                if(f.isFile() && f.exists() && f.length() > 0)
                    files.add(f.getPath());
                if(f.isDirectory())
                    files = getAllFiles(f.getPath(), files);
            }

            return files;
        }




    public static boolean checkFilePath(String path)
    {
        if(path == null || path.length()<1)
            return false;
        java.io.File file = new java.io.File(path);
        if(file.exists())
            return true;
        file.mkdir();
            return true;

    }

    public static String returnFormatExtention(int i)
    {
        switch (i)
        {
            case 1:
                return "bmp";
            case 2:
                return "jpg";
            case 3:
                return "tif";
        }
        return null;

    }
    public static String getYear()
    {
        GregorianCalendar calendar = new GregorianCalendar();
        String year = new Integer(calendar.get(GregorianCalendar.YEAR)).toString();
        return year;
    }

    public static String getMonth()
    {
        Calendar calendar = new GregorianCalendar();
        int m = calendar.get(GregorianCalendar.MONTH);
        switch (m)
        {
            case 0:return "Jenuary";
            case 1:return "February";
            case 2:return "March";
            case 3:return "April";
            case 4:return "May";
            case 5:return "June";
            case 6:return "July";
            case 7:return "August";
            case 8:return "September";
            case 9:return "October";
            case 10:return "November";
            case 11:return "December";
        }
        return null;
    }

    public boolean deleteFile(String[] filesName, String objectPath)
    {
        for (int i = 0 ; i < filesName.length ; i++ )
        {
            deleteFile(filesName[i],objectPath);
        }
        return true;
    }
    public boolean deleteFile(String fileName, String objectPath)
    {
        java.io.File ptPathTocheck = new java.io.File(objectPath + "/" + fileName);
        if ( (ptPathTocheck.exists()))
        {
            try
            {
                ptPathTocheck.delete();
            }
            catch (Exception e)
            {
                return false;
            }
        }
        return true;
    }
    public boolean createBackupCopy(String fileName, boolean deleteOriginal)
    {
        java.io.File ptPathTocheck = new java.io.File(fileName);
        if ( (ptPathTocheck.exists()))
        {
            try
            {
                copyFile(fileName, ptPathTocheck.getPath(), "bck_" +
                         this.getDayNumber() + "_" +
                         this.getMonthNumber() + "_" +
                         this.getYear() +
                         ptPathTocheck.getName());
                if(deleteOriginal)
                    deleteFile(fileName);
            }
            catch (Exception e)
            {
                return false;
            }
        }
        return true;

    }
    public boolean deleteFile(String fileName)
    {
        java.io.File ptPathTocheck = new java.io.File(fileName);
        if ( (ptPathTocheck.exists()))
        {
            try
            {
                ptPathTocheck.delete();
            }
            catch (Exception e)
            {
                return false;
            }
        }
        return true;
    }

    public static String getMonthNumber()
  {
      Calendar calendar = new GregorianCalendar();
      int m = calendar.get(GregorianCalendar.MONTH);
      String month = new Integer(m + 1).toString();
      if(month.length()<2)
          month = "0"+month;

      return  month;
  }
  public static String getDayNumber()
  {
    Calendar calendar = new GregorianCalendar();
    int d = calendar.get(GregorianCalendar.DAY_OF_MONTH);
    String day = new Integer(d + 1).toString();
    if(day.length()<2)
        day = "0"+day;
    return day;
  }

  public boolean copyFile(String source, String destinationPath, String fileDestName)
  {
      try
      {

          if (checkFilePath(source) && checkPath(destinationPath))
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
      catch (IOException ex)
      {return false;
      }

  }


  public boolean checkPath(String objectPath)
  {
     try{
         java.io.File ptPathTocheck = new java.io.File(objectPath);
         if (! (ptPathTocheck.exists()))
             ptPathTocheck.mkdirs();
         return true;
     }catch(Exception ex){return false;}
  }

  public void sendFile(String filename, HttpServletRequest req, HttpServletResponse res)
      throws ServletException
  {
      try
      {
          byte b[] = new byte[4096];

          File f = new File(filename);
          BufferedInputStream in = new BufferedInputStream(new FileInputStream(filename));
          ServletOutputStream out = res.getOutputStream();

          int size = 0;
          while (true)
          {
              size = in.read(b, 0, 4096);
              if (size == -1)
                  break;
              out.write(b, 0, size);
          }

          out.flush();
          out.close();
          in.close();
      }
      catch (Exception e)
      {
          throw new ServletException(e.getMessage());
      }
  }

  public void sendFileStream(File file, HttpServletRequest req, HttpServletResponse res)
      throws ServletException
  {
      try
      {
          byte b[] = new byte[4096];

          if(file !=null && file.exists())
          {
              ServletOutputStream out = res.getOutputStream();
              BufferedInputStream inF = new BufferedInputStream(new FileInputStream(file),4096);
              BufferedOutputStream outF = new BufferedOutputStream(out,4096);
              int i = 0;
              do
              {
                  i = inF.read(b);
                  if (i > -1)
                      outF.write(b);
              }
              while (i > -1);
              out.flush();
              out.close();
          }

      }
      catch (Exception e)
      {
          e.printStackTrace();
      }
  }

}

