(module 
  (func $log (import "console" "log") (param i32))
  (memory $memory 1)
  (global (mut i32) i32.const 0) 
  (export "main" (func $main))
  (func $main (local $tmp01 i32)(local $e1 i32)(local $j i32)(local $n1 i32)(local $a i32)(local $m i32)(local $tmp0 i32)(local $g i32)(local $p i32)(local $h1 i32)(local $k1 i32)(local $q1 i32)(local $d i32)
    block $entry
      i32.const 51
      local.set $tmp0
      i32.const 51
      local.set $tmp01
      block $c
        block $l
          block $f
            block $o
              block $i
                block $Match_tmp01
                  local.get $tmp01
                  i32.const 53
                  i32.eq
                  i32.const 1
                  i32.mul
                  local.get $tmp01
                  i32.const 51
                  i32.eq
                  i32.const 2
                  i32.mul
                  i32.add
                  local.get $tmp01
                  i32.const 54
                  i32.eq
                  i32.const 3
                  i32.mul
                  i32.add
                  local.get $tmp01
                  i32.const 52
                  i32.eq
                  i32.const 4
                  i32.mul
                  i32.add
                  br_table 4 3 2 1 0
                end
                i32.const 52
                call $log
                i32.const 0
                local.set $k1
                local.get $k1
                local.set $j
                local.get $j
                local.set $a
                br $c
              end
              i32.const 54
              call $log
              i32.const 0
              local.set $q1
              local.get $q1
              local.set $p
              local.get $p
              local.set $a
              br $c
            end
            i32.const 51
            call $log
            i32.const 0
            local.set $h1
            local.get $h1
            local.set $g
            local.get $g
            local.set $a
            br $c
          end
          i32.const 53
          call $log
          i32.const 0
          local.set $n1
          local.get $n1
          local.set $m
          local.get $m
          local.set $a
          br $c
        end
        i32.const 55
        call $log
        i32.const 0
        local.set $e1
        local.get $e1
        local.set $d
        local.get $d
        local.set $a
      end
    end
  )
  (start $main)
)