package org.nico.ourbatis.wrapper.simple;

import org.nico.ourbatis.utils.StringUtils;
import org.nico.ourbatis.wrapper.Wrapper;

/**
 * Change the hump style to glide bar style
 * 
 * @author nico
 */
public class SlideBarJointWrapper implements Wrapper<String>{

	public static final char UNDERLINE='_';
	
	@Override
	public String wrapping(String value) {
		if (value == null || StringUtils.BLANK.equals(value.trim())){
            return StringUtils.BLANK;
        }
        int len = value.length();
        StringBuilder sb = new StringBuilder(len);
        int index = 0;
        for (int i = 0; i < len; i++) {
            char c = value.charAt(i);
            if (Character.isUpperCase(c)){
            	if(index > 0) {
            		sb.append(UNDERLINE);
            	}
            	index ++;
                sb.append(Character.toLowerCase(c));
            }else{
                sb.append(c);
            }
        }
        return sb.toString();
	}

}
