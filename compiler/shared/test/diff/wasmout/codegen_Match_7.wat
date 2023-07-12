(module 
  (func $log (import "console" "log") (param i32))
  (memory $memory 1)
  (global (mut i32) i32.const 0) 
  (export "main" (func $main))
  (func $main (local $e i32)(local $m i32)(local $p i32)(local $x1 i32)(local $h i32)(local $r i32)(local $j i32)(local $app_result i32)
    i32.const 1
    local.set $x1
    local.get $x1
    i32.const 1
    i32.eq
    if
      block $k
        local.get $x1
        i32.const 1
        i32.eq
        if
          i32.const 10
          call $log
          i32.const 0
          local.set $app_result
          local.get $app_result
          local.set $r
          local.get $r
          local.set $m
        else
          i32.const 5
          call $log
          i32.const 0
          local.set $app_result
          local.get $app_result
          local.set $p
          local.get $p
          local.set $m
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
          local.set $app_result
          local.get $app_result
          local.set $j
          local.get $j
          local.set $e
        else
          i32.const -5
          call $log
          i32.const 0
          local.set $app_result
          local.get $app_result
          local.set $h
          local.get $h
          local.set $e
        end
      end
    end
  )
  (start $main)
)