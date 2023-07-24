(module 
  (import "system" "mem" (memory 100))
  (func $log (import "system" "log") (param i32 i32))
  (global (mut i32) i32.const 0) 

  (func $get1 (result i32) 
    i32.const 1
  )
  (export "main" (func $main))
  (func $main (local $a1 i32)(local $b1 i32)
    call $get1
    local.set $a1
    local.get $a1
    i32.const 2
    call $log
    i32.const 0
    local.set $b1
  )
  (start $main)
)