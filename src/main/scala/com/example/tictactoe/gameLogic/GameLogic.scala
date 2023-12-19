package com.example.tictactoe.gameLogic

import com.example.tictactoe.domain.*
import kyo.*
import kyo.aborts.*
import kyo.ios.*

trait GameLogic:
  def putPiece(board: Board, field: Field, piece: Piece): Board < Aborts[AppError]
  def gameResult(board: Board): GameResult < IOs
  def nextTurn(currentTurn: Piece): Piece
