add_last(X, [], [X]).
add_last(X, L, L1) :- append(L, [X], L1).