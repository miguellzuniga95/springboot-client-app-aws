package com.belatrix.demo.rest;

public enum AwsServiceType {

    AWS_SNS("sns"),
    AWS_SQS("sqs"),
    AWS_KINESIS("kinesis");

    private String serviceType;

    AwsServiceType(final String serviceType) {
        this.serviceType = serviceType;
    }

    public String getServiceType() {
        return serviceType;
    }
}
