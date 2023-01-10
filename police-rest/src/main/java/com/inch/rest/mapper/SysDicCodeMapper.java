package com.inch.rest.mapper;

import com.inch.model.SysDicCodeModel;

import java.util.List;


public interface SysDicCodeMapper<T> extends BaseMapper<T> {

	List<SysDicCodeModel> getDicCodeList(SysDicCodeModel model);

    SysDicCodeModel getById(SysDicCodeModel model);

}
