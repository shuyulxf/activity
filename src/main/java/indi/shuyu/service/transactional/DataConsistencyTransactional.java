package indi.shuyu.service.transactional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import indi.shuyu.model.dao.AwardDao;
import indi.shuyu.model.dao.UserDao;
import indi.shuyu.model.entity.User;

@Service
public class DataConsistencyTransactional {
	@Transactional(readOnly=true, rollbackFor = {Exception.class})	
	public boolean sendAwardDetail(Long awardId, int awardNum, User u, AwardDao ad, UserDao ud) {
		
		boolean res = false;
		try {
			
			int uRlt = ad.updateAwardNum(awardId, awardNum);
			if(uRlt == 1) {
				int aRlt = ud.insertUserAwardInfo(u);
				if (aRlt == 0) {
					TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();  
					throw new Error("insert user info error");
				} else {
					res = true;
				}
			} 
			
		}
		catch(Exception e) {
			res = false;
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();  
			throw e;
		}
		
		return res;
	}
}
