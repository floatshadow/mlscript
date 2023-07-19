(module 
  (func $log (import "console" "log") (param i32))
  (memory $memory 1)
  (global (mut i32) i32.const 0) 
  (export "main" (func $main))
  (func $main (local $n i32)(local $f1 i32)(local $a i32)(local $i1 i32)(local $b i32)(local $o1 i32)(local $x1 i32)(local $h i32)(local $k i32)(local $e i32)(local $l1 i32)
    block $entry
      global.get 0
      local.set $a
      global.get 0
      i32.const 2
      i32.store
      global.get 0
      i32.const 4
      i32.add
      global.set 0
      local.get $a
      local.set $x1
      block $d
        block $g
          block $j
            block $m
              block $Match_x1
                local.get $x1
                i32.load
                i32.const 0
                i32.eq
                i32.const 1
                i32.mul
                local.get $x1
                i32.load
                i32.const 1
                i32.eq
                i32.const 2
                i32.mul
                i32.add
                local.get $x1
                i32.load
                i32.const 2
                i32.eq
                i32.const 3
                i32.mul
                i32.add
                br_table 3 2 1 0
              end
              i32.const 30
              call $log
              i32.const 0
              local.set $o1
              local.get $o1
              local.set $n
              local.get $n
              local.set $b
              br $d
            end
            i32.const 20
            call $log
            i32.const 0
            local.set $l1
            local.get $l1
            local.set $k
            local.get $k
            local.set $b
            br $d
          end
          i32.const 10
          call $log
          i32.const 0
          local.set $i1
          local.get $i1
          local.set $h
          local.get $h
          local.set $b
          br $d
        end
        i32.const 40
        call $log
        i32.const 0
        local.set $f1
        local.get $f1
        local.set $e
        local.get $e
        local.set $b
      end
    end
  )
  (start $main)
)