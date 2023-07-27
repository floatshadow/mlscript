(module 
  (import "system" "mem" (memory 100))
  (func $log (import "system" "log") (param i32 i32))
  (global (mut i32) i32.const 0) 
  (export "main" (func $main))
  (func $main (local $e i32)(local $f1 i32)(local $a i32)(local $b i32)(local $c i32)(local $d1 i32)(local $g i32)(local $y1 i32)(local $h1 i32)(local $x1 i32)
    global.get 0
    local.set $a
    global.get 0
    i32.const 1
    i32.store
    global.get 0
    i32.const 8
    i32.add
    global.set 0
    local.get $a
    i32.const 4
    i32.add
    i32.const 101
    i32.store
    local.get $a
    local.set $x1
    global.get 0
    local.set $b
    global.get 0
    i32.const 0
    i32.store
    global.get 0
    i32.const 12
    i32.add
    global.set 0
    local.get $b
    i32.const 4
    i32.add
    i32.const 201
    i32.store
    local.get $b
    i32.const 8
    i32.add
    i32.const 202
    i32.store
    local.get $b
    local.set $y1
    local.get $x1
    i32.const 4
    i32.add
    i32.load
    local.set $c
    local.get $c
    i32.const 2
    call $log
    i32.const 0
    local.set $d1
    local.get $y1
    i32.const 4
    i32.add
    i32.load
    local.set $e
    local.get $e
    i32.const 2
    call $log
    i32.const 0
    local.set $f1
    local.get $y1
    i32.const 8
    i32.add
    i32.load
    local.set $g
    local.get $g
    i32.const 2
    call $log
    i32.const 0
    local.set $h1
  )
  (start $main)
)