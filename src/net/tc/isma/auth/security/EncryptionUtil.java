package net.tc.isma.auth.security;

public class EncryptionUtil {

/**
 * Method for Encrypting
 **/
   public static String encryptString(String psw)
   {
       byte[] b = psw.getBytes();
       Base64 x = new Base64();
       char[] alpha = x.encode(b);
       //for (int i=0; i<(b.length); i++)
       //   System.out.println(" b[i] :"+b[i]);

       String psw_encrypted = "";
       for (int i=0; i<(alpha.length); i++)
       {
          Character ch = new Character(alpha[i]);
          psw_encrypted = psw_encrypted + ch.toString();
       }
       return psw_encrypted;
    }

/**
 * Method for Decrypting
 **/
   public static String decryptString(String psw_encrypted)
   {
      char[] alpha = new char[psw_encrypted.length()];
      for (int d=0; d<psw_encrypted.length(); d++) {
         alpha[d] = psw_encrypted.charAt(d);
      }
      Base64 x = new Base64();
      byte[] chiaro = x.decode(alpha);

      String psw = new String(chiaro);
      return psw;
   }


}
