(module 
  (func $log (import "console" "log") (param i32))
  (memory $memory 1)
  (global (mut i32) i32.const 0) 
  (export "main" (func $main))
  (func $main (local $string11 i32)(local $app_result i32)
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
    local.set $string11
    local.get $string11
    call $log
    i32.const 0
    local.set $app_result
  )
  (start $main)
)