package com.groofycode.GroofyCode.controller.Game;


import com.groofycode.GroofyCode.dto.Game.ProblemSubmitDTO;
import com.groofycode.GroofyCode.model.Team.TeamModel;
import com.groofycode.GroofyCode.model.User.UserModel;
import com.groofycode.GroofyCode.repository.Team.TeamRepository;
import com.groofycode.GroofyCode.service.Game.GameService;
import com.groofycode.GroofyCode.utilities.ResponseUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;

@SecurityRequirement(name = "bearerAuth")
@RestController
@RequestMapping("/game")
public class GameController {
    @Autowired
    private TeamRepository teamRepository;

    @Autowired
    private GameService gameService;

    @GetMapping
    public ResponseEntity<Object> getAllGames(@RequestParam(value = "p", required = false) Integer page) throws Exception {
        return gameService.findAllGames(page);
    }

    @PostMapping("/solo")
    public ResponseEntity<Object> createSoloMatch() {
        return gameService.findSoloMatch();
    }

    //    @PostMapping("/ranked")
//    public Game createRankedMatch(@RequestParam String player1, @RequestParam String player2) {
//        RankedMatch rankedMatch = new RankedMatch(player1, player2, LocalDateTime.now());
//        return gameService.saveGame(rankedMatch);
//    }
//
//    @PostMapping("/casual")
//    public Game createCasualMatch(@RequestParam String player1, @RequestParam String player2) {
//        CasualMatch casualMatch = new CasualMatch(player1, player2, LocalDateTime.now());
//        return gameService.saveGame(casualMatch);
//    }
    @GetMapping("/user-queue")
    public ResponseEntity<Object> getUserQueue() {
        return gameService.getUserQueue();
    }

    @GetMapping("/user-queue/ranked")
    public ResponseEntity<Object> getUserRankedQueue() {
        return gameService.getUserRankedQueue();
    }

    @GetMapping("/user-queue/casual")
    public ResponseEntity<Object> getUserCasualQueue() {
        return gameService.getUserCasualQueue();
    }

    @GetMapping("/user-queue/velocity")
    public ResponseEntity<Object> getUserVelocityQueue() {
        return gameService.getUserVelocityQueue();
    }


    @GetMapping("/{gameId}/submissions")
    public ResponseEntity<Object> getSubmissions(@PathVariable Long gameId) {
        return gameService.getSubmissions(gameId);
    }


    @PostMapping("/ranked")
    public ResponseEntity<Object> findRankedMatch() throws Exception {
        ResponseEntity<Object> match = gameService.findRankedMatch();
        if (match != null) {
            return match;
        } else {
            return ResponseEntity.ok(ResponseUtils.successfulRes("Still looking for a match", null)); // Still looking for a match
        }
    }

    @PostMapping("/casual")
    public ResponseEntity<Object> findCasualMatch() throws Exception {
        ResponseEntity<Object> match = gameService.findCasualMatch();
        if (match != null) {
            return match;
        } else {
            return ResponseEntity.ok(ResponseUtils.successfulRes("Still looking for a match", null)); // Still looking for a match
        }
    }

    @PostMapping("/velocity")
    public ResponseEntity<Object> findVelocityMatch() throws Exception {
        ResponseEntity<Object> match = gameService.findVelocityMatch();
        if (match != null) {
            return match;
        } else {
            return ResponseEntity.ok(ResponseUtils.successfulRes("Still looking for a match", null)); // Still looking for a match
        }
    }

    @PostMapping("/leaveQueue")
    public ResponseEntity<Object> leaveQueue() {
        return gameService.leaveQueue();
    }

    @PostMapping("/leaveRankedQueue")
    public ResponseEntity<Object> leaveRankedQueue() {
        return gameService.leaveRankedQueue();
    }

    @PostMapping("/leaveCasualQueue")
    public ResponseEntity<Object> leaveCasualQueue() {
        return gameService.leaveCasualQueue();
    }

    @PostMapping("/leaveVelocityQueue")
    public ResponseEntity<Object> leaveVelocityQueue() {
        return gameService.leaveVelocityQueue();
    }

    @GetMapping("/{matchId}")
    public ResponseEntity<Object> getMatch(@PathVariable Long matchId) {
        return gameService.getMatch(matchId);
    }


    @PutMapping("/{gameId}/leave")
    public ResponseEntity<Object> leaveMatch(@PathVariable Long gameId) {
        return gameService.leaveMatch(gameId);
    }

    @PostMapping("/{gameId}/submit")
    public ResponseEntity<Object> submitCode(@PathVariable Long gameId, @RequestBody ProblemSubmitDTO problemSubmitDTO) throws Exception {
        return gameService.submitCode(gameId, problemSubmitDTO);
    }

    @PostMapping("/team-match/{invitationId}")
    public ResponseEntity<Object> createTeamMatch(@PathVariable Long invitationId) {
        try {

            return gameService.createTeamMatch(invitationId);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ResponseUtils.unsuccessfulRes("Error creating Team Match", e.getMessage()));
        }
    }

    @PostMapping("/friend-match/{invitationId}")
    public ResponseEntity<Object> createBeatAFriendMatch(@PathVariable Long invitationId) throws Exception {
        return gameService.createBeatAFriendMatch(invitationId);
    }

    @GetMapping("/invitation/{invitationId}")
    public ResponseEntity<Object> getInvitation(@PathVariable Long invitationId) {
        return gameService.getMatchInvitation(invitationId);
    }

    @GetMapping("/history")
    public ResponseEntity<Object> getMatchHistory() {
        return gameService.getUserHistory();
    }

//    @PostMapping("/join")
//    public ResponseEntity<Void> joinGame(@RequestBody UserModel user) {
//        gameService.joinGame(user);
//        return ResponseEntity.ok().build();
//    }

}