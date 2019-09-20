/*
 * Copyright (C) 2017 Institute of Communication and Computer Systems (imu.iccs.com)
 *
 * This Source Code Form is subject to the terms of the
 * Mozilla Public License, v. 2.0. If a copy of the MPL
 * was not distributed with this file, You can obtain one at
 * http://mozilla.org/MPL/2.0/.
 */

package eu.melodic.vassilis.staff;




import io.github.cloudiator.rest.model.NodeCandidate;
import io.github.cloudiator.rest.model.OperatingSystemFamily;


import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import io.github.cloudiator.rest.model.NodeCandidate;
import java.lang.reflect.Type;
import java.io.*;
import java.util.*;
import lombok.extern.slf4j.Slf4j;
import java.util.HashMap;


import org.apache.log4j.BasicConfigurator;

import com.whalin.MemCached.MemCachedClient;
import com.whalin.MemCached.SockIOPool;

//import org.apache.commons.*; 
import org.apache.commons.math3.stat.regression.OLSMultipleLinearRegression;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import java.lang.String;
import java.lang.Object;
import java.lang.*;


import eu.melodic.vassilis.staff.ConfigurationElement;

//import eu.melodic.cloudiator.client.model.NodeCandidate;
import io.github.cloudiator.rest.model.NodeCandidate;
import io.github.cloudiator.rest.model.OperatingSystemFamily;
//import eu.melodic.vassilis.staff.PenaltyFunction.ConfigurationElement;


//import org.apache.commons.*; 
import org.apache.commons.math3.stat.regression.OLSMultipleLinearRegression;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;


@SpringBootApplication
@Slf4j
public class PenaltyTests {

	public static void main(String[] args) throws Exception {
		Collection<ConfigurationElement> collection_1 = null;
		Collection<ConfigurationElement> collection_2 = null;
		
		double finalll=0;
		   
		
		try (Reader reader = new FileReader( args[0] )) {
			Type listType = new TypeToken<ArrayList<ConfigurationElement>>(){}.getType();
			collection_1 = new Gson().fromJson(reader, listType);
			
			log.info("Collection-1:\n{}", PenaltyFunction.toString(collection_1));
		}
		
		try (Reader reader = new FileReader( args[1] )) {
			Type listType = new TypeToken<ArrayList<ConfigurationElement>>(){}.getType();
			collection_2 = new Gson().fromJson(reader, listType);
			
			log.info("Collection-2:\n{}", PenaltyFunction.toString(collection_2));
		}
		PenaltyFunction penaltyCalculator = new PenaltyFunction();
		finalll = penaltyCalculator.evaluatePenaltyFunction(collection_1, collection_2);
		
	    
	
       //normalized average startup time using max-min normalization
		System.out.println("Normalized Average Time of VM Startup Time and Component Deployment Time :" + finalll);
		
			
	
	
	}
	
}