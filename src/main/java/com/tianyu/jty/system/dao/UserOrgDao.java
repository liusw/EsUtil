package com.tianyu.jty.system.dao;

import java.util.List;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import com.tianyu.jty.common.persistence.HibernateDao;
import com.tianyu.jty.system.entity.UserOrg;
import com.tianyu.jty.system.entity.UserRole;


/**
 * 用户机构Dao
 * @author kkomge
 * @date 2015年5月9日 
 */
@Repository
public class UserOrgDao extends HibernateDao<UserOrg, Integer>{

	/**
	 * 删除用户机构
	 * @param userId
	 * @param orgId
	 */
	public void deleteUO(Integer userId){
		String hql="delete UserOrg ur where ur.userId=?0 ";
		batchExecute(hql, userId);
	}
	
	/**
	 * 查询用户拥有的机构id集合
	 * @param userId
	 * @return 结果集合
	 */
	@SuppressWarnings("unchecked")
	public List<Integer> findOrgIds(Integer userId){
		String hql="select ur.orgId from UserOrg ur where ur.userId=?0";
		Query query= createQuery(hql, userId);
		return query.list();
	}
	
}
