package com.inch.rest.utils;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.log4j.Logger;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.Transaction;

import com.inch.utils.SerializationUtil;


/**
 * Redis工具类
 *
 * @author Tony
 * @date 2015年8月12日
 * @modifyBy Tony 2015年8月12日
 *
 */
public final class RedisUtil {
	private final static Logger logger = Logger.getLogger(RedisUtil.class);
//    //Redis服务器IP
//    private static String ADDR = "127.0.0.1";
//    
//    //Redis的端口号
//    private static int PORT = 6379;
    
    //访问密码
    private static String AUTH = "true";
    
    //可用连接实例的最大数目，默认值为8；
    //如果赋值为-1，则表示不限制；如果pool已经分配了maxActive个jedis实例，则此时pool的状态为exhausted(耗尽)。
    private static int MAX_ACTIVE = 1024;
    
    //控制一个pool最多有多少个状态为idle(空闲的)的jedis实例，默认值也是8。
    private static int MAX_IDLE = 200;
    
    //等待可用连接的最大时间，单位毫秒，默认值为-1，表示永不超时。如果超过等待时间，则直接抛出JedisConnectionException；
    private static int MAX_WAIT = 10000;
    
    private static int TIMEOUT = 10000;
    
    //在borrow一个jedis实例时，是否提前进行validate操作；如果为true，则得到的jedis实例均是可用的；
    private static boolean TEST_ON_BORROW = true;
    
    private static JedisPool jedisPool = null;
    
    /**
     * 初始化Redis连接池
     */
    static {
        try {
        	/*maxActive更换成了maxTotal
        	maxWait更换成了maxWaitMillis*/
        	
        	PropertiesConfiguration jconfig=new PropertiesConfiguration("config.properties");
        		
        	
            JedisPoolConfig config = new JedisPoolConfig();
            config.setMaxTotal(MAX_ACTIVE);//config.setMaxActive(MAX_ACTIVE);
            config.setMaxIdle(MAX_IDLE);
            config.setMaxWaitMillis(MAX_WAIT);//config.setMaxWait(MAX_WAIT);
            config.setTestOnBorrow(TEST_ON_BORROW);
            
//            logger.info("redis ip："+jconfig.getString("redisip"));
//            logger.info("redis port："+jconfig.getString("redisport"));
            
            if (AUTH != null && !"".equals(AUTH)) {

            	jedisPool = new JedisPool(config, jconfig.getString("redisip"), jconfig.getInt("redisport"), TIMEOUT, jconfig.getString("redispwd"));
			} else {
				jedisPool = new JedisPool(config, jconfig.getString("redisip"), jconfig.getInt("redisport"), TIMEOUT);
				
			}
        } catch (Exception e) {
        	
        	e.printStackTrace();
        }
    }
    
    /**
     * 获取Jedis实例
     * @return
     */
    public synchronized static Jedis getJedis() {
    	Jedis resource=null;
        try {
            if (jedisPool != null) {
            	resource = jedisPool.getResource();
            } 
        } catch (Exception e) {
        	e.printStackTrace();
        }
    	return resource;
    }
    
    /**
     * 释放jedis资源
     * @param jedis
     */
    public static void returnResource(final Jedis jedis) {
        if (jedis != null) {
        	jedis.close();
        }
    }
    
    
    
    /**
     * 设置key---设置过期时间-----存储对象
     * @param key
     * @param seconds
     */
    public static boolean setObjectByTime(String key,Object oc,int seconds){
        
        Jedis jedis = null;
        try {
            jedis = getJedis();
            String res = jedis.set(key.getBytes(),  SerializationUtil.serialize(oc));

			if (seconds != -1) {
				jedis.expire(key, seconds);
			}

            if (res != null && "OK".equals(res)) {
				return true;
			}
        } catch (Exception e) {
            //释放redis对象
            e.printStackTrace();
        } finally {
            //返还到连接池
            returnResource(jedis);
        }
        return false;
    }
    
