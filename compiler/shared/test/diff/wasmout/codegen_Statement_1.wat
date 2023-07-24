(module 
  (import "system" "mem" (memory 100))
  (func $log (import "system" "log") (param i32 i32))
  (global (mut i32) i32.const 0) 
  (export "main" (func $main))
  (func $main (local $e i32)(local $j1 i32)(local $f1 i32)(local $n1 i32)(local $a i32)(local $b1 i32)(local $m i32)(local $i i32)(local $o1 i32)(local $h1 i32)(local $c i32)(local $x11 i32)(local $p1 i32)(local $k i32)(local $x21 i32)(local $g i32)(local $s1 i32)(local $r1 i32)(local $l i32)(local $d1 i32)(local $q1 i32)
    i32.const 1
    local.set $x11
    local.get $x11
    local.set $x21
    i32.const 5
    i32.const 5
    i32.add
    local.set $a
    local.get $a
    i32.const 2
    call $log
    i32.const 0
    local.set $b1
    i32.const 5
    i32.const 5
    i32.sub
    local.set $c
    local.get $c
    i32.const 2
    call $log
    i32.const 0
    local.set $d1
    i32.const 5
    i32.const 5
    i32.mul
    local.set $e
    local.get $e
    i32.const 2
    call $log
    i32.const 0
    local.set $f1
    i32.const 5
    i32.const 5
    i32.div_s
    local.set $g
    local.get $g
    i32.const 2
    call $log
    i32.const 0
    local.set $h1
    i32.const 5
    i32.const 5
    i32.rem_s
    local.set $i
    local.get $i
    i32.const 2
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
    i32.const 2
    call $log
    i32.const 0
    local.set $n1
    i32.const 1
    i32.const 1
    call $log
    i32.const 0
    local.set $o1
    i32.const 0
    i32.const 1
    call $log
    i32.const 0
    local.set $p1
    global.get 0
    i32.const 0
    i32.add
    i32.const 97
    i32.store8
    global.get 0
    i32.const 1
    i32.add
    i32.const 98
    i32.store8
    global.get 0
    i32.const 2
    i32.add
    i32.const 99
    i32.store8
    global.get 0
    i32.const 3
    i32.add
    i32.const 100
    i32.store8
    global.get 0
    i32.const 4
    i32.add
    i32.const 101
    i32.store8
    global.get 0
    i32.const 5
    i32.add
    i32.const 0
    i32.store8
    global.get 0
    i32.const 6
    i32.add
    i32.const 0
    i32.store8
    global.get 0
    i32.const 7
    i32.add
    i32.const 0
    i32.store8
    global.get 0
    global.get 0
    i32.const 8
    i32.add
    global.set 0
    i32.const 4
    call $log
    i32.const 0
    local.set $q1
    global.get 0
    i32.const 0
    i32.add
    i32.const 117
    i32.store8
    global.get 0
    i32.const 1
    i32.add
    i32.const 110
    i32.store8
    global.get 0
    i32.const 2
    i32.add
    i32.const 105
    i32.store8
    global.get 0
    i32.const 3
    i32.add
    i32.const 116
    i32.store8
    global.get 0
    i32.const 4
    i32.add
    i32.const 40
    i32.store8
    global.get 0
    i32.const 5
    i32.add
    i32.const 41
    i32.store8
    global.get 0
    i32.const 6
    i32.add
    i32.const 0
    i32.store8
    global.get 0
    i32.const 7
    i32.add
    i32.const 0
    i32.store8
    global.get 0
    global.get 0
    i32.const 8
    i32.add
    global.set 0
    i32.const 4
    call $log
    i32.const 0
    local.set $r1
    local.get $r1
    i32.const 0
    call $log
    i32.const 0
    local.set $s1
  )
  (start $main)
)