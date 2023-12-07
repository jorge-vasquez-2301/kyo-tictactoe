package com.example.tictactoe.domain

import com.example.tictactoe.domain.Board.Field

enum ConfirmCommand:
  case Yes
  case No

enum GameCommand:
  case Menu
  case Put(field: Field)

enum MenuCommand:
  case NewGame
  case Resume
  case Quit
