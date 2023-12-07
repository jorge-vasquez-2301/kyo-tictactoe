package com.example.tictactoe.opponentAi

import com.example.tictactoe.domain.Board.Field
import com.example.tictactoe.domain.Piece
import kyo.*
import kyo.ios.*

trait OpponentAi:
  def randomMove(board: Map[Field, Piece]): Field > IOs
