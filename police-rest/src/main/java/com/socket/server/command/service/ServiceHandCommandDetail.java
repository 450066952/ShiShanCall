package com.socket.server.command.service;

import com.inch.model.*;
import com.inch.rest.action.ws.Caller;
import com.inch.rest.action.ws.WsService;
import com.inch.rest.service.*;
import com.inch.rest.utils.RedisUtil;
import com.inch.rest.utils.SpringUtil;
import com.inch.utils.CommonUtil;
import com.inch.utils.FastJsonUtils;
import com.inch.utils.WebConstant;
import com.socket.server.command.dto.LoginResultDto;
import com.socket.server.command.dto.PcLoginModel;
import com.socket.server.command.dto.ReauestLoginDto;
import com.socket.server.command.web.RequestAllCommand;
import com.socket.server.socket.pub.CharacterHelper;
import com.socket.server.socket.pub.Command;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.log4j.Logger;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.MessageEvent;
import org.springframework.context.ApplicationContext;

import java.util.*;


public class ServiceHandCommandDetail extends ServerCommandSendHelper  {

	private static final Logger logger = Logger.getLogger(ServiceHandCommandDetail.class);


	public void getWindowState(MessageEvent e){
		ApplicationContext ac = SpringUtil.getApplicationContext();
		WindowService<WindowModel> windowService=(WindowService<WindowModel>)ac.getBean("windowService");
		NumInfoService nservice =(NumInfoService)ac.getBean("numInfoService");
		WindowModel model=new WindowModel();
		Map<String,Object> map=windowService.queryByList2(model);

		List<WinStateModel> wlist=new ArrayList<>();

		if(map!=null){
			List<WindowModel> list=(List<WindowModel>)map.get("data");
			List<Map<String,String>> smap=nservice.getSign();

			PcLoginModel eachuser;
			for(WindowModel wmodel:list){

				WinStateModel nmodel=new WinStateModel();
				nmodel.setGuid(wmodel.getGuid());
				nmodel.setTypename(wmodel.getTypename());
				nmodel.setName(wmodel.getName());
				eachuser=getPcUser(wmodel.getGuid());

				if(eachuser!=null){
					nmodel.setPersonname(eachuser.getName());
					nmodel.setSigntime(getSignTime(smap,eachuser.getUserid()));
					nmodel.setState(eachuser.getState());
				}
				wlist.add(nmodel);
			}
		}

		RequestAllCommand cmd=new RequestAllCommand(Command.COMMAND_WIN_STATE);
		cmd.setInfo(FastJsonUtils.toJson(wlist));

		this.sendCommand(cmd,e);


	}

	public String getSignTime(List<Map<String,String>>  list,String userid){

		String s="";
		for (Map<String,String> map:list) {
			if(userid.equals(map.get("userid"))){
				s=map.get("addtime");
				break;
			}
		}
		return s;
	}



	public void big_Data_Login(Command command,MessageEvent e){

		RequestAllCommand loginC = (RequestAllCommand) command;
		ReauestLoginDto dto=FastJsonUtils.toObject(loginC.getInfo(), ReauestLoginDto.class);
		dto.setChannel(e.getChannel());
		CharacterHelper.ALL_BIG_DATA_USER.put(dto.getGuid(),dto);


		//判断版本
		ApplicationContext ac = SpringUtil.getApplicationContext();
		GetInfoBySNService vservice =(GetInfoBySNService)ac.getBean("getInfoBySNService");
		List<VersionModel> listVersion=vservice.getVersionBySN("4");
		if(listVersion!=null&&listVersion.size()>0){
			if(!listVersion.get(0).getVersion().equals(dto.getVersion())){
				sendMsg(listVersion,Command.COMMAND_UPGRADED,"",e);
				return;
			}
		}

		//今日办件数量
		Map<String,Object> qmap=new HashMap<>();
		qmap.put("orgid",0);
		StatisticsService statisticsService =(StatisticsService)ac.getBean("statisticsService");
		StatisticsModel model=statisticsService.queryByToday(qmap);
		model.setFlag("1");

		//今日趋势
		List<StatisticsModel> tlist=statisticsService.queryByDayType(model);
		//排队人数
		List<Map<String,Object>> wlist=statisticsService.queryWaitPerson(0);
		List<Map<String,Object>> notSatisfy=statisticsService.getNotSatisfy();

		BasicInfoService<BasicInfoModel> basicInfoService =(BasicInfoService<BasicInfoModel>)ac.getBean("basicInfoService");
		BasicInfoModel c=basicInfoService.queryById("");

		Map<String,Object> map=new HashMap<>();
		map.put("today",model);
		map.put("todayDetial",tlist);
		map.put("waitCnt",wlist);
		map.put("notSatisfy",notSatisfy);
		map.put("basic",c);

		loginC.command=Command.COMMAND_DATA_USER_LOGIN_RESULT;
		loginC.setInfo(FastJsonUtils.toJson(map));
		this.sendCommand(loginC, e);
	}

