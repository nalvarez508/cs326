% Nick Alvarez / CS326 / Spring 2020 / HW7
% See comments about Problem 5

% 1a. isSet (S)
isSet([]).
isSet([_]).
isSet([H|T]):- not(member(H,T)),isSet(T). % Check for duplicates.

% 1b. subset(A,S)
subset([], _).
subset([H|T], S):- member(H, S), subset(T, S). % If S is member of Head, continue on.

% 1c. union(A,B,C)
union([], B, B).
union([H|T], B, C):- member(H, B), union(T, B, C). % If B is member of head, skip it, continue on.
union([H|T], B, [H|C]):- not(member(H,B)), union(T, B, C). % B is not a member of head, add head to result.

% 1d. intersection(A,B,C)
intersection([], B, []).
intersection([H|T], B, C):- not(member(H,B)), intersection(T, B, C). % B is not a member of head, continue on.
intersection([H|T], B, [H|C]):- member(H,B), intersection(T, B, C). % If B is a member of head, add head to result.

% 2. tally(E,L,N)
tally(_, [], 0).
tally(E, [E|T], N):- !, tally(E,T,X), N is X+1. % Head is an Element, increment our counter.
tally(E, [_|T], N):- tally(E,T,N). % Head is not our Element.

% 3. subst(X,Y,L,L1)
subst(_, _, [], []).
subst(X, Y, [H|T], [H2|T2]):- X=H, Y=H2, subst(X,Y,T,T2). % Head and X are the same, so replace Head2 with Y.
subst(X, Y, [H|T], [H2|T2]):- H2 is H, subst(X,Y,T,T2). % Head and X are not the same, so replace Head2 with Head.

% 4. insert(X,L,L1)
insert(X, [], [X]).
insert(X, [H|T], [H|T2]):- X>=H, insert(X,T,T2). % If X is greater than the head, continue on.
insert(X, [H|T], [X|[H|T]]):- X<H. % X is less than head, insert it.

% 5. flatten(A,B) // Defined as my_flatten as Prolog already has flatten built in and was causing "false" to be displayed
my_flatten([], []).
my_flatten([A], [A]).
my_flatten([H0|T0], L):- flatten(H0, L1), flatten(T0, L2), append(L1,L2,L). % Flattens head into List1, flattens tail into List2. Recursive calls.