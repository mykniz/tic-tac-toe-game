package ru.mykniz.tictactoe.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.mykniz.tictactoe.entity.Board;

public interface GameRepository extends JpaRepository<Board, Long> {
}
