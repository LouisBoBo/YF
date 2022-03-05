package com.yssj.entity;


/***
 * 分页表
 * @author Administrator
 *
 */
public class Pager {
	
	private int curPage	;//n	int		当前页	默认1
	private int rowCount;//	y	int		总行数	
	private int pageSize;//	y	int		页大小	默认10
	private int pageCount;//	n	int		总页数	
	private int pageOffset;//	n	int		当前页起始记录	
	private int pageTail;//		int		当前页到达的记录	
	private String sortArr;//		String		多字段排序	
	private String order;//		String		排序方式	desc(倒序) or asc（顺序）
	private String sort;//		String		排序字段	
	private boolean pagination;	//	Boolean		是否分页	默认true
	
	public int getCurPage() {
		return curPage;
	}
	public void setCurPage(int curPage) {
		this.curPage = curPage;
	}
	public int getRowCount() {
		return rowCount;
	}
	public void setRowCount(int rowCount) {
		this.rowCount = rowCount;
	}
	public int getPageSize() {
		return pageSize;
	}
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	public int getPageCount() {
		return pageCount;
	}
	public void setPageCount(int pageCount) {
		this.pageCount = pageCount;
	}
	public int getPageOffset() {
		return pageOffset;
	}
	public void setPageOffset(int pageOffset) {
		this.pageOffset = pageOffset;
	}
	public int getPageTail() {
		return pageTail;
	}
	public void setPageTail(int pageTail) {
		this.pageTail = pageTail;
	}
	public String getSortArr() {
		return sortArr;
	}
	public void setSortArr(String sortArr) {
		this.sortArr = sortArr;
	}
	public String getOrder() {
		return order;
	}
	public void setOrder(String order) {
		this.order = order;
	}
	public String getSort() {
		return sort;
	}
	public void setSort(String sort) {
		this.sort = sort;
	}
	public boolean isPagination() {
		return pagination;
	}
	public void setPagination(boolean pagination) {
		this.pagination = pagination;
	}
	
}
