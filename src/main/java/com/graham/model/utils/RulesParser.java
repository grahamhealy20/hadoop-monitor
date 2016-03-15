package com.graham.model.utils;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.mortbay.log.Log;

import com.graham.model.Rule;

public class RulesParser {

	// Use reflection to parse through an object and compare with rules
	public static void parseRules(Object o, List<Rule> rules) throws IllegalArgumentException, IllegalAccessException {
		Log.info("Parsing METRICS");
		Class c = o.getClass();
		Field[] fields = c.getDeclaredFields();
		//Log.info("FIELDS LENGTH " + fields.length);
		// Loop through fields
		for(Field field : fields) {
			String name = field.getName();
			String type = field.getType().toString();
			//Log.info("METRIC: " + name);
			
			// Check if an array
			if(field.getType().isArray()) {
				//Log.info("Is array");
				// Get the object at Field
				
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
						//Log.info("KEY: " + pair.getKey());
						//Log.info("VAL: " + pair.getValue());
						
						// Check if the key associates with a rule, returns null if not found
						Rule r = extractRule(pair.getKey(), rules);
						
						
						if(r != null) {
							
							// Check operator
							switch (r.getOperator()) {
							case "Equals to":
								Log.info("Rule value: " + r.getValue());
								Log.info("Pair value: " + pair.getValue().toString());
								// Do comparison
								if(pair.getValue().toString().equals(Integer.toString(r.getValue()))) {
									Log.warn("ALERT ALERT ALERT");
								} else {
									Log.info("No match");
								}														
								break;
							case "Greater than":
								break;
							case "Less than":
								break;
							case "Not Equals":
								break;
							default:
								Log.info("No operator");
								break;
							}
						}
					}
															
				}
				
			} else {
				Log.info("Not an array");
			}
		}
	}
	
	// Extract a rule given a metric key
	private static Rule extractRule(String key, List<Rule> rules) {
		for(Rule rule : rules) {
			if(rule.getMetric().equals(key)) {
				Log.info("Success, rule found matching key " + key);
				return rule;
			}
		}
		return null;
	}
	
}
