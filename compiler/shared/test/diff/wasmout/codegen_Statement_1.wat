(module 
  (func $log (import "console" "log") (param i32))
  (memory $memory 1)
  (global (mut i32) i32.const 0) 
  (export "main" (func $main))
  (func $main (local $e i32)(local $j1 i32)(local $f1 i32)(local $n1 i32)(local $a i32)(local $b1 i32)(local $m i32)(local $i i32)(local $h1 i32)(local $c i32)(local $x11 i32)(local $d1 i32)(local $k i32)(local $x21 i32)(local $g i32)(local $l i32)
    i32.const 1
    local.set $x11
    local.get $x11
    local.set $x21
    i32.const 5
    i32.const 5
    i32.add
    local.set $a
    local.get $a
    call $log
    i32.const 0
    local.set $b1
    i32.const 5
    i32.const 5
    i32.sub
    local.set $c
    local.get $c
    call $log
    i32.const 0
    local.set $d1
    i32.const 5
    i32.const 5
    i32.mul
    local.set $e
    local.get $e
    call $log
    i32.const 0
    local.set $f1
    i32.const 5
    i32.const 5
    i32.div_s
    local.set $g
    local.get $g
    call $log
    i32.const 0
    local.set $h1
    i32.const 5
    i32.const 5
    i32.rem_s
    local.set $i
    local.get $i
    call $log
    i32.const 0
    local.set $j1
    i32.const 5
    i32.const 5
    i32.eq
    local.set $k
    i32.const 5
    i32.const 5
    i32.lt_s
    local.set $l
    i32.const 5
    i32.const 5
    i32.le_s
    local.set $m
    i32.const -99
    call $log
    i32.const 0
    local.set $n1
  )
  (start $main)
)