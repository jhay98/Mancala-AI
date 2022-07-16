# Mancala AI

See `Mancala rules.pdf`

After compile, run with:\
`java Game <arg1> <arg2>`

`arg1`/`arg2` can be:\
0 : Human player\
1 : Random bot\
2 : AI bot\

e.g. for a human to play against a random bot, run:\
`java Game 0 1`

The AI bot uses the minimax algorithm with alpha/beta pruning. The static evaluation function could use some improvement.
