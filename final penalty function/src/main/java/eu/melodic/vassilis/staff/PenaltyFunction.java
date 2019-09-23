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
import java.util.*;
import lombok.extern.slf4j.Slf4j;
import java.io.FileReader;  


import com.whalin.MemCached.MemCachedClient;
import com.whalin.MemCached.SockIOPool;


import org.apache.commons.math3.stat.regression.OLSMultipleLinearRegression;

import org.apache.log4j.BasicConfigurator;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import java.lang.String;
import java.lang.Object;
import java.lang.*;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.*;
import java.util.Scanner;


import java.util.Collections;


import eu.melodic.vassilis.staff.ConfigurationElement;
import eu.melodic.vassilis.staff.PenaltyFunctionProperties;

import org.apache.commons.math3.stat.regression.OLSMultipleLinearRegression;

import org.apache.log4j.BasicConfigurator;
import io.github.cloudiator.rest.model.NodeCandidate;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;


import java.net.InetSocketAddress;

//import net.spy.memcached.BinaryConnectionFactory;
//import net.spy.memcached.MemcachedClient;

import org.influxdb.InfluxDB;
import org.influxdb.InfluxDBFactory;
import org.influxdb.InfluxDBIOException;
import org.influxdb.dto.*;
import org.influxdb.impl.InfluxDBResultMapper;
import org.junit.Test;
import static org.junit.Assert.*;

// for DB queries

import java.util.List;
import java.util.logging.Logger;
import org.influxdb.InfluxDB;
import org.influxdb.InfluxDBFactory;
import org.influxdb.impl.InfluxDBImpl;
import org.influxdb.impl.InfluxDBMapper;
//import InfluxDBMapper;
import org.influxdb.InfluxDBMapperException;

// end of imports for queries to DB 

import org.influxdb.InfluxDB.LogLevel;
import org.influxdb.dto.BatchPoints;
import org.influxdb.dto.Point;
import org.influxdb.dto.Pong;
import org.influxdb.dto.Query;
import org.influxdb.dto.QueryResult;

import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

import java.sql.Connection;

//import org.influxdb.InfluxDBFactory.connect;

import org.influxdb.InfluxDB.LogLevel; 

import java.util.List;
import java.util.concurrent.TimeUnit;

import static junit.framework.TestCase.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;


//import javax.persistence.Column;

import java.time.Instant;
import java.util.concurrent.TimeUnit;
 
import org.influxdb.annotation.Column;
import org.influxdb.annotation.Measurement;

import eu.melodic.vassilis.staff.PenaltyFunctionProperties;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

@Slf4j
@Service 
public class PenaltyFunction {
	
