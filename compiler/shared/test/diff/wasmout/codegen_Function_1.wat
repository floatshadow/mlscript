(module 
  (func $log (import "console" "log") (param i32))
  (memory $memory 1)
  (global (mut i32) i32.const 0) 
  (export "main" (func $main))
  (func $main (local $app_result i32)
    i32.const 2
    call $incr
    i32.const 0
    local.set $app_result
    local.get $app_result
    call $log
    i32.const 0
    local.set $app_result
  )
  (start $main)
)