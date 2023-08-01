package service;


public abstract class BaseServiceImpl {
	
	private GeneralDaoImpl _dao;
	
	protected GeneralDaoImpl getDao() {
		return this._dao;
	}
}
