package com.inch.rest.mapper;

import com.inch.model.SysDicModel;
import org.apache.ibatis.annotations.Param;

import java.util.List;


public interface SysDicMapper<T> extends BaseMapper<T> {
	
	List<T> getDicCode(SysDicModel model);
	
	List<SysDicModel> getDicList(SysDicModel model);

	SysDicModel getByDicId(SysDicModel model);

	List<SysDicModel> getDic(int orgid);

	int updateGuid(@Param("name") String name, @Param("guid") String guid);

	SysDicModel getByDicName(String dicName);

	List<SysDicModel> getByDicPid(@Param("ids") String[] ids);

	SysDicModel getByDicName2(String dicName);

    SysDicModel getByDicCodeId(String type);

	void addThings(SysDicModel dicModel);

	List<SysDicModel> getByRootId(String id);

	SysDicModel getByDicRootId(SysDicModel dicModel);
}
