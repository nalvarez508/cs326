(define (corresp L1 L2)
  (cond
    ((null? L1) 0)
    ((null? L2) 0)
    (else (if (equal? (car L1) (car L2))
              (+ 1 (corresp (cdr L1) (cdr L2)))
              (+ 0 (corresp (cdr L1) (cdr L2)))))))