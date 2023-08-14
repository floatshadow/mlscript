(module 
  (import "system" "mem" (memory 100))
  (func $logI32 (import "system" "logI32") (param i32 i32))
  (func $logI64 (import "system" "logI64") (param i64))
  (func $logF64 (import "system" "logF64") (param f64))
  (global (mut i32) i32.const 0) 
  (export "main_0" (func $main_0))
  (func $main_0 (local $e1 i32) (local $c1 i32) (local $b f64) (local $a1 i32) (local $d f64) 
    global.get 0
    i32.const 0
    i32.add
    i32.const 70
    i32.store8
    global.get 0
    i32.const 1
    i32.add
    i32.const 108
    i32.store8
    global.get 0
    i32.const 2
    i32.add
    i32.const 111
    i32.store8
    global.get 0
    i32.const 3
    i32.add
    i32.const 97
    i32.store8
    global.get 0
    i32.const 4
    i32.add
    i32.const 116
    i32.store8
    global.get 0
    i32.const 5
    i32.add
    i32.const 32
    i32.store8
    global.get 0
    i32.const 6
    i32.add
    i32.const 84
    i32.store8
    global.get 0
    i32.const 7
    i32.add
    i32.const 101
    i32.store8
    global.get 0
    i32.const 8
    i32.add
    i32.const 115
    i32.store8
    global.get 0
    i32.const 9
    i32.add
    i32.const 116
    i32.store8
    global.get 0
    i32.const 10
    i32.add
    i32.const 32
    i32.store8
    global.get 0
    i32.const 11
    i32.add
    i32.const 49
    i32.store8
    global.get 0
    i32.const 12
    i32.add
    i32.const 0
    i32.store8
    global.get 0
    i32.const 13
    i32.add
    i32.const 0
    i32.store8
    global.get 0
    i32.const 14
    i32.add
    i32.const 0
    i32.store8
    global.get 0
    i32.const 15
    i32.add
    i32.const 0
    i32.store8
    global.get 0
    global.get 0
    i32.const 16
    i32.add
    global.set 0
    i32.const 4
    call $logI32
    i32.const 0
    local.set $a1
    f64.const 0.5
    local.set $b
    local.get $b
    call $logF64
    i32.const 0
    local.set $c1
    f64.const -1.5
    local.set $d
    local.get $d
    call $logF64
    i32.const 0
    local.set $e1
  )
  (export "main_1" (func $main_1))
  (func $main_1 (local $e f64) (local $j1 i32) (local $f1 i32) (local $n1 i32) (local $b1 i32) (local $m i32) (local $i f64) (local $h1 i32) (local $d1 i32) (local $p1 i32) (local $k i32) (local $o i32) (local $g f64) (local $l i32) (local $c f64) (local $a1 i32) 
    global.get 0
    i32.const 0
    i32.add
    i32.const 70
    i32.store8
    global.get 0
    i32.const 1
    i32.add
    i32.const 108
    i32.store8
    global.get 0
    i32.const 2
    i32.add
    i32.const 111
    i32.store8
    global.get 0
    i32.const 3
    i32.add
    i32.const 97
    i32.store8
    global.get 0
    i32.const 4
    i32.add
    i32.const 116
    i32.store8
    global.get 0
    i32.const 5
    i32.add
    i32.const 32
    i32.store8
    global.get 0
    i32.const 6
    i32.add
    i32.const 84
    i32.store8
    global.get 0
    i32.const 7
    i32.add
    i32.const 101
    i32.store8
    global.get 0
    i32.const 8
    i32.add
    i32.const 115
    i32.store8
    global.get 0
    i32.const 9
    i32.add
    i32.const 116
    i32.store8
    global.get 0
    i32.const 10
    i32.add
    i32.const 32
    i32.store8
    global.get 0
    i32.const 11
    i32.add
    i32.const 50
    i32.store8
    global.get 0
    i32.const 12
    i32.add
    i32.const 0
    i32.store8
    global.get 0
    i32.const 13
    i32.add
    i32.const 0
    i32.store8
    global.get 0
    i32.const 14
    i32.add
    i32.const 0
    i32.store8
    global.get 0
    i32.const 15
    i32.add
    i32.const 0
    i32.store8
    global.get 0
    global.get 0
    i32.const 16
    i32.add
    global.set 0
    i32.const 4
    call $logI32
    i32.const 0
    local.set $a1
    f64.const -0.5
    call $logF64
    i32.const 0
    local.set $b1
    f64.const 0.5
    f64.const 0.5
    f64.sub
    local.set $c
    local.get $c
    call $logF64
    i32.const 0
    local.set $d1
    f64.const 0.5
    f64.const 0.5
    f64.add
    local.set $e
    local.get $e
    call $logF64
    i32.const 0
    local.set $f1
    f64.const 0.5
    f64.const 0.5
    f64.mul
    local.set $g
    local.get $g
    call $logF64
    i32.const 0
    local.set $h1
    f64.const 0.5
    f64.const 0.5
    f64.div
    local.set $i
    local.get $i
    call $logF64
    i32.const 0
    local.set $j1
    f64.const 0.5
    f64.const 0.5
    f64.eq
    local.set $k
    local.get $k
    local.set $l
    f64.const 0.5
    f64.const 0.5
    f64.eq
    local.set $m
    local.get $m
    i32.const 1
    call $logI32
    i32.const 0
    local.set $n1
    f64.const 0.5
    f64.const 0.1
    f64.eq
    local.set $o
    local.get $o
    i32.const 1
    call $logI32
    i32.const 0
    local.set $p1
  )
  (export "main_2" (func $main_2))
  (func $main_2 (local $e1 i32) (local $f1 f64) (local $b1 i64) (local $g1 i32) (local $h1 f64) (local $a1 i32) (local $d1 i64) (local $c1 i32) (local $i1 i32) 
    global.get 0
    i32.const 0
    i32.add
    i32.const 70
    i32.store8
    global.get 0
    i32.const 1
    i32.add
    i32.const 108
    i32.store8
    global.get 0
    i32.const 2
    i32.add
    i32.const 111
    i32.store8
    global.get 0
    i32.const 3
    i32.add
    i32.const 97
    i32.store8
    global.get 0
    i32.const 4
    i32.add
    i32.const 116
    i32.store8
    global.get 0
    i32.const 5
    i32.add
    i32.const 32
    i32.store8
    global.get 0
    i32.const 6
    i32.add
    i32.const 84
    i32.store8
    global.get 0
    i32.const 7
    i32.add
    i32.const 101
    i32.store8
    global.get 0
    i32.const 8
    i32.add
    i32.const 115
    i32.store8
    global.get 0
    i32.const 9
    i32.add
    i32.const 116
    i32.store8
    global.get 0
    i32.const 10
    i32.add
    i32.const 32
    i32.store8
    global.get 0
    i32.const 11
    i32.add
    i32.const 51
    i32.store8
    global.get 0
    i32.const 12
    i32.add
    i32.const 0
    i32.store8
    global.get 0
    i32.const 13
    i32.add
    i32.const 0
    i32.store8
    global.get 0
    i32.const 14
    i32.add
    i32.const 0
    i32.store8
    global.get 0
    i32.const 15
    i32.add
    i32.const 0
    i32.store8
    global.get 0
    global.get 0
    i32.const 16
    i32.add
    global.set 0
    i32.const 4
    call $logI32
    i32.const 0
    local.set $a1
    call $getInt1
    local.set $b1
    local.get $b1
    call $logI64
    i32.const 0
    local.set $c1
    i64.const 2
    call $getInt2
    local.set $d1
    local.get $d1
    call $logI64
    i32.const 0
    local.set $e1
    call $getFloat1
    local.set $f1
    local.get $f1
    call $logF64
    i32.const 0
    local.set $g1
    f64.const 4.0
    call $getFloat2
    local.set $h1
    local.get $h1
    call $logF64
    i32.const 0
    local.set $i1
  )

  (func $getInt1 (result i64) 
    i64.const 1
  )

  (func $getInt2 (param $integer i64) (result i64) 
    local.get $integer
  )

  (func $getFloat1 (result f64) 
    f64.const 3.0
  )

  (func $getFloat2 (param $floating f64) (result f64) 
    local.get $floating
  )
  (export "main" (func $main))
  (func $main 
    call $main_0
    call $main_1
    call $main_2
  )
)