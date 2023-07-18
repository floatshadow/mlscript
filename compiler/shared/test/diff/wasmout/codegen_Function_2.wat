(module 

  (memory $memory 1)
  (global (mut i32) i32.const 0) 
  (export "main" (func $main))
  (func $main (local $app_result i32)
    i32.const 2
    call $fib
    i32.const 0
    local.set $app_result
  )
  (start $main)
)