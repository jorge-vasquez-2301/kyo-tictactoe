package com.example.tictactoe.gameLogic

import com.example.tictactoe.domain.Board.Field
import com.example.tictactoe.domain.{ AppError, Board, GameResult, Piece }
import kyo.*
import kyo.ios.*
import kyo.aborts.*

final class GameLogicLive() extends GameLogic:
  def putPiece(board: Map[Field, Piece], field: Field, piece: Piece): Map[Field, Piece] > Aborts[AppError] =
    board.get(field) match
      case None => board.updated(field, piece)
      case _    => Aborts[AppError].fail(AppError.FieldAlreadyOccupiedError)

  def gameResult(board: Map[Field, Piece]): GameResult > IOs =
    val pieces: Map[Piece, Set[Field]] =
      board
        .groupBy(_._2)
        .view
        .mapValues(_.keys.toSet)
        .toMap
        .withDefaultValue(Set.empty)

    val crossWin  = Board.wins.exists(_ subsetOf pieces(Piece.Cross))
    val noughtWin = Board.wins.exists(_ subsetOf pieces(Piece.Nought))
    val boardFull = board.size == 9

    if crossWin && noughtWin then
      IOs.fail {
        IllegalStateException("It should not be possible for both players to meet winning conditions.")
      }
    else if crossWin then GameResult.Win(Piece.Cross)
    else if noughtWin then GameResult.Win(Piece.Nought)
    else if boardFull then GameResult.Draw
    else GameResult.Ongoing

  def nextTurn(currentTurn: Piece): Piece =
    currentTurn match
      case Piece.Cross  => Piece.Nought
      case Piece.Nought => Piece.Cross