	public double evaluatePenaltyFunction(Collection<ConfigurationElement> actualConfiguration, Collection<ConfigurationElement> newConfiguration) {
		// ........
		log.info("XXXXX properties: {}", properties);
		List<ConfigurationElement> toBeDeleted = new ArrayList<ConfigurationElement>();
		List<ConfigurationElement> toBeAdded = new ArrayList<ConfigurationElement>();
		List<ConfigurationElement> toBeChanged = new ArrayList<ConfigurationElement>();
		double resultss = 0;
		double result=0;
		double resultt=0;
		double finalll=0;
		int    value1=0;
		double value2=0;
		double result2=0;
		int tableStringLength;
		

		
	
		
		//double[][] xx = null;
		//double[][] xx = new double[tableStringLength][tableStringLength];
		
		// Vriskw ayta pou einai sto actual config. alla oxi sto neo config.
		// Ayta tha diagrafoun
		for (ConfigurationElement s : actualConfiguration) {
			//log.debug("LOOP-1: checking CE: {}", toString(s));
			if (! containsEquivalent(newConfiguration, s)) {
				//log.debug("LOOP-1: NO EQUIV FOUND: {}", s.getId());
				toBeDeleted.add(s);
				log.info(">>>>>>>>>: mcc: {}", toBeDeleted);
			}
		}
		//log.debug("To-Be-Deleted:\n{}", PenaltyFunction.toString(toBeDeleted));

		//isEquivalent isEquivalentFunction = new isEquivalent();
		//uncommon = penaltyCalculator.evaluatePenaltyFunction(collection_1, collection_2);
		
		// Vriskw ayta pou einai sto new config. alla oxi sto actual config.
		// Ayta tha prostethoun
		for (ConfigurationElement s : newConfiguration) {
			if (! containsEquivalent(actualConfiguration, s)) {
				toBeAdded.add(s);
				log.info(">>>>>>>>>: mcc: {}", toBeAdded);
				
			}
		}
		//log.debug("To-Be-Added:\n{}", PenaltyFunction.toString(toBeAdded));
		
		// Vriskw ayta pou einai KAI sto new config. KAI sto actual config.
		// Gia ayta tha vrw ti diafora twn cardinalities
		for (ConfigurationElement s1 : newConfiguration) {
			for (ConfigurationElement s2 : actualConfiguration) {
				if (isEquivalent(s1, s2)) {
					int newCardinality = s1.getCardinality() - s2.getCardinality();
					ConfigurationElement s_new = new ConfigurationElement(s1.getId(), s1.getNodeCandidate(), newCardinality);
					if (newCardinality>0) {
						toBeChanged.add(s_new);
					} else {
						toBeDeleted.add(s1);
					}
				}
			}
		}
		//log.debug("To-Be-Changed:\n{}", PenaltyFunction.toString(toBeChanged));
		//log.debug("To-Be-Deleted-UPDATED:\n{}", PenaltyFunction.toString(toBeDeleted));
		
		// Ta apotelesmata pou theloume einai ta 'toBeAdded' kai ta 'toBeChanged'
		List<ConfigurationElement> results = new ArrayList<ConfigurationElement>(toBeChanged);
		results.addAll(toBeAdded);
		
		log.info("----------------------------------------------------------------------");
		log.info("Uncommon elements:\n{}", PenaltyFunction.toString(results));
		log.info("Penalty: ++++++");
		String str1 = "";
		String str2 = "";
	
		
	    	try (InputStream reader = new FileInputStream("src\\main\\resources\\memcached.properties")) {

            Properties p=new Properties();
            p.load(reader);
            System.out.println(p.getProperty("host"));
            System.out.println(p.getProperty("port"));
            str1 = p.getProperty("host");
		    str2 = p.getProperty("port");
		    
			

            } catch (IOException ex) {
            ex.printStackTrace();
           }
	     
		 
		 /*
		 PenaltyFunctionProperties reader = new PenaltyFunctionProperties();
		 Properties p=new Properties();
         p.load(reader);
         System.out.println(p.getProperty("host"));
         System.out.println(p.getProperty("port"));
         str1 = p.getProperty("host");
		 str2 = p.getProperty("port");
		*/
		
			
			
		//initialize the SockIOPool that maintains the Memcached Server Connection Pool
		
		BasicConfigurator.configure();
		String[] servers = {str1+ ":" +str2};
		SockIOPool pool = SockIOPool.getInstance("Test2");
		System.out.println(servers);
		System.out.println(Arrays.toString(servers));
		
		
		
		pool.setServers( servers );
		pool.setFailover( true );
		pool.setInitConn( 11 );
		pool.setMinConn( 5 );
		pool.setMaxConn( 250 );
		pool.setMaintSleep( 30 );
		pool.setNagle( false );
		pool.setSocketTO( 3000 );
		pool.setAliveCheck( true );
		pool.initialize();
		
		//Get the Memcached Client from SockIOPool named Test2
		MemCachedClient mcc = new MemCachedClient("Test2");
			
        // connect to Daniel's InfluxDB and created database with queries
		
		//final InfluxDB influxDB = InfluxDBFactory.connect("http://134.60.152.213:8888", "vasilis", "EiWeif0w");
        //final String dbName = "aTimeSeries";
        //influxDB.createDatabase(dbName);
        //final BatchPoints batchPoints = BatchPoints.database(dbName).tag("async", "true").retentionPolicy("default").build();
        //final Point point1 = Point.measurement("cpu").time(System.currentTimeMillis(), TimeUnit.MILLISECONDS).field("idle", Long.valueOf(90L)).field("system", Long.valueOf(9L)).field("system", Long.valueOf(1L)).build();
        //final Point point2 = Point.measurement("disk").time(System.currentTimeMillis(), TimeUnit.MILLISECONDS).field("used", Long.valueOf(80L)).field("free", Long.valueOf(1L)).build();
        //batchPoints.point(point1);
        //batchPoints.point(point2);
        //influxDB.write(batchPoints);
        //final Query query = new Query("SELECT idle FROM cpu", dbName);
        //final QueryResult queryResult = influxDB.query(query);
		//System.out.println(queryResult);
		//log.info(">>>>>>>>>: queryResult: {}",queryResult);
		
		//log.info(">>>>>>>>>: queryResult: {}",dbName);
        //influxDB.deleteDatabase(dbName);
        /*
		InfluxDB influxDB = InfluxDBFactory.connect("http://134.60.152.213:8888", "vasilis", "EiWeif0w");
        String dbName = "aTimeSeries";
        influxDB.query(new Query("CREATE DATABASE " + dbName));
        String rpName = "aRetentionPolicy";
        influxDB.query(new Query("CREATE RETENTION POLICY " + rpName + " ON " + dbName + " DURATION 30h REPLICATION 2 DEFAULT"));

        BatchPoints batchPoints = BatchPoints
                .database(dbName)
                .tag("async", "true")
                .retentionPolicy(rpName)
                .consistency(ConsistencyLevel.ALL)
                .build();
        Point point1 = Point.measurement("cpu")
                    .time(System.currentTimeMillis(), TimeUnit.MILLISECONDS)
                    .addField("idle", 90L)
                    .addField("user", 9L)
                    .addField("system", 1L)
                    .build();
        Point point2 = Point.measurement("disk")
                    .time(System.currentTimeMillis(), TimeUnit.MILLISECONDS)
                    .addField("used", 80L)
                    .addField("free", 1L)
                    .build();
        batchPoints.point(point1);
        batchPoints.point(point2);
        influxDB.write(batchPoints);
        Query query = new Query("SELECT idle FROM cpu", dbName);
        influxDB.query(query);
	    log.info(">>>>>>>>>: queryResult: {}",queryResult);
		
		log.info(">>>>>>>>>: queryResult: {}",dbName);
        influxDB.query(new Query("DROP RETENTION POLICY " + rpName + " ON " + dbName));
        influxDB.query(new Query("DROP DATABASE " + dbName));
		*/
		
		/*
		
		Configuration configuration = new Configuration("134.60.152.213", "8888", "vasilis", "EiWeif0w", "mydb");
        Query query = new Query();
		query.setMeasurement("sampleMeasurement1");
		List list = new ArrayList();
        list.add("sampleMeasurement1");
        list.add("sampleMeasurement2");
        query.setMeasurements(list);
		
		query.addField("field1");
        query.addField("field2");
		
		query.setDuration("1h");
		
		query.setRange(new Date(2012, 12, 31), new Date());
		
		query.setAggregateFunction(AggregateFunction.MEAN);
		
		query.setGroupByTime("1m");
		
		query.setLimit(1000);
		
		query.fillNullValues("0");
		
		DataReader dataReader = new DataReader(query, configuration);
        ResultSet resultSet = dataReader.getResult();
        System.out.println(resultSet);
		 
		*/
		/*
		String dbName = "test";
		InfluxDB influxDB = InfluxDBFactory.connect("http://localhost:8086","root", "root");
        influxDB.setDatabase(dbName);
        influxDB.setRetentionPolicy("autogen");
		
		
		BatchPoints batchPoints = BatchPoints.database(dbName).tag("async", "true").build();
        Point point1 = Point
            .measurement("cpu")
            .tag("atag", "test")
            .addField("idle", 90L)
            .addField("usertime", 9L)
            .addField("system", 1L)
            .build();
        batchPoints.point(point1);
        influxDB.write(batchPoints);
        Query query = new Query("SELECT * FROM cpu ", dbName);
        QueryResult resultT = influxDB.query(query);
		*/
		
		
		/*
		InfluxDB influxDB = InfluxDBFactory.connect("http://172.17.0.2:8086", "root", "root");
        String dbName = "aTimeSeries";
        influxDB.createDatabase(dbName);

        BatchPoints batchPoints = BatchPoints
                .database(dbName)
                .tag("async", "true")
                .retentionPolicy("autogen")
                .consistency(ConsistencyLevel.ALL)
                .build();
        Point point1 = Point.measurement("cpu")
                    .time(System.currentTimeMillis(), TimeUnit.MILLISECONDS)
                    .addField("idle", 90L)
                    .addField("user", 9L)
                    .addField("system", 1L)
                    .build();
        Point point2 = Point.measurement("disk")
                    .time(System.currentTimeMillis(), TimeUnit.MILLISECONDS)
                    .addField("used", 80L)
                    .addField("free", 1L)
                    .build();
        batchPoints.point(point1);
        batchPoints.point(point2);
        influxDB.write(batchPoints);
        Query query = new Query("SELECT idle FROM cpu", dbName);
        influxDB.query(query);
        influxDB.deleteDatabase(dbName);
	    */
		/*
		try (InfluxDB influxDB = InfluxDBFactory.connect("http://127.0.0.1:8086", "root", "root")) {
        // Read or Write, do any thing you want
		String dbName = "aTimeSeries";
        influxDB.query(new Query("CREATE DATABASE " + dbName));
        String rpName = "aRetentionPolicy";
        influxDB.query(new Query("CREATE RETENTION POLICY " + rpName + " ON " + dbName + " DURATION 30h REPLICATION 2 DEFAULT"));

        BatchPoints batchPoints = BatchPoints
                .database(dbName)
                .tag("async", "true")
                .retentionPolicy(rpName)
                .consistency(ConsistencyLevel.ALL)
                .build();
        Point point1 = Point.measurement("cpu")
                    .time(System.currentTimeMillis(), TimeUnit.MILLISECONDS)
                    .addField("idle", 90L)
                    .addField("user", 9L)
                    .addField("system", 1L)
                    .build();
        Point point2 = Point.measurement("disk")
                    .time(System.currentTimeMillis(), TimeUnit.MILLISECONDS)
                    .addField("used", 80L)
                    .addField("free", 1L)
                    .build();
        batchPoints.point(point1);
        batchPoints.point(point2);
        influxDB.write(batchPoints);
        Query query = new Query("SELECT idle FROM cpu", dbName);
        influxDB.query(query);
        influxDB.query(new Query("DROP RETENTION POLICY " + rpName + " ON " + dbName));
        influxDB.query(new Query("DROP DATABASE " + dbName));
		
		
        }
		*/
		
        
		String dbName = "centos_test_db";

        InfluxDB influxDB = InfluxDBFactory.connect("http://127.0.0.1:8086", "root", "root");

        // Flush every 2000 Points, at least every 100ms
        influxDB.enableBatch(2000, 100, TimeUnit.MILLISECONDS);

        for (int i = 0; i < 1; i++) {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Point point2 = Point.measurement("ComponentTime")
                .time(System.currentTimeMillis(), TimeUnit.MILLISECONDS)
                .addField("timeDepl", Math.random() * 400L)
                .addField("ComponentName", "AppResponse")
                .build();
        influxDB.write(dbName, "autogen", point2);
        }
       
        System.out.println();
		
		Query query = new Query("SELECT * FROM ComponentTime", dbName);
        influxDB.query(query);
		System.out.println(influxDB.query(query));
		
		//InfluxDB connection = connectDatabase();
		
		// querying from centos_test_db DB 
		
		InfluxDBMapper influxDBMapper = new InfluxDBMapper(influxDB);
        //Query query1 = select("timeDepl").from(dbName,"ComponentTime");
		Query query1 = new Query("SELECT timeDepl FROM ComponentTime", dbName);
		//Logger.info("Executing query "+query1.getCommand());
        List<ComponMeasurement> ComponMeasurements = influxDBMapper.query(query1, ComponMeasurement.class);
		ComponMeasurements.forEach(System.out::println);
		
		
		
		String arr = ComponMeasurements.toString();
		System.out.println(arr);
		//Find the Average Component Deployment Time ==>  avg 
		// cnt are the number of Components Deployed along with their times
		double sum=0;
		int cnt =0;
		double avg=0;
		
		for (ComponMeasurement cm : ComponMeasurements) {
			
			sum += cm.timeDepl();
			cnt++; 
		}
		
		if (cnt>0) {avg=sum/cnt;}
		
		else
			throw new RuntimeException("some error message") ;
		System.out.println(cnt);
		System.out.println(avg);
		
		
		
		//Find the maximum Component Deployment time
		double maxx = 0;
        
		for (ComponMeasurement cmm : ComponMeasurements) {
			
			if(maxx<cmm.timeDepl()){
                  maxx=cmm.timeDepl(); //swapping
                  
                }
			 
		}
		
		
            System.out.println("The max Component Deployment Time value is "+ maxx);
		
		/*
		 * To convert String object to String array, first thing 
		 * we need to consider is to how we want to create array. 
		 * 
		 * In this example, array will be created by words contained
		 * in the original String object. 
		 * 
		 * To convert String to String array, use 
		 * String[] split(String delimiter) method of Java String 
		 * class as given below.
		 */
		/*
		String strArray[] = arr.split(" ");
		
		System.out.println("String converted to String array");
		
		//print elements of String array
		for(int i=0; i < strArray.length; i++){
			System.out.println(strArray[i]);
		}
		double[] cc = Arrays.stream(strArray).mapToDouble(Double::parseDouble).toArray();
		
		//Find the maximum Comp Depl time
		double maxx = cc[0];
        for (int i = 1; i < cc.length; i++){
             if(maxx<cc[i]){
                  maxx=cc[i]; //swapping
                  cc[i]=cc[0];
                }
            }
            System.out.println("The max Depl Time value is "+ maxx);
			
			
		*/
		/*
		for(int i=0; i<ComponMeasurements.size(); i++){
            String[] stringArray = ComponMeasurements.get(i);

           for(String s : stringArray) {
                 System.out.println(s);
               }

   
        }
		*/
		
		/*
		double[] arrDouble = new double[arrString.Length];
        for(int i=0; i<arrString.Length; i++)
        {
          arrDouble[i] = double.Parse(arrString[i]);
        }
		*/
		
		//String [] strArr;
		//for(int i = 0 ; i < ComponMeasurements.length ; i ++){
        //    strArr[i] = (String) ComponMeasurements[i];
        //}
		
		//String[] arr = (String)ComponMeasurements.toArray(new String[] {});
		
		//String[] ComponMeasString = ComponMeasurements.stream().toArray(String[]::new);
		
		
		//double[] cc = Arrays.stream(arr).mapToDouble(Double::parseDouble).toArray();
		
		//System.out.println(Arrays.toString(ComponMeasurements.toArray()));
		//System.out.println(String.valueOf(ComponMeasurements));//toString() is easy and good enough for debugging.
		//for (int i = 0; i < cc.size(); i++) {
		//	System.out.println(cc.get(i));
		//}
		
		
		
		/*
		QueryResult queryResult = connection.performQuery("Select * from ComponentTime", dbName);
		
		InfluxDBResultMapper resultMapper = new InfluxDBResultMapper();
		
		List<ComponentTimePoint> ComponentTimePointList = resultMapper.toPOJO(queryResult, ComponentTimePoint.class);
		
		assertEquals(1, ComponentTimePointList.size());
		*/
		
		
		influxDB.close();
		
        		
		/*
		String queryString = new StringBuilder()
            .append("SELECT timeDepl FROM ComponentTime")
            .append(dbName)
            .toString();

        Query query1 = new Query(queryString, dbName);
        List<QueryResult.Result> results2 = influxDB.query(query1).getResults();
		
		System.out.println(results2);
		
		*/
		
		
		//Query query1 = new Query("SELECT timeDepl FROM ComponentTime", dbName);
        //LOG.debug(query1);
        //System.out.println(influxDB.query(query1));
		
		
		
        //List<Deployment> queryResult = store.query(dbName, query1, TimeUnit.MILLISECONDS);
        //System.out.println(queryResult);
		
		
		
		//LOG.debug("{} series read", queryResult.size());
        //if (queryResult.isEmpty()) {
        //   return null;
        //}

        //LOG.debug("{} rows read in first list", queryResult.get(0).getRows().size());
	
		HashMap<String,String> hm = new HashMap<String, String>();
		
		
		// load first properties file
		try (InputStream input = new FileInputStream("src\\main\\resources\\config.properties")) {

            Properties prop = new Properties();

            // load a properties file
            prop.load(input);
			// print key and values
            prop.forEach((key, value) -> System.out.println("VMtype : " + key + ", Boot time Value : " + value));
			// for each key - value pair set to the memcached
			prop.forEach((key, value) -> mcc.set(String.valueOf(key),String.valueOf(value))); 
            //prop.forEach(key -> add.keys(String.valueOf(key))); 
			
			
            //prop.forEach((key) -> keys.add(String.valueOf(key)));			
			//prop.forEach((entry) -> keys.add(String.valueOf(entry.getKey())));
			// Get all key
            //prop.keySet().forEach(x -> keys.add(String.valueOf(x)));
            prop.keySet().forEach(x -> System.out.println(x));
            Set<Object> objects = prop.keySet();
			//---new-----
			prop.forEach((key, value) -> hm.put((String) key,(String) value));
			log.info(">>>>>>>>>: mcc: {}",hm);
		    
			

        } catch (IOException ex) {
            ex.printStackTrace();
        }
		
		//mcc.set("t1.micro", "50");
		//mcc.set("t1.small", "100");
		//mcc.set("t1.large", "110");
		//mcc.set("t1.xlarge", "120");
		//mcc.set("t1.medium", "130");
		//mcc.set("t1.xlarge", "130");
		//mcc.set("m1.tiny",   "55");
		//mcc.set("m1.small",  "79");
		//mcc.set("m1.medium", "88");
		//mcc.set("m1.large",  "132");
		//mcc.set("m1.xlarge", "140");
		
		
		
		//String [] keys = {"t1.micro","t1.small","t1.large","t1.xlarge","t1.medium","t1.xxlarge","m1.tiny","m1.small","m1.medium","m1.large","m1.xlarge"};
		//HashMap<String,Object> hm = (HashMap<String, Object>) mcc.getMulti(keys);
		//log.info(">>>>>>>>>: mcc: {}",hm);

         // get the values of the HashMap hm returned as an Array
		String[] yy = hm.values().toArray(new String[0]);
		
		System.out.println(Arrays.toString(yy));
		
		//Instantiate data for train of OLSMulitple regression algorithm
		//convert String Array to double Array
		double[] y = Arrays.stream(yy).mapToDouble(Double::parseDouble).toArray();
		
		//Find the maximum VM Startup time
		double max = y[0];
        for (int i = 1; i < y.length; i++){
             if(max<y[i]){
                  max=y[i]; //swapping
                  y[i]=y[0];
                }
            }
            System.out.println("The max VM Startup value is "+ max);
		

        //Find the mimimum VM Startup time		
		double min = y[0];
        for (int i = 1; i < y.length; i++){
             if(min>y[i]){
                  min=y[i]; //swapping
                  y[i]=y[0];
                }
            }
            System.out.println("The min VM Startup value is "+ min);
		
		
		
		
		
		
         log.info(">>>>>>>>>: y: {}",y);
		//System.out.println(Arrays.toString(values)); 
		
		log.info(">>>>>>>>>: y.length: ",y.length);
		System.out.println(y.length); 
		
		tableStringLength=y.length;
		   
		 log.info(">>>>>>>>>: tableStringLength: ",tableStringLength);
		 
		 System.out.println(y.length); 
           //double [][]tableDouble= null;
		   
		   //instantiate the double array
           double[][] xx = new double[tableStringLength][3];
		   
		   System.out.println(xx.length);
		  
		    OLSMultipleLinearRegression regression = new OLSMultipleLinearRegression();
	        //Instantiate data for train of OLSMulitple regression algorithm
	        //double[] y = new double[] {50, 100, 110, 120, 130, 130, 55, 79, 88, 132,140};
		 
		    //double[][] x = new double[tableStringLength][tableStringLength];
			// load second properties file
		    try (InputStream input1 = new FileInputStream("src\\main\\resources\\config1.properties")){

            Properties prop1 = new Properties();

            // load a properties file
            prop1.load(input1);
			
			//get array split up by the semicolon
            String[] a = prop1.getProperty("stateInfo").split(";");
			
			//get two dimensional array from the properties file that has been delineated
            //double[][] x = fetchArrayFromPropFile("stateInfo",prop1);
			
			//create the two dimensional array with correct size
            String[][] array = new String[a.length][a.length];

	        //combine the arrays split by semicolin and comma 
            for(int i = 0;i < a.length;i++) {
                array[i] = a[i].split(",");
            }
			
			//Convert two dimensions String Array to two dimensions Double Array
			
			System.out.println(a.length);
			System.out.println(xx.length);
			System.out.println("array: "+java.util.Arrays.deepToString(array));
			System.out.println("xx: "+java.util.Arrays.deepToString(xx));
			for(int k=0; k<tableStringLength; k++) {
                 for(int j=0; j<3; j++) {
                     //tableDouble[k][j]= Double.parseDouble(tableString[k][j]);
					 //xx[k][j]= Double.valueOf(array[k][j]).doubleValue();
                       xx [k][j]= Double.parseDouble(array[k][j]);
					   
					   }
                    }
			
			System.out.println("array: "+java.util.Arrays.deepToString(array));
	        System.out.println("xx_after_fill: "+java.util.Arrays.deepToString(xx));
		   
		    //x[][]= Double.valueOf(array[][]).doubleValue();
			
			//double[][] x = Arrays.stream(array).mapToDouble(Double::parseDouble).toArray();
			//double [][] x = array ;
			}
			
			catch (IOException ex) {
            ex.printStackTrace();
        }
	

       log.info(">>>>>>>>>: xx: {}",xx);
	   //whenCorrectInfoDatabaseConnects();
	   
           
			
	       // double[][] x = new double[11][];
	       // x[0]  = new double[] {1, 0.6, 0};
	       // x[1]  = new double[] {1, 1.7, 160};
	       // x[2]  = new double[] {4, 7.5, 850};
	       // x[3]  = new double[] {8, 15, 1690};
	       // x[4]  = new double[] {7, 17.1, 420};
	       // x[5]  = new double[] {5, 2, 350};
		   // x[6]  = new double[] {1, 0.5, 0};
	       // x[7]  = new double[] {1, 2.048, 10};
	       // x[8]  = new double[] {2, 4.096, 10};
	       // x[9]  = new double[] {4, 8.192, 20};
	       // x[10] = new double[] {8,16.384, 40};
	       
		   
	       

	        regression.newSampleData(y, xx);
	        regression.setNoIntercept(true);
	     // Get the regression parameters and residuals
	        double[] betaHat = regression.estimateRegressionParameters();
	        double[] residuals = regression.estimateResiduals();
	        double rSquared = regression.calculateRSquared();
	     //print them
	        
	        System.out.println("Regression parameters: ");
	        for (int i = 0; i < betaHat.length; i++) {
	            System.out.println(betaHat[i]);
	        }

	        System.out.println("Residual parameter:");
	        for (int i = 0; i < residuals.length; i++) {
	            System.out.println(residuals[i]);
	        }
	        
	        //System.out.println("residual: " + residuals);
	        System.out.println("rSquared: " + rSquared);
			
			
			//Double[] decMax = {-2.8, -8.8, 2.3, 7.9, 4.1, -1.4, 11.3, 10.4, 8.9, 8.1, 5.8, 5.9, 7.8, 4.9, 5.7, -0.9, -0.4, 7.3, 8.3, 6.5, 9.2, 3.5, 3.0, 1.1, 6.5, 5.1, -1.2, -5.1, 2.0, 5.2, 2.1};
            //List<double> a = new ArrayList<double>(Arrays.asList(yyy));
            //System.out.println("The highest VM Startup time is: " + Collections.max(a));
	        //System.out.println("rSquared: " + rSquared);
		
	for(String key : hm.keySet()){
			int value =0;
			
			
			// value=((Integer) hm.get(key)).intValue();//here is an ERROR 
			value=Integer.parseInt((String) hm.get(key));
			for (ConfigurationElement s33 : results) {
				log.info("KEY: {},  s33: {}", key, s33.getNodeCandidate().getHardware().getName());
				
				if (key.equals(s33.getNodeCandidate().getHardware().getName())){
			
			//value = Integer.valueOf((String) hm.get(key));
			result += value; 
			log.info("RESULT:{}", result);
			value1 = value1 + 1;
			System.out.println("KEY:"+key+" VALUE:"+hm.get(key));
			}
			
			if (!(hm.containsKey(s33.getNodeCandidate().getHardware().getName()))){
				
			value2=betaHat[0]+betaHat[1] * (s33.getNodeCandidate().getHardware().getCores())+betaHat[2] * (s33.getNodeCandidate().getHardware().getRam())+betaHat[3] * (s33.getNodeCandidate().getHardware().getDisk());
            System.out.println("value custom:"+value2);
            result2 += value2;			
			value1 = value1 + 1;
			}
				
			
		//	if (!(s33.getNodeCandidate().getHardware().getName()).equals("t1.micro") && !(s33.getNodeCandidate().getHardware().getName()).equals("t1.small") && !(s33.getNodeCandidate().getHardware().getName()).equals("t1.large") && !(s33.getNodeCandidate().getHardware().getName()).equals("t1.xlarge") && !(s33.getNodeCandidate().getHardware().getName()).equals("t1.medium") && !(s33.getNodeCandidate().getHardware().getName()).equals("t1.xxlarge") && 
		//	!(s33.getNodeCandidate().getHardware().getName()).equals("m1.tiny") && !(s33.getNodeCandidate().getHardware().getName()).equals("m1.small") && !(s33.getNodeCandidate().getHardware().getName()).equals("m1.medium") && !(s33.getNodeCandidate().getHardware().getName()).equals("m1.large") && !(s33.getNodeCandidate().getHardware().getName()).equals("m1.xlarge")) {
		//	value2=betaHat[0]+betaHat[1] * (s33.getNodeCandidate().getHardware().getCores())+betaHat[2] * (s33.getNodeCandidate().getHardware().getRam())+betaHat[3] * (s33.getNodeCandidate().getHardware().getDisk());
        //    System.out.println("value custom:"+value2);
        //    result2 += value2;			
		//	value1 = value1 + 1;
		//	
		//	
		//	}
			
			
		}
			
			
			
	}
	   
	   
	   //resultss= (((((result+result2)/value1)+ avg)-min)/(maxx-min));
	   //resultss= ((((result+result2)/value1)-min)/(max-min));
	   
	   resultss= ((((((result+result2)/value1)+ avg)/2)-min)/(maxx-min));
		return resultss;
	}
	
