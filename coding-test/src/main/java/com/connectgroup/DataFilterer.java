package com.connectgroup;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class DataFilterer {
	public static final String COUNTRY_CODE = "COUNTRY_CODE";
	public static final String RESPONSE_TIME = "RESPONSE_TIME";
	
	public static Collection<?> filterByCountry(Reader source, String country) throws IOException {
    	Collection<List<String>> records = new ArrayList<>();
		int index = -1;
		try (BufferedReader br = new BufferedReader(source)) {
    		String line;
    		while ((line = br.readLine()) != null) {
    			List<String> values = Arrays.asList(line.split(","));
    			if(index == -1) {
    				index = values.indexOf(COUNTRY_CODE);
        		}
    			else if(index >=0 && values.get(index).equalsIgnoreCase(country)){
    				records.add(values);
    			}
    		}
    	}
		return records;
    }

    public static Collection<?> filterByCountryWithResponseTimeAboveLimit(Reader source, String country, long limit) throws IOException {
    	Collection<List<String>> records = new ArrayList<>();
    	int index = -1;
		int resposeTimeIndex = -1;
		try (BufferedReader br = new BufferedReader(source)) {
    		String line;
    		while ((line = br.readLine()) != null) {
    			List<String> values = Arrays.asList(line.split(","));
    			if(index == -1) {
    				index = values.indexOf(COUNTRY_CODE);
    				resposeTimeIndex = values.indexOf(RESPONSE_TIME);
        		}
    			else if(values.get(index).equalsIgnoreCase(country) && Long.valueOf(values.get(resposeTimeIndex)) > limit){
    				records.add(values);
    			}
    		}
		}
		return records;
    }

    public static Collection<?> filterByResponseTimeAboveAverage(Reader source) throws IOException {
    	Collection<List<String>> records = new ArrayList<>();
    	Collection<List<String>> result  = new ArrayList<>();
		int index = -1;
		Long totalResponseTime = 0L;
		try (BufferedReader br = new BufferedReader(source)) {
    		String line;
    		while ((line = br.readLine()) != null) {
    			List<String> values = Arrays.asList(line.split(","));
    			if(index == -1) {
    				index = values.indexOf(RESPONSE_TIME);
        		}
    			else {
    				records.add(values);
    				totalResponseTime =+ Long.valueOf(values.get(index));
    			}
    		}
    		if(index > -1) {
    			final int responseTimeIndex = index;
    			Long average = totalResponseTime/records.size();
    			result = records.stream().filter(record -> (Long.valueOf(record.get(responseTimeIndex)) >= average)).collect(Collectors.toList());
    		}
		}
		return result;
    }
}