	public void pcLogin(Command command,MessageEvent e){
		ApplicationContext ac = SpringUtil.getApplicationContext();
		UserinfoService userService =(UserinfoService)ac.getBean("userinfoService");
		RequestAllCommand loginC = (RequestAllCommand) command;
		PcLoginModel dto=FastJsonUtils.toObject(loginC.getInfo(), PcLoginModel.class);

		PcLoginModel puser =getPcUser(dto.getWinid());
		if(puser!=null){
			LoginResultDto otherdto=new LoginResultDto();
			otherdto.setSuccess("false");

			otherdto.setMsg("当前窗口已经被【"+puser.getName()+"】登录！");
			List<LoginResultDto> olist=new ArrayList<>();
			olist.add(otherdto);
			sendMsg(olist,Command.COMMAND_WINDOW_HAS_LOGIN,"",e);
			return;
		}

		if(CharacterHelper.ALL_PC_USER.containsKey(dto.getUsername())){
			LoginResultDto otherdto=new LoginResultDto();
			otherdto.setSuccess("false");
			PcLoginModel pmodel=CharacterHelper.ALL_PC_USER.get(dto.getUsername());
			otherdto.setMsg("您的账号在【"+pmodel.getWinname()+"】窗口登录！");
			List<LoginResultDto> olist=new ArrayList<>();
			olist.add(otherdto);
			sendMsg(olist,Command.COMMAND_OTHER_LOGIN,"",e);
			return;
		}

		GetInfoBySNService vservice =(GetInfoBySNService)ac.getBean("getInfoBySNService");
		List<VersionModel> listVersion=vservice.getVersionBySN("3");
		if(listVersion!=null&&listVersion.size()>0){
			if(!listVersion.get(0).getVersion().equals(dto.getVersion())){
				sendMsg(listVersion,Command.COMMAND_UPGRADED,"",e);
				return;
			}
		}

		SysUser user=userService.queryPcLogin(dto.getUsername(),dto.getPassword());

		Boolean islogin=false;
		LoginResultDto retdto=new LoginResultDto();
		if(user!=null&&StringUtils.isNotBlank(user.getGuid())){
			retdto.setIsNeedUpdate("false");
			retdto.setSuccess("true");
			retdto.setName(user.getName());
			retdto.setUserid(user.getGuid());
			retdto.setMsg("登录成功！");

			dto.setChannel(e.getChannel());
			dto.setPic(user.getPic());
			dto.setMember(user.getMember());
			dto.setNo(user.getNo());
			dto.setStar(user.getStar());
			dto.setUserid(user.getGuid());
			dto.setName(user.getName());
			dto.setState("1");
			dto.setSchoolname(user.getSchoolname());
			dto.setSchoolid(user.getSchoolid());
			dto.setCourse(user.getCourse());
			CharacterHelper.ALL_PC_USER.put(user.getUsername(),dto);

			sendPersonToPad(dto);
			islogin=true;

		}else{
			retdto.setIsNeedUpdate("false");
			retdto.setSuccess("false");
			retdto.setMsg("用户名或者密码不正确！");
		}

		//返回用户登录信息
		loginC.command=Command.COMMAND_USER_LOGIN_RESULT;
		loginC.setInfo(FastJsonUtils.toJson(retdto));
		this.sendCommand(loginC, e);

		if(islogin){
			NumInfoService<NumInfoModel> service =(NumInfoService<NumInfoModel>)ac.getBean("numInfoService");
			NumInfoModel model=new NumInfoModel();
			model.setType(dto.getWintype());

			try {
				List<Map<String,Object>> list=service.queryByList2(model.getType(),user.getSchoolid(),dto.getFirsttype());
				if(list!=null&&list.size()>0){
					sendMsg(list,Command.COMMAND_NUM_LIST,"",e);
				}
				service.addSign(dto.getUserid(),dto.getSchoolid());
			}
			catch (Exception ex){
				ex.printStackTrace();
			}

			//通知大数据(pc登录)
			RequestAllCommand rcmd=new RequestAllCommand(Command.COMMAND_PC_LOGIN);
			WinStateModel nmodel=new WinStateModel();
			nmodel.setGuid(dto.getWinid());
			nmodel.setTypename(dto.getWintype());
			nmodel.setName(dto.getWinname());
			nmodel.setPersonname(dto.getName());
			nmodel.setSigntime(CommonUtil.nowHM());
			nmodel.setState(dto.getState());
			rcmd.setInfo(FastJsonUtils.toJson(nmodel));

			sendToDataUser(rcmd);
		}
	}

