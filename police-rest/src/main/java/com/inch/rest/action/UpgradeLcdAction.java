package com.inch.rest.action;

import com.inch.interceptor.Auth;
import com.inch.model.VersionModel;
import com.inch.rest.utils.SpringUtil;
import com.inch.utils.DBUtils;
import com.socket.server.command.service.TonyCommandService;
import com.socket.server.socket.pub.Command;
import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

@Controller
@RequestMapping("/update")
public class UpgradeLcdAction extends BaseAction{

    @Autowired
    private TonyCommandService tonyService;

    @Auth(verifyLogin = false)
    @RequestMapping("/ug")
    public void upgradeLcd(HttpServletResponse response) {
        int model = 4;
        String url = "http://50.79.254.58/Upload/client_pad.apk";
        int count = 0;

        ApplicationContext ac = SpringUtil.getApplicationContext();
        SqlSessionFactory sqlSessionFactory = (SqlSessionFactory) ac.getBean("sqlSessionFactory");
        SqlSession session = sqlSessionFactory.openSession();
        Connection conn = session.getConnection();
        PreparedStatement pst = null;

        StringBuffer sb=new StringBuffer();
        sb.append(" select * from lcdlist  ");
        sb.append(" where model = ?");


        ResultSet rs=null;
        try {
            pst = conn.prepareStatement(sb.toString());
            pst.setInt(1,model);
            rs=pst.executeQuery();

            if(rs!=null){
                while(rs.next()){
                    VersionModel vmodel = new VersionModel();
                    String sn = StringUtils.trimToEmpty(rs.getString("guid"));
                    vmodel.setUrl(url);
                    vmodel.setVersion("10000");
                    tonyService.sendCommandToDevice(Command.COMMAND_UPGRADED, vmodel, sn);
                    count ++;
                }
            }

            pst.close();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DBUtils.close(rs,pst, conn);
        }

        sendSuccessMessage(response, "发送----成功！共计：" + count + "台设备。");
    }
}
