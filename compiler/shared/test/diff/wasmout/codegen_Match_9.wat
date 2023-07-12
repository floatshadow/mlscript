(module 
  (func $log (import "console" "log") (param i32))
  (memory $memory 1)
  (global (mut i32) i32.const 0) 
  (export "main" (func $main))
  (func $main (local $e i32)(local $s i32)(local $tmp6 i32)(local $am i32)(local $t i32)(local $u i32)(local $a i32)(local $g i32)(local $tmp52 i32)(local $ak i32)(local $an i32)(local $h i32)(local $tmp8 i32)(local $tmp61 i32)(local $tmp5 i32)(local $tmp71 i32)(local $app_result i32)(local $m i32)(local $bg i32)(local $aw i32)(local $al i32)(local $tmp7 i32)(local $q i32)(local $ac i32)(local $b i32)(local $tmp62 i32)(local $tmp4 i32)(local $ai i32)(local $tmp51 i32)(local $result1 i32)
    block $entry
      block $entry
        global.get 0
        local.set $a
        global.get 0
        i32.const 3
        i32.store
        global.get 0
        i32.const 16
        i32.add
        global.set 0
        local.get $a
        i32.const 4
        i32.add
        i32.const 1
        i32.store
        local.get $a
        i32.const 4
        i32.add
        i32.const 2
        i32.store
        local.get $a
        i32.const 4
        i32.add
        i32.const 3
        i32.store
        local.get $a
        local.set $tmp4
        block $d
          block $r
            block $aj
              block $f
                block $Match_tmp4
                  local.get $tmp4
                  i32.load
                  i32.const 3
                  i32.eq
                  i32.const 1
                  i32.mul
                  local.get $tmp4
                  i32.load
                  i32.const 1
                  i32.eq
                  i32.const 2
                  i32.mul
                  i32.add
                  local.get $tmp4
                  i32.load
                  i32.const 2
                  i32.eq
                  i32.const 3
                  i32.mul
                  i32.add
                  br_table 3 2 1 0
                end
                local.get $tmp4
                i32.const 4
                i32.add
                i32.load
                local.set $g
                local.get $g
                local.set $tmp5
                local.get $tmp4
                i32.const 8
                i32.add
                i32.load
                local.set $h
                local.get $h
                local.set $tmp6
                block $d
                  block $n
                    block $j
                      block $Match_tmp5
                        local.get $tmp5
                        i32.const 1
                        i32.eq
                        i32.const 1
                        i32.mul
                        local.get $tmp5
                        i32.const 0
                        i32.eq
                        i32.const 2
                        i32.mul
                        i32.add
                        br_table 2 1 0
                      end
                      local.get $tmp6
                      i32.const 0
                      i32.eq
                      if
                        i32.const 0
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
                    local.get $tmp6
                    i32.const 2
                    i32.eq
                    if
                      i32.const 1
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
                  i32.const 6
                  call $log
                  i32.const 0
                  local.set $app_result
                  local.get $app_result
                  local.set $e
                  local.get $e
                  local.set $b
                end
                br $d
              end
              local.get $tmp4
              i32.const 4
              i32.add
              i32.load
              local.set $ak
              local.get $ak
              local.set $tmp52
              local.get $tmp4
              i32.const 8
              i32.add
              i32.load
              local.set $al
              local.get $al
              local.set $tmp62
              local.get $tmp4
              i32.const 12
              i32.add
              i32.load
              local.set $am
              local.get $am
              local.set $tmp71
              local.get $tmp4
              i32.const 0
              i32.add
              i32.load
              local.set $an
              local.get $an
              local.set $tmp8
              block $d
                block $ax
                  block $ap
                    block $Match_tmp52
                      local.get $tmp52
                      i32.const 1
                      i32.eq
                      i32.const 1
                      i32.mul
                      local.get $tmp52
                      i32.const 0
                      i32.eq
                      i32.const 2
                      i32.mul
                      i32.add
                      br_table 2 1 0
                    end
                    local.get $tmp62
                    i32.const 0
                    i32.eq
                    if
                      local.get $tmp71
                      i32.const 0
                      i32.eq
                      if
                        local.get $tmp8
                        i32.const 0
                        i32.eq
                        if
                          i32.const 4
                          call $log
                          i32.const 0
                          local.set $app_result
                          local.get $app_result
                          local.set $aw
                          local.get $aw
                          local.set $b
                        else
                        end
                      else
                      end
                    else
                    end
                    br $d
                  end
                  local.get $tmp62
                  i32.const 2
                  i32.eq
                  if
                    local.get $tmp71
                    i32.const 3
                    i32.eq
                    if
                      local.get $tmp8
                      i32.const 4
                      i32.eq
                      if
                        i32.const 5
                        call $log
                        i32.const 0
                        local.set $app_result
                        local.get $app_result
                        local.set $bg
                        local.get $bg
                        local.set $b
                      else
                      end
                    else
                    end
                  else
                  end
                  br $d
                end
                i32.const 6
                call $log
                i32.const 0
                local.set $app_result
                local.get $app_result
                local.set $e
                local.get $e
                local.set $b
              end
              br $d
            end
            local.get $tmp4
            i32.const 4
            i32.add
            i32.load
            local.set $s
            local.get $s
            local.set $tmp51
            local.get $tmp4
            i32.const 8
            i32.add
            i32.load
            local.set $t
            local.get $t
            local.set $tmp61
            local.get $tmp4
            i32.const 12
            i32.add
            i32.load
            local.set $u
            local.get $u
            local.set $tmp7
            block $d
              block $ad
                block $w
                  block $Match_tmp51
                    local.get $tmp51
                    i32.const 1
                    i32.eq
                    i32.const 1
                    i32.mul
                    local.get $tmp51
                    i32.const 0
                    i32.eq
                    i32.const 2
                    i32.mul
                    i32.add
                    br_table 2 1 0
                  end
                  local.get $tmp61
                  i32.const 0
                  i32.eq
                  if
                    local.get $tmp7
                    i32.const 0
                    i32.eq
                    if
                      i32.const 2
                      call $log
                      i32.const 0
                      local.set $app_result
                      local.get $app_result
                      local.set $ac
                      local.get $ac
                      local.set $b
                    else
                    end
                  else
                  end
                  br $d
                end
                local.get $tmp61
                i32.const 2
                i32.eq
                if
                  local.get $tmp7
                  i32.const 3
                  i32.eq
                  if
                    i32.const 3
                    call $log
                    i32.const 0
                    local.set $app_result
                    local.get $app_result
                    local.set $ai
                    local.get $ai
                    local.set $b
                  else
                  end
                else
                end
                br $d
              end
              i32.const 6
              call $log
              i32.const 0
              local.set $app_result
              local.get $app_result
              local.set $e
              local.get $e
              local.set $b
            end
            br $d
          end
          i32.const 6
          call $log
          i32.const 0
          local.set $app_result
          local.get $app_result
          local.set $e
          local.get $e
          local.set $b
        end
      end
      i32.const 6
      call $log
      i32.const 0
      local.set $app_result
      local.get $app_result
      local.set $e
      local.get $e
      local.set $b
    end
    local.get $b
    local.set $result1
  )
  (start $main)
)