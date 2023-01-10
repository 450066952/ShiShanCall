package com.socket.server.command.thread;

import com.inch.utils.CommonUtil;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

public class ThreadRunService {
	private static  ThreadRunService single =null;
	final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
	
	private ThreadRunService() {
	}

	public synchronized static ThreadRunService getInstance() {
		if(single==null){
			single= new ThreadRunService();
		}
		return single;
	}
	
	public static void main(String[] args){
		ThreadRunService.getInstance().initScan();
	}

	public void initScan(){
		InitNumRunnable wrun= new InitNumRunnable();
		
		long oneDay = 24 * 60 * 60 * 1000;  
	    long initDelay  = CommonUtil.getTimeMillis("17:35:00") - System.currentTimeMillis(); // 17:35
	    initDelay = initDelay > 0 ? initDelay : oneDay + initDelay;  
	    
	    ScheduledFuture<?> auditFuture = scheduler.scheduleAtFixedRate(wrun, initDelay, oneDay, TimeUnit.MILLISECONDS);
		SessionUserRunnable uRunnable = new SessionUserRunnable();
		this.scheduler.scheduleAtFixedRate(uRunnable, 20L, 20L, TimeUnit.MINUTES);

	}
	public void shutdown(){
		scheduler.shutdownNow();
	}
}
