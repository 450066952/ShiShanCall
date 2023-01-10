package com.inch.rest.service;

import java.util.List;
import java.util.Map;

import com.inch.model.WindowModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.inch.model.CalendarModel;
import com.inch.model.GalleryModel;
import com.inch.model.LcdOnOffModel;
import com.inch.model.NoticeModel;
import com.inch.model.SchoolModel;
import com.inch.model.VersionModel;
import com.inch.model.VideoModel;
import com.inch.model.WelcomeModel;
import com.inch.rest.mapper.GetInfoBySNMapper;

@Service("getInfoBySNService")
public class GetInfoBySNService {

	public List<NoticeModel> getNoticeBySN(String guid) {

		return getMapper().getNoticeBySN(guid);
	}


	public List<WelcomeModel> getWelcomeBySN(String guid) {

		return getMapper().getWelcomeBySN(guid);
	}


	public List<SchoolModel> getSchoolInfoBySN(String guid) {

		return getMapper().getSchoolInfoBySN(guid);
	}
	
	public List<VideoModel> getVideoNoticeBySN(String guid) {

		return getMapper().getVideoNoticeBySN(guid);
	}
	
	public List<String> getSelDeviceSN(String guid) {

		return getMapper().getSelDeviceSN(guid);
	}
	
	public List<String> getPicSelDeviceSN(String guid) {

		return getMapper().getPicSelDeviceSN(guid);
	}

	public List<String> getSchoolDeviceSN(String guid) {

		return getMapper().getSchoolDeviceSN(guid);
	}
	
	public List<LcdOnOffModel> getOnOffTimeBySN(String guid) {

		return getMapper().getOnOffTimeBySN(guid);
	}
	
	public CalendarModel getCalendarBySN(Map<String, String> map){
		
		return getMapper().getCalendarBySN(map);
	}
	
	public List<Map> getClassBySN(Map<String, String> map){
		
		return getMapper().getClassBySN(map);
	}

	public List<VersionModel> getVersionBySN(String guid){
		
		return getMapper().getVersionBySN(guid);
	}
	

	public List<GalleryModel> getPicBySN(String guid){
		
		return getMapper().getPicBySN(guid);
	}


	public List<WindowModel> getWindowBySN(String guid){

		return getMapper().getWindowBySN(guid);
	}
	

	@Autowired
	private GetInfoBySNMapper mapper;

	public GetInfoBySNMapper getMapper() {
		return mapper;
	}

}
