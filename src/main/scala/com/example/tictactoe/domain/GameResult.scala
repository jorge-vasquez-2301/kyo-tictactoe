package com.example.tictactoe.domain

enum GameResult:
  case Ongoing
  case Win(piece: Piece)
  case Draw
