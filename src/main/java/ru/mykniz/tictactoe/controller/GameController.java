package ru.mykniz.tictactoe.controller;

import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.mykniz.tictactoe.service.GameService;

import static ru.mykniz.tictactoe.service.GameService.DASH;

/**
 * @author Shundeev Nick
 */
@Controller
public class GameController {

    @Autowired
    private GameService gameService;

    @GetMapping("/")
    public String getBoard(Model model) {
        if (gameService.isWin()) {
            gameService.restore();
        }
        gameService.createBoard();
        renderModel(model);
        return "main";
    }

    @GetMapping("/reset")
    public String reset() {
        gameService.restoreAll();
        return "redirect:/";
    }

    @PostMapping("/send")
    public String send(@RequestParam String position, Model model) {
        try {
            val result = gameService.getResult(position);
            renderModel(model);
            model.addAttribute("result", result);
        } catch (RuntimeException e) {
            model.addAttribute("error", e.getMessage());
        }
        return "main";
    }

    private void renderModel(Model model) {
        val board = gameService.getBoardRows();
        model.addAttribute("dash", DASH);
        model.addAttribute("boards", board);
        model.addAttribute("turn", gameService.getTurn());
        model.addAttribute("isWin", gameService.isWin());
    }
}
