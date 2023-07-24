(module 
  (import "system" "mem" (memory 100))
  (func $log (import "system" "log") (param i32 i32))
  (global (mut i32) i32.const 0) 
  (export "main" (func $main))
  (func $main (local $f i32)(local $a i32)(local $g1 i32)(local $x1 i32)(local $d i32)
    block $entry
      i32.const 1
      i32.const 1
      i32.eq
      if
        i32.const 1
        local.set $f
        local.get $f
        local.set $a
      else
        i32.const 0
        local.set $d
        local.get $d
        local.set $a
      end
    end
    local.get $a
    local.set $x1
    local.get $x1
    i32.const 2
    call $log
    i32.const 0
    local.set $g1
  )
  (start $main)
)