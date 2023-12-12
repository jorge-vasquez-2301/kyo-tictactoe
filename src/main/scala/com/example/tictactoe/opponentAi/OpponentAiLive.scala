package com.example.tictactoe.opponentAi

import com.example.tictactoe.domain.{ Board, Field }
import kyo.*
import kyo.ios.*
import kyo.randoms.*

final class OpponentAiLive() extends OpponentAi:
  override def randomMove(board: Board): Field > IOs =
    val unoccupied = board.unoccupiedFields
    board.unoccupiedFields.size match
      case 0 => IOs.fail(IllegalStateException("Board is full"))
      case n => Randoms.nextInt(n).map(unoccupied(_))
