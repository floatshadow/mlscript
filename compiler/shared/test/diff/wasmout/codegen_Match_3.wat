(module 
  (func $log (import "console" "log") (param i32))
  (memory $memory 1)
  (global (mut i32) i32.const 0) 
  (export "main" (func $main))
  (func $main (local $tmp01 i32)(local $f i32)(local $a i32)(local $tmp0 i32)(local $l i32)(local $h i32)(local $d i32)(local $j i32)(local $app_result i32)
    block $entry
      i32.const 51
      local.set $tmp0
      i32.const 51
      local.set $tmp01
      block $c
        block $i
          block $e
            block $k
              block $g
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
                local.set $app_result
                local.get $app_result
                local.set $h
                local.get $h
                local.set $a
                br $c
              end
              i32.const 54
              call $log
              i32.const 0
              local.set $app_result
              local.get $app_result
              local.set $l
              local.get $l
              local.set $a
              br $c
            end
            i32.const 51
            call $log
            i32.const 0
            local.set $app_result
            local.get $app_result
            local.set $f
            local.get $f
            local.set $a
            br $c
          end
          i32.const 53
          call $log
          i32.const 0
          local.set $app_result
          local.get $app_result
          local.set $j
          local.get $j
          local.set $a
          br $c
        end
        i32.const 55
        call $log
        i32.const 0
        local.set $app_result
        local.get $app_result
        local.set $d
        local.get $d
        local.set $a
      end
    end
  )
  (start $main)
)