	public void sendToDataUser(RequestAllCommand rcmd){
		for(ReauestLoginDto sdto: CharacterHelper.ALL_BIG_DATA_USER.values()){
			this.sendCommandC(rcmd, sdto.getChannel());
		}
	}

	public void LogonDetail(Command command, MessageEvent e){

		Boolean islogin=false;
		RequestAllCommand loginC = (RequestAllCommand) command;
		ReauestLoginDto dto=FastJsonUtils.toObject(loginC.getInfo(), ReauestLoginDto.class);
		LcdModel redisLcdModel =(LcdModel)RedisUtil.getObjByKey(WebConstant.SCREENUSER+dto.getGuid());
		
		ApplicationContext ac = SpringUtil.getApplicationContext();
		ReauestLoginDto user=null;
		
//		GetInfoBySNService service =(GetInfoBySNService)ac.getBean("getInfoBySNService");
//		List<VersionModel> listVersion=service.getVersionBySN(dto.getType()+"");
//		if(listVersion!=null&&listVersion.size()>0){
//			if(!listVersion.get(0).getVersion().equals(dto.getVersion())){
//				sendMsg(listVersion,Command.COMMAND_UPGRADED,dto.getGuid(),e);
//				return;
//			}
//		}

		if(redisLcdModel==null){
			LcdService<LcdModel> lcdService =(LcdService<LcdModel>)ac.getBean("lcdService");
			LcdModel loginUser=lcdService.queryById(dto.getGuid()) ;
			
			if(loginUser!=null&&loginUser.getGuid().length()>0){
				
				user=new ReauestLoginDto();
				
				user.setChannel(e.getChannel());
				user.setGuid(loginUser.getGuid());
				user.setVersion(dto.getVersion());
				user.setModel(loginUser.getModel());
				user.setType(dto.getType());
				user.setWinguid(loginUser.getWinguid());
				user.setIsvoice(loginUser.getIsvoice());
				user.setSchoolid(loginUser.getSchoolid());

				CharacterHelper.ALL_LOGIN_USER.put(user.getGuid(),user);
				RedisUtil.setObjectByTime(WebConstant.SCREENUSER+user.getGuid(), loginUser, 30*60*60);
				islogin=true;
			}
		}else{
			islogin=true;
			user=new ReauestLoginDto();
			
			user.setChannel(e.getChannel());
			user.setGuid(redisLcdModel.getGuid());
			user.setVersion(dto.getVersion());
			user.setModel(redisLcdModel.getModel());
			user.setType(dto.getType());
			user.setWinguid(redisLcdModel.getWinguid());
			user.setIsvoice(redisLcdModel.getIsvoice());
			user.setSchoolid(redisLcdModel.getSchoolid());
			
			CharacterHelper.ALL_LOGIN_USER.put(redisLcdModel.getGuid(),user);
		}
		LoginResultDto retdto=new LoginResultDto();
		
		if(islogin){
			if(user.getType()==WebConstant.PAD){
				PcLoginModel eachuser=getPcUser(user.getWinguid());
				if(eachuser!=null){
					List<PcLoginModel> list=new ArrayList<>();

					PcLoginModel mm=new PcLoginModel();
					mm.setTypename(eachuser.getTypename());
					mm.setName(eachuser.getName());
					mm.setStar(eachuser.getStar());
					mm.setMember(eachuser.getMember());
					mm.setNo(eachuser.getNo());
					mm.setPic(eachuser.getPic());
					mm.setUsername(eachuser.getUsername());
					mm.setWinid(eachuser.getWinid());
					mm.setUserid(eachuser.getUserid());
					mm.setCourse(eachuser.getCourse());
					mm.setWinname(StringUtils.substringBeforeLast(eachuser.getWinname(),"-"));
					list.add(mm);
					sendMsg(list,Command.COMMAND_SEND_STAFF,"",e);
				}
			}

			retdto.setIsNeedUpdate("false");
			retdto.setSuccess("true");
			retdto.setGuid(dto.getGuid());
			retdto.setMsg("登录成功！");
			retdto.setModel(user.getModel());
			retdto.setIsvoice(user.getIsvoice());

			BasicInfoService<BasicInfoModel> basicInfoService =(BasicInfoService<BasicInfoModel>)ac.getBean("basicInfoService");
			BasicInfoModel c=basicInfoService.queryById("");
			retdto.setName_cn(c.getName_cn());
			retdto.setName_en(c.getName_en());
			retdto.setTel(c.getTel());
			retdto.setQrcode(c.getQrcode());
			retdto.setWelcome(c.getWelcome());
		}else{
			retdto.setIsNeedUpdate("false");
			retdto.setSuccess("false");
			retdto.setGuid(dto.getGuid());
			retdto.setMsg("设备序列号不正确！");
		}
		
		loginC.command=Command.COMMAND_USER_LOGIN_RESULT;
		loginC.setInfo(FastJsonUtils.toJson(retdto));
		this.sendCommand(loginC, e);
	}

