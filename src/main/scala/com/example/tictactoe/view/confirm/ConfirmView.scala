package com.example.tictactoe.view.confirm

import com.example.tictactoe.domain.{ ConfirmAction, ConfirmFooterMessage }

trait ConfirmView:
  def header(action: ConfirmAction): String
  def content: String
  def footer(message: ConfirmFooterMessage): String
