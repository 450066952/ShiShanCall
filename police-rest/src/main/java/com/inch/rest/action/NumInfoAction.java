
package com.inch.rest.action;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.inch.interceptor.Auth;
import com.inch.model.InchModel;
import com.inch.model.NumInfoModel;
import com.inch.model.SysDicModel;
import com.inch.model.SysUser;
import com.inch.rest.service.NumInfoService;
import com.inch.rest.service.SysDicService;
import com.inch.rest.utils.MessageUtils;
import com.inch.rest.utils.RedisUtil;
import com.inch.utils.*;
import com.socket.server.command.service.TonyCommandService;
import com.socket.server.socket.pub.Command;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.util.*;

@RestController
@RequestMapping("/num")
public class NumInfoAction extends BaseAction{

	@Autowired
	private NumInfoService<NumInfoModel> numInfoService;
	@Autowired
	private TonyCommandService tonyService;
	@Autowired
	private SysDicService<SysDicModel> sysDicService;

	//取号机取号
	@Auth(verifyLogin=false)
	@RequestMapping("/save")
	public InchModel save(NumInfoModel bean) throws Exception{
		boolean flag = false;
		InchModel model = null;
		Map<String,Object> map=new HashMap<>();

		//取号前先去区里平台获取该时间段是否可以取号
// 		String url = "https://www.sndzwfw.com/apijava/api/queue/get/timeNumber";
//		String itemId = bean.getChilds();
//		map.put("itemId",itemId);
//		map.put("chooseDate",CommonUtil.nowDay());
//		String json= HttpClientUtil.doPostJson(url, FastJsonUtils.toJson(map),null);
//		JSONObject jsonObject = JSONObject.parseObject(json);
//		if ("200".equals(jsonObject.getString("code"))){
//			JSONObject result = JSONObject.parseObject(jsonObject.getString("result"));
//			String item = result.getString("items");
//			List<Map> items = JSONArray.parseArray(item, Map.class);
//			if (items != null && items.size() > 0){
//				for (int i = 0; i < items.size(); i++) {
//					Map<String, String> itemValue = items.get(i);
//					String startTime = CommonUtil.nowDay() + " " + itemValue.get("startTime");
//					String endTime = CommonUtil.nowDay() + " " + itemValue.get("endTime");
//					boolean flagTime = CommonUtil.checkTime(startTime, endTime);
//					if (flagTime){
//						int preOffCount = Integer.parseInt(itemValue.get("preOffCount"));
//						if (preOffCount > 0){
//							flag = true;
//						}else{
//							model= this.failMsg("当前时间段可取号数量为0！");
//							return model;
//						}
//					}
//				}
//			}
//		}else {
//			model= this.failMsg("区里暂无此业务，取号失败！");
//			return model;
//		}
//
//		if (flag){
//			model = queryNumber(bean);
//		}else {
//			model= this.failMsg("当前时间段不可取号！");
//			return model;
//		}

		model = queryNumber(bean);

		return model;
	}

	/**
	 *  增量取号，不用去区里面验证是否可以取号
	 *  取号成功后。通知区里此号是增量取号
	 * */
	@Auth(verifyLogin=false)
	@RequestMapping("/addNumber")
	public InchModel addNumber(NumInfoModel bean) throws Exception{
		InchModel model = queryNumber(bean);
		return model;
	}

