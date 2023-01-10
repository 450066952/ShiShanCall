package com.inch.rest.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.inch.model.EvaModel;
import com.inch.model.NumInfoModel;
import com.inch.rest.dbbase.DbContextHolder;
import com.inch.rest.mapper.NumInfoMapper;
import com.inch.utils.CommonUtil;
import com.inch.utils.FastJsonUtils;
import com.inch.utils.HttpClientUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.*;

@Service("numInfoService")
public class NumInfoService<T> extends BaseService<T> {

	private static final Logger logger = Logger.getLogger(NumInfoService.class);

	//用户在叫号机上取号
	public NumInfoModel queryNum(NumInfoModel model) throws Exception {
		int ret;
		if (model.getLevel() == 2){
			ret=getMapper().addTwo(model);
		}else {
			ret=this.add((T) model);
		}

		if(ret>0){
			NumInfoModel ssmodel;
			if (model.getLevel() == 2){
				ssmodel=getMapper().queryByIdTwo(model);
			}else {
				ssmodel=(NumInfoModel)getMapper().queryById(model);
			}

			List<String> wlist=new ArrayList<>();
			String winname="";
			if(ssmodel.getInfoList()!=null){
				for(Map<String,String> map:ssmodel.getInfoList()){
					if(StringUtils.isNotBlank(map.get("firsttype"))){
						if(isContains(model.getChilds(),map.get("firsttype"))){
							winname=StringUtils.substringBeforeLast(map.get("name"),"-");
							if(!wlist.contains(winname)){
								wlist.add(winname);
							}
						}
					}
				}
			}

			if(wlist.size()>0){
				ssmodel.setwList(wlist);
			}
			return ssmodel;
		}
		return null;
	}

	//预约取号在取号机上取号
	public NumInfoModel queryNumOrder(NumInfoModel model) throws Exception {
		int ret;
		if (model.getLevel() == 2){
			ret=getMapper().addTwoOrder(model);
		}else {
			ret=getMapper().addOrder(model);
		}

		if(ret>0){
			NumInfoModel ssmodel;
			if (model.getLevel() == 2){
				ssmodel=getMapper().queryByIdTwo(model);
			}else {
				ssmodel=(NumInfoModel)getMapper().queryById(model);
			}

			List<String> wlist=new ArrayList<>();
			String winname="";
			if(ssmodel.getInfoList()!=null){
				for(Map<String,String> map:ssmodel.getInfoList()){
					if(StringUtils.isNotBlank(map.get("firsttype"))){
						if(isContains(model.getChilds(),map.get("firsttype"))){
							winname=StringUtils.substringBeforeLast(map.get("name"),"-");
							if(!wlist.contains(winname)){
								wlist.add(winname);
							}
						}
					}
				}
			}

			if(wlist.size()>0){
				ssmodel.setwList(wlist);
			}
			return ssmodel;
		}
		return null;
	}

	public int insertChildBus(NumInfoModel bean){

		if(StringUtils.isNotBlank(bean.getChilds())){
			List<Map<String,Object>> list=new ArrayList<>();
			String[] ids=bean.getChilds().split(",");
			String[] childname=bean.getChildname().split(",");
			int i=0;
			for(String id:ids){
				Map<String,Object> map=new HashMap<>();
				map.put("type",id);
				map.put("pguid",bean.getGuid());
				map.put("ptype",bean.getType());
				map.put("allnum",bean.getNum());
				map.put("orgid",bean.getOrgid());
				map.put("typename",childname[i]);
				list.add(map);
				i++;
			}
			return getMapper().insertChildBus(list);
		}else{
			return 0;
		}
	}

	public void sandInfoToThird(NumInfoModel bean){
		Map<String,Object> map=new HashMap<>();
		//取号成功后反馈到区平台
		String urlNumber = "https://www.sndzwfw.com/apijava/api/queue/get/number";
		//通过身份证号码获取用户预约信息
		map.put("flag","2");
		map.put("userName","222");
		map.put("userCard",bean.getIdcard());
		map.put("itemId",bean.getChilds());
		map.put("ouGuid","320505002004CS");
		map.put("startTime",timeAdd(0));
		map.put("endTime",timeAdd(10));
		map.put("onlyCheck","1");
		map.put("distanceFlag",false);
		map.put("checkLocalFlag",false);


		String jsonNumber = HttpClientUtil.doPostJson(urlNumber, FastJsonUtils.toJson(map),null);
		JSONObject jsonObjectNumber = JSONObject.parseObject(jsonNumber);
		if ("200".equals(jsonObjectNumber.getString("code"))){
			JSONObject resultNumber = JSONObject.parseObject(jsonObjectNumber.getString("result"));
			String itemNumber = resultNumber.getString("items");
			List<Map> itemsNumber = JSONArray.parseArray(itemNumber, Map.class);
			logger.info("取号返回信息：" + itemsNumber);
		}
	}

	public static String timeAdd(int addLong){
		SimpleDateFormat df = new SimpleDateFormat("HH:mm");
		Date date = new Date();
		date.setTime(date.getTime() + (addLong * 60 * 1000));
		return df.format(date);
	}


