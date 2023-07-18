(module 
  (func $log (import "console" "log") (param i32))
  (memory $memory 1)
  (global (mut i32) i32.const 0) 
  (export "main" (func $main))
  (func $main (local $a i32)(local $b i32)(local $c1 i32)
    global.get 0
    local.set $a
    global.get 0
    i32.const 0
    i32.store
    global.get 0
    i32.const 8
    i32.add
    global.set 0
    local.get $a
    i32.const 4
    i32.add
    i32.const 111
    i32.store
    local.get $a
    i32.const 4
    i32.add
    i32.load
    local.set $b
    local.get $b
    call $log
    i32.const 0
    local.set $c1
  )
  (start $main)
)