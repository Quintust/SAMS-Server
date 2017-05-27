package com.fighting.sams.dal;

import com.fighting.sams.utils.STATE;

public interface DatabaseOperation {
	/**
	 * 插入数据
	 * @param obj 需要插入的实体
	 * @return 状态枚举值
	 */
	public abstract STATE insert(Object obj);
	/**
	 * 删除数据
	 * @param obj 需要删除的实体
	 * @return 状态枚举值
	 */
	public abstract STATE delete(Object obj);
	/**
	 * 更新数据
	 * @param obj 需要更新的实体
	 * @return 状态枚举值
	 */
	public abstract STATE update(Object obj);
	/**
	 * 查询数据
	 * @param obj 需要查询的实体
	 * @return 查询出来的实体
	 */
	public abstract Object query(Object obj);
}
