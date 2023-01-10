package com.socket.server.command.thread;

import com.inch.rest.service.NumInfoService;
import com.inch.rest.utils.SpringUtil;
import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;

public class InitNumRunnable implements Runnable{

	private static final Logger logger = Logger.getLogger(InitNumRunnable.class);

	@Override
	public void run() {
			logger.warn("------------------更新起始号------------------------------- ");
			ApplicationContext ac = SpringUtil.getApplicationContext();
			NumInfoService lcds=(NumInfoService)ac.getBean("numInfoService");

			try{
				lcds.copyNumData();
			}catch (Exception e){
				logger.warn("--copy data fail-- "+e.getMessage());
			}

			try{
				lcds.updateStartNum();
			}catch (Exception e){
				logger.warn("--init data fail-- "+e.getMessage());
			}


	}
}