	//预约后取号机取号
	@Auth(verifyLogin=false)
	@RequestMapping("/saveOrder")
	public List<InchModel> saveOrder(NumInfoModel bean) throws Exception{
		boolean pass = false;
		Map<String,Object> map=new HashMap<>();
		List<InchModel> inchModels = new ArrayList<>();
		InchModel models = null;
		String url = "https://www.sndzwfw.com/apijava/app/queue/my/queue";
		//通过身份证号码获取用户预约信息
		String idcard = bean.getIdcard();
		map.put("userCard",idcard);
		map.put("page",1);
		map.put("size",10);
		String json= HttpClientUtil.doPostJson(url, FastJsonUtils.toJson(map),null);
		JSONObject jsonObject = JSONObject.parseObject(json);
		if ("200".equals(jsonObject.getString("code"))){
			JSONObject result = JSONObject.parseObject(jsonObject.getString("result"));
			String item = result.getString("items");
			List<Map> items = JSONArray.parseArray(item, Map.class);
			List<Map> datas = JSONArray.parseArray(items.get(0).get("data").toString(), Map.class);
			if (datas != null && datas.size() > 0) {
				for (int i = 0; i < datas.size(); i++) {
					InchModel model = null;
					Map<String,Object> data = datas.get(i);
					if (Integer.parseInt(data.get("bizStatus") + "") == 0){
						//接口暂无返回
						String idCard = data.get("userCard") + "";
						String startTime = data.get("startTime") + "";
						String endTime = data.get("endTime") + "";

						//当前取号时间是否在预约时间范围内，可以提前15分钟取号
						if (CommonUtil.checkTime2(startTime,endTime)){
							//取号
							String itemIdParam = data.get("itemid") + "";
//							String dicName = data.get("taskName") + "";
							SysDicModel modelParam = new SysDicModel();
							modelParam.setGuid(itemIdParam);
							SysDicModel dicModel = sysDicService.getByDicId(modelParam);
							//二级菜单，获取dic_code获取guid作为type
							if (StringUtils.isBlank(dicModel.getPid())){
								SysDicModel codeModel = sysDicService.getByDicRootId(dicModel);
								bean.setType(codeModel.getGuid());
							}else {
								bean.setType(dicModel.getPid());
							}
//							SysDicModel dicModel = sysDicService.getByDicName(dicName);
							if (dicModel != null){
								bean.setChilds(dicModel.getGuid());
								bean.setChildname(dicModel.getName());
								bean.setOrgid(dicModel.getOrgid());
								bean.setOrdertype(1);
								bean.setIdcard(idCard);
								model = queryNumberOrder(bean);
								inchModels.add(model);
							}else{
								models = this.failMsg("业务不存在！");
								inchModels.add(models);
							}
						}else {
							models = this.failMsg("不在预约取号时间范围！");
							inchModels.add(models);
						}
					}else{
						models = this.failMsg("暂无今日预约记录！");
						inchModels.add(models);
					}
				}
			}else{
				models = this.failMsg("暂无今日预约记录！");
				inchModels.add(models);
			}
		}
		return inchModels;
	}

	public InchModel queryNumberOrder(NumInfoModel bean) throws Exception{

		NumInfoModel retmodel;
		//判断是二级业务还在三级业务
		SysDicModel dicModel = sysDicService.getByDicCodeId(bean.getType());
		if (dicModel != null){
			bean.setLevel(2);
		}

		InchModel model;
		bean.setGuid(UUID.randomUUID().toString());
		retmodel =numInfoService.queryNumOrder(bean);

		retmodel.setChilds(bean.getChilds());
		retmodel.setOrgid(bean.getOrgid());
		retmodel.setChildname(bean.getChildname());
		if(retmodel!=null ){

			if(retmodel.getwList()==null||retmodel.getwList().size()==0){
				numInfoService.deleteNum(retmodel.getGuid());
				model= this.failMsg("当前选择业务不能在同一窗口办理，请分别取号。");
				return model;
			}

			tonyService.sendNumToPC(bean.getType(),retmodel,Command.COMMAND_PC_GET_NUM,bean.getOrgid());
			retmodel.setInfoList(null);
			tonyService.sendToDeviceGetCode(retmodel,bean.getOrgid());
			model= this.successData("取号成功",retmodel);

			new Thread(() -> {
				try{
					//通知区里平台号码已经被使用
					numInfoService.sandInfoToThird(bean);
					try {
						numInfoService.insertChildBus(retmodel);
					}catch (Exception e){
						e.printStackTrace();
					}
				}catch (Exception e){
					e.printStackTrace();
				}
			}).start();

		}else{
			model= this.failMsg("当前无号可取");
		}
		return model;
	}

