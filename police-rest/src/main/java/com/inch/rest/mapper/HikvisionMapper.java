package com.inch.rest.mapper;

import com.inch.model.HikvisionModel;

public interface HikvisionMapper<T> extends BaseMapper<T> {


    HikvisionModel getVideoByEvId(int id);

    int addVideo(HikvisionModel model);
}
