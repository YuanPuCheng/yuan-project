package com.zihui.cwoa.system.common;


import org.apache.shiro.crypto.hash.SimpleHash;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class Basecommon {

	/**@author yuanpucheng
	 * shiro 使用MD5密码进行1024次加密 盐值默认用户工号
	 * @param password
	 * @param salt
	 * @return
	 */
	public static Object getShiroMd5(String password,String salt){
		String hashAlgorithmName = "MD5";//加密方式
		//int hashIterations = 1024;//加密1024次
		Object result = new SimpleHash(hashAlgorithmName,password,salt);
		return result;
	}


	/**@author yuanpucheng
    * 获取INT类型UUID
    * @return
    */
	public static int getIntUUid(){
		Random random=new Random();
		int ids=random.nextInt(999999999);
		System.out.println(ids);
		return ids;
	}


	//数字验证码
	public static String sendcode(){
		Random random=new Random();
		String str1=random.nextInt(9)+"";
		String str2=random.nextInt(9)+"";
		String str3=random.nextInt(9)+"";
		String str4=random.nextInt(9)+"";
		String str5=random.nextInt(9)+"";
		String str6=random.nextInt(9)+"";
		String code=str1+str2+str3+str4+str5+str6;	
		return code;	
	}
		
	/***
	 * 获取String型UUID
	 * @author yuanpucheng
	 * @return
	 */
	public static String getUUid(){
		UUID uuid = UUID.randomUUID();
		String newuuid=uuid.toString();
		return newuuid;
	  }
	/***
	 * 获取拼接String型ID
	 * @author yuanpucheng
	 * @param front 前缀
	 * @param sign  时间格式
	 * @param forment 后缀
	 * @return
	 */
	public static String getStringmarks(String front,String sign,String forment){
		Date date=new Date();
		SimpleDateFormat time=new SimpleDateFormat(sign); 
		time.format(date);
		String result=front.toString()+time.format(date).toString()+forment.toString();
		return result;
	}

    /**
     * 获取图片验证码
     * @author liaoqiang
     * @return
     */
     public static String getcode(){
     	String code="ABCDEFGHIJKLMNOPQRESUVWXYZ" +
     			    "abcdefghijklmnopqresuvwxyz" +
     			    "1234567890";
         int ia=getnum(62);
 		String a=code.substring(ia-1, ia);
 		int ib=getnum(62);
 		String b=code.substring(ib-1, ib);
 		int ic=getnum(62);
 		String c=code.substring(ic-1, ic);
 		int id=getnum(62);
 		String d=code.substring(id-1, id);
 		int ie=getnum(62);
 		String e=code.substring(ie-1, ie);
 		int ig=getnum(62);
 		String g=code.substring(ig-1, ig);
 		String checkcode=a+b+c+d+e+g;
 		System.out.println(checkcode);
 		return checkcode;
     }
     
     
     /*****
      * @author liaoqiang
      * 图片验证码校验核心
      * @param aArray
      * @param aArray2
      * @param aArray3
      * @param aArray4
      * @return
      */
     public static String checkforcode(String aArray,String aArray2,String aArray3,String aArray4){
     	String code="ABCDEFGHIJKLMNOPQRESUVWXYZ" +
     			    "abcdefghijklmnopqresuvwxyz" +
     			    "1234567890";
         int ia=Integer.parseInt(aArray);
 		String a=code.substring(ia-1, ia);
 		int ib=Integer.parseInt(aArray2);
 		String b=code.substring(ib-1, ib);
 		int ic=Integer.parseInt(aArray3);
 		String c=code.substring(ic-1, ic);
 		int id=Integer.parseInt(aArray4);
 		String d=code.substring(id-1, id);
 		String checkcode=a+b+c+d;
 		return checkcode;
     }
     
 /***
  * 图片验证码验证
  * @param code
  * @param num
  * @return
  */
 	public static int checkcode(String code,String num){
         int checknum=0;
 		String[] aArray = new String[3];
 		aArray=num.split(";");
 		String checkcode=checkforcode(aArray[0],aArray[1],aArray[2],aArray[3]);
 		if(checkcode.equals(code)){
 			checknum=1;
 		}
 		else{
 			checknum=0;
 		}		
 		return checknum;
 	}

 /***
  * 获取int随机数
  * @author yuanpucheng
  * @param perm
  * @return
  */
 	public static int getnum(int perm){
 		Random random=new Random();
 		int ids=random.nextInt(perm);
 		return ids;
 	}	
 	/***
 	 * 判断是否是数字
 	 * @param str
 	 * @return
 	 */
 	public static boolean isNumeric(String str){ 
 	   Pattern pattern = Pattern.compile("[0-9]*"); 
 	   Matcher isNum = pattern.matcher(str);
 	   if( !isNum.matches() ){
 	       return false; 
 	   } 
 	   return true; 
 	}
	
	/**
	 * 判断参数是否为空
	 * @param parm
	 * @return
	 */
    public  boolean checknull(String parm){
    	boolean flag=true;
    	if("null".equals(parm)||parm==null||parm=="undefinied"||parm==""){
    		flag=false;
    	}else{
    		flag=true;
    	}
		return flag;
    }

	/**
	 * 判断参数是否为空
	 * @param parm
	 * @return
	 */
    public static  boolean checkNOnull(String parm){
    	boolean flag=false;
    	if("null".equals(parm)||parm==null||parm=="undefinied"||parm==""){
    		flag=true;
    	}else{
    		flag=false;
    	}
		return flag;
    }
    


	
	
	   /****
	    * int 转 short
	    */
	   public static short IntToShort(int data){
		   short b = (short) data;
		   return b;
	   }
	   
	   /****
	    * string转long
	    * @param data
	    * @return
	    */
	   public static long StringToLong(String data){
		   //   包装类型： Byte，Integer，Short，Long，Boolean，Character，Float，Double等
		   long datas=Long.valueOf(data);
		   // Long.parseLong("String");
		   //   基本数据类型：byte，int， short， long， boolean，char， float，double等
		return datas;
		   
	   }

	

	   /****
	    * string转long
	    * @param data
	    * @return
	    */
	   public static String LongtoString(long data){
		   String datas=String.valueOf(data);
		return datas;

	   }



}
