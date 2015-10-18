package com.yuchengtech.mobile.console.service.log;

import org.apache.commons.lang.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;

import com.yuchengtech.core.utils.mapper.JsonBinder;
import com.yuchengtech.core.utils.reflection.ReflectionUtils;
import com.yuchengtech.mobile.console.common.EntityInfo;
import com.yuchengtech.mobile.console.common.ModuleInfo;
import com.yuchengtech.mobile.console.entity.account.VUser;
import com.yuchengtech.mobile.console.entity.log.OptLog;
import com.yuchengtech.mobile.console.utils.DateUtil;

@Aspect
public class LogAspect {
	
	private static final String SAVE_POINTCUT = "@target(com.yuchengtech.mobile.console.common.EntityInfo)&&execution(*  com.yuchengtech.mobile.console.service..*.save(..))";
	private static final String UPDATE_POINTCUT = "@target(com.yuchengtech.mobile.console.common.EntityInfo)&&execution(*  com.yuchengtech.mobile.console.service..*.update(..))";
	private static final String DELETE_POINTCUT = "@target(com.yuchengtech.mobile.console.common.EntityInfo)&&execution(*  com.yuchengtech.mobile.console.service..*.delete(..))";
	private static final String CHECK_POINTCUT = "@target(com.yuchengtech.mobile.console.common.EntityInfo)&&execution(*  com.yuchengtech.mobile.console.service..*.check(..))";
	private static final String BATCH_DELETE_POINTCUT = "@target(com.yuchengtech.mobile.console.common.EntityInfo)&&execution(*  com.yuchengtech.mobile.console.service..*.batchDelete(..))&&args(identifiers,ids)";
	
	@Autowired
	private JdbcLogWriter jdbcLogWriter;
	
	@After(SAVE_POINTCUT)
	public void save(JoinPoint joinPoint){
		log(joinPoint);
	}

	@After(UPDATE_POINTCUT)
	public void update(JoinPoint joinPoint){
		log(joinPoint);
	}
	
	@After(DELETE_POINTCUT)
	public void delete(JoinPoint joinPoint){
		log(joinPoint);
	}
	
	@After(CHECK_POINTCUT)
	public void check(JoinPoint joinPoint){
		log(joinPoint);
	}
	
	@After(BATCH_DELETE_POINTCUT)
	public void batchDelete(JoinPoint joinPoint,Object[] identifiers,Object[] ids){
		VUser user = (VUser)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String operaor = user.getUsername();
		Long loginId = user.getLoginId();
		String time = DateUtil.format();
		String opt = joinPoint.getSignature().getName();
		EntityInfo entityInfo = joinPoint.getTarget().getClass().getAnnotation(EntityInfo.class);
		String obj = entityInfo.name();
		String label = ModuleInfo.getModuleLabel(obj);
		String identifier = null;
		if (identifiers!=null) {
			identifier = StringUtils.join(identifiers, ',');
		}
		OptLog optLog = new OptLog(null, operaor, time, opt, obj, label, identifier,loginId);
		jdbcLogWriter.processMessage(optLog);
		System.out.println(JsonBinder.buildNormalBinder().toJson(optLog));
	}
	
	private void log(JoinPoint joinPoint) {
		VUser user = (VUser)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String operaor = user.getUsername();
		Long loginId = user.getLoginId();
		String time = DateUtil.format();
		String opt = joinPoint.getSignature().getName();
		Object arg = joinPoint.getArgs()[0];
		EntityInfo entityInfo = joinPoint.getTarget().getClass().getAnnotation(EntityInfo.class);
		String obj = entityInfo.name();
		String label = ModuleInfo.getModuleLabel(obj);
		String identifier;
		if(arg instanceof java.lang.Long||arg instanceof java.lang.String){
			identifier=arg+"";
		}else{		
			identifier = (String)ReflectionUtils.getFieldValue(arg, entityInfo.identityFiled());
		}
		OptLog optLog = new OptLog(null, operaor, time, opt, obj, label, identifier,loginId);
		jdbcLogWriter.processMessage(optLog);
	}
}
