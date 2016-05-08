package com.graham.model.utils;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.mortbay.log.Log;

import com.graham.model.Column;
import com.graham.model.Layout;
import com.graham.model.LayoutLocation;
import com.graham.model.LayoutMetric;
import com.graham.model.Metric;
import com.graham.model.Row;

public class MetricsHelper {
	
	// Use reflection to parse through an object and extract metrics from given rules
	public static List<LayoutMetric> parseMetrics(Object o, Layout layout) throws IllegalAccessException {
		List<LayoutMetric> layoutMetrics = new ArrayList<LayoutMetric>();
		Class<?> c = o.getClass();
		Field[] fields = c.getDeclaredFields();

		// Loop through fields
		for(Field field : fields) {			

			// Check if an array
			if(field.getType().isArray()) {

				// If the object is an array - loop
				Object obj = field.get(o);
				int length = Array.getLength(obj);
				
				for(int i = 0; i < length; i++) {
					// Convert to hashmap
					LinkedHashMap<String, Object> item = (LinkedHashMap<String, Object>) Array.get(obj, i);					

					// Iterate over hashmap
					Iterator<Entry<String, Object>> it = item.entrySet().iterator();
					while(it.hasNext()) {
						Map.Entry<String, Object> pair = (Map.Entry<String, Object>)it.next();

						//Check if layout exists, if not null add to list to be returned
						Metric m = isInLayout(pair.getKey(), layout);
						//Get layout location
						LayoutLocation ll = getLocation(pair.getKey(), layout);
						if(m != null && ll != null) {
							layoutMetrics.add(new LayoutMetric(m, pair.getValue().toString(), ll.getRow(), ll.getCol()));
						}
					}
				}
			}
		}
		return layoutMetrics;
	}

	// Check if a metric in the metrics object is in the cluster layout
	private static Metric isInLayout(String key, Layout layout) {
		for(Row row : layout.getRows()) {
			List<Column> cols = row.getCols();
			
			for(Column col : cols) {
				Metric m = col.getMetric();
				if(m.getKey().equals(key)) {
					return m;
				}
			}			
		}
		return null;
	}
	
	private static LayoutLocation getLocation(String key, Layout layout) {
		
		for(int i = 0; i < layout.getRows().size(); i++) {
			for(int j = 0; j < layout.getRows().get(i).getCols().size(); j++) {
				if(layout.getRows().get(i).getCols().get(j).getMetric().getKey().equals(key)) {
					return new LayoutLocation(i, j);
			}
		}
	}
		return null;
}

	public static String getMetric(Object o, String key) throws IllegalArgumentException, IllegalAccessException {
		String value = "";
		Class<?> c = o.getClass();
		Field[] fields = c.getDeclaredFields();

		// Loop through fields
		for(Field field : fields) {			

			// Check if an array
			if(field.getType().isArray()) {

				// If the object is an array - loop
				Object obj = field.get(o);
				int length = Array.getLength(obj);
				
				for(int i = 0; i < length; i++) {
					// Convert to hashmap
					LinkedHashMap<String, Object> item = (LinkedHashMap<String, Object>) Array.get(obj, i);					

					// Iterate over hashmap
					Iterator<Entry<String, Object>> it = item.entrySet().iterator();
					while(it.hasNext()) {
						Map.Entry<String, Object> pair = (Map.Entry<String, Object>)it.next();
						
						// Check if metric matches key
						if(pair.getKey().equals(key)) {
							value = pair.getValue().toString();
						}
					}
				}
			}
		}
		return value;
	}
}

