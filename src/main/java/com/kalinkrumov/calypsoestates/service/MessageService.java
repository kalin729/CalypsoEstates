package com.kalinkrumov.calypsoestates.service;

import com.kalinkrumov.calypsoestates.model.dto.MessageSendDTO;
import com.kalinkrumov.calypsoestates.model.entity.Message;
import com.kalinkrumov.calypsoestates.repository.MessageRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDateTime;
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
        message.setCreatedAt(LocalDateTime.now());

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
