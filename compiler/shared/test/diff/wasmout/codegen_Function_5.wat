(module 
  (import "system" "mem" (memory 100))
  (func $log (import "system" "log") (param i32 i32))
  (global (mut i32) i32.const 0) 

  (func $getString (result i32) 
    global.get 0
    i32.const 0
    i32.add
    i32.const 77
    i32.store8
    global.get 0
    i32.const 1
    i32.add
    i32.const 76
    i32.store8
    global.get 0
    i32.const 2
    i32.add
    i32.const 115
    i32.store8
    global.get 0
    i32.const 3
    i32.add
    i32.const 99
    i32.store8
    global.get 0
    i32.const 4
    i32.add
    i32.const 114
    i32.store8
    global.get 0
    i32.const 5
    i32.add
    i32.const 105
    i32.store8
    global.get 0
    i32.const 6
    i32.add
    i32.const 112
    i32.store8
    global.get 0
    i32.const 7
    i32.add
    i32.const 116
    i32.store8
    global.get 0
    i32.const 8
    i32.add
    i32.const 0
    i32.store8
    global.get 0
    i32.const 9
    i32.add
    i32.const 0
    i32.store8
    global.get 0
    i32.const 10
    i32.add
    i32.const 0
    i32.store8
    global.get 0
    i32.const 11
    i32.add
    i32.const 0
    i32.store8
    global.get 0
    global.get 0
    i32.const 12
    i32.add
    global.set 0
  )

  (func $getTrue (result i32) 
    i32.const 1
  )

  (func $getFalse (result i32) 
    i32.const 0
  )

  (func $getZero (result i32) 
    i32.const 0
  )

  (func $getClass (result i32) (local $a i32)
    global.get 0
    local.set $a
    global.get 0
    i32.const 0
    i32.store
    global.get 0
    i32.const 8
    i32.add
    global.set 0
    local.get $a
    i32.const 4
    i32.add
    i32.const 10
    i32.store
    local.get $a
  )
  (export "main" (func $main))
  (func $main (local $e1 i32)(local $j i32)(local $f1 i32)(local $b1 i32)(local $g1 i32)(local $h1 i32)(local $k1 i32)(local $a1 i32)(local $d1 i32)(local $c1 i32)(local $i1 i32)
    call $getString
    local.set $a1
    local.get $a1
    i32.const 4
    call $log
    i32.const 0
    local.set $b1
    call $getTrue
    local.set $c1
    local.get $c1
    i32.const 1
    call $log
    i32.const 0
    local.set $d1
    call $getFalse
    local.set $e1
    local.get $e1
    i32.const 1
    call $log
    i32.const 0
    local.set $f1
    call $getZero
    local.set $g1
    local.get $g1
    i32.const 2
    call $log
    i32.const 0
    local.set $h1
    call $getClass
    local.set $i1
    local.get $i1
    i32.const 4
    i32.add
    i32.load
    local.set $j
    local.get $j
    i32.const 2
    call $log
    i32.const 0
    local.set $k1
  )
  (start $main)
)