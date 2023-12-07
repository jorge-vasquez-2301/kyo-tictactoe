package com.example.tictactoe.gameLogic

import com.example.tictactoe.domain.*
import kyo.*
import kyo.aborts.*
import kyo.ios.*

final class GameLogicLive() extends GameLogic:
  def putPiece(board: Board, field: Field, piece: Piece): Board > Aborts[AppError] =
    if board.fieldIsFree(field) then board.updated(field, piece)
    else Aborts[AppError].fail(AppError.FieldAlreadyOccupiedError)

  def gameResult(board: Board): GameResult > IOs =
    val crossWin  = board.isPieceWinner(Piece.X)
    val noughtWin = board.isPieceWinner(Piece.O)

    if crossWin && noughtWin then
      IOs.fail {
        IllegalStateException("It should not be possible for both players to meet winning conditions.")
      }
    else if crossWin then GameResult.Win(Piece.X)
    else if noughtWin then GameResult.Win(Piece.O)
    else if board.isFull then GameResult.Draw
    else GameResult.Ongoing

  def nextTurn(currentTurn: Piece): Piece =
    currentTurn match
      case Piece.X => Piece.O
      case Piece.O => Piece.X
