/* CustomToStringBuilder.java
 *
 * Copyright (C) 2015 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 * 
 */

package utilities.internal;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

public class CustomToStringBuilder extends  ReflectionToStringBuilder {
	
	public CustomToStringBuilder(Object object) {
		super(object);
	}
	
	public CustomToStringBuilder(Object object, ToStringStyle style, StringBuffer buffer, Class<?> reflectUpToClass, boolean outputTransients, boolean outputStatics) {
		super(object, style, buffer, reflectUpToClass, outputTransients, outputStatics);
	}
	 
	public CustomToStringBuilder(Object object, ToStringStyle style) {
		super(object, style);
	}
	
	public CustomToStringBuilder(Object object, ToStringStyle style, StringBuffer buffer) {
		super(object, style, buffer);
	}
	
    public CustomToStringBuilder(Object object, ToStringStyle style, StringBuffer buffer, Class<?> reflectUpToClass, boolean outputTransients) {
    	super(object, style, buffer);
        this.setUpToClass(reflectUpToClass);
        this.setAppendTransients(outputTransients);
    }
    
	private static CustomPrintStyle customQueryStyle = new CustomPrintStyle();
	
	public static String toString(Object obj) {
		String result;
		StringBuffer buffer;
		CustomToStringBuilder stringBuilder;
		
		if (isPrimitive(obj)) {
			buffer = new StringBuffer();
			buffer.append(obj.getClass().getName());
			buffer.append("{");
			customQueryStyle.appendObject(buffer, obj);
			buffer.append("}");
			result = buffer.toString();	        
		} else {			
			stringBuilder = new CustomToStringBuilder(obj, customQueryStyle, null, null, true, true);
			result = stringBuilder.toString();
		}
				
		return result;
	}
		
	@Override
	public String toString() {	
		String result;
		Object obj;
		CustomPrintStyle style;
		StringBuffer buffer;
		Class<?> clazz;
		
		obj = this.getObject();
		
        if (obj == null) {
            result = "<null>"; // super.getStyle().getNullText();
        } else {
        	clazz = obj.getClass();
        	processSuperClazzes(clazz);        	
	    	style = (CustomPrintStyle) this.getStyle();
			buffer = this.getStringBuffer();		
	        style.appendEnd(buffer, obj);
	        result = buffer.toString();
        }
        
        return result;
	}

	private void processSuperClazzes(Class<?> clazz) {
		List<Class<?>> superClazzes;
		
		superClazzes = new ArrayList<Class<?>>();
		while (clazz != null && clazz != this.getUpToClass()) {	            
		    superClazzes.add(clazz);
		    clazz = clazz.getSuperclass();
		}
		
		for (int i = superClazzes.size() - 1; i >= 0; i--) {
			clazz = superClazzes.get(i);
		    this.appendFieldsIn(clazz);			        
		}
	}
	

	private static boolean isPrimitive(Object obj) {
		boolean result;
		
		result = (obj instanceof String || obj instanceof Number || obj instanceof Character || obj instanceof Boolean || 
				obj instanceof java.util.Date || obj instanceof java.sql.Date || obj instanceof Timestamp);
		
		return result;
	}
	
}
