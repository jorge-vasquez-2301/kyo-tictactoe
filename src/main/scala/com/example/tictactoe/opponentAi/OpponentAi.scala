package com.example.tictactoe.opponentAi

import com.example.tictactoe.domain.{Board, Field}
import kyo.*
import kyo.ios.*

trait OpponentAi:
  def randomMove(board: Board): Field > IOs
