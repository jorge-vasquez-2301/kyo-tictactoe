package com.example.tictactoe.opponentAi

import com.example.tictactoe.domain.Board.Field
import com.example.tictactoe.domain.Piece
import kyo.*
import kyo.ios.*
import kyo.randoms.*

final case class OpponentAiLive() extends OpponentAi:
  override def randomMove(board: Map[Field, Piece]): Field > IOs =
    val unoccupied = (Field.values.toSet -- board.keySet).toList.sortBy(_.ordinal)
    unoccupied.size match
      case 0 => IOs.fail(IllegalStateException("Board is full"))
      case n => Randoms.nextInt(n).map(unoccupied(_))