	public void sendmsg(NumInfoModel retmodel){
		//发短信
		try{
			if(StringUtils.isNotBlank(retmodel.getCardno())){
				Map<String,Object> map=new HashMap<>();
				map.put("mobilephone",retmodel.getCardno());

				//您办理的【出入境登记】业务已经成功取号，排队号：A1003，3号窗口办理，本业务等候人数3人，请注意叫号语音或短信通知，太仓公安为您服务。
				StringBuffer sb=new StringBuffer();
				sb.append("您办理的【");
				sb.append(retmodel.getTypename());
				sb.append("】业务已经成功取号，排队号：");
				sb.append(retmodel.getNum()+"，");

//				String msg="";
//
//				if(retmodel.getwList()!=null){
//					for(String s :retmodel.getwList()){
//						msg+=s+"、";
//					}
//				}

//				sb.append(StringUtils.substringBeforeLast(msg,"、"));
//				sb.append("号窗口办理，本业务等候人数"+retmodel.getWaitcnt()+"人，请注意叫号语音或短信通知，太仓公安为您服务。");
				sb.append("本业务等候人数"+retmodel.getWaitcnt()+"人，请注意叫号语音或短信通知，太仓公安为您服务。");

				map.put("content",sb.toString());
				map.put("statusTime", CommonUtil.now());
				DbContextHolder.setDbType("old");
				getMapper().addMobileMsg(map);
			}
		}catch (Exception e){
			e.printStackTrace();
		}finally {
			DbContextHolder.setDbType("now");
		}

	}

	public void sendMsgNext(NumInfoModel retmodel,String winname){
		try{
			if(StringUtils.isNotBlank(retmodel.getCardno())){
				Map<String,Object> map=new HashMap<>();
				map.put("mobilephone",retmodel.getCardno());

				//您等候的【出入境登记】业务已经可办理，您的排队号：A1003，请到3号窗口办理，太仓公安为您服务。
				StringBuffer sb=new StringBuffer();
//				sb.append("您等候的【");
//				sb.append(retmodel.getTypename());
//				sb.append("】业务已经可办理，您的排队号：");

				sb.append("您等候的业务已经可办理，您的排队号：");
				sb.append(retmodel.getNum()+"，请到"+winname);
				sb.append("号窗口办理，太仓公安为您服务。");

				map.put("content",sb.toString());
				map.put("statusTime", CommonUtil.now());
				DbContextHolder.setDbType("old");
				getMapper().addMobileMsg(map);
			}

		}catch (Exception e){
			e.printStackTrace();
		}finally {
			DbContextHolder.setDbType("now");
		}

	}

	public synchronized NumInfoModel  updateNextNum(String type,int orgid,String firsttype,
													String winid,String winname,String userid,String name){
		Map<String,Object> map=new HashMap<>();
		map.put("type",type);
		map.put("orgid",orgid);
		map.put("firsttype",firsttype);
//		NumInfoModel model=getMapper().queryNextNum(map);

		List<NumInfoModel> list= getMapper().queryNextNum2(map);
		if(list==null||list.size()==0){
			return null;
		}

		//判断这个窗口和所选择的业务是否相同
		for(NumInfoModel mode:list){
			if(isContains(mode.getChilds(),firsttype)){
				mode.setWinid(winid);
				mode.setWinname(winname);
				mode.setUserid(userid);
				mode.setName(name);

				mapper.update((T) mode);
				return mode;
			}
		}

		return null;
	}



	public int addEva(EvaModel model){
		return getMapper().addEva(model);
	}

	public int copyNumData(){
		return getMapper().copyNumData();
	}

	public int addSign(String userid,int orgid){
		Map<String,Object> map=new HashMap<>();
		map.put("userid",userid);
		map.put("orgid",orgid);
		return getMapper().addSign(map);
	}

	public List<String> getNotifyLcd(String guid){
		return mapper.getNotifyLcd(guid);
	}

	public List<Map<String,String>> getSign(){
		return getMapper().getSign();

	}

	public List<Map<String,Object>> queryByList2(String type,int orgid,String firsttype){
		Map<String,Object> map=new HashMap<>();
		map.put("type",type);
		map.put("orgid",orgid);
//		map.put("ids",firsttype.split(","));

		List<Map<String,Object>> backList=new ArrayList<>();
		List<Map<String,Object>> list=getMapper().queryByList2(map);
		if(list!=null){
			String childs="";
			for(Map<String,Object> smap:list){
				childs=String.valueOf(smap.get("childs"));
				if(isContains(childs,firsttype)){
					backList.add(smap);
				}
			}
		}

		return backList;
	}

	public int updateStartNum(){
		return mapper.updateStartNum();
	}
	@Autowired
    private NumInfoMapper<T> mapper;

	public NumInfoMapper<T> getMapper() {
		return mapper;
	}


	private Boolean isContains(String a,String b){

		List<String> aa=new ArrayList<>();
		for(String aaa:a.split(",")){
			aa.add(aaa);
		}

		List<String> bb=new ArrayList<>();
		for(String aaa:b.split(",")){
			bb.add(aaa);
		}
		bb.retainAll(aa);

		if(bb.size() == aa.size()){
			return true;
		}else{
			return false;
		}
	}

    public NumInfoModel queryHistoryInfo(NumInfoModel bean) {
		return getMapper().queryHistoryInfo(bean);
    }

	public int deleteNum(String guid) {
		return getMapper().deleteNum(guid);
	}
}