    /**
     * 获取数据
     * 
     * @param key
     * @return
     */
	public static Object getObjByKey(String key){
		
    	byte[] s = null;
    	
        Jedis jedis = null;
        try {
            jedis = getJedis();
            s = jedis.get(key.getBytes());
        } catch (Exception e) {
            //释放redis对象
            e.printStackTrace();
        } finally {
            //返还到连接池
            returnResource(jedis);
        }
        
        return SerializationUtil.unserialize(s);
    }
    
    public static boolean hmset(String useridKey,Map<String,String> map){
    	Jedis jedis = null;
    	try {
			jedis = getJedis();
			String res = jedis.hmset(useridKey, map);
			if (res != null && "OK".equals(res)) {
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			returnResource(jedis);
		}
    	return false;
    }
    public static List hmget(String useridKey,String ... fields){
    	Jedis jedis = null;
    	List list=null;
    	try {
			jedis = getJedis();
			 list = jedis.hmget(useridKey, fields);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			returnResource(jedis);
		}
    	return list;
    }
    public static List hvals(String useridKey){
    	Jedis jedis = null;
    	List list=null;
    	try {
			jedis = getJedis();
			 list = jedis.hvals(useridKey);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			returnResource(jedis);
		}
    	return list;
    }
    /**
     * set
     * @author zlj
     * @date 2015年8月12日
     * @modifyBy zlj 2015年8月12日
     *
     * @param key
     * @param value
     * @param seconds -1 不失效,单位：秒
     */
    public static boolean set(String key, String value, int seconds) {
    	Jedis jedis = null;
    	try {
			jedis = getJedis();
			String res = jedis.set(key, value);
			// 如果不等于-1
			if (seconds != -1) {
				
				jedis.expire(key, seconds);
			}
			if (res != null && "OK".equals(res)) {
				
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			
			returnResource(jedis);
		}
    	return false;
    }
    
    /**
     * redis> ZADD myzset 1 "one"
		(integer) 1
		redis> ZADD myzset 1 "uno"
		(integer) 1
		redis> ZADD myzset 2 "two" 3 "three"
		(integer) 2
    * @Title: zadd  
    * @Description: TODO 
    * @param @param key
    * @param @param value
    * @param @param seconds
    * @return boolean    返回类型  
    * @throws  
    */
    public static boolean zadd(String key, double score, String member) {
    	Jedis jedis = null;
    	try {
			jedis = getJedis();
			Long res = jedis.zadd(key, score, member);
			if (res >0) {
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			returnResource(jedis);
		}
    	return false;
    }
    
    
    /**
     * 
    * @Title: hset
    * @Description: hset
    * @param key 关键key
    * @param field 域
    * @param value 值
    * @param seconds -1 不失效,单位：秒
    * @return
    * @return Long 返回类型,1-新建域，0-覆盖值
    * @throws
     */
    public static Long hset(String key, String field, String value, int seconds) {
    	Long res = -1L;
    	Jedis jedis = null;
    	try {
			jedis = getJedis();
			res = jedis.hset(key, field, value);
			//如果 field 是哈希表中的一个新建域，并且值设置成功，返回 1 。
			//如果哈希表中域 field 已经存在且旧值已被新值覆盖，返回 0 。
			if (res == 1 || res == 0) {
				jedis.expire(key, seconds);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			
			returnResource(jedis);
		}
    	return res;
    }
    
    public static Long hset(String key, String field, String value) {
    	Long res = -1L;
    	Jedis jedis = null;
    	try {
			jedis = getJedis();
			logger.debug("hset key:"+key+" value:"+value);
			res = jedis.hset(key, field, value);
			//如果 field 是哈希表中的一个新建域，并且值设置成功，返回 1 。
			//如果哈希表中域 field 已经存在且旧值已被新值覆盖，返回 0 。
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			returnResource(jedis);
		}
    	return res;
    }
    
    public static Long hsetObj(String key, String field, Object oc) {
    	Long res = -1L;
    	Jedis jedis = null;
    	try {
			jedis = getJedis();
			res = jedis.hset(key.getBytes(), field.getBytes(), SerializationUtil.serialize(oc));//.hset(key, field, value);
			//如果 field 是哈希表中的一个新建域，并且值设置成功，返回 1 。
			//如果哈希表中域 field 已经存在且旧值已被新值覆盖，返回 0 。
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			returnResource(jedis);
		}
    	return res;
    }
    
    /**
     * 存入对象
     * @param key
     * @param field
     * @param oc
     * @param seconds
     * @return
     */
    public static Long hsetObjByTime(String key, String field, Object oc,int seconds) {
    	Long res = -1L;
    	Jedis jedis = null;
    	try {
			jedis = getJedis();
			res = jedis.hset(key.getBytes(), field.getBytes(), SerializationUtil.serialize(oc));//.hset(key, field, value);
			//如果 field 是哈希表中的一个新建域，并且值设置成功，返回 1 。
			//如果哈希表中域 field 已经存在且旧值已被新值覆盖，返回 0 。
			
			if (res == 1 || res == 0) {
				jedis.expire(key, seconds);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			returnResource(jedis);
		}
    	return res;
    }
    
    /**
     * 根据key field去删除数据
     * @param key
     * @param field
     * @return
     */
    public static Long hdel(String key, String field) {
    	Long res = -1L;
    	Jedis jedis = null;
    	try {
    		
			jedis = getJedis();
			res = jedis.hdel(key, field);
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			returnResource(jedis);
		}
    	return res;
    }
    
    /**
     * 根据key去删除
     * @param key
     * @return
     */
    public static Long hdel(String key) {
    	Long res = -1L;
    	Jedis jedis = null;
    	try {
    		
			jedis = getJedis();
			res = jedis.hdel(key);
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			returnResource(jedis);
		}
    	return res;
    }

    /**
     * get
     *
     * @author zlj
     * @date 2015年8月12日
     * @modifyBy zlj 2015年8月12日
     *
     * @param key
     * @return
     */
    public static String get(String key){
    	Jedis jedis = null;
    	try {
			jedis = getJedis();
			if(jedis.exists(key)){
				return jedis.get(key);
			}
			 
		} catch (Exception e) {
			e.printStackTrace();
		}  finally {
			returnResource(jedis);
		}
    	return "";
    }
    
    /**   开启事务
    * @Title: multi  
    * @Description: TODO 
    * @param     设定文件  
    * @return void    返回类型  
    * @throws  
    */
    public static Transaction multi(){
    	Jedis jedis = null;
    	try {
			jedis = getJedis();
			return jedis.multi();
		} catch (Exception e) {
			e.printStackTrace();
		}  finally {
			returnResource(jedis);
		}
    	return null;
    }
    /**   事务执行
    * @Title: exec  
    * @Description: TODO 
    * @param     设定文件  
    * @return void    返回类型  
    * @throws  
    */
    public static List exec(Transaction ta){
    	Jedis jedis = null;
    	List list = null;
    	try {
			jedis = getJedis();
			list=	ta.exec();
		} catch (Exception e) {
			e.printStackTrace();
		}  finally {
			returnResource(jedis);
		}
    	return list;
    }
    
    /**  
     * 
     * 
    * @Title: zrangeWithScores  
    * @Description: TODO 
    * @param @param key
    * @param @return  
    * @return Set 
    * @throws  
    */
    public static Set zrangeWithScores(String key ){
    	Jedis jedis = null;
    	Set set = null;
    	try {
			jedis = getJedis();
			//取全部数据
			set=jedis.zrangeWithScores(key, 0, 1);
		} catch (Exception e) {
			e.printStackTrace();
		}  finally {
			returnResource(jedis);
		}
    	return set;
    }
    public static Set<String> zrange(String key ){
    	Jedis jedis = null;
    	Set<String> set = null;
    	try {
			jedis = getJedis();
			//取全部数据
			set=jedis.zrange(key, 0L, -1L);
		} catch (Exception e) {
			e.printStackTrace();
		}  finally {
			returnResource(jedis);
		}
    	return set;
    }

	public static Set<String> getPrefixKeys(String pre_str){

		Jedis jedis = null;

		Set<String> set;
		try {
			jedis = getJedis();
			set = jedis.keys(pre_str +"*");
			return set ;
		} catch (Exception e) {
			e.printStackTrace();
		}  finally {
			returnResource(jedis);
		}

		return null;
	}
    
    /**
     * 
    * @Title: hget
    * @Description: 获取值
    * @param key 
    * @param field 域
    * @return
    * @return String 返回类型
    * @throws
     */
    public static String hget(String key, String field){
    	Jedis jedis = null;
    	try {
			jedis = getJedis();
			return jedis.hget(key, field);
		} catch (Exception e) {
			
			e.printStackTrace();
		}  finally {
			
			returnResource(jedis);
		}
    	return null;
    }
    /**  b:value和s:value的形式
    * @Title: hgetAll  
    * @Description: TODO 
    * @param @param key
    * @return Map<String,String> 
    * @throws  
    */
    public static Map<String, String> hgetAll(String key){
    	Jedis jedis = null;
    	try {
			jedis = getJedis();
			return jedis.hgetAll(key);
		} catch (Exception e) {
			
			e.printStackTrace();
		}  finally {
			
			returnResource(jedis);
		}
    	return null;
    }
    
    /**  b:value和s:value的形式
     * @Title: hgetAll  
     * @Description: TODO 
     * @param @param key
     * @return Map<String,String> 
     * @throws  
     */
     public static Map<byte[], byte[]> hgetAllObj(String key){
     	
    	Jedis jedis = null;
     	try {
 			jedis = getJedis();
 			
 			Map<byte[], byte[]> map=jedis.hgetAll(key.getBytes());
 			
// 			for (byte[] skey : map.keySet()) {
// 			   
// 				mapreturn.put(new String(skey), SerializationUtil.unserialize(map.get(skey)));
// 			}
 			
 			return map;
 		} catch (Exception e) {
 			
 			e.printStackTrace();
 		}  finally {
 			
 			returnResource(jedis);
 		}
     	return null;
     }
     
     public static Object hgetObj(String key, String field){
     	Jedis jedis = null;
     	byte[] oo=null;
     	try {
 			jedis = getJedis();
 			
 			oo=jedis.hget(key.getBytes(), field.getBytes());
 			
 			
 		} catch (Exception e) {
 			
 			e.printStackTrace();
 		}  finally {
 			
 			returnResource(jedis);
 		}
     	
     	if(oo!=null){
     		return SerializationUtil.unserialize(oo);
     	}else{
     		return null;
     	}
     }
    

    /**
     * key是否存在
     *
     * @author zlj
     * @date 2015年8月12日
     * @modifyBy zlj 2015年8月12日
     *
     * @param key
     * @return
     */
    public static boolean exists(String key){
    	Jedis jedis = null;
    	try {
    		jedis = getJedis();
			return jedis.exists(key);
		} catch (Exception e) {

			e.printStackTrace();
		}   finally {
			
			returnResource(jedis);
		}
    	return false;
    }
    
    /**
     * 
    * @Title: del
    * @Description: 删除
    * @param key
    * @return
    * @return boolean 返回类型
    * @throws
     */
    public static boolean del(byte[] key){
    	Jedis jedis = null;
    	try {
    		jedis = getJedis();
    		jedis.del(key);
			return true;
		} catch (Exception e) {

			e.printStackTrace();
		}   finally {
			
			returnResource(jedis);
		}
    	return false;
    }
    public static int del(String key){
    	Jedis jedis = null;
    	long ret=0;
    	try {
    		jedis = getJedis();
    		ret=jedis.del(key);
			
		} catch (Exception e) {

			e.printStackTrace();
		}   finally {
			
			returnResource(jedis);
		}
    	return (int)ret;
    }
    
    public static boolean zrem(String key,String ... members){
    	Jedis jedis = null;
    	try {
    		jedis = getJedis();
    		jedis.zrem(key, members);
			return true;
		} catch (Exception e) {

			e.printStackTrace();
		}   finally {
			
			returnResource(jedis);
		}
    	return false;
    }
    
    public static Set<String> allKey( ){
    	Jedis jedis = null;
    	Set<String> set = null;
    	try {
			jedis = getJedis();
			//取全部数据
			set=jedis.keys("*");
		} catch (Exception e) {
			e.printStackTrace();
		}  finally {
			returnResource(jedis);
		}
    	return set;
    }
    
    public static String mkTagID() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }
    public static void main(String[] args) throws Exception{
//    	String s ="300.00";
//    	new Double(s);
//    	MsgDto aaa=new MsgDto();
////    	aaa.msg="test";
//    	
//    	Object a=aaa;
//    	
//    	if(a instanceof MsgDto){
//    		MsgDto ccc=(MsgDto)a;
//    	}
//    	
//    	if(a instanceof FtpMsgdDto){
//    		FtpMsgdDto bbb=(FtpMsgdDto)a;
//    	}
//    	
//    	for(int j=0;j<10000;j++){
//    		for(int i=0;i<60;i++){
//        		MsgDto ccc=new MsgDto();
//        		ccc.setGuid(UUID.randomUUID().toString());
//        		ccc.setSendname("aaa"+i+j);
//        		ccc.setReceivename("bbbb"+i+j);
//        		hsetObj("name1"+j,ccc.getGuid(),ccc);
//        	}
//    	}
    	
    	
    	logger.info("--------start--------------");
//    	hsetObj("rrrrr","test1",aaa);
//    	hsetObj("rrrrr","test2",aaa);
    	Map<byte[], byte[]> map888=hgetAllObj("name1100");
    	
    	logger.info("--------end--------------");
    	
    	
    	hdel("rrrrr","test1");
    	
//    	Map<String,Object> map1111=hgetAllObj("rrrrr");
    	
    	
    	hset("users","tony","xujun");
    	hset("users","hao","haohao");
    	hset("users","vivin","tanwei");
    	
//    	set("tony","xujun");
//    	set("hao","haohao");
//    	set("vivin","tanwei");
    	
    	Map<String,String> map=hgetAll("users");
    	
    	hdel("users","vivin");
    	
    	Map<String,String> map2=hgetAll("users");
    	
    	
    	System.out.print(get("tony"));
    	System.out.print(del("tanwei"));
    	System.out.print(del("tony"));
    	
//    	zadd("10060", 1940, "Alan Kay");  
//    	zrange("10060");
    	/*String value = String.format("{\"id\":%s,\"nickName\":\"%s\",\"email\":\"%s\",\"mobile\":\"%s\",\"token\":\"%s\",\"expiresIn\":%s}", 666, "万年寿司", "longjun@jince.com", "15618872008", "19881019", 60);
		RedisUtil.set("19881019", value, 60);
		System.out.println(RedisUtil.get("19881019"));*/
    	
    	/*set("test1", "test1", 5);
    	set("test2", "test2", 5);
    	set("test3", "test3", 5);*/
    	
    	/*exists("test1");
    	exists("test1");
    	exists("test1");*/
    	
    	//System.out.println(RedisUtil.get("18321980023"));
//    	RedisUtil.del("zlj".getBytes());
//    	RedisUtil.hset("zlj".getBytes(), "java:zlj".getBytes(), "哈哈".getBytes(), 120);
//    	System.out.println(RedisUtil.hget("zlj".getBytes(), "java:zlj".getBytes()));
//    	RedisUtil.del("zlj".getBytes());
//    	System.out.println(RedisUtil.hget("zlj".getBytes(), "java:zlj".getBytes()));
    	
    	
    	/*System.out.println("1111>"+RedisUtil.hset("hset_test", "zlj", "1", 5));
    	System.out.println("1111>"+RedisUtil.hset("hset_test", "zlj2", "2", 10));
    	
    	System.out.println(exists("hset_test"));
    	
    	System.out.println("2222>"+RedisUtil.hget("hset_test", "zlj2"));
    	System.out.println("3333>"+RedisUtil.hget("hset_test", "zlj"));
    	Thread.sleep(5000);
    	System.out.println("4444>"+RedisUtil.hget("hset_test", "zlj"));
    	System.out.println("5555>"+RedisUtil.hget("hset_test", "zlj2"));
    	Thread.sleep(5000);
    	System.out.println("4444>"+RedisUtil.hget("hset_test", "zlj"));
    	System.out.println("5555>"+RedisUtil.hget("hset_test", "zlj2"));*/
	}
}