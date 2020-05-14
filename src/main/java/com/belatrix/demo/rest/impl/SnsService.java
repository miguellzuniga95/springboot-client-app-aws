package com.belatrix.demo.rest.impl;

import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.sns.AmazonSNS;
import com.amazonaws.services.sns.AmazonSNSClientBuilder;
import com.amazonaws.services.sns.model.PublishResult;
import com.belatrix.demo.rest.AwsServiceType;
import com.belatrix.demo.rest.AwsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
@Slf4j
public class SnsService implements AwsService {

    @Value("${aws.accessKey}")
    private String awsAccessKey;

    @Value("${aws.secretKey}")
    private String awsSecretKey;

    @Value("${aws.region}")
    private String awsRegion;

    @Value("${sns.topic.arn}")
    private String snsTopicARN;

    private AmazonSNS amazonSNS;

    @PostConstruct
    private void postConstructor() {

        log.info("SNS arn: " + snsTopicARN);

        AWSCredentialsProvider awsCredentialsProvider = new AWSStaticCredentialsProvider(
                new BasicAWSCredentials(awsAccessKey, awsSecretKey)
        );

        this.amazonSNS = AmazonSNSClientBuilder.standard()
                .withCredentials(awsCredentialsProvider)
                .withRegion(awsRegion)
                .build();
    }

    @Override
    public void publish(String message) {
        log.info("Publishing SNS message: " + message);
        PublishResult result = this.amazonSNS.publish(this.snsTopicARN, message);
        log.info("SNS Message ID: " + result.getMessageId());
    }

    @Override
    public String getServiceName() {
        return AwsServiceType.AWS_SNS.getServiceType();
    }

}
