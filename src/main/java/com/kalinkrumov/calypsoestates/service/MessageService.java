package com.kalinkrumov.calypsoestates.service;

import com.kalinkrumov.calypsoestates.model.dto.MessageSendDTO;
import com.kalinkrumov.calypsoestates.model.entity.Message;
import com.kalinkrumov.calypsoestates.repository.MessageRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.UUID;

@Service
public class MessageService {

    private final MessageRepository messageRepository;
    private final ModelMapper modelMapper;
    private final EmailService emailService;
    private final DateTimeProviderService dateTimeProvider;

    public MessageService(MessageRepository messageRepository,
                          ModelMapper modelMapper,
                          EmailService emailService, DateTimeProviderService dateTimeProvider) {
        this.messageRepository = messageRepository;
        this.modelMapper = modelMapper;
        this.emailService = emailService;
        this.dateTimeProvider = dateTimeProvider;
    }

    public void addMessage(MessageSendDTO messageSendDTO) {
//        Message message = modelMapper.map(messageSendDTO, Message.class);
        Message message = new Message();
        message.setMessage(messageSendDTO.getMessage());
        message.setSenderName(messageSendDTO.getSenderName());
        message.setReplied(false);
        message.setReply(null);
        message.setEmail(messageSendDTO.getEmail());
        message.setSubject(messageSendDTO.getSubject());
        message.setProperty(messageSendDTO.getProperty());
        message.setCreatedAt(dateTimeProvider.now());

        messageRepository.save(message);
    }

    public List<Message> getAllMessages() {
        return messageRepository.findAllByOrderByCreatedAtDesc();
    }

    public Message getMessageById(UUID id) {
        return messageRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Message with this ID not found."));
    }

    public void addReply(UUID id, String reply) {
        Message message = messageRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Message with this ID not found."));
        message.setReply(reply);
        message.setReplied(true);

        emailService.sendMessageReplyEmail(message.getEmail(), message.getReply(), "RE: " + message.getSubject());

        messageRepository.save(message);
    }
}
