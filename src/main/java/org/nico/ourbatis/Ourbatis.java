package org.nico.ourbatis;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.nico.ourbatis.adapter.AssistAdapter;
import org.nico.ourbatis.adapter.ForeachAdapter;
import org.nico.ourbatis.adapter.RefAdapter;
import org.nico.ourbatis.wrapper.Wrapper;
import org.nico.ourbatis.wrapper.simple.SlideBarJointWrapper;

/**
 * Ourbatis configuration center defines some global parameters
 * 
 * @author nico
 */
public class Ourbatis {
	
	/**
	 * Template matching prefix
	 */
	public static String prefix = "@{";
	
	/**
	 * Template matching suffix
	 */
	public static String suffix = "}";
	
	/**
	 * Template print backup
	 */
	public static String print = null;
	
	/**
	 * Adaptor dictionaries for parsing templates
	 */
	public static final Map<String, AssistAdapter> ASSIST_ADAPTERS = new HashMap<String, AssistAdapter>(){
		private static final long serialVersionUID = 1L;
		{
			put("ourbatis:foreach", new ForeachAdapter());
			put("ourbatis:ref", new RefAdapter());
		}
	};
	
	/**
	 * Database field wrapper collection, Field wrapping for Mapping when entity classes are database information.
	 * For example, a simple implementation: transform the hump naming to the glide bar splicing {@link SlideBarJointWrapper}
	 */
	public static final List<Wrapper<String>> JDBC_NAME_WRAPPERS = new ArrayList<Wrapper<String>>();
	
	/**
	 * Table name wrapper collection, lass names can be wrapped when mapping table names
	 * For example, a simple implementation: transform the hump naming to the glide bar splicing {@link SlideBarJointWrapper}
	 */
	public static final List<Wrapper<String>> TABLE_NAME_WRAPPERS = new ArrayList<Wrapper<String>>();
	
	/**
	 * Mapper file wrapper collection
	 * For example, a simple implementation: transform the hump naming to the glide bar splicing {@link SlideBarJointWrapper}
	 */
	public static final List<Wrapper<String>> MAPPER_NAME_WRAPPERS = new ArrayList<Wrapper<String>>();
	
	/**
	 * The default Java type to database field mapping relationship
	 */
	public static final Map<Class<?>, String> JAVA_TYPE_WRAPPERS = new HashMap<Class<?>, String>(){
		private static final long serialVersionUID = 3388503832916481034L;
		{
			put(String.class, "VARCHAR");
			put(BigDecimal.class, "DECIMAL");
			put(BigInteger.class, "BIGINT");
			put(boolean.class, "BIT");
			put(Boolean.class, "BIT");
			put(byte.class, "TINYINT");
			put(Byte.class, "TINYINT");
			put(short.class, "SMALLINT");
			put(Short.class, "SMALLINT");
			put(int.class, "INTEGER");
			put(Integer.class, "INTEGER");
			put(long.class, "BIGINT");
			put(Long.class, "BIGINT");
			put(float.class, "REAL");
			put(Float.class, "REAL");
			put(double.class, "DOUBLE");
			put(Double.class, "DOUBLE");
			put(Date.class, "DATE");
	}};
	
	
}
