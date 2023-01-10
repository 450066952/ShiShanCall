package com.inch.rest.service;

import com.inch.model.SysDicCodeModel;
import com.inch.model.SysDicModel;
import com.inch.rest.mapper.SysDicCodeMapper;
import com.inch.rest.mapper.SysDicMapper;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service("SysDicCodeService")
public class SysDicCodeService<T> extends BaseService<T> {
	private final static Logger log= Logger.getLogger(SysDicCodeService.class);

	@Autowired
	private SysDicCodeMapper<T>  mapper;

	public SysDicCodeModel getById(SysDicCodeModel model) {
		return mapper.getById(model);
	}
	public SysDicCodeMapper<T> getMapper() {
		return mapper;
	}

}
