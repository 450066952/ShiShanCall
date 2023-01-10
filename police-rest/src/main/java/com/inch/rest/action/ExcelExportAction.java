package com.inch.rest.action;

import com.inch.interceptor.Auth;
import com.inch.model.User;
import com.inch.rest.utils.ExcelUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/export")
public class ExcelExportAction extends BaseAction{

    @Auth(verifyLogin = false)
    @RequestMapping("/exportExcel")
    public void exportExcel(HttpServletResponse response, HttpServletRequest request) throws Exception{
        // 造几条数据
        List<User> list = new ArrayList<>();
        list.add(new User("唐三藏", "男", 30, "13411111111", "东土大唐", "取西经"));
        list.add(new User("孙悟空", "男", 29, "13411111112", "菩提院", "打妖怪"));
        list.add(new User("猪八戒", "男", 28, "13411111113", "高老庄", "偷懒"));
        list.add(new User("沙悟净", "男", 27, "13411111114", "流沙河", "挑担子"));
        list.add(new User("唐三藏", "男", 30, "13411111111", "东土大唐", "取西经"));
        list.add(new User("孙悟空", "男", 29, "13411111112", "菩提院", "打妖怪"));
        list.add(new User("猪八戒", "男", 28, "13411111113", "高老庄", "偷懒"));
        list.add(new User("沙悟净", "男", 27, "13411111114", "流沙河", "挑担子"));
        list.add(new User("唐三藏", "男", 30, "13411111111", "东土大唐", "取西经"));
        list.add(new User("孙悟空", "男", 29, "13411111112", "菩提院", "打妖怪"));
        list.add(new User("猪八戒", "男", 28, "13411111113", "高老庄", "偷懒"));
        list.add(new User("沙悟净", "男", 27, "13411111114", "流沙河", "挑担子"));
        list.add(new User("唐三藏", "男", 30, "13411111111", "东土大唐", "取西经"));
        list.add(new User("孙悟空", "男", 29, "13411111112", "菩提院", "打妖怪"));
        list.add(new User("猪八戒", "男", 28, "13411111113", "高老庄", "偷懒"));
        list.add(new User("沙悟净", "男", 27, "13411111114", "流沙河", "挑担子"));

        if(list!=null && list.size()>0){
            String companyName = "西天取经";
            String sheetTitle = companyName;
            String [] title = new String[]{"姓名","性别","年龄","手机号码","家庭住址","爱好"};        //设置表格表头字段
            ExcelUtils excelExport = new ExcelUtils();
            excelExport.setData(list);
            excelExport.setFontSize(14);
            excelExport.setSheetName(sheetTitle);
            excelExport.setTitle(sheetTitle);
            excelExport.setHeardList(title);
            excelExport.exportExport(request, response);
        }
    }

}
