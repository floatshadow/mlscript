(module 
  (func $log (import "console" "log") (param i32))
  (memory $memory 1)
  (global (mut i32) i32.const 0) 
  (export "main" (func $main))
  (func $main (local $u i32)(local $v1 i32)(local $i1 i32)(local $s1 i32)(local $x1 i32)(local $h i32)(local $r i32)(local $k i32)(local $o i32)(local $e i32)(local $l1 i32)
    i32.const 1
    local.set $x1
    local.get $x1
    i32.const 1
    i32.eq
    if
      block $m
        local.get $x1
        i32.const 1
        i32.eq
        if
          i32.const 10
          call $log
          i32.const 0
          local.set $v1
          local.get $v1
          local.set $u
          local.get $u
          local.set $o
        else
          i32.const 5
          call $log
          i32.const 0
          local.set $s1
          local.get $s1
          local.set $r
          local.get $r
          local.set $o
        end
      end
    else
      block $c
        local.get $x1
        i32.const 1
        i32.eq
        if
          i32.const -10
          call $log
          i32.const 0
          local.set $l1
          local.get $l1
          local.set $k
          local.get $k
          local.set $e
        else
          i32.const -5
          call $log
          i32.const 0
          local.set $i1
          local.get $i1
          local.set $h
          local.get $h
          local.set $e
        end
      end
    end
  )
  (start $main)
)