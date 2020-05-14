package com.belatrix.demo.service.impl;

import com.belatrix.demo.domain.Message;
import com.belatrix.demo.rest.AwsService;
import com.belatrix.demo.rest.impl.KinesisService;
import com.belatrix.demo.rest.impl.SnsService;
import com.belatrix.demo.service.LabService;
import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class LabServiceImpl implements LabService {

    @Value("${default.message}")
    private String defaultDescription;

    private Map<String, AwsService> awsServices;

    public LabServiceImpl(List<AwsService> awsServices) {
        this.awsServices = awsStrategies(awsServices);
    }

    @Override
    public Message sendMessage(String service, String id) {
        AwsService awsService = findAwsService(service);
        Message message = new Message(id,defaultDescription);
        awsService.publish(new Gson().toJson(message));
        return message;
    }

    private Map<String, AwsService> awsStrategies(List<AwsService> awsServices) {
        Map<String,AwsService> awsServiceStrategies = new HashMap<>();
        awsServices.forEach(awsService -> awsServiceStrategies.put(awsService.getServiceName(),awsService));
        return awsServiceStrategies;
    }

    private AwsService findAwsService(String serviceName) {
        AwsService awsService = awsServices.get(serviceName);
        if(awsService != null) {
            return  awsService;
        }
        throw new IllegalArgumentException(String.format("AWS service not found -> %s", serviceName));
    }
}
