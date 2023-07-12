(module 
  (func $log (import "console" "log") (param i32))
  (memory $memory 1)
  (global (mut i32) i32.const 0) 
  (export "main" (func $main))
  (func $main (local $e i32)(local $tmp2 i32)(local $length1 i32)(local $u i32)(local $tmp3 i32)(local $m i32)(local $g i32)(local $h i32)(local $app_result i32)(local $y i32)(local $a i32)(local $pos1 i32)(local $q i32)(local $b i32)
    block $entry
      block $entry
        global.get 0
        local.set $a
        global.get 0
        i32.const 0
        i32.store
        global.get 0
        i32.const 12
        i32.add
        global.set 0
        local.get $a
        i32.const 4
        i32.add
        i32.const 5
        i32.store
        local.get $a
        i32.const 4
        i32.add
        i32.const 12
        i32.store
        local.get $a
        local.set $pos1
        local.get $pos1
        i32.load
        i32.const 0
        i32.eq
        if
          local.get $pos1
          i32.const 4
          i32.add
          i32.load
          local.set $g
          local.get $g
          local.set $tmp2
          local.get $pos1
          i32.const 8
          i32.add
          i32.load
          local.set $h
          local.get $h
          local.set $tmp3
          block $d
            block $r
              block $v
                block $n
                  block $j
                    block $Match_tmp2
                      local.get $tmp2
                      i32.const 5
                      i32.eq
                      i32.const 1
                      i32.mul
                      local.get $tmp2
                      i32.const 12
                      i32.eq
                      i32.const 2
                      i32.mul
                      i32.add
                      local.get $tmp2
                      i32.const 4
                      i32.eq
                      i32.const 3
                      i32.mul
                      i32.add
                      local.get $tmp2
                      i32.const 3
                      i32.eq
                      i32.const 4
                      i32.mul
                      i32.add
                      br_table 4 3 2 1 0
                    end
                    local.get $tmp3
                    i32.const 4
                    i32.eq
                    if
                      i32.const 5
                      call $log
                      i32.const 0
                      local.set $app_result
                      local.get $app_result
                      local.set $m
                      local.get $m
                      local.set $b
                    else
                    end
                    br $d
                  end
                  local.get $tmp3
                  i32.const 3
                  i32.eq
                  if
                    i32.const 5
                    call $log
                    i32.const 0
                    local.set $app_result
                    local.get $app_result
                    local.set $q
                    local.get $q
                    local.set $b
                  else
                  end
                  br $d
                end
                local.get $tmp3
                i32.const 5
                i32.eq
                if
                  i32.const 13
                  call $log
                  i32.const 0
                  local.set $app_result
                  local.get $app_result
                  local.set $y
                  local.get $y
                  local.set $b
                else
                end
                br $d
              end
              local.get $tmp3
              i32.const 12
              i32.eq
              if
                i32.const 13
                call $log
                i32.const 0
                local.set $app_result
                local.get $app_result
                local.set $u
                local.get $u
                local.set $b
              else
              end
              br $d
            end
            i32.const 0
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
      local.get $b
      local.set $length1
    end
    i32.const 0
    call $log
    i32.const 0
    local.set $app_result
    local.get $app_result
    local.set $e
    local.get $e
    local.set $b
  )
  (start $main)
)