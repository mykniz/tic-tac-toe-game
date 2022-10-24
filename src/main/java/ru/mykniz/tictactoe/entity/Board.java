package ru.mykniz.tictactoe.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import static javax.persistence.GenerationType.SEQUENCE;

/**
 * @author Shundeev Nick
 */
@Entity
@Table(name = "board")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Board {
    @Id
    @GeneratedValue(strategy=SEQUENCE, generator="CUST_SEQ")
    private Long id;
    private String rows;
}
