package com.socket.server.socket.pub;

import org.apache.log4j.Logger;


public class ThroughputMonitor extends Thread {
	private static final Logger logger = Logger.getLogger(ThroughputMonitor.class);
	private long sleeptime=1000;//默认休眠一秒
    /**默认休眠一秒
	 * @param sleeptime
	 */
	public ThroughputMonitor(long sleeptime) {
		super();
		this.sleeptime = sleeptime;
	}

	/**默认休眠一秒，如果想自定义休眠时间，请调用ThroughputMonitor(long sleeptime)构造器
     * Constructor
     */
    public ThroughputMonitor() {
    	
    }
    @Override
    public void run() {
        try {
//            long oldCounter = ServerHandler.getTransferredBytes();
//            long startTime = System.currentTimeMillis();
            for (;;) {
            	Thread.sleep(sleeptime);
//                long endTime = System.currentTimeMillis();
//                long newCounter = ServerHandler.getTransferredBytes();
//                logger.info("SessionFactory Invoke Cache: "+
//                		IbatisSessionFactory.getSessionFactoryInvokeCount()+" Time,Create Instance: "+
//                		IbatisSessionFactory.getSessionFactoryInstanceCount()+" Time,Socket Request: "+ServerHandler.getClientCount()+" Time  ");
//                System.out.println("ThroughputMonitor getTransferredBytes:"+newCounter+"oldCounter:"+oldCounter);
//                System.err.format("ThroughputMonitor:"+"%4.7f MiB/s%n", (newCounter - oldCounter) *1000 / (endTime - startTime) / 1048576.0);//字节
//                oldCounter = newCounter;
//                startTime = endTime;
            }
        } catch (InterruptedException e) {
            // Stop monitoring asked
            return;
        }
    }
}
