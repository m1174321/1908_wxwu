package com.fh.util;

import java.util.List;

/**
 * @ClassName: PageBean
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author shangfeng
 * @date 2018-7-19 上午10:01:24
 */
public class PageBean<T> {

	//开始下标
	private Integer start;
	//每页条数
	private Integer length;
	//重新绘制表格
	private Integer draw;
	//总条数
	private  long recordsTotal;
	//满足条件的总条数
	private long recordsFiltered;
	//存放数据
	private List<?> data;



	public Integer getStart() {
		return start;
	}
	public void setStart(Integer start) {
		this.start = start;
	}
	public Integer getLength() {
		return length;
	}
	public void setLength(Integer length) {
		this.length = length;
	}
	public Integer getDraw() {
		return draw;
	}
	public void setDraw(Integer draw) {
		this.draw = draw;
	}
	public long getRecordsTotal() {
		return recordsTotal;
	}
	public void setRecordsTotal(long recordsTotal) {
		this.recordsTotal = recordsTotal;
	}
	public long getRecordsFiltered() {
		return recordsFiltered;
	}
	public void setRecordsFiltered(long recordsFiltered) {
		this.recordsFiltered = recordsFiltered;
	}
	public List<?> getData() {
		return data;
	}
	public void setData(List<?> data) {
		this.data = data;
	}


}
