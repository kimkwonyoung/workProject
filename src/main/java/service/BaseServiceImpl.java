package service;

import workDao.MemberDao;

public abstract class BaseServiceImpl {
	
	private MemberDao _dao;
	
	protected MemberDao getDao() {
		return this._dao;
	}
}