	@Autowired
	private PenaltyFunctionProperties properties;

	//@Test
    //static public void whenCorrectInfoDatabaseConnects() {
    // 
    //    InfluxDB connection = connectDatabase();
    //    assertTrue(pingServer(connection));
	//	assertTrue("This will succeed.", pingServer(connection));
    //    assertTrue("This will fail!", pingServer(connection));
    //}

    //static private InfluxDB connectDatabase() {

        // Connect to database assumed on localhost with default credentials.
      //  return  InfluxDBFactory.connect("http://134.60.152.213:8888", "vasilis", "EiWeif0w");

    //}
	
	
	
	//static private boolean pingServer(InfluxDB influxDB) {
    //    try {
    //        // Ping and check for version string
    //        Pong response = influxDB.ping();
    //        if (response.getVersion().equalsIgnoreCase("unknown")) {
    //            log.error("Error pinging server.");
    //            return false;
    //        } else {
    //            log.info("Database version: {}", response.getVersion());
    //            return true;
    //        }
    //    } catch (InfluxDBIOException idbo) {
    //        log.error("Exception while pinging database: ", idbo);
    //        return false;
    //    }
    //}	
	
	
	/*@Measurement(name = "ComponentTime")
    public class ComponentTimePoint {
 
    @Column(name = "time")
    private Instant time;
 
    @Column(name = "ComponentName")
    private String ComponentName;
 
    @Column(name = "timeDepl")
    private Long timeDepl;
 
    }
	*/
	
