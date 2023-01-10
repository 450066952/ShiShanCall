package com.inch.rest.mapper;

import com.inch.model.EvaModel;
import com.inch.model.NumInfoModel;

import java.util.List;
import java.util.Map;

public interface NumInfoMapper<T> extends BaseMapper<T> {

    List<String> getNotifyLcd(String guid);
    NumInfoModel queryNextNum(Map<String,Object> map);
    List<Map<String,String>> getSign();
    int updateStartNum();
    int addEva(EvaModel model);
    int copyNumData();
    int addSign(Map<String,Object> map);
    List<Map<String,Object>> queryByList2(Map<String,Object> map);
    int addMobileMsg(Map<String,Object> map);
    int insertChildBus(List<Map<String,Object>> list);

    List<NumInfoModel> queryNextNum2(Map<String,Object> map);

    NumInfoModel queryHistoryInfo(NumInfoModel bean);

    NumInfoModel queryByIdTwo(NumInfoModel model);

    int addTwo(NumInfoModel model);

    int deleteNum(String guid);

    int addTwoOrder(NumInfoModel model);

    int addOrder(NumInfoModel model);

    int updateNumberStatus(NumInfoModel model);
}
