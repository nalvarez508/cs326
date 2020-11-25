(define (remove-last L)
  (cond
    ((null? L) L)
    (else (if (null? (cdr (cdr L)))
              (cons (car L) '())
              (cons (car L) (remove-last (cdr L)))))))