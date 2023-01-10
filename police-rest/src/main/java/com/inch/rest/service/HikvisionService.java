package com.inch.rest.service;

import com.inch.model.HikvisionModel;
import com.inch.rest.mapper.HikvisionMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("HikvisionService")
public class HikvisionService<T> extends BaseService<T>{

    @Autowired
    private HikvisionMapper<T> mapper;

    @Override
    public HikvisionMapper<T> getMapper() {
        return mapper;
    }

    public HikvisionModel getVideoByEvId(int id) {
        return getMapper().getVideoByEvId(id);
    }

    public int addVideo(HikvisionModel model){
        return getMapper().addVideo(model);
    }

}
