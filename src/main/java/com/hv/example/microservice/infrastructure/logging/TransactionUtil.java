package com.hv.example.microservice.infrastructure.logging;

import org.springframework.util.StopWatch;
import org.springframework.web.util.ContentCachingRequestWrapper;

import javax.servlet.http.HttpServletResponseWrapper;
import java.util.Optional;
import java.util.UUID;

public class TransactionUtil {

    private TransactionUtil(){
        //empty
    }

    public static final String HEADER_SKIP_LOG          = "Skip-Log";
    public static final String HEADER_SEQUENCE          = "Sequence";
    public static final String HEADER_REFERENCE         = "Operation-Reference";
    public static final String HEADER_END_USER          = "End-User";
    public static final String HEADER_DURATION          = "Service-Duration";

    private static ThreadLocal<Boolean> logSkipped      = new ThreadLocal();
    private static ThreadLocal<Integer> sequence        = new ThreadLocal();
    private static ThreadLocal<String> reference        = new ThreadLocal();
    private static ThreadLocal<String> endUser          = new ThreadLocal();
    private static ThreadLocal<StopWatch> timer         = new ThreadLocal();

    private static ThreadLocal<String> threadLocal      = ThreadLocal.withInitial(TransactionUtil::generateReference);

    public static void begin() {
        setLogSkipped(false);
        setSequence(0);
        setReference(generateReference());
        setEndUser("NONE");
        timer.set(new StopWatch());
    }

    public static void timerStart(){
        timer.get().start();
    }

    public static void timerStop(){
        if(timer != null && timer.get()!=null){
            timer.get().stop();
        }
    }

    public static StopWatch getTimer(){
        return timer.get();
    }

    public static void begin(ContentCachingRequestWrapper requestWrapper) {
        begin();
        if(requestWrapper.getHeader(HEADER_SKIP_LOG)!=null && requestWrapper.getHeader(HEADER_SKIP_LOG).equalsIgnoreCase("true")){
            setLogSkipped(true);
        }
        if(requestWrapper.getHeader(HEADER_SEQUENCE)!=null){
            setSequence(Integer.valueOf(requestWrapper.getHeader(HEADER_SEQUENCE)));
        }
        if(requestWrapper.getHeader(HEADER_REFERENCE)!=null){
            setReference(requestWrapper.getHeader(HEADER_REFERENCE));
        }
        if(requestWrapper.getHeader(HEADER_END_USER)!=null){
            setEndUser(requestWrapper.getHeader(HEADER_END_USER));
        }
    }


    public static void clear(HttpServletResponseWrapper response){
        response.setHeader(TransactionUtil.HEADER_SKIP_LOG, Boolean.toString(isLogSkipped()));
        response.setHeader(TransactionUtil.HEADER_REFERENCE, getReference());
        if(sequence!=null && getSequence()!=null){
            response.setHeader(TransactionUtil.HEADER_SEQUENCE, Integer.toString(getSequence()));
        }
        response.setHeader(TransactionUtil.HEADER_END_USER, getEndUser());
        if(timer != null && timer.get()!=null){
            response.setHeader(TransactionUtil.HEADER_DURATION,Long.toString(getTimer().getTotalTimeMillis()));
        }

        clear();
    }

    public static void clear(){
        logSkipped.remove();
        sequence.remove();
        reference.remove();
        endUser.remove();
        timer.remove();
    }

    public static void setLogSkipped(boolean enabled){
        logSkipped.set(enabled);
    }

    public static boolean isLogSkipped(){
        if(logSkipped.get()==null){
            return false;
        }
        return logSkipped.get();
    }

    public static Integer getSequence() {
        return (Integer)sequence.get();
    }

    public static void setSequence(Integer seq) {
        sequence.set(seq);
    }

    public static String getEndUser(){
        return endUser.get();
    }

    public static void setEndUser(String user){
        endUser.set(user);
    }

    public static String getReference() {
        if(reference.get()==null){
            reference.set(generateReference());
        }
        return reference.get();
    }

    public static void setReference(String ref) {
        reference.set(ref);
    }

    public static Integer nextSequenceId() {
        Optional<Integer> sequenceOpt = Optional.ofNullable(getSequence());
        int sequence = 0;
        if (sequenceOpt.isPresent()) {
            sequence = (Integer)sequenceOpt.get();
        }

        ++sequence;
        setSequence(sequence);
        return getSequence();
    }

    private static String generateReference() {
        return UUID.randomUUID().toString().replace("-", "");
    }

}
