package com.hv.example.microservice.infrastructure.logging;

public class LogData {
    private String service;
    private LogLevel level;
    private LogCategory category;
    private int sequence;
    private String reference;
    private String originClass;
    private String originMethod;
    private String msg;
    private String description;
    private String endUser;
    private Long duration;
    private Object data;

    public String getEndUser() {
        return endUser;
    }

    public String getService() {
        return service;
    }

    public Long getDuration() {
        return duration;
    }

    public LogLevel getLevel() {
        return level;
    }

    public LogCategory getCategory() {
        return category;
    }

    public int getSequence() {
        return sequence;
    }

    public String getReference() {
        return reference;
    }

    public String getOriginClass() {
        return originClass;
    }

    public String getOriginMethod() {
        return originMethod;
    }

    public String getMsg() {
        return msg;
    }

    public String getDescription() {
        return description;
    }

    public Object getData() {
        return data;
    }

    public LogData(){
        //empty
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {

        private LogData instance = new LogData();

        public Builder(){
            //constructor vacio
        }

        public Builder setEndUser(String endUser){
            this.instance.endUser = endUser;
            return this;
        }

        public Builder setService(String service){
            this.instance.service = service;
            return this;
        }

        public Builder setLevel(LogLevel level){
            this.instance.level = level;
            return this;
        }

        public Builder setCategory(LogCategory logCategory){
            this.instance.category = logCategory;
            return this;
        }

        public Builder setSequence(int sequence){
            this.instance.sequence = sequence;
            return this;
        }

        public Builder setReference(String reference){
            this.instance.reference = reference;
            return this;
        }

        public Builder setDuration(Long duration){
            this.instance.duration = duration;
            return this;
        }

        public Builder setOriginClass(String originClass){
            this.instance.originClass = originClass;
            return this;
        }

        public Builder setOriginMethod(String originMethod){
            this.instance.originMethod = originMethod;
            return this;
        }

        public Builder setMsg(String msg){
            this.instance.msg = msg;
            return this;
        }

        public Builder setDescription(String description){
            this.instance.description = description;
            return this;
        }
        public Builder setData(Object data){
            this.instance.data = data;
            return this;
        }

        public LogData build(){
            return instance;
        }
    }
}
