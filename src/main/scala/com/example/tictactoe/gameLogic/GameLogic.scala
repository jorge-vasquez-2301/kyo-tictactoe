package com.example.tictactoe.gameLogic

import com.example.tictactoe.domain.Board.Field
import com.example.tictactoe.domain.{ AppError, GameResult, Piece }
import kyo.*
import kyo.ios.*
import kyo.aborts.*

trait GameLogic:
  def putPiece(board: Map[Field, Piece], field: Field, piece: Piece): Map[Field, Piece] > Aborts[AppError]
  def gameResult(board: Map[Field, Piece]): GameResult > IOs
  def nextTurn(currentTurn: Piece): Piece
