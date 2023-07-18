(module 
  (func $log (import "console" "log") (param i32))
  (memory $memory 1)
  (global (mut i32) i32.const 0) 
  (export "main" (func $main))
  (func $main (local $n i32)(local $f i32)(local $a i32)(local $m i32)(local $x1 i32)(local $h i32)(local $q1 i32)(local $p1 i32)(local $k i32)(local $g i32)(local $y1 i32)(local $o i32)(local $d i32)
    block $entry
      i32.const 1
      i32.const 0
      i32.and
      local.set $g
      local.get $g
      i32.const 1
      i32.eq
      if
        i32.const 0
        local.set $f
        local.get $f
        local.set $a
      else
        i32.const 10
        local.set $d
        local.get $d
        local.set $a
      end
    end
    block $b
      local.get $a
      local.set $x1
      local.get $x1
      i32.const 10
      i32.add
      local.set $n
      local.get $n
      i32.const 10
      i32.eq
      local.set $o
      local.get $o
      i32.const 1
      i32.eq
      if
        i32.const 100
        local.set $m
        local.get $m
        local.set $h
      else
        i32.const 200
        local.set $k
        local.get $k
        local.set $h
      end
    end
    local.get $h
    local.set $y1
    local.get $x1
    call $log
    i32.const 0
    local.set $p1
    local.get $y1
    call $log
    i32.const 0
    local.set $q1
  )
  (start $main)
)