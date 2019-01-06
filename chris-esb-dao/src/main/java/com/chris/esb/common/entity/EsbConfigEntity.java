package com.chris.esb.common.entity;

import java.io.Serializable;


/**
 * ESB配置信息表
 * 
 * @author chris
 * @email 258321511@qq.com
 * @since Jan 06.19
 */
public class EsbConfigEntity  implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//
	private Integer id;
	//key
	private String key;
	//value
	private String value;
	//状态   0：无效   1：有效
	private Integer status;
	//备注
	private String remark;

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getId() {
		return id;
	}
	public void setKey(String key) {
		this.key = key;
	}

	public String getKey() {
		return key;
	}
	public void setValue(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getStatus() {
		return status;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getRemark() {
		return remark;
	}
}
