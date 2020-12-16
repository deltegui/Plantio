package com.deltegui.plantio.game.implementation;

import com.deltegui.plantio.game.application.*;
import com.deltegui.plantio.game.domain.Game;
import com.deltegui.plantio.game.domain.Plant;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.HashSet;

@Validated
@RestController
@RequestMapping("/api/game")
public class GameController {
    private final LoadCase loadCase;
    private final SaveCase saveCase;

    public GameController(LoadCase loadCase, SaveCase saveCase) {
        this.loadCase = loadCase;
        this.saveCase = saveCase;
    }

    @GetMapping
    public Game loadGame(Principal user) {
        return this.loadCase.handle(new LoadRequest(user.getName()));
    }

    @PostMapping
    public SaveResponse saveGame(Principal user, @Valid @RequestBody HashSet<Plant> plants) {
        System.out.println(plants);
        return this.saveCase.handle(new SaveRequest(user.getName(), plants));
    }
}
