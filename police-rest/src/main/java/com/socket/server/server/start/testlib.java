package com.socket.server.server.start;

import java.io.File;
import java.io.PrintStream;

public class testlib
{
  public static String fullname = "";
  public static int n = 0;

  public static void fun(File file, String ext)
  {
    File[] f = file.listFiles();
    if (f != null) {
      for (int i = 0; i < f.length; ++i){
    	  if (!f[i].isDirectory()) { // 判断是文件还是文件夹  
    		  fun(f[i], ext);
    	  } 
      }
    	  
    	  
        
    }
    else {
      String filename = file.getName();
      if (filename.length() > ext.length()) {
        filename = filename.substring(filename.length() - ext.length());
        if (filename.equals(ext)) {
          n += 1;

          fullname = fullname + "lib\\" + file.getName() + ";";
          System.out.println(file.getName());
        }
      }
    }
  }

  public static void main(String[] args) {
    File javaFile = new File("E:\\testNettyServer\\NettySmartwjServer\\lib");
    fun(javaFile, "");
    System.out.println(fullname);
    System.out.println("----------共" + n + "个文件");
  }
}