	public static boolean containsEquivalent(Collection<ConfigurationElement> collection, ConfigurationElement element) {
		for (ConfigurationElement ce : collection) {
			//log.debug("containsEquivalent: comparing col. elem. to given elem.: \n\t{}\n\t{}", toString(ce), toString(element));
			if (isEquivalent(ce, element)) {
				//log.debug("containsEquivalent: ARE EQUIV");
				return true;
			}
		}
		//log.debug("containsEquivalent: NO EQUIV FOUND");
		return false;
	}
	
    public static boolean isEquivalent(ConfigurationElement a, ConfigurationElement b) {
		/*log.debug("isEquivalent:              checking: {} <--> {}", toString(a), toString(b));
		log.debug("isEquivalent:                  ram:   {} <--> {}", a.getNodeCandidate().getHardware().getRam()-b.getNodeCandidate().getHardware().getRam()==0);
		log.debug("isEquivalent:                  cores: {} <--> {}", a.getNodeCandidate().getHardware().getCores()-b.getNodeCandidate().getHardware().getCores()==0);
		log.debug("isEquivalent:                  disk:  {} <--> {}", a.getNodeCandidate().getHardware().getDisk()-b.getNodeCandidate().getHardware().getDisk()==0);*/
		if (a.getNodeCandidate().getHardware().getRam()-b.getNodeCandidate().getHardware().getRam()==0) {
			//log.debug("isEquivalent:                  PASS-1");
			if (a.getNodeCandidate().getHardware().getCores()-b.getNodeCandidate().getHardware().getCores()==0) {
				//log.debug("isEquivalent:                  PASS-2");
				if (a.getNodeCandidate().getHardware().getName()== b.getNodeCandidate().getHardware().getName()) {
				  //log.debug("isEquivalent:                  PASS-3");
				    if (a.getNodeCandidate().getHardware().getDisk()-b.getNodeCandidate().getHardware().getDisk()==0) {
					/*if (a.getCardinality() == b.getCardinality()){
						return false;
					}*/ 
					//log.debug("isEquivalent:              checking: EQUIV");
					   return true;
				    }
			    }
			}
			
		} 
		//return true;
		//log.debug("isEquivalent:              checking: NOT EQUIV");
		return false;
	}
	