	/**
	 * 通知对应的评价器
	 */
	public void sendPersonToPad(PcLoginModel pcmodel){
		ReauestLoginDto displayUser;
		if(StringUtils.isNotBlank(pcmodel.getWinid())){

			for(String m: CharacterHelper.ALL_LOGIN_USER.keySet()){
				displayUser=CharacterHelper.ALL_LOGIN_USER.get(m);

				if(displayUser.getType()==WebConstant.PAD&&pcmodel.getWinid().equals(displayUser.getWinguid())){

					List<PcLoginModel> list=new ArrayList<>();

					PcLoginModel mm=new PcLoginModel();
					mm.setTypename(pcmodel.getTypename());
					mm.setName(pcmodel.getName());
					mm.setStar(pcmodel.getStar());
					mm.setMember(pcmodel.getMember());
					mm.setNo(pcmodel.getNo());
					mm.setPic(pcmodel.getPic());
					mm.setUsername(pcmodel.getUsername());
					mm.setWinid(pcmodel.getWinid());
					mm.setUserid(pcmodel.getUserid());
					mm.setCourse(pcmodel.getCourse());
//					mm.setWinname(pcmodel.getWinname());
					mm.setWinname(StringUtils.substringBeforeLast(pcmodel.getWinname(),"-"));
					list.add(mm);

					sendMsgCh(list,Command.COMMAND_SEND_STAFF,displayUser.getChannel());

					break;
				}
			}
		}
	}


	public void notifySendOK(Command command, MessageEvent e){
	}


