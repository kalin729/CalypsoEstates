package com.kalinkrumov.calypso_estates.service;

import com.kalinkrumov.calypso_estates.model.dto.MessageSendDTO;
import com.kalinkrumov.calypso_estates.model.entity.Message;
import com.kalinkrumov.calypso_estates.repository.MessageRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class MessageService {

    private final MessageRepository messageRepository;
    private final ModelMapper modelMapper;
    private final EmailService emailService;

    public MessageService(MessageRepository messageRepository,
                          ModelMapper modelMapper,
                          EmailService emailService) {
        this.messageRepository = messageRepository;
        this.modelMapper = modelMapper;
        this.emailService = emailService;
    }

    public void addMessage(MessageSendDTO messageSendDTO) {
        Message message = modelMapper.map(messageSendDTO, Message.class);

        messageRepository.save(message);
    }

    public List<Message> getAllMessages() {
        return messageRepository.findAll();
    }

    public Message getMessageById(UUID id) {
        return messageRepository.findById(id).orElse(null);
    }

    public void addReply(UUID id, String reply) {
        Message message = messageRepository.findById(id).orElse(null);
        message.setReply(reply);
        message.setReplied(true);

        emailService.sendMessageReplyEmail(message.getEmail(), message.getReply(), "RE: " + message.getSubject());

        messageRepository.save(message);
    }
}
