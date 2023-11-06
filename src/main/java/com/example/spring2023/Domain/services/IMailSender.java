package com.example.spring2023.Domain.services;

public interface IMailSender {

    /**
     *
     * @param emailTo email to send message
     * @param subject subject of the message
     * @param message text of the message
     */
    void send(String emailTo, String subject, String message);
}