	//获取评价器评价结果
	public void receiveEva(Command command,MessageEvent e){

		RequestAllCommand commandok=(RequestAllCommand)command;
		EvaModel model=FastJsonUtils.toObject(commandok.getInfo(),EvaModel.class);

		this.sendCommand(commandok, e);

		ApplicationContext ac = SpringUtil.getApplicationContext();
		NumInfoService service =(NumInfoService)ac.getBean("numInfoService");

		//找出winid----
		PcLoginModel currentmodel=null;
		for(PcLoginModel m:CharacterHelper.ALL_PC_USER.values()){
			if(model.getUserid().equals(m.getUserid())){
				model.setWinid(m.getWinid());
				model.setOrgid(m.getSchoolid());
				model.setName(m.getName());

				currentmodel=m;
				break;
			}
		}

		//未评价时，默认为非常满意
		if(model.getScore()==0){
			model.setScore(5);
		}

		service.addEva(model);

		if(model!=null&&model.getOrgid()==1){
			new Thread(() -> {
				try{
					//---------------苏州对接-----------------
					Caller of=new Caller();
//					of.setQueueNO(model.getWinname());
//					of.setWindowNO(model.getWinname());
					of.setQueueNO("101");
					of.setWindowNO("101");
					of.setNumber(model.getNum());
					of.setDateTimer(CommonUtil.now());

//					System.out.println(""FastJsonUtils.toJson(of));
					logger.info("---end---"+FastJsonUtils.toJson(of));
					WsService.Complete(of);
					//---------------苏州对接-----------------
				}catch (Exception ex){
					ex.printStackTrace();
				}
			}).start();
		}

		if(model.getScore()==1){
//			PcLoginModel currentmodel=null;
//			for(String m: CharacterHelper.ALL_PC_USER.keySet()){
//				PcLoginModel umodel=CharacterHelper.ALL_PC_USER.get(m);
//				if(model.getUserid().equals(umodel.getUserid())){
//					currentmodel=umodel;
//					break;
//				}
//			}

			if(currentmodel!=null){
				RequestAllCommand cmmond=new RequestAllCommand(Command.COMMAND_REC_NOT_SATISFY);
				Map<String,String> map=new HashMap<>();
				map.put("name",currentmodel.getName());
				map.put("pic",currentmodel.getPic());
				map.put("winname",currentmodel.getWinname());
				map.put("addtime", CommonUtil.nowHM());
				map.put("msg","服务评价不满意");
				cmmond.setInfo(FastJsonUtils.toJson(map));

				for(ReauestLoginDto dto: CharacterHelper.ALL_BIG_DATA_USER.values()){
					this.sendCommandC(cmmond, dto.getChannel());
				}
			}
		}
		for(ReauestLoginDto dto: CharacterHelper.ALL_BIG_DATA_USER.values()){

			RequestAllCommand cmmond=new RequestAllCommand(Command.COMMAND_REC_EVA);
			Map<String,Object> map=new HashMap<>();
			map.put("score",model.getScore());
			cmmond.setInfo(FastJsonUtils.toJson(map));
			this.sendCommandC(cmmond, dto.getChannel());
		}
	}

	public void GetHistoryMsgDetail(Command command, MessageEvent e){

		RequestAllCommand commandHS=(RequestAllCommand)command;
		PcLoginModel dto=FastJsonUtils.toObject(commandHS.getInfo(), PcLoginModel.class);

		Map<String, String> map=RedisUtil.hgetAll(CharacterHelper.HISTORY+dto.getUsername());

		if(map!=null&&!map.isEmpty()){

			String jsonstr="";

			List<SortModel> list=new ArrayList<SortModel>();

			for(String key:map.keySet()){

				jsonstr=map.get(key);

				String cmd=StringUtils.substringBefore(jsonstr, "@");
				String rettime=StringUtils.substringBetween(jsonstr, "@");
				String sendjson=StringUtils.substringAfterLast(jsonstr, "@");

				SortModel gdto=new SortModel();
				gdto.setCmd(NumberUtils.toInt(cmd));
				if(!StringUtils.isBlank(rettime)){
					gdto.setRectime(new Date(NumberUtils.toLong(rettime)));
				}
				gdto.setJson(sendjson);
				list.add(gdto);
			}

			Collections.sort(list, (arg0, arg1) -> {
				if(arg0.getRectime()==null||arg1.getRectime()==null){
					return 1;
				}

				return arg0.getRectime().compareTo(arg1.getRectime());
			});

			SortModel gdto=null;
			for(int i=0;i<list.size();i++){
				gdto=list.get(i);
				RequestAllCommand noticecmd = new RequestAllCommand(gdto.getCmd(),gdto.getRectime());
				noticecmd.setInfo(gdto.getJson());
				this.sendCommand(noticecmd, e);
			}
		}
	}




public void initLodaData(Command command, MessageEvent e){
	
	ApplicationContext ac = SpringUtil.getApplicationContext();
	GetInfoBySNService service =(GetInfoBySNService)ac.getBean("getInfoBySNService");
	RequestAllCommand commandok=(RequestAllCommand)command;//获取guid
	OkModel dto=FastJsonUtils.toObject(commandok.getInfo(), OkModel.class);

	//返回通知信息
	List<NoticeModel> noticelist=service.getNoticeBySN(dto.getGuid());
	if(noticelist!=null&&noticelist.size()>0){
		sendMsg(noticelist,Command.COMMAND_NOTICE,dto.getGuid(),e);
	}

	//----返回【宣传照片】信息-------------------
	List<GalleryModel> plist=service.getPicBySN(dto.getGuid());
	if(plist!=null&&plist.size()>0){
		
		sendMsg(plist,Command.COMMAND_PIC_PHOTO,dto.getGuid(),e);
	}
	
	//----返回【欢迎词】信息-------------------
	List<WelcomeModel> wlist=service.getWelcomeBySN(dto.getGuid());
	if(wlist!=null&&wlist.size()>0){
		
		sendMsg(wlist,Command.COMMAND_WELCOME,dto.getGuid(),e);
	}
	
	//----返回【校园基本信息】信息-------------------
	List<SchoolModel> ilist=service.getSchoolInfoBySN(dto.getGuid());
	if(ilist!=null&&ilist.size()>0){
		
		sendMsg(ilist,Command.COMMAND_SCHOOL_INFO,dto.getGuid(),e);
	}
	
	//----返回【视频】信息-------------------
	List<VideoModel> vlist=service.getVideoNoticeBySN(dto.getGuid());
	if(vlist!=null&&vlist.size()>0){
		
		sendMsg(vlist,Command.COMMAND_VIDEO,dto.getGuid(),e);
	}

	//----返回【屏显对应窗口信息】-------------------
	List<WindowModel> winlist=service.getWindowBySN(dto.getGuid());
	if(winlist!=null&&winlist.size()>0){

		sendMsg(winlist,Command.COMMAND_SOCOAL_GET_WINDOW,dto.getGuid(),e);
	}

	RedisUtil.del(CharacterHelper.HISTORY+dto.getGuid());
}

