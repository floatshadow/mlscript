(module 
  (import "system" "mem" (memory 100))
  (func $log (import "system" "log") (param i32 i32))
  (global (mut i32) i32.const 0) 
  (export "main" (func $main))
  (func $main (local $av1 i32)(local $tmp6 i32)(local $ay1 i32)(local $ah i32)(local $bn1 i32)(local $tmp61 i32)(local $bw1 i32)(local $e i32)(local $bf1 i32)(local $bs i32)(local $cj1 i32)(local $cr1 i32)(local $df1 i32)(local $ad1 i32)(local $ce1 i32)(local $dk1 i32)(local $tmp71 i32)(local $y i32)(local $br i32)(local $cw1 i32)(local $u1 i32)(local $t i32)(local $f1 i32)(local $bq i32)(local $cd i32)(local $a i32)(local $bj i32)(local $z1 i32)(local $ap i32)(local $cn i32)(local $m1 i32)(local $tmp7 i32)(local $af i32)(local $de i32)(local $bm i32)(local $i i32)(local $cv i32)(local $bk1 i32)(local $q i32)(local $ac i32)(local $b i32)(local $cq i32)(local $r1 i32)(local $l i32)(local $bp i32)(local $ag i32)(local $al1 i32)(local $dj i32)(local $tmp52 i32)(local $tmp62 i32)(local $tmp4 i32)(local $aq1 i32)(local $dm i32)(local $ak i32)(local $ax i32)(local $ci i32)(local $au i32)(local $bv i32)(local $be i32)(local $h i32)(local $tmp8 i32)(local $co1 i32)(local $tmp51 i32)(local $result1 i32)(local $dn1 i32)(local $tmp5 i32)
    block $entry
      global.get 0
      local.set $a
      global.get 0
      i32.const 0
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
        block $g
          block $ae
            block $bo
              block $Match_tmp4
                local.get $tmp4
                i32.load
                i32.const 2
                i32.eq
                i32.const 1
                i32.mul
                local.get $tmp4
                i32.load
                i32.const 0
                i32.eq
                i32.const 2
                i32.mul
                i32.add
                local.get $tmp4
                i32.load
                i32.const 1
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
              local.set $bp
              local.get $bp
              local.set $tmp52
              local.get $tmp4
              i32.const 8
              i32.add
              i32.load
              local.set $bq
              local.get $bq
              local.set $tmp62
              local.get $tmp4
              i32.const 12
              i32.add
              i32.load
              local.set $br
              local.get $br
              local.set $tmp71
              local.get $tmp4
              i32.const 0
              i32.add
              i32.load
              local.set $bs
              local.get $bs
              local.set $tmp8
              block $bu
                block $bx
                  block $cs
                    block $Match_tmp52
                      local.get $tmp52
                      i32.const 0
                      i32.eq
                      i32.const 1
                      i32.mul
                      local.get $tmp52
                      i32.const 1
                      i32.eq
                      i32.const 2
                      i32.mul
                      i32.add
                      br_table 2 1 0
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
                          i32.const 2
                          call $log
                          i32.const 0
                          local.set $dn1
                          local.get $dn1
                          local.set $dm
                          local.get $dm
                          local.set $b
                        else
                          i32.const 6
                          i32.const 2
                          call $log
                          i32.const 0
                          local.set $dk1
                          local.get $dk1
                          local.set $dj
                          local.get $dj
                          local.set $b
                        end
                      else
                        i32.const 6
                        i32.const 2
                        call $log
                        i32.const 0
                        local.set $df1
                        local.get $df1
                        local.set $de
                        local.get $de
                        local.set $b
                      end
                    else
                      i32.const 6
                      i32.const 2
                      call $log
                      i32.const 0
                      local.set $cw1
                      local.get $cw1
                      local.set $cv
                      local.get $cv
                      local.set $b
                    end
                    br $bu
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
                        i32.const 2
                        call $log
                        i32.const 0
                        local.set $cr1
                        local.get $cr1
                        local.set $cq
                        local.get $cq
                        local.set $b
                      else
                        i32.const 6
                        i32.const 2
                        call $log
                        i32.const 0
                        local.set $co1
                        local.get $co1
                        local.set $cn
                        local.get $cn
                        local.set $b
                      end
                    else
                      i32.const 6
                      i32.const 2
                      call $log
                      i32.const 0
                      local.set $cj1
                      local.get $cj1
                      local.set $ci
                      local.get $ci
                      local.set $b
                    end
                  else
                    i32.const 6
                    i32.const 2
                    call $log
                    i32.const 0
                    local.set $ce1
                    local.get $ce1
                    local.set $cd
                    local.get $cd
                    local.set $b
                  end
                  br $bu
                end
                i32.const 6
                i32.const 2
                call $log
                i32.const 0
                local.set $bw1
                local.get $bw1
                local.set $bv
                local.get $bv
                local.set $b
              end
              br $d
            end
            local.get $tmp4
            i32.const 4
            i32.add
            i32.load
            local.set $af
            local.get $af
            local.set $tmp51
            local.get $tmp4
            i32.const 8
            i32.add
            i32.load
            local.set $ag
            local.get $ag
            local.set $tmp61
            local.get $tmp4
            i32.const 12
            i32.add
            i32.load
            local.set $ah
            local.get $ah
            local.set $tmp7
            block $aj
              block $am
                block $az
                  block $Match_tmp51
                    local.get $tmp51
                    i32.const 0
                    i32.eq
                    i32.const 1
                    i32.mul
                    local.get $tmp51
                    i32.const 1
                    i32.eq
                    i32.const 2
                    i32.mul
                    i32.add
                    br_table 2 1 0
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
                      i32.const 2
                      call $log
                      i32.const 0
                      local.set $bn1
                      local.get $bn1
                      local.set $bm
                      local.get $bm
                      local.set $b
                    else
                      i32.const 6
                      i32.const 2
                      call $log
                      i32.const 0
                      local.set $bk1
                      local.get $bk1
                      local.set $bj
                      local.get $bj
                      local.set $b
                    end
                  else
                    i32.const 6
                    i32.const 2
                    call $log
                    i32.const 0
                    local.set $bf1
                    local.get $bf1
                    local.set $be
                    local.get $be
                    local.set $b
                  end
                  br $aj
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
                    i32.const 2
                    call $log
                    i32.const 0
                    local.set $ay1
                    local.get $ay1
                    local.set $ax
                    local.get $ax
                    local.set $b
                  else
                    i32.const 6
                    i32.const 2
                    call $log
                    i32.const 0
                    local.set $av1
                    local.get $av1
                    local.set $au
                    local.get $au
                    local.set $b
                  end
                else
                  i32.const 6
                  i32.const 2
                  call $log
                  i32.const 0
                  local.set $aq1
                  local.get $aq1
                  local.set $ap
                  local.get $ap
                  local.set $b
                end
                br $aj
              end
              i32.const 6
              i32.const 2
              call $log
              i32.const 0
              local.set $al1
              local.get $al1
              local.set $ak
              local.get $ak
              local.set $b
            end
            br $d
          end
          local.get $tmp4
          i32.const 4
          i32.add
          i32.load
          local.set $h
          local.get $h
          local.set $tmp5
          local.get $tmp4
          i32.const 8
          i32.add
          i32.load
          local.set $i
          local.get $i
          local.set $tmp6
          block $k
            block $n
              block $v
                block $Match_tmp5
                  local.get $tmp5
                  i32.const 0
                  i32.eq
                  i32.const 1
                  i32.mul
                  local.get $tmp5
                  i32.const 1
                  i32.eq
                  i32.const 2
                  i32.mul
                  i32.add
                  br_table 2 1 0
                end
                local.get $tmp6
                i32.const 2
                i32.eq
                if
                  i32.const 1
                  i32.const 2
                  call $log
                  i32.const 0
                  local.set $ad1
                  local.get $ad1
                  local.set $ac
                  local.get $ac
                  local.set $b
                else
                  i32.const 6
                  i32.const 2
                  call $log
                  i32.const 0
                  local.set $z1
                  local.get $z1
                  local.set $y
                  local.get $y
                  local.set $b
                end
                br $k
              end
              local.get $tmp6
              i32.const 0
              i32.eq
              if
                i32.const 0
                i32.const 2
                call $log
                i32.const 0
                local.set $u1
                local.get $u1
                local.set $t
                local.get $t
                local.set $b
              else
                i32.const 6
                i32.const 2
                call $log
                i32.const 0
                local.set $r1
                local.get $r1
                local.set $q
                local.get $q
                local.set $b
              end
              br $k
            end
            i32.const 6
            i32.const 2
            call $log
            i32.const 0
            local.set $m1
            local.get $m1
            local.set $l
            local.get $l
            local.set $b
          end
          br $d
        end
        i32.const 6
        i32.const 2
        call $log
        i32.const 0
        local.set $f1
        local.get $f1
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