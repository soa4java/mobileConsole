package com.yuchengtech.mobile.console.service.log;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataAccessResourceFailureException;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.yuchengtech.mobile.console.entity.log.OptLog;
import com.yuchengtech.mobile.console.utils.queue.BlockingConsumer;

/**
 * 将Queue中的log4j event写入数据库的消费者任务.
 * 
 * 即时阻塞的读取Queue中的事件,达到缓存上限后使用Jdbc批量写入模式.
 * 如需换为定时读取模式,继承于PeriodConsumer稍加改造即可.
 * 
 * @see BlockingConsumer
 * 
 */
public class JdbcLogWriter extends BlockingConsumer {

	protected String sql;
	protected int batchSize = 3;

	protected List<OptLog> buffer = Lists.newArrayList();
	
	@Autowired
	private OptLogManager optLogmanager;

	/**
	 * 带Named Parameter的insert sql.
	 * 
	 * Named Parameter的名称见AppenderUtils中的常量定义.
	 */
	public void setSql(String sql) {
		this.sql = sql;
	}

	/**
	 * 批量读取事件数量, 默认为10.
	 */
	public void setBatchSize(int batchSize) {
		this.batchSize = batchSize;
	}


	/**
	 * 消息处理函数,将消息放入buffer,当buffer达到batchSize时执行批量更新函数.
	 */
	@Override
	protected void processMessage(Object message) {
		OptLog log = (OptLog) message;
		buffer.add(log);
		//已到达BufferSize则执行批量插入操作
		if (buffer.size() >= batchSize) {
			updateBatch();
		}
	}

	/**
	 * 将Buffer中的事件列表批量插入数据库.
	 */
	@SuppressWarnings("unchecked")
	public void updateBatch() {
		try {
			//日志信息, 转换为jdbc批处理参数.
			int i = 0;
			Map[] paramMapArray = new HashMap[buffer.size()];
			for (OptLog log : buffer) {

				optLogmanager.save(log);
			}
			
//			for (OptLog log : buffer) {
//				
//				paramMapArray[i++] = parseObject(log);
//			}
//			final SqlParameterSource[] batchParams = SqlParameterSourceUtils.createBatch(paramMapArray);
//
//			//执行批量插入,如果失败调用失败处理函数.
//			transactionTemplate.execute(new TransactionCallbackWithoutResult() {
//				@Override
//				protected void doInTransactionWithoutResult(TransactionStatus status) {
//					try {
//						jdbcTemplate.batchUpdate(getActualSql(), batchParams);
//					} catch (DataAccessException e) {
//						status.setRollbackOnly();
//						handleDataAccessException(e, buffer);
//					}
//				}
//			});

			//清除已完成的Buffer
			buffer.clear();
		} catch (Exception e) {
			logger.error("批量提交任务时发生错误.", e);
		}
	}

	/**
	 * 退出清理函数,完成buffer中未完成的消息.
	 */
	@Override
	protected void clean() {
		if (!buffer.isEmpty()) {
			updateBatch();
		}
		logger.debug("cleaned task {}", this);
	}

	/**
	 * 分析Object, 建立Parameter Map, 用于绑定sql中的Named Parameter.
	 */
	protected Map<String, Object> parseObject(OptLog log) {
		Map<String, Object> parameterMap = Maps.newHashMap();
		parameterMap.put("OPERATOR", log.getOperator());
		parameterMap.put("TIME", log.getTime());
		parameterMap.put("OPT", log.getOpt());
		parameterMap.put("OBJ", log.getObj());
		parameterMap.put("LABEL", log.getLabel());
		parameterMap.put("IDENTIFIER", log.getIdentifier());
		parameterMap.put("LOGIN_ID", log.getLoginId());
		return parameterMap;
	}

	/**
	 * 可被子类重载的数据访问错误处理函数,如将出错的事件持久化到文件.
	 */
	protected void handleDataAccessException(DataAccessException e, List<OptLog> errorBatch) {
		if (e instanceof DataAccessResourceFailureException) {
			logger.error("database connection error", e);
		} else {
			logger.error("other database error", e);
		}
	}

	/**
	 * 可被子类重载的sql提供函数,可对sql语句进行特殊处理，如日志表的表名可带日期后缀 LOG_2009_02_31.
	 */
	protected String getActualSql() {
		return sql;
	}
}
