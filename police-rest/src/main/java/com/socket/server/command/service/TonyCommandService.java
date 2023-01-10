package com.socket.server.command.service;

import com.inch.model.BaseSocketModel;
import com.inch.model.Lcd_RelModel;
import com.inch.model.NumInfoModel;
import com.inch.rest.service.GetInfoBySNService;
import com.inch.rest.service.Lcd_RelService;
import com.inch.rest.service.NumInfoService;
import com.inch.rest.utils.RedisUtil;
import com.inch.utils.FastJsonUtils;
import com.inch.utils.WebConstant;
import com.socket.server.command.dto.PcLoginModel;
import com.socket.server.command.dto.ReauestLoginDto;
import com.socket.server.command.web.RequestAllCommand;
import com.socket.server.socket.pub.CharacterHelper;
import com.socket.server.socket.pub.Command;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class TonyCommandService extends ServerCommandSendHelper {
	private static final Logger logger = Logger
			.getLogger(TonyCommandService.class);
	
	@Autowired
	private Lcd_RelService<Lcd_RelModel> lcdRelService;

	@Autowired
	private NumInfoService<NumInfoModel> numInfoService;
	
	@Autowired
	private GetInfoBySNService getInfoBySNService;
	
	
	public  void sendCommandToServer(int cmd,Object obj,String ids) {
			
			try {
				RequestAllCommand noticecmd ;
				//------------start 组成消息体---------------------------------
				if(cmd==Command.COMMAND_SEND_INIT_SCHOOL){
					 noticecmd = new RequestAllCommand(Command.COMMAND_SEND_INIT);
				}else{
					 noticecmd = new RequestAllCommand(cmd);
				}

				BaseSocketModel smodel=new BaseSocketModel();
				String guid=UUID.randomUUID().toString();
				smodel.setGuid(guid);
				smodel.setReceivetime(new Date());

				if(obj instanceof java.util.List){
					smodel.setList((List)obj);
				}else{
					if(obj!=null){
						List<Object> listdata=new ArrayList<Object>();
						listdata.add(obj);
						smodel.setList(listdata);
					}
				}

				String sendjson=FastJsonUtils.toJson(smodel);
				
				noticecmd.setInfo(sendjson);
				//------------end 组成消息体---------------------------------
				
				List<String> list=null;
				
				if(cmd==Command.COMMAND_SCHOOLNOTICE||cmd==Command.COMMAND_SCHOOL_INFO
						||cmd==Command.COMMAND_ONOFF_TIME||cmd==Command.COMMAND_UPGRADED||cmd==Command.COMMAND_HONOR_UPDATE
						||cmd==Command.COMMAND_GUIDE||cmd==Command.COMMAND_COURSE_Time
						||cmd==Command.COMMAND_SEND_INIT_SCHOOL
){
					list=lcdRelService.getLcdSnBySchool(ids);
				}else if(cmd==Command.COMMAND_SOCOAL_SEND_CALL
						||cmd==Command.COMMAND_SOCOAL_STOP_BUS
						||cmd==Command.COMMAND_SOCOAL_STOP_SERVICE
						||cmd==Command.COMMAND_SOCOAL_REPEAT_cALL
						||cmd==Command.COMMAND_SOCOAL_START_BUS
						||cmd==Command.COMMAND_SOCOAL_START_SIGN){
					list=numInfoService.getNotifyLcd(ids);
				}
				else{
					list=lcdRelService.getLcdSn(ids);
				}
				
				//-------------------开始发送-----------------------------------------
				if(list!=null&&list.size()>0){
					for (String sn : list) {
						if(CharacterHelper.ALL_LOGIN_USER!=null){
							if(CharacterHelper.ALL_LOGIN_USER.containsKey(sn)){
								sendCommandC(noticecmd, CharacterHelper.ALL_LOGIN_USER.get(sn).getChannel());
							}
						}
					}
				}
				
			} catch (IllegalArgumentException ex) {
				logger.error("命令格式不正确, 请求命令中不包含此命令!", ex.getCause());
			}
	}
	
	public  void sendDeleteToServer(int cmd,String ids) {

			// 获取客户端发送来的Command,根据不同的命令进行相应的逻辑处理
			logger.info("++++++++++删除屏显--显示信息+++++++++++command is :"+cmd);
			
			if(StringUtils.trimToEmpty(ids).length()>0){
				
				List<String> dList=new ArrayList<String>();
				
				String[] guids=ids.split(",");
				
				for (String s : guids) {
					dList.add(s);
				}
				
				//--------------start 组成消息体-----------------------
				BaseSocketModel smodel=new BaseSocketModel();
				String guid=UUID.randomUUID().toString();
				
				smodel.setGuid(guid);
				smodel.setReceivetime(new Date());
				smodel.setList(dList);
				
				RequestAllCommand sendCommand =new RequestAllCommand(cmd);
				
				String sendjson=FastJsonUtils.toJson(smodel);
				sendCommand.setInfo(sendjson);
				//--------------end 组成消息体结束-----------------------
				
				//--------------start 开始发送-----------------------
				List<String> snlist;
				
				if(cmd==Command.COMMAND_DEL_SCHOOLNOTICE){
					snlist=getInfoBySNService.getSchoolDeviceSN(ids);
				}
				else if(cmd==Command.COMMAND_DEL_PIC_PHOTO){
					snlist=getInfoBySNService.getPicSelDeviceSN(ids);
				}
				else{
					//根据lcd_rel对应关系查询对应设备
					snlist=getInfoBySNService.getSelDeviceSN(ids);
				}
				
				if(snlist!=null&&snlist.size()>0){
					for (String sn : snlist) {
						
						RedisUtil.hset(CharacterHelper.HISTORY+sn,guid, cmd+"@"+sendjson);//同时保留一根数据在最近的Map中
						
						if(CharacterHelper.ALL_LOGIN_USER!=null){
							
							if(CharacterHelper.ALL_LOGIN_USER.containsKey(sn)){
								sendCommandC(sendCommand, CharacterHelper.ALL_LOGIN_USER.get(sn).getChannel());
							}
						}
					}
				}
				//--------------end 停止发送-----------------------
			}
	}

	/**
	 * 通知PC客户端 and 大数据 有新的取号信息
	 */
	public void sendNumToPC(String type,NumInfoModel model,int cmd,int orgid){
		PcLoginModel ssmodel;

		for(String m:CharacterHelper.ALL_PC_USER.keySet()){
			ssmodel=CharacterHelper.ALL_PC_USER.get(m);
//			type为二级目录   判断该窗口

			//如果是优先业务----取号---取得是优先服务（暂住人口信息）
			if(ssmodel.getWintype().contains(type)
					&&ssmodel.getSchoolid()==orgid
					&&isContains(model.getChilds(),ssmodel.getFirsttype())
				){
				RequestAllCommand sendCommand =new RequestAllCommand(cmd);
				BaseSocketModel smodel=new BaseSocketModel();
				String guid=UUID.randomUUID().toString();
				smodel.setGuid(guid);

				List<NumInfoModel> nlist=new ArrayList<>();
				nlist.add(model);
				smodel.setList(nlist);

				sendCommand.setInfo(FastJsonUtils.toJson(smodel));
				sendCommandC(sendCommand,ssmodel.getChannel());
//				return;
			}
//
//			//通用的取号业务
//			if(ssmodel.getWintype().contains(type)&&ssmodel.getSchoolid()==orgid){
//
//				RequestAllCommand sendCommand =new RequestAllCommand(cmd);
//				BaseSocketModel smodel=new BaseSocketModel();
//				String guid=UUID.randomUUID().toString();
//				smodel.setGuid(guid);
//
//				List<NumInfoModel> nlist=new ArrayList<>();
//				nlist.add(model);
//				smodel.setList(nlist);
//
//				sendCommand.setInfo(FastJsonUtils.toJson(smodel));
//				sendCommandC(sendCommand,ssmodel.getChannel());
//			}
		}
	}

	public void sendMsgToPC(List<String> recList, Object model,int cmd){

		if(recList==null&&recList.size()==0){
			return;
		}

		PcLoginModel ssmodel;
		for(String username:recList){
			ssmodel=CharacterHelper.ALL_PC_USER.get(username);
			RequestAllCommand sendCommand =new RequestAllCommand(cmd);
			BaseSocketModel smodel=new BaseSocketModel();
			String guid=UUID.randomUUID().toString();
			smodel.setGuid(guid);

			List nlist=new ArrayList<>();
			nlist.add(model);
			smodel.setList(nlist);

			sendCommand.setInfo(FastJsonUtils.toJson(smodel));

			if(ssmodel!=null){
				sendCommandC(sendCommand,ssmodel.getChannel());
			}else{
				//存离线
				RedisUtil.hset(CharacterHelper.HISTORY+username,username, cmd+"@"+new Date().getTime()+"@"+sendCommand.getInfo());
			}
		}
	}

	

	public void sendToDeviceGetCode(NumInfoModel model,int orgid){
		ReauestLoginDto ssmodel;

		for(String m:CharacterHelper.ALL_LOGIN_USER.keySet()){
			ssmodel=CharacterHelper.ALL_LOGIN_USER.get(m);

			if(ssmodel.getType()== WebConstant.GETCODE&&ssmodel.getSchoolid()==orgid){
				RequestAllCommand sendCommand =new RequestAllCommand(Command.COMMAND_GET_CODE);
				BaseSocketModel smodel=new BaseSocketModel();
				String guid=UUID.randomUUID().toString();
				smodel.setGuid(guid);

				List<NumInfoModel> nlist=new ArrayList<>();
				nlist.add(model);
				smodel.setList(nlist);

				sendCommand.setInfo(FastJsonUtils.toJson(smodel));
				sendCommandC(sendCommand,ssmodel.getChannel());
			}
		}
	}

	public void sendNumToBroardCast(String type,NumInfoModel model,int cmd,int orgid){
		PcLoginModel ssmodel;

		for(String m:CharacterHelper.ALL_PC_USER.keySet()){
			ssmodel=CharacterHelper.ALL_PC_USER.get(m);
			//通用的取号业务
			if(ssmodel.getWintype().contains(type)&&ssmodel.getSchoolid()==orgid){

				RequestAllCommand sendCommand =new RequestAllCommand(cmd);
				BaseSocketModel smodel=new BaseSocketModel();
				String guid=UUID.randomUUID().toString();
				smodel.setGuid(guid);

				List<NumInfoModel> nlist=new ArrayList<>();
				nlist.add(model);
				smodel.setList(nlist);

				sendCommand.setInfo(FastJsonUtils.toJson(smodel));
				sendCommandC(sendCommand,ssmodel.getChannel());
			}
		}
	}




	public void sendToBigData(String type,int cmd,String name){
		RequestAllCommand sendCommand =new RequestAllCommand(cmd);
		Map<String,Object> map=new HashMap<>();
		map.put("type",type);
		map.put("name",name);
		sendCommand.setInfo(FastJsonUtils.toJson(map));

		for(ReauestLoginDto dmodel:CharacterHelper.ALL_BIG_DATA_USER.values()){
			sendCommandC(sendCommand,dmodel.getChannel());
		}
	}

	public void sendStopToBigData(String winid,Boolean isstop,int cmd){

		if(CharacterHelper.ALL_PC_USER!=null){
			PcLoginModel model;
			for(String m: CharacterHelper.ALL_PC_USER.keySet()){
				model=CharacterHelper.ALL_PC_USER.get(m);
				if(winid.equals(model.getWinid())){

					if(isstop){
						CharacterHelper.ALL_PC_USER.get(m).setState("2");
					}else{
						CharacterHelper.ALL_PC_USER.get(m).setState("1");
					}
					break;
				}
			}
		}

		RequestAllCommand sendCommand =new RequestAllCommand(cmd);
		Map<String,Object> map=new HashMap<>();
		map.put("winid",winid);
		map.put("isstop",isstop);
		sendCommand.setInfo(FastJsonUtils.toJson(map));

		for(ReauestLoginDto dmodel:CharacterHelper.ALL_BIG_DATA_USER.values()){
			sendCommandC(sendCommand,dmodel.getChannel());
		}
	}

	public  void sendCommandToDevice(int cmd,Object obj,String deviceid) {
			
			// 获取客户端发送来的Command,根据不同的命令进行相应的逻辑处理
			logger.info("+++++++++++++++++++++command is :"+cmd);
			try {
				
				//------------start 组成消息体---------------------------------
				RequestAllCommand noticecmd = new RequestAllCommand(cmd);
				
				BaseSocketModel smodel=new BaseSocketModel();
				String guid=UUID.randomUUID().toString();
				smodel.setGuid(guid);
				smodel.setReceivetime(new Date());
				
				if(obj!=null){
					//发送给屏幕端
					List<Object> listdata=new ArrayList<Object>();
					listdata.add(obj);
					smodel.setList(listdata);
				}
				
				String sendjson=FastJsonUtils.toJson(smodel);
				
				noticecmd.setInfo(sendjson);
				//------------end 组成消息体---------------------------------
				//-------------------开始发送-----------------------------------------
				
//				RedisUtil.hset(CharacterHelper.HISTORY+deviceid,guid, cmd+"@"+sendjson);//同时保留一根数据在最近的Map中
				
				if(CharacterHelper.ALL_LOGIN_USER!=null){
					
					if(CharacterHelper.ALL_LOGIN_USER.containsKey(deviceid)){
						sendCommandC(noticecmd, CharacterHelper.ALL_LOGIN_USER.get(deviceid).getChannel());
					}
				}
				
			} catch (IllegalArgumentException ex) {
				logger.error("命令格式不正确, 请求命令中不包含此命令!", ex.getCause());
			}
	}

	private Boolean isContains(String a,String b){

		if(StringUtils.isBlank(a)||StringUtils.isBlank(b)){
			return false;
		}

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

//		String[] as=a.split(",");
//		for(String aa:as){
//			if(b.contains(aa)){
//				return true;
//			}
//		}
//		return false;
	}
}
