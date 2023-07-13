(module 

  (memory $memory 1)
  (global (mut i32) i32.const 0) 
  (export "main" (func $main))
  (func $main (local $a i32)(local $b i32)(local $y1 i32)(local $x1 i32)
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
    i32.const 101
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
    i32.const 201
    i32.store
    local.get $b
    i32.const 8
    i32.add
    i32.const 202
    i32.store
    local.get $b
    local.set $y1
  )
  (start $main)
)