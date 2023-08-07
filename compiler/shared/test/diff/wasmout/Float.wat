(module 
  (import "system" "mem" (memory 100))
  (func $logI32 (import "system" "logI32") (param i32 i32))
  (func $logF64 (import "system" "logF64") (param f64))
  (global (mut i32) i32.const 0) 
  (export "main_0" (func $main_0))
  (func $main_0 (local $e1 i32)(local $f f64)(local $g1 i32)(local $b f64)(local $d f64)(local $c1 i32)(local $i1 i32)(local $h f64)(local $a1 i32)
    f64.const -0.5
    call $logF64
    i32.const 0
    local.set $a1
    f64.const 0.5
    f64.const 0.5
    f64.sub
    local.set $b
    local.get $b
    call $logF64
    i32.const 0
    local.set $c1
    f64.const 0.5
    f64.const 0.5
    f64.add
    local.set $d
    local.get $d
    call $logF64
    i32.const 0
    local.set $e1
    f64.const 0.5
    f64.const 0.5
    f64.mul
    local.set $f
    local.get $f
    call $logF64
    i32.const 0
    local.set $g1
    f64.const 0.5
    f64.const 0.5
    f64.div
    local.set $h
    local.get $h
    call $logF64
    i32.const 0
    local.set $i1
  )
  (export "main_1" (func $main_1))
  (func $main_1 (local $a1 i32)(local $fp21 f64)(local $fp11 f64)(local $b1 i32)
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
    call $main_1
  )
)