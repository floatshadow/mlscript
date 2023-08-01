(module 
  (import "system" "mem" (memory 100))
  (func $log (import "system" "log") (param i32 i32))
  (global (mut i32) i32.const 0) 
  (export "main_0" (func $main_0))
  (func $main_0 (local $e i32)(local $f1 i32)(local $a i32)(local $z1 i32)(local $b i32)(local $x1 i32)(local $h i32)(local $d i32)(local $c1 i32)(local $i1 i32)(local $g i32)(local $y1 i32)
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
    local.set $x1
    local.get $x1
    i32.const 4
    i32.add
    i32.load
    local.set $b
    local.get $b
    i32.const 2
    call $log
    i32.const 0
    local.set $c1
    global.get 0
    local.set $d
    global.get 0
    i32.const 1
    i32.store
    global.get 0
    i32.const 8
    i32.add
    global.set 0
    local.get $d
    i32.const 4
    i32.add
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
    i32.store
    local.get $d
    local.set $y1
    local.get $y1
    i32.const 4
    i32.add
    i32.load
    local.set $e
    local.get $e
    i32.const 4
    call $log
    i32.const 0
    local.set $f1
    global.get 0
    local.set $g
    global.get 0
    i32.const 2
    i32.store
    global.get 0
    i32.const 8
    i32.add
    global.set 0
    local.get $g
    i32.const 4
    i32.add
    i32.const 1
    i32.store
    local.get $g
    local.set $z1
    local.get $z1
    i32.const 4
    i32.add
    i32.load
    local.set $h
    local.get $h
    i32.const 1
    call $log
    i32.const 0
    local.set $i1
  )
  (export "main_1" (func $main_1))
  (func $main_1 (local $a i32)(local $b i32)(local $c1 i32)
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
    i32.const 111
    i32.store
    local.get $a
    i32.const 4
    i32.add
    i32.load
    local.set $b
    local.get $b
    i32.const 2
    call $log
    i32.const 0
    local.set $c1
  )
  (export "main_2" (func $main_2))
  (func $main_2 (local $e i32)(local $f1 i32)(local $a i32)(local $i1 i32)(local $b i32)(local $x1 i32)(local $d1 i32)(local $g i32)(local $y1 i32)(local $c i32)(local $h i32)
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
    i32.const 98
    i32.store
    local.get $a
    local.set $x1
    global.get 0
    local.set $b
    global.get 0
    i32.const 1
    i32.store
    global.get 0
    i32.const 12
    i32.add
    global.set 0
    local.get $b
    i32.const 4
    i32.add
    i32.const 99
    i32.store
    local.get $b
    i32.const 8
    i32.add
    local.get $x1
    i32.store
    local.get $b
    local.set $y1
    local.get $x1
    i32.const 4
    i32.add
    i32.load
    local.set $c
    local.get $c
    i32.const 2
    call $log
    i32.const 0
    local.set $d1
    local.get $y1
    i32.const 4
    i32.add
    i32.load
    local.set $e
    local.get $e
    i32.const 2
    call $log
    i32.const 0
    local.set $f1
    local.get $y1
    i32.const 8
    i32.add
    i32.load
    local.set $g
    local.get $g
    i32.const 4
    i32.add
    i32.load
    local.set $h
    local.get $h
    i32.const 2
    call $log
    i32.const 0
    local.set $i1
  )
  (export "main_3" (func $main_3))
  (func $main_3 (local $e i32)(local $f1 i32)(local $a i32)(local $b i32)(local $c i32)(local $d1 i32)(local $g i32)(local $y1 i32)(local $h1 i32)(local $x1 i32)
    global.get 0
    local.set $a
    global.get 0
    i32.const 1
    i32.store
    global.get 0
    i32.const 8
    i32.add
    global.set 0
    local.get $a
    i32.const 4
    i32.add
    i32.const 101
    i32.store
    local.get $a
    local.set $x1
    global.get 0
    local.set $b
    global.get 0
    i32.const 0
    i32.store
    global.get 0
    i32.const 12
    i32.add
    global.set 0
    local.get $b
    i32.const 4
    i32.add
    i32.const 201
    i32.store
    local.get $b
    i32.const 8
    i32.add
    i32.const 202
    i32.store
    local.get $b
    local.set $y1
    local.get $x1
    i32.const 4
    i32.add
    i32.load
    local.set $c
    local.get $c
    i32.const 2
    call $log
    i32.const 0
    local.set $d1
    local.get $y1
    i32.const 4
    i32.add
    i32.load
    local.set $e
    local.get $e
    i32.const 2
    call $log
    i32.const 0
    local.set $f1
    local.get $y1
    i32.const 8
    i32.add
    i32.load
    local.set $g
    local.get $g
    i32.const 2
    call $log
    i32.const 0
    local.set $h1
  )
)