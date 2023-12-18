package com.example.tictactoe.view.confirm
import com.example.tictactoe.domain.{ ConfirmAction, ConfirmFooterMessage }
import kyo.envs.*
import kyo.layers.*

final class ConfirmViewLive() extends ConfirmView:
  def header(action: ConfirmAction): String =
    action match
      case ConfirmAction.NewGame =>
        """[New game]
          |
          |This will discard current game progress.""".stripMargin
      case ConfirmAction.Quit    =>
        """[Quit]
          |
          |This will discard current game progress.""".stripMargin

  val content: String =
    """Are you sure?
      |<yes> / <no>""".stripMargin

  def footer(message: ConfirmFooterMessage): String =
    message match
      case ConfirmFooterMessage.Empty          => ""
      case ConfirmFooterMessage.InvalidCommand => "Invalid command. Try again."

object ConfirmViewLive:
  val layer: Layer[Envs[ConfirmView], Any] = Envs[ConfirmView].layer(ConfirmViewLive())
