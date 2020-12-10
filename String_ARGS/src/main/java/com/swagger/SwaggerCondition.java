package com.swagger;

import org.apache.log4j.Logger;
import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.type.AnnotatedTypeMetadata;

public class SwaggerCondition implements Condition {

    private static final Logger logger = Logger.getLogger(SwaggerCondition.class);

    @Override
    public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
        boolean enableSwagger = context.getEnvironment().getProperty("swagger.implementation", Boolean.class, false);
        logger.debug("\n============\n" + ".swagger enabled : " + enableSwagger + "\n============\n");
        return enableSwagger;
    }

}
