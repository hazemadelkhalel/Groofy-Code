package com.groofycode.GroofyCode.model.Game;

import com.groofycode.GroofyCode.model.User.UserModel;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Setter
@Getter
public class Submission {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "game_id")
    private Game game;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserModel user;

    private String language;

    @Lob
    @Column(columnDefinition = "LONGBLOB")
    private byte[] code;

    private LocalDateTime submissionTime;

    private Integer result;

    private String problemUrl;

    // Constructors, getters, and setters
    public Submission() {
    }

    public Submission(Game game, UserModel user, byte[] code, String language, LocalDateTime submissionTime, Integer result, String problemUrl) {
        this.game = game;
        this.user = user;
        this.code = code;
        this.language = language;
        this.submissionTime = submissionTime;
        this.result = result;
        this.problemUrl = problemUrl;
    }
}
