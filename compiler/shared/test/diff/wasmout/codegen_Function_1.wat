(module 
  (func $log (import "console" "log") (param i32))
  (memory $memory 1)
  (global (mut i32) i32.const 0) 
  (export "main" (func $main))
  (func $main (local $c1 i32)(local $b1 i32)
    i32.const 2
    call $incr
    i32.const 0
    local.set $b1
    local.get $b1
    call $log
    i32.const 0
    local.set $c1
  )
  (start $main)
)