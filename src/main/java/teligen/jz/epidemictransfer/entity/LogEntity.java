package teligen.jz.epidemictransfer.entity;

import java.util.Date;

public class LogEntity {

    public String UUID= cn.hutool.core.lang.UUID.fastUUID().toString();
    public Integer processCode;
    public String code;
    public String errorMsg;
    public String message;
    public String contentJson;
    public Date insertTime=new Date();

    public LogEntity(Integer processCode, String code, String message) {
        this.processCode = processCode;
        this.code = code;
        this.message = message;
    }

    public LogEntity(Integer processCode, String code, String errorMsg, String message) {
        this.processCode = processCode;
        this.code = code;
        this.errorMsg = errorMsg;
        this.message = message;
    }

    public LogEntity(Integer processCode, String code, String errorMsg, String message, String contentJson) {
        this.processCode = processCode;
        this.code = code;
        this.errorMsg = errorMsg;
        this.message = message;
        this.contentJson = contentJson;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getUUID() {
        return UUID;
    }

    public void setUUID(String UUID) {
        this.UUID = UUID;
    }

    public Integer getProcessCode() {
        return processCode;
    }

    public void setProcessCode(Integer processCode) {
        this.processCode = processCode;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public String getContentJson() {
        return contentJson;
    }

    public void setContentJson(String contentJson) {
        this.contentJson = contentJson;
    }

    public Date getInsertTime() {
        return insertTime;
    }

    public void setInsertTime(Date insertTime) {
        this.insertTime = insertTime;
    }
}
