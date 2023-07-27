(module 
  (import "system" "mem" (memory 100))
  (func $log (import "system" "log") (param i32 i32))
  (global (mut i32) i32.const 0) 

  (func $Square (param $x i32) (result i32) (local $a i32)
    local.get $x
    local.get $x
    i32.mul
    local.set $a
    local.get $a
  )

  (func $Cube (param $x i32) (result i32) (local $a i32)(local $b i32)
    local.get $x
    local.get $x
    i32.mul
    local.set $a
    local.get $a
    local.get $x
    i32.mul
    local.set $b
    local.get $b
  )
  (export "main" (func $main))
  (func $main (local $a1 i32)(local $c i32)(local $d1 i32)(local $b1 i32)
    i32.const 3
    call $Square
    local.set $a1
    i32.const 2
    call $Cube
    local.set $b1
    local.get $a1
    local.get $b1
    i32.add
    local.set $c
    local.get $c
    i32.const 2
    call $log
    i32.const 0
    local.set $d1
  )
  (start $main)
)