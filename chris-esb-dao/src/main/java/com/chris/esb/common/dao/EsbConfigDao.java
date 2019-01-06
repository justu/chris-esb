package com.chris.esb.common.dao;

import com.chris.esb.common.entity.EsbConfigEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * ESB配置信息表
 * 
 * @author chris
 * @email 258321511@qq.com
 * @since Jan 06.19
 */
@Mapper
public interface EsbConfigDao {

    EsbConfigEntity queryObject(Integer id);

    List<EsbConfigEntity> queryList(Map<String, Object> map);

    int queryTotal(Map<String, Object> map);

    void save(EsbConfigEntity esbConfig);

    void update(EsbConfigEntity esbConfig);

}
