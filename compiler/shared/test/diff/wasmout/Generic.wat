(module 
  (import "system" "mem" (memory 100))

  (global (mut i32) i32.const 0) 
  (export "main_0" (func $main_0))
  (func $main_0 (local $a i32)(local $b i32)
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
    i32.const 0
    i32.store
    local.get $a
    i32.const 4
    i32.add
    i32.load
    local.set $b
  )
)