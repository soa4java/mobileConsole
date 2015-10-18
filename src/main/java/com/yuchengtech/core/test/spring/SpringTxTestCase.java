
package com.yuchengtech.core.test.spring;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;

public abstract class SpringTxTestCase extends AbstractTransactionalJUnit4SpringContextTests {

	protected DataSource dataSource;

	@Override
	@Autowired
	public void setDataSource(DataSource dataSource) {
		super.setDataSource(dataSource);
		this.dataSource = dataSource;
	}
}
