package com.belatrix.demo.rest;

public interface AwsService {

    void publish(String message);
    String getServiceName();
}
