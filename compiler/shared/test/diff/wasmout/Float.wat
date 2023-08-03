(module 
  (import "system" "mem" (memory 100))
  (func $logI32 (import "system" "logI32") (param i32 i32))
  (func $logF64 (import "system" "logF64") (param f64))
  (global (mut i32) i32.const 0) 
  (export "main_0" (func $main_0))
  (func $main_0 (local $a1 i32)(local $fp21 f64)(local $fp11 f64)(local $b1 i32)
    f64.const 0.5
    local.set $fp11
    local.get $fp11
    call $logF64
    i32.const 0
    local.set $a1
    f64.const -1.5
    local.set $fp21
    local.get $fp21
    call $logF64
    i32.const 0
    local.set $b1
  )
  (export "main" (func $main))
  (func $main 
    call $main_0
  )
)