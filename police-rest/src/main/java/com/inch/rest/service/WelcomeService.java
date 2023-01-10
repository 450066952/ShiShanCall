package com.inch.rest.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.inch.model.Lcd_RelModel;
import com.inch.rest.mapper.WelcomeMapper;

/**
 * 
 * @Title: WelcomeService.java
 * @Package com.tony.service
 * @Description: TODO
 * @author tony
 * @Email 303976091@qq.com
 * @date 2016年5月20日
 */
@Service("welcomeService")
public class WelcomeService<T> extends BaseService<T> {

	
	@Autowired
	private Lcd_RelService<Lcd_RelModel> lcd_RelService;

	@Override
	public int deleteBactch(Object[] ids) throws Exception {
		int ret=0;
		ret=super.deleteBactch(ids);
		if(ret>0){
			
			lcd_RelService.deleteBactch(ids);//删除该条消息对应的屏显信息
		}
		
		return ret;
	}
	
	@Autowired
    private WelcomeMapper<T> mapper;

		
	public WelcomeMapper<T> getMapper() {
		return mapper;
	}
	
}
