package com.groofycode.GroofyCode.controller.Game;

import com.groofycode.GroofyCode.service.Game.MatchInvitationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/match")
public class matchInvitationController {

    @Autowired
    private MatchInvitationService matchInvitationService;

    @PostMapping("/invite/{teamId1}/{teamId2}")
    public ResponseEntity<Object> sendInvitation(@PathVariable Long teamId1, @PathVariable Long teamId2) {
        try {
            return matchInvitationService.sendTeamMatchInvitationMain(teamId1, teamId2);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @PostMapping("/acceptInvitation/{invitationId}")
    public ResponseEntity<Object> acceptInvitation(@PathVariable Long invitationId) {
        return matchInvitationService.acceptMatchInvitation(invitationId);
    }

    @PostMapping("/rejectInvitation/{invitationId}")
    public ResponseEntity<Object> rejectInvitation(@PathVariable Long invitationId) {
        return matchInvitationService.rejectMatchInvitation(invitationId);
    }

    @PostMapping("/cancelInvitation/{invitationId}")
    public ResponseEntity<Object> cancelInvitation(@PathVariable Long invitationId) {
        return matchInvitationService.cancelMatchInvitation(invitationId);
    }
}
