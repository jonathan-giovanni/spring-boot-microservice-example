package com.hv.example.microservice.infrastructure.logging;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import static net.logstash.logback.marker.Markers.append;

@Component
@Qualifier(LogConstants.SL4FJ_LOG)
@Slf4j
public class Slf4jLogService implements LogService {

    @Value("${spring.profiles.active}")
    private String springProfile;

    private final String PROFILE_PRODUCTION = "production";


    @Override
    public void log(LogData logData) {
        if (logData != null) {
            switch (logData.getLevel()) {
                case INFO:
                    if(springProfile.equals(PROFILE_PRODUCTION)){
                        log.info(append("data",logData),logData.getMsg());
                    }else{
                        log.info(loggingFormat(logData));
                    }
                    break;
                case DEBUG:
                    if(springProfile.equals(PROFILE_PRODUCTION)){
                        log.debug(append("data",logData),logData.getMsg());
                    }else{
                        log.debug(loggingFormat(logData));
                    }
                    break;
                case ERROR:
                    if(springProfile.equals(PROFILE_PRODUCTION)){
                        log.error(append("data",logData),logData.getMsg());
                    }else{
                        log.error(loggingFormat(logData));
                    }
                    break;
                default:
                    if(springProfile.equals(PROFILE_PRODUCTION)){
                        log.trace(append("data",logData),logData.getMsg());
                    }else{
                        log.trace(loggingFormat(logData));
                    }
                    break;
            }
        }
    }

    private String loggingFormat(LogData logData) {
        String main = "%s - %s %s %s %s : %s";//sequence
        String formatted = String.format(main,logData.getReference(),logData.getEndUser(), categoryFormat(logData.getCategory()), sequenceFormat(logData.getSequence()), originFormat(logData.getOriginClass(), logData.getOriginMethod()), logData.getMsg());

        StringBuilder builder = new StringBuilder();
        builder.append(formatted);
        if (logData.getDescription() != null) {
            builder.append(" ");
            builder.append(logData.getDescription());
        }

        if (logData.getDuration() != null) {
            builder.append(" Duration: ").append(logData.getDuration()).append("ms");
        }
        return builder.toString();
    }

    private String categoryFormat(LogCategory category) {
        int size = 10;
        String cat;
        if (category != null) {
            cat = category.toString();
        } else {
            cat = "INVALID";
        }
        return cat + addSpaces(size - cat.length());
    }

    private String sequenceFormat(int sequence) {
        int size = 3;
        int sequenceSize = String.valueOf(sequence).length();

        return sequence + addSpaces(size - sequenceSize);
    }

    private String originFormat(String clazz, String method) {
        int size = 62;
        String appended = String.format("%s.%s", getCompressedSource(clazz), method);
        return appended + addSpaces(size - appended.length());
    }

    private String addSpaces(int quantity) {
        String spaces = "";
        if (quantity > 0) {
            spaces = String.format("%0" + quantity + "d", 0).replace("0", " ");
        }
        return spaces;
    }

    private String getCompressedSource(String source) {
        String[] sourceArray = source.split("\\.");
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < sourceArray.length; ++i) {
            String element = sourceArray[i];
            if (i < sourceArray.length - 1) {
                sb.append(element.charAt(0));
                sb.append(".");
            } else {
                sb.append(element);
            }
        }

        return sb.toString();
    }
}
