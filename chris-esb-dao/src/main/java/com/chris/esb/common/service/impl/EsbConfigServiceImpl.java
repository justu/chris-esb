package com.chris.esb.common.service.impl;

import com.chris.esb.common.service.EsbConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.chris.esb.common.dao.EsbConfigDao;
import com.chris.esb.common.entity.EsbConfigEntity;
import org.springframework.util.CollectionUtils;


@Service("esbConfigService")
public class EsbConfigServiceImpl implements EsbConfigService {
	@Autowired
	private EsbConfigDao esbConfigDao;
	
	@Override
	public EsbConfigEntity queryObject(Integer id){
		return esbConfigDao.queryObject(id);
	}
	
	@Override
	public List<EsbConfigEntity> queryList(Map<String, Object> map){
		return esbConfigDao.queryList(map);
	}
	
	@Override
	public int queryTotal(Map<String, Object> map){
		return esbConfigDao.queryTotal(map);
	}
	
	@Override
	public void save(EsbConfigEntity esbConfig){
		esbConfigDao.save(esbConfig);
	}
	
	@Override
	public void update(EsbConfigEntity esbConfig){
		esbConfigDao.update(esbConfig);
	}
	
	@Override
	public String getValueByKey(String key) {
		Map<String, Object> map = new HashMap<>();
		map.put("key", key);
		List<EsbConfigEntity> esbConfigList = this.queryList(map);
		return CollectionUtils.isEmpty(esbConfigList) ? null : esbConfigList.get(0).getValue();
	}
}
