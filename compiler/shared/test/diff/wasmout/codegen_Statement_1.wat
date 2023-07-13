(module 
  (func $log (import "console" "log") (param i32))
  (memory $memory 1)
  (global (mut i32) i32.const 0) 
  (export "main" (func $main))
  (func $main (local $e i32)(local $app_result i32)(local $f i32)(local $a i32)(local $b i32)(local $g i32)(local $x11 i32)(local $c i32)(local $h i32)(local $x21 i32)(local $d i32)
    i32.const 1
    local.set $x11
    local.get $x11
    local.set $x21
    i32.const 5
    i32.const 5
    i32.add
    local.set $a
    local.get $a
    call $log
    i32.const 0
    local.set $app_result
    i32.const 5
    i32.const 5
    i32.sub
    local.set $b
    local.get $b
    call $log
    i32.const 0
    local.set $app_result
    i32.const 5
    i32.const 5
    i32.mul
    local.set $c
    local.get $c
    call $log
    i32.const 0
    local.set $app_result
    i32.const 5
    i32.const 5
    i32.div_s
    local.set $d
    local.get $d
    call $log
    i32.const 0
    local.set $app_result
    i32.const 5
    i32.const 5
    i32.rem_s
    local.set $e
    local.get $e
    call $log
    i32.const 0
    local.set $app_result
    i32.const 5
    i32.const 5
    i32.eq
    local.set $f
    i32.const 5
    i32.const 5
    i32.lt_s
    local.set $g
    i32.const 5
    i32.const 5
    i32.le_s
    local.set $h
    i32.const -99
    call $log
    i32.const 0
    local.set $app_result
  )
  (start $main)
)