package com.belatrix.demo.rest.impl;

import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.kinesis.AmazonKinesis;
import com.amazonaws.services.kinesis.AmazonKinesisClientBuilder;
import com.amazonaws.services.kinesis.model.PutRecordRequest;
import com.amazonaws.services.kinesis.model.PutRecordResult;
import com.belatrix.demo.rest.AwsServiceType;
import com.belatrix.demo.rest.AwsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.nio.ByteBuffer;

@Component
@Slf4j
public class KinesisService implements AwsService {

    @Value("${aws.accessKey}")
    private String awsAccessKey;

    @Value("${aws.secretKey}")
    private String awsSecretKey;

    @Value("${aws.region}")
    private String awsRegion;

    @Value("${aws.stream.name}")
    private String awsStreamName;

    private AmazonKinesis amazonKinesis;

    @PostConstruct
    private void postConstructor() {
        AWSCredentialsProvider awsCredentialsProvider = new AWSStaticCredentialsProvider(
                new BasicAWSCredentials(awsAccessKey, awsSecretKey)
        );

        this.amazonKinesis = AmazonKinesisClientBuilder.standard()
                .withCredentials(awsCredentialsProvider)
                .withRegion(awsRegion)
                .build();
    }

    @Override
    public void publish(String message) {
        log.info("Publishing Kinesis data: " + message);

        PutRecordRequest putRecordRequest = new PutRecordRequest();
        putRecordRequest.setStreamName(awsStreamName);
        putRecordRequest.setPartitionKey("session" + "S1");
        putRecordRequest.setData(ByteBuffer.wrap(message.getBytes()));

        log.info("$$$: " + putRecordRequest.toString());

        PutRecordResult putRecordResult = amazonKinesis.putRecord(putRecordRequest);
        log.info("Kinesis Sequence Number: " + putRecordResult.getSequenceNumber());
    }

    @Override
    public String getServiceName() {
        return AwsServiceType.AWS_KINESIS.getServiceType();
    }
}
