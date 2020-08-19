package com.sat.sisat.common.security;

import java.io.Serializable;
import java.util.logging.Logger;

import javax.interceptor.AroundInvoke;
import javax.interceptor.InvocationContext;

import com.sat.sisat.exception.SisatException;

public class TracingInterceptor implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 918753390187484517L;

	@AroundInvoke
    public Object logCall(InvocationContext context) throws SisatException{
		long start = System.currentTimeMillis();
		try{
			return context.proceed();
		}catch(Exception e){
			throw new SisatException(e);
		}finally{
			long time = System.currentTimeMillis() - start;
			String className = context.getTarget().getClass().getName(); 
			String method = context.getMethod().getName();
			Logger logger = Logger.getLogger(className);
			logger.info("Invocation of " + className+"."+method + " took " + time + "ms");
		}
    }
}


