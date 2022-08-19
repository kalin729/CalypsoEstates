package com.kalinkrumov.calypso_estates.service;

import com.kalinkrumov.calypso_estates.model.dto.MessageSendDTO;
import com.kalinkrumov.calypso_estates.model.entity.Message;
import com.kalinkrumov.calypso_estates.repository.MessageRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
public class MessageService {

    private final MessageRepository messageRepository;
    private final ModelMapper modelMapper;

    public MessageService(MessageRepository messageRepository, ModelMapper modelMapper) {
        this.messageRepository = messageRepository;
        this.modelMapper = modelMapper;
    }

    public void addMessage(MessageSendDTO messageSendDTO){
        Message message = modelMapper.map(messageSendDTO, Message.class);

        messageRepository.save(message);
    }
}
