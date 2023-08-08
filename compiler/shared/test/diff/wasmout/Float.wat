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
  (export "main_1" (func $main_1))
  (func $main_1 (local $l1 i32)(local $e1 i32)(local $j i32)(local $m i32)(local $g1 i32)(local $b f64)(local $y1 i32)(local $k i32)(local $d f64)(local $n1 i32)(local $f f64)(local $c1 i32)(local $i1 i32)(local $h f64)(local $a1 i32)
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
    f64.const 0.5
    f64.const 0.5
    f64.eq
    local.set $j
    local.get $j
    local.set $y1
    f64.const 0.5
    f64.const 0.5
    f64.eq
    local.set $k
    local.get $k
    i32.const 1
    call $logI32
    i32.const 0
    local.set $l1
    f64.const 0.5
    f64.const 0.1
    f64.eq
    local.set $m
    local.get $m
    i32.const 1
    call $logI32
    i32.const 0
    local.set $n1
  )
  (export "main_2" (func $main_2))
  (func $main_2 (local $e1 f64)(local $f1 i32)(local $b1 i32)(local $g1 f64)(local $c1 i32)(local $h1 i32)(local $a1 i32)(local $d1 i32)
    call $getInt1
    local.set $a1
    local.get $a1
    i32.const 2
    call $logI32
    i32.const 0
    local.set $b1
    i32.const 2
    call $getInt2
    local.set $c1
    local.get $c1
    i32.const 2
    call $logI32
    i32.const 0
    local.set $d1
    call $getFloat1
    local.set $e1
    local.get $e1
    call $logF64
    i32.const 0
    local.set $f1
    f64.const 4.0
    call $getFloat2
    local.set $g1
    local.get $g1
    call $logF64
    i32.const 0
    local.set $h1
  )

  (func $getInt1 (result i32) 
    i32.const 1
  )

  (func $getInt2 (param $integer i32) (result i32) 
    local.get $integer
  )

  (func $getFloat1 (result f64) 
    f64.const 3.0
  )

  (func $getFloat2 (param $floating f64) (result f64) 
    local.get $floating
  )
  (export "main" (func $main))
  (func $main 
    call $main_0
    call $main_1
    call $main_2
  )
)