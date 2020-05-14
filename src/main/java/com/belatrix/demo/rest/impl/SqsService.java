package com.belatrix.demo.rest.impl;

import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.sns.AmazonSNSClientBuilder;
import com.amazonaws.services.sns.model.PublishResult;
import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.AmazonSQSClientBuilder;
import com.amazonaws.services.sqs.model.SendMessageResult;
import com.belatrix.demo.rest.AwsServiceType;
import com.belatrix.demo.rest.AwsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
@Slf4j
public class SqsService implements AwsService {

    @Value("${aws.accessKey}")
    private String awsAccessKey;

    @Value("${aws.secretKey}")
    private String awsSecretKey;

    @Value("${aws.region}")
    private String awsRegion;

    @Value("${sqs.url}")
    private String sqsUrl;

    private AmazonSQS amazonSQS;

    @PostConstruct
    public void postConstructor() {
        log.info("SQS url: " + sqsUrl);

        AWSCredentialsProvider awsCredentialsProvider = new AWSStaticCredentialsProvider(
                new BasicAWSCredentials(awsAccessKey, awsSecretKey)
        );

        this.amazonSQS = AmazonSQSClientBuilder.standard()
                .withCredentials(awsCredentialsProvider)
                .withRegion(awsRegion)
                .build();
    }

    @Override
    public void publish(String message) {
        log.info("Publishing SQS message: " + message);
        SendMessageResult result = amazonSQS.sendMessage(this.sqsUrl, message);
        log.info("SNS Message ID: " + result.getMessageId());
    }

    @Override
    public String getServiceName() {
        return AwsServiceType.AWS_SQS.getServiceType();
    }

}
