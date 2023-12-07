package com.example.tictactoe.domain

import com.example.tictactoe.domain.Board.Field

enum State:
  case Confirm(action: ConfirmAction, confirmed: State, declined: State, footerMessage: ConfirmFooterMessage)
  case Menu(game: Option[Game], footerMessage: MenuFooterMessage)
  case Game(
    board: Map[Field, Piece],
    cross: Player,
    nought: Player,
    turn: Piece,
    result: GameResult,
    footerMessage: GameFooterMessage
  )
  case Shutdown

object State:
  val initial: State = Menu(None, MenuFooterMessage.Empty)
