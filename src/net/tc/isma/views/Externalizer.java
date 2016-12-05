/*
 * KIDS - Key Indicator Data System
 *
 * Copyright (c) 2004, by AFIST at FAO of UN
 *
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA
 *
 */


package net.tc.isma.views;

//import org.fao.waicent.attributes.*;

//import com.sun.javaws.xml.XMLable;


public abstract class Externalizer //implements XMLable
{
  public abstract Object loadObject()
    throws ExternalizerException;

  public abstract void saveObject(Object o)
    throws ExternalizerException;

  public abstract void deleteObject()
    throws ExternalizerException;

    public abstract Class getObjectClass()
    throws ClassNotFoundException;

  public abstract void appendObject(Object o)
    throws ExternalizerException;

   /*macdc added to handle updates for National and Provincial level 12172003*/
  	public abstract void updateObject(Object o, String country)
           throws ExternalizerException;

  //asf: this should move into the attribute externalizer
  //public abstract void changeLanguageForExtents(Attributes a, String language);

   public class ExternalizerException extends Exception
   {
    public Exception e = null;

    public ExternalizerException() {}
    public ExternalizerException(String message) {super(message);}
    public ExternalizerException(String message, Exception e) {super(message);this.e = e; }
    public ExternalizerException(Exception e) {this.e = e; }

   }
};
