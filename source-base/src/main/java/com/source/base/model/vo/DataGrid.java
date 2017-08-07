package com.source.base.model.vo;

import java.util.List;

import com.source.base.model.response.IgnoreResponse;

/**
 * Easyui DataGrid的数据模型封装. <br/>
 */
public class DataGrid extends IgnoreResponse{

	public DataGrid() {
		super();
	}

	public DataGrid(List<?> rows, Long total) {
		super();
		this.rows = rows;
		this.total = total;
	}

	/**
	 * 当前页记录数
	 */
	private List<?> rows;
	/**
	 * 总数
	 */
	private Long total;

	public List<?> getRows() {
		return rows;
	}

	public void setRows(List<?> rows) {
		this.rows = rows;
	}

	public long getTotal() {
		return total;
	}

	public void setTotal(long total) {
		this.total = total;
	}

}
