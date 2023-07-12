(module 
  (func $log (import "console" "log") (param i32))
  (memory $memory 1)
  (global (mut i32) i32.const 0) 
  (export "main" (func $main))
  (func $main (local $a i32)(local $m i32)(local $b i32)(local $g i32)(local $tmp1 i32)(local $o i32)(local $e i32)(local $l1 i32)(local $j i32)(local $app_result i32)
    block $entry
      block $entry
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
        i32.const 100
        i32.store
        local.get $a
        local.set $l1
        local.get $l1
        i32.load
        i32.const 0
        i32.eq
        if
          local.get $l1
          i32.const 4
          i32.add
          i32.load
          local.set $g
          local.get $g
          local.set $tmp1
          block $d
            block $i
              block $k
                block $n
                  block $Match_tmp1
                    local.get $tmp1
                    i32.const 100
                    i32.eq
                    i32.const 1
                    i32.mul
                    local.get $tmp1
                    i32.const 101
                    i32.eq
                    i32.const 2
                    i32.mul
                    i32.add
                    local.get $tmp1
                    i32.const 102
                    i32.eq
                    i32.const 3
                    i32.mul
                    i32.add
                    br_table 3 2 1 0
                  end
                  i32.const 102
                  call $log
                  i32.const 0
                  local.set $app_result
                  local.get $app_result
                  local.set $o
                  local.get $o
                  local.set $b
                  br $d
                end
                i32.const 101
                call $log
                i32.const 0
                local.set $app_result
                local.get $app_result
                local.set $m
                local.get $m
                local.set $b
                br $d
              end
              i32.const 100
              call $log
              i32.const 0
              local.set $app_result
              local.get $app_result
              local.set $j
              local.get $j
              local.set $b
              br $d
            end
            i32.const 103
            call $log
            i32.const 0
            local.set $app_result
            local.get $app_result
            local.set $e
            local.get $e
            local.set $b
          end
        else
        end
      end
      i32.const 103
      call $log
      i32.const 0
      local.set $app_result
      local.get $app_result
      local.set $e
      local.get $e
      local.set $b
    end
  )
  (start $main)
)