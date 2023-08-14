(module 
  (import "system" "mem" (memory 100))
  (func $logI32 (import "system" "logI32") (param i32 i32))
  (func $logI64 (import "system" "logI64") (param i64))
  (func $logF64 (import "system" "logF64") (param f64))
  (global (mut i32) i32.const 0) 
  (export "main_0" (func $main_0))
  (func $main_0 (local $a1 i32) (local $c1 i32) (local $b1 i64) 
    global.get 0
    i32.const 0
    i32.add
    i32.const 70
    i32.store8
    global.get 0
    i32.const 1
    i32.add
    i32.const 117
    i32.store8
    global.get 0
    i32.const 2
    i32.add
    i32.const 110
    i32.store8
    global.get 0
    i32.const 3
    i32.add
    i32.const 99
    i32.store8
    global.get 0
    i32.const 4
    i32.add
    i32.const 116
    i32.store8
    global.get 0
    i32.const 5
    i32.add
    i32.const 105
    i32.store8
    global.get 0
    i32.const 6
    i32.add
    i32.const 111
    i32.store8
    global.get 0
    i32.const 7
    i32.add
    i32.const 110
    i32.store8
    global.get 0
    i32.const 8
    i32.add
    i32.const 32
    i32.store8
    global.get 0
    i32.const 9
    i32.add
    i32.const 84
    i32.store8
    global.get 0
    i32.const 10
    i32.add
    i32.const 101
    i32.store8
    global.get 0
    i32.const 11
    i32.add
    i32.const 115
    i32.store8
    global.get 0
    i32.const 12
    i32.add
    i32.const 116
    i32.store8
    global.get 0
    i32.const 13
    i32.add
    i32.const 32
    i32.store8
    global.get 0
    i32.const 14
    i32.add
    i32.const 49
    i32.store8
    global.get 0
    i32.const 15
    i32.add
    i32.const 0
    i32.store8
    global.get 0
    global.get 0
    i32.const 16
    i32.add
    global.set 0
    i32.const 4
    call $logI32
    i32.const 0
    local.set $a1
    i64.const 2
    call $incr
    local.set $b1
    local.get $b1
    call $logI64
    i32.const 0
    local.set $c1
  )
  (export "main_1" (func $main_1))
  (func $main_1 (local $a1 i32) (local $c1 i32) (local $b1 i64) 
    global.get 0
    i32.const 0
    i32.add
    i32.const 70
    i32.store8
    global.get 0
    i32.const 1
    i32.add
    i32.const 117
    i32.store8
    global.get 0
    i32.const 2
    i32.add
    i32.const 110
    i32.store8
    global.get 0
    i32.const 3
    i32.add
    i32.const 99
    i32.store8
    global.get 0
    i32.const 4
    i32.add
    i32.const 116
    i32.store8
    global.get 0
    i32.const 5
    i32.add
    i32.const 105
    i32.store8
    global.get 0
    i32.const 6
    i32.add
    i32.const 111
    i32.store8
    global.get 0
    i32.const 7
    i32.add
    i32.const 110
    i32.store8
    global.get 0
    i32.const 8
    i32.add
    i32.const 32
    i32.store8
    global.get 0
    i32.const 9
    i32.add
    i32.const 84
    i32.store8
    global.get 0
    i32.const 10
    i32.add
    i32.const 101
    i32.store8
    global.get 0
    i32.const 11
    i32.add
    i32.const 115
    i32.store8
    global.get 0
    i32.const 12
    i32.add
    i32.const 116
    i32.store8
    global.get 0
    i32.const 13
    i32.add
    i32.const 32
    i32.store8
    global.get 0
    i32.const 14
    i32.add
    i32.const 50
    i32.store8
    global.get 0
    i32.const 15
    i32.add
    i32.const 0
    i32.store8
    global.get 0
    global.get 0
    i32.const 16
    i32.add
    global.set 0
    i32.const 4
    call $logI32
    i32.const 0
    local.set $a1
    i64.const 10
    call $fib
    local.set $b1
    local.get $b1
    call $logI64
    i32.const 0
    local.set $c1
  )
  (export "main_2" (func $main_2))
  (func $main_2 (local $a1 i32) (local $c1 i32) (local $b1 i64) 
    global.get 0
    i32.const 0
    i32.add
    i32.const 70
    i32.store8
    global.get 0
    i32.const 1
    i32.add
    i32.const 117
    i32.store8
    global.get 0
    i32.const 2
    i32.add
    i32.const 110
    i32.store8
    global.get 0
    i32.const 3
    i32.add
    i32.const 99
    i32.store8
    global.get 0
    i32.const 4
    i32.add
    i32.const 116
    i32.store8
    global.get 0
    i32.const 5
    i32.add
    i32.const 105
    i32.store8
    global.get 0
    i32.const 6
    i32.add
    i32.const 111
    i32.store8
    global.get 0
    i32.const 7
    i32.add
    i32.const 110
    i32.store8
    global.get 0
    i32.const 8
    i32.add
    i32.const 32
    i32.store8
    global.get 0
    i32.const 9
    i32.add
    i32.const 84
    i32.store8
    global.get 0
    i32.const 10
    i32.add
    i32.const 101
    i32.store8
    global.get 0
    i32.const 11
    i32.add
    i32.const 115
    i32.store8
    global.get 0
    i32.const 12
    i32.add
    i32.const 116
    i32.store8
    global.get 0
    i32.const 13
    i32.add
    i32.const 32
    i32.store8
    global.get 0
    i32.const 14
    i32.add
    i32.const 51
    i32.store8
    global.get 0
    i32.const 15
    i32.add
    i32.const 0
    i32.store8
    global.get 0
    global.get 0
    i32.const 16
    i32.add
    global.set 0
    i32.const 4
    call $logI32
    i32.const 0
    local.set $a1
    call $get1
    local.set $b1
    local.get $b1
    call $logI64
    i32.const 0
    local.set $c1
  )
  (export "main_3" (func $main_3))
  (func $main_3 (local $e i32) (local $f i32) (local $i1 i32) (local $b i32) (local $g i32) (local $h1 i64) (local $d i32) (local $c i32) (local $a1 i32) 
    global.get 0
    i32.const 0
    i32.add
    i32.const 70
    i32.store8
    global.get 0
    i32.const 1
    i32.add
    i32.const 117
    i32.store8
    global.get 0
    i32.const 2
    i32.add
    i32.const 110
    i32.store8
    global.get 0
    i32.const 3
    i32.add
    i32.const 99
    i32.store8
    global.get 0
    i32.const 4
    i32.add
    i32.const 116
    i32.store8
    global.get 0
    i32.const 5
    i32.add
    i32.const 105
    i32.store8
    global.get 0
    i32.const 6
    i32.add
    i32.const 111
    i32.store8
    global.get 0
    i32.const 7
    i32.add
    i32.const 110
    i32.store8
    global.get 0
    i32.const 8
    i32.add
    i32.const 32
    i32.store8
    global.get 0
    i32.const 9
    i32.add
    i32.const 84
    i32.store8
    global.get 0
    i32.const 10
    i32.add
    i32.const 101
    i32.store8
    global.get 0
    i32.const 11
    i32.add
    i32.const 115
    i32.store8
    global.get 0
    i32.const 12
    i32.add
    i32.const 116
    i32.store8
    global.get 0
    i32.const 13
    i32.add
    i32.const 32
    i32.store8
    global.get 0
    i32.const 14
    i32.add
    i32.const 52
    i32.store8
    global.get 0
    i32.const 15
    i32.add
    i32.const 0
    i32.store8
    global.get 0
    global.get 0
    i32.const 16
    i32.add
    global.set 0
    i32.const 4
    call $logI32
    i32.const 0
    local.set $a1
    global.get 0
    local.set $b
    global.get 0
    i32.const 0
    i32.store
    global.get 0
    i32.const 4
    i32.add
    global.set 0
    global.get 0
    local.set $c
    global.get 0
    i32.const 1
    i32.store
    global.get 0
    i32.const 16
    i32.add
    global.set 0
    local.get $c
    i32.const 4
    i32.add
    i64.const 7
    i64.store
    local.get $c
    i32.const 12
    i32.add
    local.get $b
    i32.store
    global.get 0
    local.set $d
    global.get 0
    i32.const 1
    i32.store
    global.get 0
    i32.const 16
    i32.add
    global.set 0
    local.get $d
    i32.const 4
    i32.add
    i64.const 5
    i64.store
    local.get $d
    i32.const 12
    i32.add
    local.get $c
    i32.store
    global.get 0
    local.set $e
    global.get 0
    i32.const 1
    i32.store
    global.get 0
    i32.const 16
    i32.add
    global.set 0
    local.get $e
    i32.const 4
    i32.add
    i64.const 3
    i64.store
    local.get $e
    i32.const 12
    i32.add
    local.get $d
    i32.store
    global.get 0
    local.set $f
    global.get 0
    i32.const 1
    i32.store
    global.get 0
    i32.const 16
    i32.add
    global.set 0
    local.get $f
    i32.const 4
    i32.add
    i64.const 1
    i64.store
    local.get $f
    i32.const 12
    i32.add
    local.get $e
    i32.store
    local.get $f
    local.set $g
    local.get $g
    call $sum
    local.set $h1
    local.get $h1
    call $logI64
    i32.const 0
    local.set $i1
  )
  (export "main_4" (func $main_4))
  (func $main_4 (local $l1 i32) (local $j1 i32) (local $e1 i32) (local $f1 i32) (local $b1 i32) (local $g1 i32) (local $h1 i64) (local $a1 i32) (local $d1 i32) (local $k i64) (local $c1 i32) (local $i1 i32) 
    global.get 0
    i32.const 0
    i32.add
    i32.const 70
    i32.store8
    global.get 0
    i32.const 1
    i32.add
    i32.const 117
    i32.store8
    global.get 0
    i32.const 2
    i32.add
    i32.const 110
    i32.store8
    global.get 0
    i32.const 3
    i32.add
    i32.const 99
    i32.store8
    global.get 0
    i32.const 4
    i32.add
    i32.const 116
    i32.store8
    global.get 0
    i32.const 5
    i32.add
    i32.const 105
    i32.store8
    global.get 0
    i32.const 6
    i32.add
    i32.const 111
    i32.store8
    global.get 0
    i32.const 7
    i32.add
    i32.const 110
    i32.store8
    global.get 0
    i32.const 8
    i32.add
    i32.const 32
    i32.store8
    global.get 0
    i32.const 9
    i32.add
    i32.const 84
    i32.store8
    global.get 0
    i32.const 10
    i32.add
    i32.const 101
    i32.store8
    global.get 0
    i32.const 11
    i32.add
    i32.const 115
    i32.store8
    global.get 0
    i32.const 12
    i32.add
    i32.const 116
    i32.store8
    global.get 0
    i32.const 13
    i32.add
    i32.const 32
    i32.store8
    global.get 0
    i32.const 14
    i32.add
    i32.const 53
    i32.store8
    global.get 0
    i32.const 15
    i32.add
    i32.const 0
    i32.store8
    global.get 0
    global.get 0
    i32.const 16
    i32.add
    global.set 0
    i32.const 4
    call $logI32
    i32.const 0
    local.set $a1
    call $getString
    local.set $b1
    local.get $b1
    i32.const 4
    call $logI32
    i32.const 0
    local.set $c1
    call $getTrue
    local.set $d1
    local.get $d1
    i32.const 1
    call $logI32
    i32.const 0
    local.set $e1
    call $getFalse
    local.set $f1
    local.get $f1
    i32.const 1
    call $logI32
    i32.const 0
    local.set $g1
    call $getZero
    local.set $h1
    local.get $h1
    call $logI64
    i32.const 0
    local.set $i1
    call $getClass
    local.set $j1
    local.get $j1
    i32.const 4
    i32.add
    i64.load
    local.set $k
    local.get $k
    call $logI64
    i32.const 0
    local.set $l1
  )
  (export "main_5" (func $main_5))
  (func $main_5 (local $e1 i32) (local $b1 i64) (local $c1 i64) (local $a1 i32) (local $d i64) 
    global.get 0
    i32.const 0
    i32.add
    i32.const 70
    i32.store8
    global.get 0
    i32.const 1
    i32.add
    i32.const 117
    i32.store8
    global.get 0
    i32.const 2
    i32.add
    i32.const 110
    i32.store8
    global.get 0
    i32.const 3
    i32.add
    i32.const 99
    i32.store8
    global.get 0
    i32.const 4
    i32.add
    i32.const 116
    i32.store8
    global.get 0
    i32.const 5
    i32.add
    i32.const 105
    i32.store8
    global.get 0
    i32.const 6
    i32.add
    i32.const 111
    i32.store8
    global.get 0
    i32.const 7
    i32.add
    i32.const 110
    i32.store8
    global.get 0
    i32.const 8
    i32.add
    i32.const 32
    i32.store8
    global.get 0
    i32.const 9
    i32.add
    i32.const 84
    i32.store8
    global.get 0
    i32.const 10
    i32.add
    i32.const 101
    i32.store8
    global.get 0
    i32.const 11
    i32.add
    i32.const 115
    i32.store8
    global.get 0
    i32.const 12
    i32.add
    i32.const 116
    i32.store8
    global.get 0
    i32.const 13
    i32.add
    i32.const 32
    i32.store8
    global.get 0
    i32.const 14
    i32.add
    i32.const 54
    i32.store8
    global.get 0
    i32.const 15
    i32.add
    i32.const 0
    i32.store8
    global.get 0
    global.get 0
    i32.const 16
    i32.add
    global.set 0
    i32.const 4
    call $logI32
    i32.const 0
    local.set $a1
    i64.const 3
    call $Square
    local.set $b1
    i64.const 2
    call $Cube
    local.set $c1
    local.get $b1
    local.get $c1
    i64.add
    local.set $d
    local.get $d
    call $logI64
    i32.const 0
    local.set $e1
  )
  (export "main_6" (func $main_6))
  (func $main_6 (local $a1 i32) (local $c1 i32) (local $b1 i64) 
    global.get 0
    i32.const 0
    i32.add
    i32.const 70
    i32.store8
    global.get 0
    i32.const 1
    i32.add
    i32.const 117
    i32.store8
    global.get 0
    i32.const 2
    i32.add
    i32.const 110
    i32.store8
    global.get 0
    i32.const 3
    i32.add
    i32.const 99
    i32.store8
    global.get 0
    i32.const 4
    i32.add
    i32.const 116
    i32.store8
    global.get 0
    i32.const 5
    i32.add
    i32.const 105
    i32.store8
    global.get 0
    i32.const 6
    i32.add
    i32.const 111
    i32.store8
    global.get 0
    i32.const 7
    i32.add
    i32.const 110
    i32.store8
    global.get 0
    i32.const 8
    i32.add
    i32.const 32
    i32.store8
    global.get 0
    i32.const 9
    i32.add
    i32.const 84
    i32.store8
    global.get 0
    i32.const 10
    i32.add
    i32.const 101
    i32.store8
    global.get 0
    i32.const 11
    i32.add
    i32.const 115
    i32.store8
    global.get 0
    i32.const 12
    i32.add
    i32.const 116
    i32.store8
    global.get 0
    i32.const 13
    i32.add
    i32.const 32
    i32.store8
    global.get 0
    i32.const 14
    i32.add
    i32.const 55
    i32.store8
    global.get 0
    i32.const 15
    i32.add
    i32.const 0
    i32.store8
    global.get 0
    global.get 0
    i32.const 16
    i32.add
    global.set 0
    i32.const 4
    call $logI32
    i32.const 0
    local.set $a1
    i64.const 6
    i64.const 7
    call $addition
    local.set $b1
    local.get $b1
    call $logI64
    i32.const 0
    local.set $c1
  )

  (func $incr (param $x i64) (result i64) (local $b i64) 
    local.get $x
    i64.const 1
    i64.add
    local.set $b
    local.get $b
  )

  (func $fib (param $x i64) (result i64) (local $e i64) (local $n i64) (local $j i64) (local $f i64) (local $g1 i64) (local $i1 i64) (local $b i64) (local $l i64) (local $h i64) 
    block $fib
      block $d
        block $k
          block $m
            block $Match_x
              local.get $x
              i64.const 0
              i64.eq
              i32.const 1
              i32.mul
              local.get $x
              i64.const 1
              i64.eq
              i32.const 2
              i32.mul
              i32.add
              br_table 2 1 0
            end
            i64.const 1
            local.set $n
            local.get $n
            local.set $b
            br $d
          end
          i64.const 0
          local.set $l
          local.get $l
          local.set $b
          br $d
        end
        i64.const -1
        local.get $x
        i64.add
        local.set $f
        local.get $f
        call $fib
        local.set $g1
        i64.const -2
        local.get $x
        i64.add
        local.set $h
        local.get $h
        call $fib
        local.set $i1
        local.get $g1
        local.get $i1
        i64.add
        local.set $j
        local.get $j
        local.set $e
        local.get $e
        local.set $b
      end
    end
    local.get $b
  )

  (func $get1 (result i64) 
    i64.const 1
  )

  (func $sum (param $x i32) (result i64) (local $n i64) (local $t i32) (local $m i32) (local $p i64) (local $h1 i64) (local $h i64) (local $r i64) (local $q1 i64) (local $k i64) (local $o i32) 
    block $sum
      local.get $x
      i32.load
      i32.const 1
      i32.eq
      if
        local.get $x
        local.set $m
        local.get $m
        i32.const 4
        i32.add
        i64.load
        local.set $n
        local.get $n
        local.set $h1
        local.get $m
        i32.const 12
        i32.add
        i32.load
        local.set $o
        local.get $o
        local.set $t
        local.get $t
        call $sum
        local.set $q1
        local.get $h1
        local.get $q1
        i64.add
        local.set $r
        local.get $r
        local.set $p
        local.get $p
        local.set $h
      else
        i64.const 0
        local.set $k
        local.get $k
        local.set $h
      end
    end
    local.get $h
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

  (func $getZero (result i64) 
    i64.const 0
  )

  (func $getClass (result i32) (local $b i32) 
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
    i64.const 10
    i64.store
    local.get $b
  )

  (func $Square (param $x i64) (result i64) (local $b i64) 
    local.get $x
    local.get $x
    i64.mul
    local.set $b
    local.get $b
  )

  (func $Cube (param $x i64) (result i64) (local $b i64) (local $c i64) 
    local.get $x
    local.get $x
    i64.mul
    local.set $b
    local.get $b
    local.get $x
    i64.mul
    local.set $c
    local.get $c
  )

  (func $addition (param $x i64) (param $y i64) (result i64) (local $b i64) 
    local.get $x
    local.get $y
    i64.add
    local.set $b
    local.get $b
  )
  (export "main" (func $main))
  (func $main 
    call $main_0
    call $main_1
    call $main_2
    call $main_3
    call $main_4
    call $main_5
    call $main_6
  )
)