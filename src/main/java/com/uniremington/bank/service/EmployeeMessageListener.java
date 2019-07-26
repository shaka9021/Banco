package com.uniremington.bank.service;

import javax.ejb.MessageDriven;
import javax.inject.Inject;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

@MessageDriven
public class EmployeeMessageListener implements MessageListener {

    @Inject
    private ClientBusiness business;

    @Override
    public void onMessage(Message message) {

        TextMessage text = (TextMessage) message;

        try {

            String uid = text.getText();

            System.out.println(uid);

            ClientDTO client = business.get(uid).orElse(null);

            System.out.println(client);

        } catch (JMSException e) {
            e.printStackTrace();
        }

    }

}
