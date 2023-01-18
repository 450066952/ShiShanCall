import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.inch.model.SysDicCodeModel;
import com.inch.model.SysDicModel;
import com.inch.rest.service.NumInfoService;
import com.inch.rest.service.SysDicService;
import com.inch.rest.utils.MessageUtils;
import com.inch.utils.FastJsonUtils;
import com.inch.utils.HttpClientUtil;
import org.apache.commons.lang.StringUtils;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.IOException;
import java.security.SecureRandom;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class test {

    @Test
    public void test1(){
        //获取配置信息，从中获取bean
        ApplicationContext context = new ClassPathXmlApplicationContext("spring/applicationContext.xml");
        //从spring配置文件中获取bean
        SysDicService sysDicService = (SysDicService) context.getBean("sysDicService");

        SysDicModel model = new SysDicModel();
        model.setOrgid(58);

        List<SysDicCodeModel> codeList = sysDicService.getDicCode(model);
        List<SysDicModel> dicList = sysDicService.getDicList(model);

        for (int i = 0; i < codeList.size(); i++) {
            for (int j = 0; j < dicList.size(); j++) {
                if (dicList.get(j).getRootid().equals(codeList.get(i).getRootid())){

                    System.out.println(codeList.get(i).getName() + "===" + dicList.get(j).getName());

                }
            }
        }

        for (int i = 0; i < dicList.size(); i++) {
            for (int j = 0; j < dicList.size(); j++){

            }
        }

    }

    @Test
    public void test2(){
        for (int i = 0; i < 10; i++) {
            System.out.println(UUID.randomUUID().toString());
        }
    }


    /**
     * 从区里面获取所有事项数据
     * 把获取回来的guid作为本地事项guid
     *
     * */
    @Test
    public void test03(){

        //获取配置信息，从中获取bean
        ApplicationContext context = new ClassPathXmlApplicationContext("spring/applicationContext.xml");
        //从spring配置文件中获取bean
        SysDicService sysDicService = (SysDicService) context.getBean("sysDicService");

        String url = "https://www.sndzwfw.com/apijava/app/queue/things";
        Map<String,Object> map=new HashMap<>();
        map.put("qtId","be7f3c1f-b415-4158-9fe5-69becc7d3808");
        map.put("page",1);
        map.put("size",300);

        String json= HttpClientUtil.doPostJson(url, FastJsonUtils.toJson(map),null);

        JSONObject jsonObject = JSONObject.parseObject(json);

        if ("200".equals(jsonObject.getString("code"))){
            JSONObject result = JSONObject.parseObject(jsonObject.getString("result"));
            String item = result.getString("items");
            List<Map> items = JSONArray.parseArray(item, Map.class);
            List<Map> datas = JSONArray.parseArray(items.get(0).get("data").toString(), Map.class);
            String nameNo = "";
            for (int i = 0; i < datas.size(); i++) {
                SysDicModel dicModel = new SysDicModel();
                String name = datas.get(i).get("taskName") + "";
                String guid = datas.get(i).get("itemId") + "";
                dicModel.setOrgid(58);
                dicModel.setGuid(guid);
                dicModel.setName(name);
                dicModel.setPaixu(i + 1);
                try {
//                    sysDicService.updateGuid(name, guid);
//                    sysDicService.add(dicModel);
                    sysDicService.addThings(dicModel);

//                    SysDicModel dicModel1 = sysDicService.getByDicName2(name);
//                    if (dicModel1 == null){
//                        System.out.println(guid +"---" + name);
//                        nameNo += guid + "--" + name + ",";
//                    }

                }catch (Exception e) {
                    e.printStackTrace();
                }
            }
            System.out.println("================start====================");
            System.out.println(nameNo);
            System.out.println("================end====================");
        }
    }

    @Test
    public void initNumber(){
        //获取配置信息，从中获取bean
        ApplicationContext context = new ClassPathXmlApplicationContext("spring/applicationContext.xml");
        //从spring配置文件中获取bean
        NumInfoService lcds=(NumInfoService)context.getBean("numInfoService");
        try{
            lcds.copyNumData();
        }catch (Exception e){
            e.printStackTrace();
        }

        try{
            lcds.updateStartNum();
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    @Test
    public void testDic(){
        //获取配置信息，从中获取bean
        ApplicationContext context = new ClassPathXmlApplicationContext("spring/applicationContext.xml");
        //从spring配置文件中获取bean
        SysDicService sysDicService = (SysDicService) context.getBean("sysDicService");
        String pid = "1b935699-cade-465b-82d3-c36745946d86";
        List<SysDicModel> dicModels = sysDicService.getByDicPid(pid.split(","));

        String firsttype = "";
        for (int i = 0; i < dicModels.size(); i++) {
            String guid = dicModels.get(i).getGuid();
            firsttype += guid + ",";
        }
        System.out.println("===================start================================");
        System.out.println(StringUtils.substringBeforeLast(firsttype, ","));
        System.out.println("===================end================================");
    }

    @Test
    public void testDicByRoot(){
        //获取配置信息，从中获取bean
        ApplicationContext context = new ClassPathXmlApplicationContext("spring/applicationContext.xml");
        //从spring配置文件中获取bean
        SysDicService sysDicService = (SysDicService) context.getBean("sysDicService");
        String id = "0f8311dc-e228-4c62-932b-f569315a5099";
        List<SysDicModel> dicModels = sysDicService.getByRootId(id);

        String firsttype = "";
        for (int i = 0; i < dicModels.size(); i++) {
            String guid = dicModels.get(i).getGuid();
            firsttype += guid + ",";
        }
        System.out.println("===================start================================");
        System.out.println(StringUtils.substringBeforeLast(firsttype, ","));
        System.out.println("===================end================================");
    }


    @Test
    public void testGuid(){
        for (int i = 0; i < 10; i++) {
            System.out.println(UUID.randomUUID().toString());
        }
    }

    @Test
    public void testMsg(){
        String content2 = "您办理的【横塘户口迁移审批（市内迁移）】业务已经成功取号，排队号：SA011，办理窗口：4，5号，本业务等候人数：12人，请注意叫号语音提示或短信通知，狮山横塘便民服务中心为您服务。";
        String content = " You have successfully enrolled in the waiting line for [代开普票]. Your queue number is SA011. The processing window will be No. 4,5,6,7. There are currently 12 people waiting for this service. Please pay attention to the voice prompt or SMS notification. Shishan Hengtang Convenience Service Center at your service.";

        try {
            MessageUtils.sendNorMsg("18806134876",content,"");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void testTime(){
        String time = "2023-01-12 00:00:00";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date();
            for (int i = 0; i < 1318; i++) {
                date.setTime(date.getTime() + 1000);
                System.out.println(sdf.format(date));
            }

    }

    @Test
    public void dailyCheck(){
        int[] id = {49653,49652,49650,49649,49593,49592,49523,48985,48984,48983,48982,48981,48980,48979,48978,48977,48975,48974,48973,48972,48971,48970,48969,48968,48967,48966,48965,48964,48963,48962,48961,48960,48956,48957,48958,48959,48955,48954,48953,48952,48951,48949,48950,48947,48856,48313,48279,48275,48274,48272,48271,48154,1};
        for (int i = 0; i < id.length; i++) {
            SecureRandom random = new SecureRandom();

            int num = random.nextInt(100000);

            String formatted = String.format("%05d", num);
            
            System.out.println("INSERT INTO daily_check_config_point VALUES (UUID(), 'd9c8dda7-760d-49fa-aaf8-bb2e8142b91c', '点位1', '2022-9-9 09:47:20', '校园大门', "+ id[i] +", 0, '', 0, '', 1, 3, 0, '"+ formatted +"');");
            System.out.println("INSERT INTO daily_check_config_point VALUES (UUID(), 'd9c8dda7-760d-49fa-aaf8-bb2e8142b91c', '点位2', '2022-9-14 10:37:19', '教学楼', "+ id[i] +", 0, '', 0, '', 1, 3, 0, '"+ formatted +"');");
            System.out.println("INSERT INTO daily_check_config_point VALUES (UUID(), 'd9c8dda7-760d-49fa-aaf8-bb2e8142b91c', '点位5', '2022-9-14 10:37:39', '行政楼', "+ id[i] +", 0, '', 0, '', 1, 3, 0, '"+ formatted +"');");
            System.out.println("INSERT INTO daily_check_config_point VALUES (UUID(), 'd9c8dda7-760d-49fa-aaf8-bb2e8142b91c', '点位8', '2023-1-10 14:48:05', '器材室', "+ id[i] +", 0, '', 0, '', 1, 3, 0, '"+ formatted +"');");
            System.out.println("INSERT INTO daily_check_config_point VALUES (UUID(), 'd9c8dda7-760d-49fa-aaf8-bb2e8142b91c', '点位7', '2023-1-10 14:46:33', '操场', "+ id[i] +", 0, '', 0, '', 1, 3, 0, '"+ formatted +"');");
            System.out.println("INSERT INTO daily_check_config_point VALUES (UUID(), 'd9c8dda7-760d-49fa-aaf8-bb2e8142b91c', '点位3', '2022-9-14 10:37:27', '教学楼2', "+ id[i] +", 0, '', 0, '', 1, 3, 0, '"+ formatted +"');");
            System.out.println("INSERT INTO daily_check_config_point VALUES (UUID(), 'd9c8dda7-760d-49fa-aaf8-bb2e8142b91c', '点位4', '2022-9-14 10:37:33', '食堂', "+ id[i] +", 0, '', 0, '', 1, 3, 0, '"+ formatted +"');");
            System.out.println("INSERT INTO daily_check_config_point VALUES (UUID(), 'd9c8dda7-760d-49fa-aaf8-bb2e8142b91c', '点位6', '2023-1-10 14:46:20', '行政楼2', "+ id[i] +", 0, '', 0, '', 1, 3, 0, '"+ formatted +"');");

        }
    }

}
