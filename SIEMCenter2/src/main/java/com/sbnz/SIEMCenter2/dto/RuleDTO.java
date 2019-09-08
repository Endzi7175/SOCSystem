package com.sbnz.SIEMCenter2.dto;

import com.sbnz.SIEMCenter2.model.Rule;

public class RuleDTO {
    public Rule rule;
    public int count;
    public String message;
    public String interval;

    public  RuleDTO(){}

    public RuleDTO(Rule rule, int count, String message, String interval) {
        this.rule = rule;
        this.count = count;
        this.message = message;
        this.interval = interval;
    }

    public void setRule(Rule rule) {
        this.rule = rule;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setInterval(String interval) {
        this.interval = interval;
    }

    public Rule getRule() {
        return rule;
    }

    public int getCount() {
        return count;
    }

    public String getMessage() {
        return message;
    }

    public String getInterval() {
        return interval;
    }
}
