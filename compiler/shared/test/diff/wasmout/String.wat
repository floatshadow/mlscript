(module 
  (import "system" "mem" (memory 100))
  (func $logI32 (import "system" "logI32") (param i32 i32))
  (func $logI64 (import "system" "logI64") (param i64))
  (func $logF64 (import "system" "logF64") (param f64))
  (global (mut i32) i32.const 0) 
  (export "main_0" (func $main_0))
  (func $main_0 (local $a1 i32) (local $b i32) (local $c1 i32) 
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
    i32.const 114
    i32.store8
    global.get 0
    i32.const 3
    i32.add
    i32.const 105
    i32.store8
    global.get 0
    i32.const 4
    i32.add
    i32.const 110
    i32.store8
    global.get 0
    i32.const 5
    i32.add
    i32.const 103
    i32.store8
    global.get 0
    i32.const 6
    i32.add
    i32.const 32
    i32.store8
    global.get 0
    i32.const 7
    i32.add
    i32.const 84
    i32.store8
    global.get 0
    i32.const 8
    i32.add
    i32.const 101
    i32.store8
    global.get 0
    i32.const 9
    i32.add
    i32.const 115
    i32.store8
    global.get 0
    i32.const 10
    i32.add
    i32.const 116
    i32.store8
    global.get 0
    i32.const 11
    i32.add
    i32.const 32
    i32.store8
    global.get 0
    i32.const 12
    i32.add
    i32.const 49
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
    global.get 0
    i32.const 0
    i32.add
    i32.const 99
    i32.store8
    global.get 0
    i32.const 1
    i32.add
    i32.const 0
    i32.store8
    global.get 0
    i32.const 2
    i32.add
    i32.const 0
    i32.store8
    global.get 0
    i32.const 3
    i32.add
    i32.const 0
    i32.store8
    global.get 0
    global.get 0
    i32.const 4
    i32.add
    global.set 0
    local.set $b
    local.get $b
    i32.const 4
    call $logI32
    i32.const 0
    local.set $c1
  )
  (export "main_1" (func $main_1))
  (func $main_1 (local $a1 i32) (local $c1 i32) (local $b1 i32) 
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
    i32.const 114
    i32.store8
    global.get 0
    i32.const 3
    i32.add
    i32.const 105
    i32.store8
    global.get 0
    i32.const 4
    i32.add
    i32.const 110
    i32.store8
    global.get 0
    i32.const 5
    i32.add
    i32.const 103
    i32.store8
    global.get 0
    i32.const 6
    i32.add
    i32.const 32
    i32.store8
    global.get 0
    i32.const 7
    i32.add
    i32.const 84
    i32.store8
    global.get 0
    i32.const 8
    i32.add
    i32.const 101
    i32.store8
    global.get 0
    i32.const 9
    i32.add
    i32.const 115
    i32.store8
    global.get 0
    i32.const 10
    i32.add
    i32.const 116
    i32.store8
    global.get 0
    i32.const 11
    i32.add
    i32.const 32
    i32.store8
    global.get 0
    i32.const 12
    i32.add
    i32.const 50
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
    call $getString
    local.set $b1
    local.get $b1
    i32.const 4
    call $logI32
    i32.const 0
    local.set $c1
  )

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
  (func $main 
    call $main_0
    call $main_1
  )
)