	public static String toString(ConfigurationElement ce) {
		StringBuilder sb = new StringBuilder();
		sb.append("{");
		sb.append("id=").append( ce.getId() );
		sb.append(",");
		sb.append("cardinality=").append( ce.getCardinality() );
		sb.append(",");
		sb.append("nodeCandidate={")
			.append("id:").append( ce.getNodeCandidate().getId() ).append(",")
			.append("Name:").append( ce.getNodeCandidate().getHardware().getName() ).append(",")
			.append("ram:").append( ce.getNodeCandidate().getHardware().getRam() ).append(",")
			.append("cores:").append( ce.getNodeCandidate().getHardware().getCores() ).append(",")
			.append("disk:").append( ce.getNodeCandidate().getHardware().getDisk() ).append("}");
		sb.append("}");
		return sb.toString();
	}
	
	public static String toString(Collection<ConfigurationElement> collection) {
		boolean first = true;
		StringBuilder sb = new StringBuilder();
		sb.append("[");
		for (ConfigurationElement ce : collection) {
			if (first) first=false; else sb.append(",");
			sb.append(toString(ce));
		}
		sb.append("]");
		return sb.toString();
	}
	/*
	@ApplicationScoped
   public class Producer {
 
    private InfluxDB influxDB;
 
    @Produces
    @Named
    public InfluxDB getInfluxDB() {
        InfluxDB db = InfluxDBFactory.connect("http://172.17.0.2:8086", "root", "root");
 
        String dbName = "java";
 
            // Create a database
        db.createDatabase(dbName);
        return db;
    }
 
}

*/

}
