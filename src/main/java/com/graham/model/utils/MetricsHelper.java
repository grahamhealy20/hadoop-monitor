package com.graham.model.utils;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.mortbay.log.Log;

import com.graham.model.Alert;
import com.graham.model.Column;
import com.graham.model.Layout;
import com.graham.model.LayoutMetric;
import com.graham.model.Metric;
import com.graham.model.Row;
import com.graham.model.Rule;

public class MetricsHelper {
	// Use reflection to parse through an object and extract metrics from given rules
	public static List<LayoutMetric> parseMetrics(Object o, Layout layout) throws IllegalAccessException {
		List<LayoutMetric> layoutMetrics = new ArrayList<LayoutMetric>();
		Class c = o.getClass();
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
						if(m != null) {
							layoutMetrics.add(new LayoutMetric(m, pair.getValue().toString()));
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
}
