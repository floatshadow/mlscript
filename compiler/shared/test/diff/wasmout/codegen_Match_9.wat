(module 
  (func $log (import "console" "log") (param i32))
  (memory $memory 1)
  (global (mut i32) i32.const 0) 
  (export "main" (func $main))
  (func $main (local $e i32)(local $res1 i32)(local $a i32)(local $b i32)(local $p i32)(local $x1 i32)(local $r i32)(local $tmp9 i32)(local $j i32)(local $app_result i32)(local $g i32)(local $l i32)
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
      i32.const 1
      i32.store
      local.get $a
      local.set $x1
      block $d
        block $o
          block $q
            block $f
              block $Match_x1
                local.get $x1
                i32.load
                i32.const 1
                i32.eq
                i32.const 1
                i32.mul
                local.get $x1
                i32.load
                i32.const 2
                i32.eq
                i32.const 2
                i32.mul
                i32.add
                local.get $x1
                i32.load
                i32.const 0
                i32.eq
                i32.const 3
                i32.mul
                i32.add
                br_table 3 2 1 0
              end
              local.get $x1
              local.set $tmp9
              local.get $tmp9
              i32.const 4
              i32.add
              i32.load
              local.set $g
              local.get $g
              local.set $x1
              block $i
                block $m
                  block $k
                    block $Match_x1
                      local.get $x1
                      i32.const 1
                      i32.eq
                      i32.const 1
                      i32.mul
                      local.get $x1
                      i32.const 0
                      i32.eq
                      i32.const 2
                      i32.mul
                      i32.add
                      br_table 2 1 0
                    end
                    i32.const 0
                    call $log
                    i32.const 0
                    local.set $app_result
                    local.get $app_result
                    local.set $l
                    local.get $l
                    local.set $b
                    br $i
                  end
                  i32.const 1
                  call $log
                  i32.const 0
                  local.set $app_result
                  br $i
                end
                i32.const 40
                call $log
                i32.const 0
                local.set $app_result
                local.get $app_result
                local.set $j
                local.get $j
                local.set $b
              end
              br $d
            end
            i32.const 30
            call $log
            i32.const 0
            local.set $app_result
            local.get $app_result
            local.set $r
            local.get $r
            local.set $b
            br $d
          end
          i32.const 20
          call $log
          i32.const 0
          local.set $app_result
          local.get $app_result
          local.set $p
          local.get $p
          local.set $b
          br $d
        end
        i32.const 40
        call $log
        i32.const 0
        local.set $app_result
        local.get $app_result
        local.set $e
        local.get $e
        local.set $b
      end
    end
    local.get $b
    local.set $res1
    local.get $res1
    call $log
    i32.const 0
    local.set $app_result
  )
  (start $main)
)