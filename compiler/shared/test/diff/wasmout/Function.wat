(module 
  (import "system" "mem" (memory 100))
  (func $log (import "system" "log") (param i32 i32))
  (global (mut i32) i32.const 0) 
  (export "main_0" (func $main_0))
  (func $main_0 (local $a1 i32)(local $c i32)(local $d1 i32)(local $b1 i32)
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
  (export "main_1" (func $main_1))
  (func $main_1 (local $e1 i32)(local $j i32)(local $f1 i32)(local $b1 i32)(local $g1 i32)(local $h1 i32)(local $k1 i32)(local $a1 i32)(local $d1 i32)(local $c1 i32)(local $i1 i32)
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
  (export "main_2" (func $main_2))
  (func $main_2 (local $e i32)(local $f1 i32)(local $a i32)(local $g1 i32)(local $list1 i32)(local $b i32)(local $c i32)(local $d i32)
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
    local.set $f1
    local.get $f1
    i32.const 2
    call $log
    i32.const 0
    local.set $g1
  )
  (export "main_3" (func $main_3))
  (func $main_3 (local $a1 i32)(local $b1 i32)
    call $get1
    local.set $a1
    local.get $a1
    i32.const 2
    call $log
    i32.const 0
    local.set $b1
  )
  (export "main_4" (func $main_4))
  (func $main_4 (local $a1 i32)(local $b1 i32)
    i32.const 10
    call $fib
    local.set $a1
    local.get $a1
    i32.const 2
    call $log
    i32.const 0
    local.set $b1
  )
  (export "main_5" (func $main_5))
  (func $main_5 (local $a1 i32)(local $b1 i32)
    i32.const 2
    call $incr
    local.set $a1
    local.get $a1
    i32.const 2
    call $log
    i32.const 0
    local.set $b1
  )

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

  (func $sum (param $x i32) (result i32) (local $t i32)(local $m i32)(local $i i32)(local $l i32)(local $h1 i32)(local $k i32)(local $o i32)(local $n1 i32)(local $f i32)
    block $sum
      local.get $x
      i32.load
      i32.const 1
      i32.eq
      if
        local.get $x
        i32.const 4
        i32.add
        i32.load
        local.set $k
        local.get $k
        local.set $h1
        local.get $x
        i32.const 8
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

  (func $get1 (result i32) 
    i32.const 1
  )

  (func $fib (param $x i32) (result i32) (local $e i32)(local $f1 i32)(local $a i32)(local $m i32)(local $i i32)(local $g i32)(local $h1 i32)(local $k i32)(local $d i32)
    block $fib
      block $c
        block $j
          block $l
            block $Match_x
              local.get $x
              i32.const 0
              i32.eq
              i32.const 1
              i32.mul
              local.get $x
              i32.const 1
              i32.eq
              i32.const 2
              i32.mul
              i32.add
              br_table 2 1 0
            end
            i32.const 1
            local.set $m
            local.get $m
            local.set $a
            br $c
          end
          i32.const 0
          local.set $k
          local.get $k
          local.set $a
          br $c
        end
        i32.const -1
        local.get $x
        i32.add
        local.set $e
        local.get $e
        call $fib
        local.set $f1
        i32.const -2
        local.get $x
        i32.add
        local.set $g
        local.get $g
        call $fib
        local.set $h1
        local.get $f1
        local.get $h1
        i32.add
        local.set $i
        local.get $i
        local.set $d
        local.get $d
        local.set $a
      end
    end
    local.get $a
  )

  (func $incr (param $x i32) (result i32) (local $a i32)
    local.get $x
    i32.const 1
    i32.add
    local.set $a
    local.get $a
  )
)