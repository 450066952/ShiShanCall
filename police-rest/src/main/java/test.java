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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

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
                    sysDicService.updateGuid(name, guid);
//                    sysDicService.add(dicModel);
//                    sysDicService.addThings(dicModel);

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
        String content = "您等候的【失业金申领】业务即将开始办理，您的排队号：A1003，请到3号窗口办理，狮山横塘便民服务中心为您服务。";
        try {
            MessageUtils.sendNorMsg("18806134876",content,"");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


}
