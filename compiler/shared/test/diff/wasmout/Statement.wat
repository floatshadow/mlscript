(module 
  (import "system" "mem" (memory 100))
  (func $logI32 (import "system" "logI32") (param i32 i32))
  (func $logI64 (import "system" "logI64") (param i64))
  (func $logF64 (import "system" "logF64") (param f64))
  (global (mut i32) i32.const 0) 
  (export "main_0" (func $main_0))
  (func $main_0 (local $n i32) (local $e1 i32) (local $j i64) (local $u1 i32) (local $f i64) (local $m1 i32) (local $g1 i32) (local $v1 i32) (local $i1 i32) (local $b i64) (local $p i32) (local $k1 i32) (local $s1 i32) (local $r1 i32) (local $l i64) (local $c i64) (local $h i64) (local $a1 i32) (local $t1 i32) (local $q1 i32) (local $o i32) (local $d i64) 
    global.get 0
    i32.const 0
    i32.add
    i32.const 83
    i32.store8
    global.get 0
    i32.const 1
    i32.add
    i32.const 116
    i32.store8
    global.get 0
    i32.const 2
    i32.add
    i32.const 97
    i32.store8
    global.get 0
    i32.const 3
    i32.add
    i32.const 116
    i32.store8
    global.get 0
    i32.const 4
    i32.add
    i32.const 101
    i32.store8
    global.get 0
    i32.const 5
    i32.add
    i32.const 109
    i32.store8
    global.get 0
    i32.const 6
    i32.add
    i32.const 101
    i32.store8
    global.get 0
    i32.const 7
    i32.add
    i32.const 110
    i32.store8
    global.get 0
    i32.const 8
    i32.add
    i32.const 116
    i32.store8
    global.get 0
    i32.const 9
    i32.add
    i32.const 32
    i32.store8
    global.get 0
    i32.const 10
    i32.add
    i32.const 84
    i32.store8
    global.get 0
    i32.const 11
    i32.add
    i32.const 101
    i32.store8
    global.get 0
    i32.const 12
    i32.add
    i32.const 115
    i32.store8
    global.get 0
    i32.const 13
    i32.add
    i32.const 116
    i32.store8
    global.get 0
    i32.const 14
    i32.add
    i32.const 32
    i32.store8
    global.get 0
    i32.const 15
    i32.add
    i32.const 49
    i32.store8
    global.get 0
    i32.const 16
    i32.add
    i32.const 0
    i32.store8
    global.get 0
    i32.const 17
    i32.add
    i32.const 0
    i32.store8
    global.get 0
    i32.const 18
    i32.add
    i32.const 0
    i32.store8
    global.get 0
    i32.const 19
    i32.add
    i32.const 0
    i32.store8
    global.get 0
    global.get 0
    i32.const 20
    i32.add
    global.set 0
    i32.const 4
    call $logI32
    i32.const 0
    local.set $a1
    i64.const 1
    local.set $b
    local.get $b
    local.set $c
    i64.const 5
    i64.const 5
    i64.add
    local.set $d
    local.get $d
    call $logI64
    i32.const 0
    local.set $e1
    i64.const 5
    i64.const 5
    i64.sub
    local.set $f
    local.get $f
    call $logI64
    i32.const 0
    local.set $g1
    i64.const 5
    i64.const 5
    i64.mul
    local.set $h
    local.get $h
    call $logI64
    i32.const 0
    local.set $i1
    i64.const 5
    i64.const 5
    i64.div_s
    local.set $j
    local.get $j
    call $logI64
    i32.const 0
    local.set $k1
    i64.const 5
    i64.const 5
    i64.rem_s
    local.set $l
    local.get $l
    call $logI64
    i32.const 0
    local.set $m1
    i64.const 5
    i64.const 5
    i64.eq
    local.set $n
    i64.const 5
    i64.const 5
    i64.lt_s
    local.set $o
    i64.const 5
    i64.const 5
    i64.le_s
    local.set $p
    i64.const -99
    call $logI64
    i32.const 0
    local.set $q1
    i32.const 1
    i32.const 1
    call $logI32
    i32.const 0
    local.set $r1
    i32.const 0
    i32.const 1
    call $logI32
    i32.const 0
    local.set $s1
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
    call $logI32
    i32.const 0
    local.set $t1
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
    call $logI32
    i32.const 0
    local.set $u1
    local.get $u1
    i32.const 0
    call $logI32
    i32.const 0
    local.set $v1
  )
  (export "main" (func $main))
  (func $main 
    call $main_0
  )
)