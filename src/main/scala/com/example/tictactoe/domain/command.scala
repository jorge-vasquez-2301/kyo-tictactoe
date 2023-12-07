package com.example.tictactoe.domain

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
