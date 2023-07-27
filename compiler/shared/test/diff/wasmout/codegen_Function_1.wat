(module 
  (import "system" "mem" (memory 100))
  (func $log (import "system" "log") (param i32 i32))
  (global (mut i32) i32.const 0) 

  (func $incr (param $x i32) (result i32) (local $a i32)
    local.get $x
    i32.const 1
    i32.add
    local.set $a
    local.get $a
  )
  (export "main" (func $main))
  (func $main (local $a1 i32)(local $b1 i32)
    i32.const 2
    call $incr
    local.set $a1
    local.get $a1
    i32.const 2
    call $log
    i32.const 0
    local.set $b1
  )
  (start $main)
)