	public InchModel queryNumber(NumInfoModel bean) throws Exception{

		NumInfoModel retmodel;
		//判断是二级业务还在三级业务
		SysDicModel dicModel = sysDicService.getByDicCodeId(bean.getType());
		if (dicModel != null){
			bean.setLevel(2);
		}

		InchModel model;
		bean.setGuid(UUID.randomUUID().toString());
		retmodel =numInfoService.queryNum(bean);

		retmodel.setChilds(bean.getChilds());
		retmodel.setOrgid(bean.getOrgid());
		retmodel.setChildname(bean.getChildname());
		if(retmodel!=null ){

			if(retmodel.getwList()==null||retmodel.getwList().size()==0){
				numInfoService.deleteNum(retmodel.getGuid());
				model= this.failMsg("当前选择业务不能在同一窗口办理，请分别取号。");
				return model;
			}

			tonyService.sendNumToPC(bean.getType(),retmodel,Command.COMMAND_PC_GET_NUM,bean.getOrgid());
			retmodel.setInfoList(null);
			tonyService.sendToDeviceGetCode(retmodel,bean.getOrgid());
			model= this.successData("取号成功",retmodel);

			new Thread(() -> {
				try{
					//扫码取号人发送短信
					if (bean.getAreatype() > 0){
						//扫码取号手机号码部位空的发送短信
						if (StringUtils.isNotBlank(bean.getCardno())){
							//发送短信给取号人
							String childname = retmodel.getChildname();
							String num = retmodel.getNum();
							String wName = "";
							List<String> wList = retmodel.getwList();
							if (wList!= null && wList.size() > 0){
								for (String w : wList){
									wName += w + ",";
								}

								String content = "您等候的【"+ childname +"】业务即将开始办理，您的排队号："+ num +"，请到"+ StringUtils.substringBeforeLast(wName,",") + "号窗口办理，狮山横塘便民服务中心为您服务。";
								//发送短信到取号人手机
								MessageUtils.sendNorMsg(bean.getCardno(),content,"");
							}
						}
					}
							//通知区里平台号码已经被使用
//							numInfoService.sandInfoToThird(bean);
					try {
						numInfoService.insertChildBus(retmodel);
					}catch (Exception e){
						e.printStackTrace();
					}
				}catch (Exception e){
					e.printStackTrace();
				}
			}).start();

		}else{
			model= this.failMsg("当前无号可取");
		}
		return model;
	}

	@Auth(verifyLogin = false)
	@RequestMapping("/queryHistoryInfo")
	public void queryHistoryInfo(NumInfoModel bean, HttpServletResponse response){
		Map<String, Object> context = new HashMap<>();
		context.put(SUCCESS, true);
		context.put("data",numInfoService.queryHistoryInfo(bean));
		HtmlUtil.writerJson(response,context);
	}


	@Auth(verifyLogin = false)
	@RequestMapping("/getNumList")
	public Map<String,Object> getNumList(String username) throws Exception{
		SysUser suser =(SysUser) RedisUtil.getObjByKey(WebConstant.WEBUSER+username);
		NumInfoModel model=new NumInfoModel();
		model.setType(suser.getWintype());
		model.setOrgid(suser.getSchoolid());
		return  numInfoService.queryByList(model);
	}

	@Auth(verifyLogin = false)
	@RequestMapping("/getNextNum")
	public InchModel getId(String wintype,String winname,String winid,int orgid,String firsttype ,String userid,String name) throws Exception{

		NumInfoModel bean  = numInfoService.updateNextNum(wintype,orgid,firsttype,winid,winname,userid,name);
		if(bean  == null){
			return this.failMsg("暂时无取号记录!");
		}
		bean.setCid(bean.getGuid());
		bean.setGuid(winid);
		// add 20190312
		winname= StringUtils.substringBeforeLast(winname,"-");
		bean.setWinname(winname);
		bean.setOrgid(orgid);

		tonyService.sendCommandToServer(Command.COMMAND_SOCOAL_SEND_CALL, bean,winid);
//		tonyService.sendNumToPC(bean.getType(),bean,Command.COMMAND_SOCOAL_SEND_CALL,bean.getOrgid());
		tonyService.sendNumToBroardCast(bean.getType(),bean,Command.COMMAND_SOCOAL_SEND_CALL,bean.getOrgid());
		tonyService.sendToBigData(wintype,Command.COMMAND_SOCOAL_SEND_CALL,"");

		if (StringUtils.isNotBlank(bean.getCardno())){
			//发送短信给当前叫号人
			String content = "您等候的【"+ bean.getChildname() +"】业务已经可办理，您的排队号："+ bean.getNum() +"，请到"+ bean.getWinname() + "号窗口办理，狮山横塘便民服务中心为您服务。";
			//发送短信到取号人手机
			MessageUtils.sendNorMsg(bean.getCardno(),content,"");
		}

		return this.successData("ok",bean);
	}

}
