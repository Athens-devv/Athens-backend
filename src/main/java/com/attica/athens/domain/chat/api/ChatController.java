package com.attica.athens.domain.chat.api;

import com.attica.athens.domain.chat.application.ChatCommandService;
import com.attica.athens.domain.chat.application.ChatQueryService;
import com.attica.athens.domain.chat.dto.request.SendChatRequest;
import com.attica.athens.domain.chat.dto.response.SendChatResponse;
import com.attica.athens.domain.chat.dto.response.SendMetaResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ChatController {

    private final ChatCommandService chatCommandService;
    private final ChatQueryService chatQueryService;

    @MessageMapping("/agoras/{agora-id}/chats")
    @SendTo(value = "/topic/agoras/{agora-id}/chats")
    public SendChatResponse sendChat(@DestinationVariable("agora-id") Long agoraId,
                                     @Payload SendChatRequest sendChatRequest,
                                     @AuthenticationPrincipal UserDetails userDetails) {

        return chatCommandService.sendChat(userDetails, agoraId, sendChatRequest);
    }

    @MessageMapping("/agoras/{agora-id}")
    @SendTo(value = "/topic/agoras/{agora-id}")
    public SendMetaResponse sendMeta(@DestinationVariable("agora-id") Long agoraId) {

        return chatQueryService.sendMeta(agoraId);
    }
}