package com.inch.rest.mapper;


import java.util.List;
import com.inch.model.WindowModel;

public interface WindowMapper<T> extends BaseMapper<T> {

    List<T> queryByList2(WindowModel model);
}
