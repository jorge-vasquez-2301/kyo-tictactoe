package com.example.tictactoe.view.game

import com.example.tictactoe.domain.Board.Field
import com.example.tictactoe.domain.{ GameFooterMessage, GameResult, Piece, Player }

trait GameView:
  def header(result: GameResult, turn: Piece, player: Player): String
  def content(board: Map[Field, Piece], result: GameResult): String
  def footer(message: GameFooterMessage): String
