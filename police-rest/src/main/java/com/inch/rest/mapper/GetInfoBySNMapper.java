package com.inch.rest.mapper;

import java.util.List;
import java.util.Map;

import com.inch.model.CalendarModel;
import com.inch.model.ClassInfoModel;
import com.inch.model.GalleryModel;
import com.inch.model.LcdOnOffModel;
import com.inch.model.NoticeModel;
import com.inch.model.SchoolModel;
import com.inch.model.VersionModel;
import com.inch.model.VideoModel;
import com.inch.model.WelcomeModel;
import com.inch.model.WindowModel;


/**
 * SysUser Mapper
 *
 * @author Administrator
 */
public interface GetInfoBySNMapper {

    public List<NoticeModel> getNoticeBySN(String guid);

    public List<ClassInfoModel> getClassInfoBySN(String guid);

    public List<WelcomeModel> getWelcomeBySN(String guid);

    public List<GalleryModel> getGalleryBySN(String guid);

    public List<String> getSelDeviceSN(String ids);

    public List<String> getPicSelDeviceSN(String ids);

    public List<String> getSchoolDeviceSN(String ids);

    public List<VideoModel> getVideoNoticeBySN(String guid);

    public List<LcdOnOffModel> getOnOffTimeBySN(String guid);

    public CalendarModel getCalendarBySN(Map<String, String> map);

    public List<SchoolModel> getSchoolInfoBySN(String guid);

    public List<Map> getClassBySN(Map<String, String> map);

    public List<VersionModel> getVersionBySN(String guid);

    public List<GalleryModel> getPicBySN(String guid);

    public List<WindowModel> getWindowBySN(String guid);
}
