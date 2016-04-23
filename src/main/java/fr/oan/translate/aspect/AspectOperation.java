package fr.oan.translate.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;

@Aspect
@Configuration
public class AspectOperation {

	static final Logger logger = LoggerFactory.getLogger(AspectOperation.class);

	@Before("within(fr.oan.translate.service.*)")
	private void anyPublicOperation(JoinPoint jp) {
		// logger.info("*******" + jp.toLongString());
	}

}
