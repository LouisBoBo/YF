package com.yssj.entity;

import java.util.Date;

public class Help {
	private int id;
	private String question;//问题
	private String answer;//答案
	private Integer sort;//排序
	private Integer adm_id;//添加人
	private String add_time;//添加时间
	private int type;//类型
	private int u_adm_id;//修改人id
	private String update_time;//修改时间
	public int getU_adm_id() {
		return u_adm_id;
	}
	@Override
	public String toString() {
		return "Help [id=" + id + ", question=" + question + ", answer="
				+ answer + ", sort=" + sort + ", adm_id=" + adm_id
				+ ", add_time=" + add_time + ", type=" + type + ", u_adm_id="
				+ u_adm_id + ", update_time=" + update_time + "]";
	}
	public void setU_adm_id(int u_adm_id) {
		this.u_adm_id = u_adm_id;
	}
	public String getUpdate_time() {
		return update_time;
	}
	public void setUpdate_time(String update_time) {
		if(update_time==null){
			update_time="";
		}
		this.update_time = update_time;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getQuestion() {
		return question;
	}
	public void setQuestion(String question) {
		if(question==null){
			question="";
		}
		this.question = question;
	}
	public String getAnswer() {
		return answer;
	}
	public void setAnswer(String answer) {
		this.answer = answer;
	}
	public Integer getSort() {
		return sort;
	}
	public void setSort(Integer sort) {
		this.sort = sort;
	}
	public Integer getAdm_id() {
		return adm_id;
	}
	public void setAdm_id(Integer adm_id) {
		this.adm_id = adm_id;
	}
	public String getAdd_time() {
		return add_time;
	}
	public void setAdd_time(String add_time) {
		if(add_time==null){
			add_time="";
		}
		this.add_time = add_time;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public Help(int id, String question, String answer, Integer sort,
			Integer adm_id, String add_time, int type) {
		super();
		this.id = id;
		this.question = question;
		this.answer = answer;
		this.sort = sort;
		this.adm_id = adm_id;
		this.add_time = add_time;
		this.type = type;
	}
	public Help() {
		super();
		// TODO Auto-generated constructor stub
	}
	
}