	/**
	 * 通过管道获取用户名
	 * @param ch
	 * @return
	 */
	public Map<String,String> getUsernameByChannel(Channel ch){
		String username="";

		Map<String,String> map=new HashMap<>();

		if(CharacterHelper.ALL_LOGIN_USER!=null){
			//遍历MAP
			for(String m: CharacterHelper.ALL_LOGIN_USER.keySet()){
				if(CharacterHelper.ALL_LOGIN_USER.get(m).getChannel().getId()==ch.getId()){
					username=m;
					map.put("loginuser",username);
					return map;
				}
			}
		}

		if(CharacterHelper.ALL_PC_USER!=null){
			//遍历MAP
			for(String m: CharacterHelper.ALL_PC_USER.keySet()){
				if(CharacterHelper.ALL_PC_USER.get(m).getChannel().getId()==ch.getId()){
					username=m;
					map.put("pcuser",username);
					return map;
				}
			}
		}

		if(CharacterHelper.ALL_BIG_DATA_USER!=null){
			//遍历MAP
			for(String m: CharacterHelper.ALL_BIG_DATA_USER.keySet()){
				if(CharacterHelper.ALL_BIG_DATA_USER.get(m).getChannel().getId()==ch.getId()){
					username=m;
					map.put("datauser",username);
					return map;
				}
			}
		}

		return map;
	}


	private PcLoginModel getPcUser(String winguid){

		if(CharacterHelper.ALL_PC_USER!=null){
			//遍历MAP
			PcLoginModel model;
			for(String m: CharacterHelper.ALL_PC_USER.keySet()){
				model=CharacterHelper.ALL_PC_USER.get(m);
				if(winguid.equals(model.getWinid())){
					return model;
				}
			}
		}
		return null;
	}

	public void  sendMsg(List list ,int cmd,String sn,MessageEvent e) {
		this.sendCommand(getCommand(list,cmd), e);
	}

	public void  sendMsgCh(List list ,int cmd,Channel ch) {
		this.sendCommandC(getCommand(list,cmd), ch);
	}

	public RequestAllCommand getCommand(List list ,int cmd){
		RequestAllCommand noticecmd = new RequestAllCommand(cmd);

		BaseSocketModel smodel=new BaseSocketModel();
		String guid=UUID.randomUUID().toString();

		smodel.setGuid(guid);
		smodel.setList(list);
		smodel.setReceivetime(new Date());

		String sendjson=FastJsonUtils.toJson(smodel);
		noticecmd.setInfo(sendjson);
		return noticecmd;
	}
}


