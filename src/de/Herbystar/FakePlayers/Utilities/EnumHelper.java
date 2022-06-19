package de.Herbystar.FakePlayers.Utilities;

import java.util.List;

public class EnumHelper {
	
	 public static Object getEnumByString(List<Object> enumList, String target) {
	    	Object o = null;
	    	for(Object e : enumList) {
	    		if(e.toString().equals(target)) {
	    			o = enumList.get(enumList.indexOf(e));
	    		}
	    	}
	    	return o;
	    }

}
