package com.inch.rest.service;

import com.inch.model.SysDicModel;
import com.inch.rest.mapper.SysDicCodeMapper;
import com.inch.rest.mapper.SysDicMapper;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;



@Service("sysDicService")
public class SysDicService<T> extends BaseService<T> {
	private final static Logger log= Logger.getLogger(SysDicService.class);
	
	public List<T> getDicCode(SysDicModel model){
		return getMapper().getDicCode(model);
	}
	
	public List<SysDicModel> getDicList(SysDicModel model){
		return getMapper().getDicList(model);
	}
	

	@Autowired
    private SysDicMapper<T> mapper;

	@Autowired
	private SysDicCodeMapper<T>  codeMapper;

		
	public SysDicMapper<T> getMapper() {
		return mapper;
	}

	public SysDicModel getByDicId(SysDicModel model) {
		return mapper.getByDicId(model);
	}

	public List<SysDicModel> getDic(int orgid) {
		return mapper.getDic(orgid);
	}

	public Integer deleteCode(Object[] ids) {
		return codeMapper.delete(ids);
	}

	public int updateGuid(String name, String guid) {
		return getMapper().updateGuid(name, guid);
	}

	public SysDicModel getByDicName(String dicName) {
		return getMapper().getByDicName(dicName);
	}

	public List<SysDicModel> getByDicPid(String[] ids) {
		return getMapper().getByDicPid(ids);
	}

	public SysDicModel getByDicName2(String dicName) {
		return getMapper().getByDicName2(dicName);
	}

    public SysDicModel getByDicCodeId(String type) {
		return getMapper().getByDicCodeId(type);
    }

	public void addThings(SysDicModel dicModel) {
		getMapper().addThings(dicModel);
	}

	public List<SysDicModel> getByRootId(String id) {
		return getMapper().getByRootId(id);
	}

	public SysDicModel getByDicRootId(SysDicModel dicModel) {
		return getMapper().getByDicRootId(dicModel);
	}
}
