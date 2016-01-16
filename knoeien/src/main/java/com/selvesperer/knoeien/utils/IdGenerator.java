package com.selvesperer.knoeien.utils;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SessionImplementor;
import org.hibernate.id.IdentifierGenerator;

public class IdGenerator implements IdentifierGenerator { 
	
	  /** 
	   * @see org.hibernate.id.IdentifierGenerator#generate(org.hibernate.engine.SessionImplementor, java.lang.Object) 
	   */ 
	  public Serializable generate(SessionImplementor session, Object obj) throws HibernateException { 
		  String component = "uuid";
		  return doStuff(component);
	 }
	  
	 public static String generate(String code) {
		 if (code == null || code.length() != 4) {
			 code = "cust";
		 }
		 return doStuff(code);
	 }
	 
	 private static String doStuff(String code) {
		 UUID uuid = UUID.randomUUID();
		 String time = new Date().getTime() + "";
		 return time.substring(1, 9) + "-" + time.substring(9,13) + "-" + code + uuid.toString().substring(18);   
	 }
	 
	 /**
	  * Generate a two-part ID where the first part is a date in millis and the second part is a unique id
	  * @return
	  */
	 public static String generateTimeID() {
		 String pt1 = Long.toString(SelvEDate.now().getTimeInMillis());
		 UUID uuid = UUID.randomUUID();
		 String pt2 = uuid.toString().substring(18);
		 return pt1 + pt2;
	 }
} 