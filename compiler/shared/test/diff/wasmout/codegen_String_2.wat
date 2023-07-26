(module 
  (import "system" "mem" (memory 100))
  (func $log (import "system" "log") (param i32 i32))
  (global (mut i32) i32.const 0) 

  (func $getString (result i32) 
    global.get 0
    i32.const 0
    i32.add
    i32.const 77
    i32.store8
    global.get 0
    i32.const 1
    i32.add
    i32.const 76
    i32.store8
    global.get 0
    i32.const 2
    i32.add
    i32.const 115
    i32.store8
    global.get 0
    i32.const 3
    i32.add
    i32.const 99
    i32.store8
    global.get 0
    i32.const 4
    i32.add
    i32.const 114
    i32.store8
    global.get 0
    i32.const 5
    i32.add
    i32.const 105
    i32.store8
    global.get 0
    i32.const 6
    i32.add
    i32.const 112
    i32.store8
    global.get 0
    i32.const 7
    i32.add
    i32.const 116
    i32.store8
    global.get 0
    i32.const 8
    i32.add
    i32.const 0
    i32.store8
    global.get 0
    i32.const 9
    i32.add
    i32.const 0
    i32.store8
    global.get 0
    i32.const 10
    i32.add
    i32.const 0
    i32.store8
    global.get 0
    i32.const 11
    i32.add
    i32.const 0
    i32.store8
    global.get 0
    global.get 0
    i32.const 12
    i32.add
    global.set 0
  )
  (export "main" (func $main))
  (func $main (local $a1 i32)(local $b1 i32)
    call $getString
    local.set $a1
    local.get $a1
    i32.const 4
    call $log
    i32.const 0
    local.set $b1
  )
  (start $main)
)