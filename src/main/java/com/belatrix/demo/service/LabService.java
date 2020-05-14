package com.belatrix.demo.service;

import com.belatrix.demo.domain.Message;

public interface LabService {

    Message sendMessage(String service, String id);

    // SNS
//    Message publishSNS(String id);
//    Message publishSNS(Message message);
    // Kinesis
//    Message publishKinesis(String id);
//    Message publishKinesis(Message message);
}
