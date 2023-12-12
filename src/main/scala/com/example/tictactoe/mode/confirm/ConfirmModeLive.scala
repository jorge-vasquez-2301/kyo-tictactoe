package com.example.tictactoe.mode.confirm

import com.example.tictactoe.domain.*
import com.example.tictactoe.parser.confirm.ConfirmCommandParser
import com.example.tictactoe.view.confirm.ConfirmView
import kyo.*
import kyo.aborts.*

final class ConfirmModeLive(confirmCommandParser: ConfirmCommandParser, confirmView: ConfirmView) extends ConfirmMode:
  def process(input: String, state: State.Confirm): State =
    Aborts[AppError].run {
      confirmCommandParser
        .parse(input)
        .map {
          case ConfirmCommand.Yes => state.confirmed
          case ConfirmCommand.No  => state.declined
        }
    }.pure.toOption.getOrElse(state.copy(footerMessage = ConfirmFooterMessage.InvalidCommand))

  def render(state: State.Confirm): String =
    List(
      confirmView.header(state.action),
      confirmView.content,
      confirmView.footer(state.footerMessage)
    ).mkString("\n\n")
