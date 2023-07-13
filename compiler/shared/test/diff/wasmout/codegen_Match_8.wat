(module 
  (func $log (import "console" "log") (param i32))
  (memory $memory 1)
  (global (mut i32) i32.const 0) 
  (export "main" (func $main))
  (func $main (local $e i32)(local $ae i32)(local $am i32)(local $bw i32)(local $ao i32)(local $g i32)(local $tmp52 i32)(local $tmp6 i32)(local $bf i32)(local $tmp71 i32)(local $app_result i32)(local $y i32)(local $bq i32)(local $u i32)(local $cd i32)(local $bc i32)(local $a i32)(local $ab i32)(local $as i32)(local $bi i32)(local $cn i32)(local $aw i32)(local $tmp7 i32)(local $bm i32)(local $ch i32)(local $q i32)(local $bu i32)(local $bd i32)(local $b i32)(local $tmp62 i32)(local $tmp4 i32)(local $ai i32)(local $be i32)(local $h i32)(local $w i32)(local $tmp8 i32)(local $tmp51 i32)(local $result1 i32)(local $k i32)(local $tmp61 i32)(local $cl i32)(local $ay i32)(local $tmp5 i32)(local $o i32)(local $z i32)
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
      i32.const 8
      i32.add
      i32.const 2
      i32.store
      local.get $a
      i32.const 12
      i32.add
      i32.const 3
      i32.store
      local.get $a
      local.set $tmp4
      block $d
        block $x
          block $az
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
              block $j
                block $r
                  block $l
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
                      local.set $q
                      local.get $q
                      local.set $b
                    else
                      i32.const 6
                      call $log
                      i32.const 0
                      local.set $app_result
                      local.get $app_result
                      local.set $o
                      local.get $o
                      local.set $b
                    end
                    br $j
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
                    local.set $w
                    local.get $w
                    local.set $b
                  else
                    i32.const 6
                    call $log
                    i32.const 0
                    local.set $app_result
                    local.get $app_result
                    local.set $u
                    local.get $u
                    local.set $b
                  end
                  br $j
                end
                i32.const 6
                call $log
                i32.const 0
                local.set $app_result
                local.get $app_result
                local.set $k
                local.get $k
                local.set $b
              end
              br $d
            end
            local.get $tmp4
            i32.const 4
            i32.add
            i32.load
            local.set $bc
            local.get $bc
            local.set $tmp52
            local.get $tmp4
            i32.const 8
            i32.add
            i32.load
            local.set $bd
            local.get $bd
            local.set $tmp62
            local.get $tmp4
            i32.const 12
            i32.add
            i32.load
            local.set $be
            local.get $be
            local.set $tmp71
            local.get $tmp4
            i32.const 0
            i32.add
            i32.load
            local.set $bf
            local.get $bf
            local.set $tmp8
            block $bh
              block $bx
                block $bj
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
                        local.set $bw
                        local.get $bw
                        local.set $b
                      else
                        i32.const 6
                        call $log
                        i32.const 0
                        local.set $app_result
                        local.get $app_result
                        local.set $bu
                        local.get $bu
                        local.set $b
                      end
                    else
                      i32.const 6
                      call $log
                      i32.const 0
                      local.set $app_result
                      local.get $app_result
                      local.set $bq
                      local.get $bq
                      local.set $b
                    end
                  else
                    i32.const 6
                    call $log
                    i32.const 0
                    local.set $app_result
                    local.get $app_result
                    local.set $bm
                    local.get $bm
                    local.set $b
                  end
                  br $bh
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
                      local.set $cn
                      local.get $cn
                      local.set $b
                    else
                      i32.const 6
                      call $log
                      i32.const 0
                      local.set $app_result
                      local.get $app_result
                      local.set $cl
                      local.get $cl
                      local.set $b
                    end
                  else
                    i32.const 6
                    call $log
                    i32.const 0
                    local.set $app_result
                    local.get $app_result
                    local.set $ch
                    local.get $ch
                    local.set $b
                  end
                else
                  i32.const 6
                  call $log
                  i32.const 0
                  local.set $app_result
                  local.get $app_result
                  local.set $cd
                  local.get $cd
                  local.set $b
                end
                br $bh
              end
              i32.const 6
              call $log
              i32.const 0
              local.set $app_result
              local.get $app_result
              local.set $bi
              local.get $bi
              local.set $b
            end
            br $d
          end
          local.get $tmp4
          i32.const 4
          i32.add
          i32.load
          local.set $y
          local.get $y
          local.set $tmp51
          local.get $tmp4
          i32.const 8
          i32.add
          i32.load
          local.set $z
          local.get $z
          local.set $tmp61
          local.get $tmp4
          i32.const 12
          i32.add
          i32.load
          local.set $ab
          local.get $ab
          local.set $tmp7
          block $ad
            block $ap
              block $af
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
                    local.set $ao
                    local.get $ao
                    local.set $b
                  else
                    i32.const 6
                    call $log
                    i32.const 0
                    local.set $app_result
                    local.get $app_result
                    local.set $am
                    local.get $am
                    local.set $b
                  end
                else
                  i32.const 6
                  call $log
                  i32.const 0
                  local.set $app_result
                  local.get $app_result
                  local.set $ai
                  local.get $ai
                  local.set $b
                end
                br $ad
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
                  local.set $ay
                  local.get $ay
                  local.set $b
                else
                  i32.const 6
                  call $log
                  i32.const 0
                  local.set $app_result
                  local.get $app_result
                  local.set $aw
                  local.get $aw
                  local.set $b
                end
              else
                i32.const 6
                call $log
                i32.const 0
                local.set $app_result
                local.get $app_result
                local.set $as
                local.get $as
                local.set $b
              end
              br $ad
            end
            i32.const 6
            call $log
            i32.const 0
            local.set $app_result
            local.get $app_result
            local.set $ae
            local.get $ae
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
    local.get $b
    local.set $result1
  )
  (start $main)
)