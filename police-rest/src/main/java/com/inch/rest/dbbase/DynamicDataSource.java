/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.inch.rest.dbbase;
import java.util.logging.Logger;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

/**
 *
 * @author Administrator
 */
public class DynamicDataSource extends AbstractRoutingDataSource{
//static Logger log = Logger.getLogger("DynamicDataSource");   
   
	
	@Override
    protected Object determineCurrentLookupKey() {
        return DbContextHolder.getDbType();   
    }

	@Override //tony 注释
	public Logger getParentLogger()  {
		// TODO Auto-generated method stub
		return null;
	}
    
}
