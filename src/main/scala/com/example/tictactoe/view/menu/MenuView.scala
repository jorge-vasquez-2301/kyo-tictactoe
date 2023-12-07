package com.example.tictactoe.view.menu

import com.example.tictactoe.domain.MenuFooterMessage

trait MenuView:
  def header: String
  def content(isSuspended: Boolean): String
  def footer(message: MenuFooterMessage): String
