(module 
  (import "system" "mem" (memory 100))
  (func $log (import "system" "log") (param i32 i32))
  (global (mut i32) i32.const 0) 

  (func $sum (param $x i32) (result i32) (local $t i32)(local $m i32)(local $i i32)(local $l i32)(local $h1 i32)(local $k i32)(local $o i32)(local $n1 i32)(local $f i32)
    block $sum
      local.get $x
      i32.load
      i32.const 1
      i32.eq
      if
        local.get $x
        i32.const 8
        i32.add
        i32.load
        local.set $k
        local.get $k
        local.set $h1
        local.get $x
        i32.const 4
        i32.add
        i32.load
        local.set $l
        local.get $l
        local.set $t
        local.get $t
        call $sum
        local.set $n1
        local.get $h1
        local.get $n1
        i32.add
        local.set $o
        local.get $o
        local.set $m
        local.get $m
        local.set $f
      else
        i32.const 0
        local.set $i
        local.get $i
        local.set $f
      end
    end
    local.get $f
  )
  (export "main" (func $main))
  (func $main (local $e i32)(local $a i32)(local $list1 i32)(local $b i32)(local $c i32)(local $q1 i32)(local $p1 i32)(local $d i32)
    global.get 0
    local.set $a
    global.get 0
    i32.const 0
    i32.store
    global.get 0
    i32.const 4
    i32.add
    global.set 0
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
    i32.const 7
    i32.store
    local.get $b
    i32.const 8
    i32.add
    local.get $a
    i32.store
    global.get 0
    local.set $c
    global.get 0
    i32.const 1
    i32.store
    global.get 0
    i32.const 12
    i32.add
    global.set 0
    local.get $c
    i32.const 4
    i32.add
    i32.const 5
    i32.store
    local.get $c
    i32.const 8
    i32.add
    local.get $b
    i32.store
    global.get 0
    local.set $d
    global.get 0
    i32.const 1
    i32.store
    global.get 0
    i32.const 12
    i32.add
    global.set 0
    local.get $d
    i32.const 4
    i32.add
    i32.const 3
    i32.store
    local.get $d
    i32.const 8
    i32.add
    local.get $c
    i32.store
    global.get 0
    local.set $e
    global.get 0
    i32.const 1
    i32.store
    global.get 0
    i32.const 12
    i32.add
    global.set 0
    local.get $e
    i32.const 4
    i32.add
    i32.const 1
    i32.store
    local.get $e
    i32.const 8
    i32.add
    local.get $d
    i32.store
    local.get $e
    local.set $list1
    local.get $list1
    call $sum
    local.set $p1
    local.get $p1
    i32.const 2
    call $log
    i32.const 0
    local.set $q1
  )
  (start $main)
)