/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.inch.rest.dbbase;

/**
 *
 * @author Administrator
 */
public class DbContextHolder {

    private static final ThreadLocal<String> contextHolder = new ThreadLocal<String>();

    public static void setDbType(String dbType) {
        contextHolder.set(dbType);
    }

    public static String getDbType() {
        return (String) contextHolder.get();
    }

    public static void clearDbType() {
        contextHolder.remove();
    }
}
