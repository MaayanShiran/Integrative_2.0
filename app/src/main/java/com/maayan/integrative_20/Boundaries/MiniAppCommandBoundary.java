package com.maayan.integrative_20.Boundaries;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.TimeZone;

public class MiniAppCommandBoundary {

    private CommandId commandId;
    private String command;
    private TargetObject targetObject;
    private String invocationTimestamp;
    private InvokedBy invokedBy;
    private Map<String, Object> commandAttributes;

    public MiniAppCommandBoundary() {
        super();
    }

    public MiniAppCommandBoundary(CommandId commandId, String command, TargetObject targetObject, InvokedBy invokedBy, Map<String, Object> commandAttributes) {
        this.commandId = commandId;
        this.command = command;
        this.targetObject = targetObject;
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSX");
        dateFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
        Date date = new Date();
        this.invocationTimestamp = dateFormat.format(date);
        this.invokedBy = invokedBy;
        this.commandAttributes = commandAttributes;
    }

    public CommandId getCommandId() {
        return commandId;
    }
    public void setCommandId(CommandId commandId) {
        this.commandId = commandId;
    }
    public String getCommand() {
        return command;
    }
    public void setCommand(String command) {
        this.command = command;
    }
    public TargetObject getTargetObject() {
        return targetObject;
    }
    public void setTargetObject(TargetObject targetObject) {
        this.targetObject = targetObject;
    }

    public String getInvocationTimestamp() {
        return invocationTimestamp;
    }

    public void setInvocationTimestamp(String invocationTimestamp) {
        this.invocationTimestamp = invocationTimestamp;
    }

    public InvokedBy getInvokedBy() {
        return invokedBy;
    }
    public void setInvokedBy(InvokedBy invokedBy) {
        this.invokedBy = invokedBy;
    }
    public Map<String, Object> getCommandAttributes() {
        return commandAttributes;
    }
    public void setCommandAttributes(Map<String, Object> commandAttributes) {
        this.commandAttributes = commandAttributes;
    }

    @Override
    public String toString() {
        return "MiniAppCommandBoundary [commandId=" + commandId + ", command=" + command + ", targetObject="
                + targetObject + ", invocationTimestamp=" + invocationTimestamp + ", invokedBy=" + invokedBy
                + ", commandAttributes=" + commandAttributes + "]";
    }



}
