(module 
  (import "system" "mem" (memory 100))
  (func $log (import "system" "log") (param i32 i32))
  (global (mut i32) i32.const 0) 

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
  (export "main" (func $main))
  (func $main (local $a1 i32)(local $b1 i32)
    i32.const 10
    call $fib
    local.set $a1
    local.get $a1
    i32.const 2
    call $log
    i32.const 0
    local.set $b1
  )
  (start $main)
)