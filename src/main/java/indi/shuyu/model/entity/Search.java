package indi.shuyu.model.entity;

import java.sql.Timestamp;
import indi.shuyu.common.base.Base;
public class Search {
	String[] activityIds;
	String[] activityProvince;
	String[] activityApplyCode;
	String[] phoneNumbers;
	String[] awardTypeIds;
	Timestamp startTime;
	Timestamp endTime;
	String createUser;
	Timestamp sysTime;
	//online=0、willonline=1、offline=2
	int status;
	int numPerPage = 10;
	int page = 1;
	int start = 0;
	int end = 10;
	

	public String[] getActivityIds() {
		return activityIds;
	}
	public void setActivityIds(String an) {
		if (an != null && an.length() > 0) {
			String[] ids = an.split(",");
			this.activityIds =  ids;
		}
	}
	
	public String[] getActivityProvince() {
		return activityProvince;
	}
	public void setActivityProvince(String aap) {
		if ( aap != null && aap.length() > 0) {
			String[] items = aap.split(",");
			if (items.length > 0)	this.activityProvince =  items;
		}
	}
	
	public String[] getActivityApplyCode() {
		return activityApplyCode;
	}
	public void setActivityApplyCode(String aac) {
		if ( aac != null && aac.length() > 0) {
			String[] items = aac.split(",");
			if (items.length > 0)	this.activityApplyCode =  items;
		}
	}
	
	public String[] getAwardTypeIds() {
		return awardTypeIds;
	}
	public void setAwardTypeIds(String ati) {
		if ( ati != null && ati.length() > 0) {
			String[] items = ati.split(",");
			if (items.length > 0)	this.awardTypeIds =  items;
		}
	}
	
	public String[] getPhoneNumbers() {
		return phoneNumbers;
	}
	public void setPhoneNumbers(String pns) {
		if ( pns != null && pns.length() > 0) {
			String[] items = pns.split(",");
			if (items.length > 0)	this.phoneNumbers =  items;
		}
	}
	
	public Timestamp getStartTime() {
		return startTime;
	}
	public void setStartTime(String ast) {
		this.startTime =  Base.strToTimeStamp(ast);
	}
	
	public Timestamp getEndTime() {
		return endTime;
	}
	public void setEndTime(String aet) {
		this.endTime =  Base.strToTimeStamp(aet);
	}
	
	public int getNumPerPage() {
		return numPerPage;
	}
	public void setNumPerPage(int n) {
		this.numPerPage =  n;
	}
	
	public String getCreateUser() {
		return createUser;
	}
	public void setCreateUser(String u) {
		this.createUser =  u;
	}
	
	public int getPage() {
		return page;
	}
	public void setPage(int n) {
		this.page =  n;
		setEnd(numPerPage, page);
		setStart(numPerPage, page);
	}
	
	public int getStart() {
		return start;
	}
	public void setStart(int numPerPage, int page) {
		this.start = (page - 1) * numPerPage;
	}
	
	public int getEnd() {
		return end;
	}
	public void setEnd(int numPerPage, int page) {
		this.end = page * numPerPage;
	}
	
	public int getStatus() {
		return status;
	}
	public void setStatus(int s) {
		this.status = s;
	}
	
	public Timestamp getSysTime() {
		return sysTime;
	}
	public void setSysTime(Timestamp t) {
		this.sysTime = t;
	}
	
}
