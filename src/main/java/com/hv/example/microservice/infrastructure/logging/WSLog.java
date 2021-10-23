package com.hv.example.microservice.infrastructure.logging;

import com.hv.example.microservice.util.AppUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.env.Environment;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class WSLog {

    private static final String ERROR_LOGGER_UTIL   = "Error LogUtil {0}";
    private final String APP_NAME = "spring.application.name";

    private final Environment env;
    private final LogService logService;

    public WSLog(Environment env, LogService logService) {
        this.env = env;
        this.logService = logService;
    }

    /**
     * Just the message
     * @param category
     * @param msg
     */
    @Async
    public void info(LogCategory category,String msg){
        if(TransactionUtil.isLogSkipped()) return;
        try {
            logService.log(
                    LogData.builder()
                            .setReference(TransactionUtil.getReference())
                            .setEndUser(TransactionUtil.getEndUser())
                            .setService(env.getProperty(APP_NAME))
                            .setLevel(LogLevel.INFO)
                            .setSequence(TransactionUtil.nextSequenceId())
                            .setCategory(category)
                            .setOriginClass(AppUtil.getClassName(3))
                            .setOriginMethod(AppUtil.getMethodName(3))
                            .setMsg(msg)
                            .build()
            );
        }catch (Exception e){
            log.error(ERROR_LOGGER_UTIL,e);
        }
    }

    /**
     * Message and description
     * @param category
     * @param msg
     * @param description
     */
    @Async
    public void info(LogCategory category,String msg,String description){
        if(TransactionUtil.isLogSkipped()) return;

        try {
            logService.log(
                    LogData.builder()
                            .setReference(TransactionUtil.getReference())
                            .setEndUser(TransactionUtil.getEndUser())
                            .setService(env.getProperty(APP_NAME))
                            .setLevel(LogLevel.INFO)
                            .setSequence(TransactionUtil.nextSequenceId())
                            .setCategory(category)
                            .setOriginClass(AppUtil.getClassName(3))
                            .setOriginMethod(AppUtil.getMethodName(3))
                            .setMsg(msg)
                            .setDescription(description)
                            .build()
            );
        }catch (Exception e){
            log.error(ERROR_LOGGER_UTIL,e);
        }
    }

    /**
     * Message, description and duration
     * @param category
     * @param msg
     * @param description
     * @param duration
     */
    @Async
    public void info(LogCategory category,String msg,String description,Long duration){
        if(TransactionUtil.isLogSkipped()) return;
        try {
            logService.log(
                    LogData.builder()
                            .setReference(TransactionUtil.getReference())
                            .setEndUser(TransactionUtil.getEndUser())
                            .setService(env.getProperty(APP_NAME))
                            .setLevel(LogLevel.INFO)
                            .setSequence(TransactionUtil.nextSequenceId())
                            .setCategory(category)
                            .setOriginClass(AppUtil.getClassName(3))
                            .setOriginMethod(AppUtil.getMethodName(3))
                            .setMsg(msg)
                            .setDescription(description)
                            .setDuration(duration)
                            .build()
            );
        }catch (Exception e){
            log.error(ERROR_LOGGER_UTIL,e);
        }
    }

    /**
     * Message, description and object
     * @param category
     * @param msg
     * @param description
     * @param data
     */
    @Async
    public void info(LogCategory category,String msg,String description,Object data){
        if(TransactionUtil.isLogSkipped()) return;
        try {
            logService.log(
                    LogData.builder()
                            .setReference(TransactionUtil.getReference())
                            .setEndUser(TransactionUtil.getEndUser())
                            .setService(env.getProperty(APP_NAME))
                            .setLevel(LogLevel.INFO)
                            .setSequence(TransactionUtil.nextSequenceId())
                            .setCategory(category)
                            .setOriginClass(AppUtil.getClassName(3))
                            .setOriginMethod(AppUtil.getMethodName(3))
                            .setMsg(msg)
                            .setDescription(description)
                            .setData(data)
                            .build()
            );
        }catch (Exception e){
            log.error(ERROR_LOGGER_UTIL,e);
        }
    }

    /**
     * Message, description, duration and object
     * @param category
     * @param msg
     * @param description
     * @param data
     * @param duration
     */
    @Async
    public void info(LogCategory category,String msg,String description,Object data,Long duration){
        if(TransactionUtil.isLogSkipped()) return;
        try {
            logService.log(
                    LogData.builder()
                            .setReference(TransactionUtil.getReference())
                            .setEndUser(TransactionUtil.getEndUser())
                            .setService(env.getProperty(APP_NAME))
                            .setLevel(LogLevel.INFO)
                            .setSequence(TransactionUtil.nextSequenceId())
                            .setCategory(category)
                            .setOriginClass(AppUtil.getClassName(3))
                            .setOriginMethod(AppUtil.getMethodName(3))
                            .setMsg(msg)
                            .setDescription(description)
                            .setData(data)
                            .setDuration(duration)
                            .build()
            );
        }catch (Exception e){
            log.error(ERROR_LOGGER_UTIL,e);
        }
    }

    /**
     * Simple error log
     * @param category
     * @param msg
     * @param description
     */
    @Async
    public void error(LogCategory category,String msg,String description){
        if(TransactionUtil.isLogSkipped()) return;
        try {
            logService.log(
                    LogData.builder()
                            .setReference(TransactionUtil.getReference())
                            .setEndUser(TransactionUtil.getEndUser())
                            .setService(env.getProperty(APP_NAME))
                            .setLevel(LogLevel.ERROR)
                            .setSequence(TransactionUtil.nextSequenceId())
                            .setCategory(category)
                            .setOriginClass(AppUtil.getClassName(3))
                            .setOriginMethod(AppUtil.getMethodName(3))
                            .setMsg(msg)
                            .setDescription(description)
                            .build()
            );
        }catch (Exception e){
            log.error(ERROR_LOGGER_UTIL,e);
        }
    }

    /**
     * Error log with exception
     * @param category
     * @param msg
     * @param ex
     */
    @Async
    public void error(LogCategory category,String msg,Exception ex){
        if(TransactionUtil.isLogSkipped()) return;
        try {
            logService.log(
                    LogData.builder()
                            .setReference(TransactionUtil.getReference())
                            .setEndUser(TransactionUtil.getEndUser())
                            .setService(env.getProperty(APP_NAME))
                            .setLevel(LogLevel.ERROR)
                            .setSequence(TransactionUtil.nextSequenceId())
                            .setCategory(category)
                            .setOriginClass(AppUtil.getClassName(3))
                            .setOriginMethod(AppUtil.getMethodName(3))
                            .setMsg(msg)
                            .setData(ex)
                            .build()
            );
        }catch (Exception e){
            log.error(ERROR_LOGGER_UTIL,e);
        }
    }
}
