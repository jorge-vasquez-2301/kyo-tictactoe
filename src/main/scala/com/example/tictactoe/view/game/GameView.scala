package com.example.tictactoe.view.game

import com.example.tictactoe.domain.*

trait GameView:
  def header(result: GameResult, turn: Piece, player: Player): String
  def content(board: Board, result: GameResult): String
  def footer(message: GameFooterMessage): String
